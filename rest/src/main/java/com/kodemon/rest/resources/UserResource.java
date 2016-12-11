package com.kodemon.rest.resources;

import com.kodemon.api.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
