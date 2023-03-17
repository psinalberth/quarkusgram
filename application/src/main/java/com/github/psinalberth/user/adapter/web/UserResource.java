package com.github.psinalberth.user.adapter.web;

import com.github.psinalberth.user.application.port.in.NewUser;
import com.github.psinalberth.user.application.port.in.RegisterUserUseCase;
import com.github.psinalberth.user.domain.model.User;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {

    private final RegisterUserUseCase registerUserUseCase;

    @POST
    public Uni<Response> register(@Valid NewUser newUser, @Context UriInfo uriInfo) {
        return registerUserUseCase.register(newUser)
                .onItem()
                .transform(user -> createResponse(user, uriInfo));
    }

    private Response createResponse(User user, UriInfo uriInfo) {
        var uriLocation = uriInfo.getAbsolutePathBuilder().path(user.getId()).build();
        return Response.created(uriLocation)
                .entity(user)
                .build();
    }
}
