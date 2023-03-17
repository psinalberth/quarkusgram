package com.github.psinalberth.user.application.service;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.out.AddFriendGateway;
import com.github.psinalberth.user.application.port.out.RequestFriendshipGateway;
import com.github.psinalberth.user.application.service.friendship.FriendshipRule;
import com.github.psinalberth.user.application.service.friendship.PrivateAccountFriendshipRule;
import com.github.psinalberth.user.application.service.friendship.PublicAccountFriendshipRule;
import com.github.psinalberth.user.domain.event.UserBecameFriends;
import com.github.psinalberth.user.domain.event.FriendshipRequested;
import com.github.psinalberth.user.domain.exception.UserNotFoundException;
import com.github.psinalberth.user.fixtures.UserFixtures;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipMakerTest {

    @Mock
    private Events events;

    @Mock
    private AddFriendGateway addFriendGateway;

    @Mock
    private RequestFriendshipGateway requestFriendshipGateway;

    @Spy
    private Set<FriendshipRule> friendshipRules = new HashSet<>();

    private PrivateAccountFriendshipRule privateAccountFriendshipRule;

    private PublicAccountFriendshipRule publicAccountFriendshipRule;

    @InjectMocks
    private FriendshipMaker friendshipMaker;

    @BeforeEach
    void before() {

        this.publicAccountFriendshipRule = Mockito.spy(new PublicAccountFriendshipRule(addFriendGateway, events));
        this.privateAccountFriendshipRule = Mockito.spy(new PrivateAccountFriendshipRule(requestFriendshipGateway, events));

        this.friendshipRules.add(privateAccountFriendshipRule);
        this.friendshipRules.add(publicAccountFriendshipRule);
    }

    @Nested
    class SuccessCases {

        @Test
        void shouldBecomeFriendsSuccessfully_WhenFriendHasPublicProfile() {

            var user = UserFixtures.user();
            var friend = UserFixtures.user();
            var friendship = Friendship.of(user, friend);

            when(addFriendGateway.addFriend(user, friend))
                    .thenReturn(Uni.createFrom().item(user));

            var sub = friendshipMaker.make(friendship)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertItem(user)
                    .assertCompleted();

            verify(addFriendGateway, times(1))
                    .addFriend(user, friend);

            verify(publicAccountFriendshipRule, times(1))
                    .make(friendship);

            verify(events, times(1))
                    .publish(any(UserBecameFriends.class));

            verify(privateAccountFriendshipRule, never())
                    .make(friendship);
        }

        @Test
        void shouldRequestFriendship_WhenFriendHasPrivateProfile() {

            var user = UserFixtures.user();
            var friend = UserFixtures.privateUser();
            var friendship = Friendship.of(user, friend);

            when(requestFriendshipGateway.requestFriendship(user, friend))
                    .thenReturn(Uni.createFrom().item(user));

            var sub = friendshipMaker.make(friendship)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertItem(user)
                    .assertCompleted();

            verify(requestFriendshipGateway, times(1))
                    .requestFriendship(user, friend);

            verify(events, times(1))
                    .publish(any(FriendshipRequested.class));

            verify(publicAccountFriendshipRule, never())
                    .make(friendship);

            verify(privateAccountFriendshipRule, times(1))
                    .make(friendship);
        }
    }

    @Nested
    class ErrorCases {

        @Test
        void shouldFail_WhenUserNotExists() {

            var friend = UserFixtures.user();
            var friendship = Friendship.of(null, friend);

            var sub = friendshipMaker.make(friendship)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertFailedWith(UserNotFoundException.class, "We couldn't find you on database. Wait, what?")
                    .assertTerminated();

            verify(requestFriendshipGateway, never())
                    .requestFriendship(any(), any());
        }

        @Test
        void shouldFail_WhenFriendNotExists() {

            var user = UserFixtures.user();
            var friendship = Friendship.of(user, null);

            var sub = friendshipMaker.make(friendship)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create());

            sub.assertSubscribed()
                    .assertFailedWith(UserNotFoundException.class, "Friend not found with given id.")
                    .assertTerminated();

            verify(requestFriendshipGateway, never())
                    .requestFriendship(any(), any());
        }
    }
}