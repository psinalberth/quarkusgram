package com.github.psinalberth.user.application.service;

import com.github.psinalberth.user.application.port.in.GetFriends;
import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.service.friendship.LoadFriendsPort;
import com.github.psinalberth.user.domain.exception.UserNotFoundException;
import com.github.psinalberth.user.fixtures.FriendFixtures;
import com.github.psinalberth.user.fixtures.UserFixtures;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFriendsUserServiceTest {

    @Mock
    private LoadUserPort loadUserPort;
    @Mock
    private LoadFriendsPort loadFriendsPort;

    @InjectMocks
    private GetFriendsUserService getFriendsUserService;

    @Nested
    class SuccessCases {

        @Test
        void shouldGetFriendsSuccessfully() {

            var me = UserFixtures.user();
            var friend = FriendFixtures.someFriend();

            when(loadUserPort.loadById(me.getId()))
                    .thenReturn(Uni.createFrom().item(me));

            when(loadFriendsPort.loadFriends(me))
                    .thenReturn(Multi.createFrom().item(friend));

            var sub = getFriendsUserService.getFriends(new GetFriends(me.getId()))
                    .subscribe()
                    .withSubscriber(AssertSubscriber.create());

            sub.assertHasNotReceivedAnyItem()
                    .assertSubscribed()
                    .request(1)
                    .assertCompleted()
                    .assertItems(friend);

            verify(loadUserPort, times(1))
                    .loadById(me.getId());

            verify(loadFriendsPort,  times(1))
                    .loadFriends(me);
        }
    }

    @Nested
    class FailureCases {

        @Test
        void shouldFail_WhenUserNotExists() {

            var userId = UUID.randomUUID().toString();

            when(loadUserPort.loadById(userId))
                    .thenReturn(Uni.createFrom().nullItem());

            var sub = getFriendsUserService.getFriends(new GetFriends(userId))
                    .subscribe()
                    .withSubscriber(AssertSubscriber.create());

            sub.assertHasNotReceivedAnyItem()
                    .assertSubscribed()
                    .assertFailedWith(UserNotFoundException.class)
                    .assertTerminated();

            verify(loadUserPort, times(1))
                    .loadById(userId);

            verify(loadFriendsPort, never())
                    .loadFriends(any());
        }
    }
}