package com.github.psinalberth.user.infrastructure;

import com.github.psinalberth.common.application.Events;
import com.github.psinalberth.user.application.port.out.AddFriendGateway;
import com.github.psinalberth.user.application.port.out.RequestFriendshipGateway;
import com.github.psinalberth.user.application.service.FriendshipMaker;
import com.github.psinalberth.user.application.service.friendship.FriendshipRule;
import com.github.psinalberth.user.application.service.friendship.PrivateAccountFriendshipRule;
import com.github.psinalberth.user.application.service.friendship.PublicAccountFriendshipRule;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.util.stream.Collectors;

public class FriendshipRulesConfig {

    @Singleton
    public FriendshipMaker friendshipMaker(Instance<FriendshipRule> friendshipRules) {
        return new FriendshipMaker(friendshipRules.stream().collect(Collectors.toSet()));
    }

    @Produces
    public PublicAccountFriendshipRule publicAccountFriendshipRule(AddFriendGateway addFriendGateway, Events events) {
        return new PublicAccountFriendshipRule(addFriendGateway, events);
    }

    @Produces
    public PrivateAccountFriendshipRule privateAccountFriendshipRule(RequestFriendshipGateway requestFriendshipGateway,
                                                                     Events events) {
        return new PrivateAccountFriendshipRule(requestFriendshipGateway, events);
    }
}
