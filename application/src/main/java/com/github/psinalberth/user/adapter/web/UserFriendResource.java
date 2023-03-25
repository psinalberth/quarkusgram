package com.github.psinalberth.user.adapter.web;

import com.github.psinalberth.user.application.port.in.BeFriends;
import com.github.psinalberth.user.application.port.in.BeFriendsWithUseCase;
import com.github.psinalberth.user.application.port.in.Friendship;
import com.github.psinalberth.user.application.port.in.GetFriends;
import com.github.psinalberth.user.application.port.in.GetFriendsUseCase;
import com.github.psinalberth.user.domain.model.Friend;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/friends")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserFriendResource {

    private final BeFriendsWithUseCase beFriendsWithUseCase;
    private final GetFriendsUseCase getFriendsUseCase;

    @POST
    public Uni<Response> addFriend(@PathParam("userId") String userId, BeFriends beFriends) {
        beFriends.setUserId(userId);
        return beFriendsWithUseCase.beFriendsWith(beFriends)
                .onItem()
                .ifNotNull()
                .transform(result -> Response.accepted(result).build());
    }

    @GET
    public Multi<Friend> findFriends(@PathParam("userId") String userId) {
        return getFriendsUseCase.getFriends(new GetFriends(userId));
    }
}
