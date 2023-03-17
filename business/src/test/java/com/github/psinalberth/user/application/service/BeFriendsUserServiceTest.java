package com.github.psinalberth.user.application.service;

import com.github.psinalberth.user.application.port.in.BeFriends;
import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.domain.exception.UserNotFoundException;
import com.github.psinalberth.user.fixtures.UserFixtures;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeFriendsUserServiceTest {

    @Mock
    private LoadUserPort loadUserPort;
    @Spy
    private FriendshipMaker friendshipMaker = new FriendshipMaker(new HashSet<>());
    @InjectMocks
    private BeFriendsUserService beFriendsUserService;

    @Nested
    class SuccessCases {

        @Test
        void shouldBeFriendsSuccessfully() {

            var user = UserFixtures.user();
            var friend = UserFixtures.user();
            var beFriends = new BeFriends();

            beFriends.setUserId(user.getId());
            beFriends.setFriendId(friend.getId());

            when(loadUserPort.loadById(beFriends.getUserId()))
                    .thenReturn(Uni.createFrom().item(user));

            when(loadUserPort.loadById(beFriends.getFriendId()))
                    .thenReturn(Uni.createFrom().item(friend));

            when(friendshipMaker.make(Friendship.of(user, friend)))
                    .thenReturn(Uni.createFrom().item(user));

            var sub = beFriendsUserService.beFriendsWith(beFriends)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertItem(user)
                    .assertCompleted();

            verify(loadUserPort, times(1))
                    .loadById(user.getId());

            verify(loadUserPort, times(1))
                    .loadById(friend.getId());

            verify(friendshipMaker, times(1))
                    .make(Friendship.of(user, friend));
        }
    }

    @Nested
    class ErrorCases {

        @Test
        void shouldFail_WhenCurrentUserNotExists() {

            var user = UserFixtures.user();
            var friend = UserFixtures.privateUser();
            var beFriends = new BeFriends();

            beFriends.setUserId(user.getId());
            beFriends.setFriendId(friend.getId());

            when(loadUserPort.loadById(beFriends.getUserId()))
                    .thenReturn(Uni.createFrom().nullItem());

            when(loadUserPort.loadById(beFriends.getFriendId()))
                    .thenReturn(Uni.createFrom().nullItem());

            beFriends.setUserId(user.getId());
            beFriends.setFriendId(friend.getId());

            var sub = beFriendsUserService.beFriendsWith(beFriends)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertFailedWith(UserNotFoundException.class)
                    .assertFailed();

            verify(loadUserPort, times(1))
                    .loadById(user.getId());

            verify(loadUserPort, times(1))
                    .loadById(friend.getId());

            verify(friendshipMaker, times(1))
                    .make(Friendship.of(null, null));
        }

        @Test
        void shouldFail_WhenFriendNotExists() {

            var user = UserFixtures.user();
            var friend = UserFixtures.privateUser();
            var beFriends = new BeFriends();

            beFriends.setUserId(user.getId());
            beFriends.setFriendId(friend.getId());

            when(loadUserPort.loadById(beFriends.getUserId()))
                    .thenReturn(Uni.createFrom().item(user));

            when(loadUserPort.loadById(beFriends.getFriendId()))
                    .thenReturn(Uni.createFrom().nullItem());

            beFriends.setUserId(user.getId());
            beFriends.setFriendId(friend.getId());

            var sub = beFriendsUserService.beFriendsWith(beFriends)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertFailedWith(UserNotFoundException.class, "Friend not found with given id.")
                    .assertTerminated();

            verify(loadUserPort, times(1))
                    .loadById(user.getId());

            verify(loadUserPort, times(1))
                    .loadById(friend.getId());

            verify(friendshipMaker, times(1))
                    .make(Friendship.of(user, null));
        }
    }
}