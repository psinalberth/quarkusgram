package com.github.psinalberth.user.application.service;

import com.github.psinalberth.user.application.port.out.LoadUserPort;
import com.github.psinalberth.user.application.service.friendship.LoadFriendsPort;
import com.github.psinalberth.user.fixtures.FriendFixtures;
import com.github.psinalberth.user.fixtures.UserFixtures;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        }
    }

    @Nested
    class FailureCases {

    }
}