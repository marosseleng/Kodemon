package com.kodemon.rest.resources;

import com.kodemon.api.dto.UserAndPasswordDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by mseleng on 12/11/16.
 */
@Component
@Singleton
@Path("users")
public class UserResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private UserFacade userFacade;

    @Inject
    public UserResource(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userWithId(@PathParam("id") Long id) {
        return Response.ok(userFacade.findOneUser(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@DefaultValue("") @QueryParam("userName") String userName) {
        if (userName.isEmpty()) {
            LOG.debug("Listing users with username={}.", userName);
            return Response.ok(userFacade.findAllUsers()).build();
        } else {
            LOG.debug("Listing all users.", userName);
            return Response.ok(userFacade.findUserByUserName(userName)).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserAndPasswordDTO dto) {
        UserDTO user = dto.getUser();
        String password = dto.getPassword();
        LOG.debug("Registering user with username {}", user.getUserName());
        UserDTO created = userFacade.register(user, password);
        if (created != null) {
            return Response.created(URI.create("/users/" + created.getId())).build();
        } else {
            return Response.notModified().build();
        }
    }
}
