package com.kodemon.rest.resources;

import com.kodemon.api.dto.UserAndPasswordDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.rest.exception.RestDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * RESTful resource representing Users
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Component
@Singleton
@Path("users")
public class UserResource {
    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);
    private static final String USERS_URI_PREFIX = "/pa165/rest/users/";

    private UserFacade userFacade;

    @Inject
    public UserResource(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * Returns User with given id
     *
     * @param id id of the user
     * @return 200 iff found, 404 otherwise
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userWithId(@PathParam("id") Long id) {
        UserDTO found = userFacade.findOneUser(id);
        if (found != null) {
            return Response.ok(found).build();
        } else {
            LOG.error("User with id {} not found.", id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    /**
     * Returns the list of users
     *
     * @param userName if not empty, returns the list of users with username similar to the given one, otherwise all users
     * @return 200 when found
     */
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

    /**
     * Creates an user
     *
     * @param dto object containing user details and password
     * @return 200 if created, 400 if some data was missing, 409 if creation was unsuccessful
     */
    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserAndPasswordDTO dto) {
        UserDTO user = dto.getUser();
        String password = dto.getPassword();
        if (user == null || password == null || password.isEmpty()) {
            LOG.error("Data for registration was incorrect.");
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        try {
            LOG.debug("Registering user with username {}", user.getUserName());
            UserDTO created = userFacade.register(user);
            return Response.created(URI.create(USERS_URI_PREFIX + created.getId())).build();
        } catch (DataAccessException e) {
            LOG.error("Error while registering user.", e);
            throw new RestDataAccessException(e);
        }
    }*/
    // TODO remove UserAndPasswordDTO, which is replaced by UserRegisterDTO

    /**
     * Edits the user with the given id
     *
     * @param id  id of the user to be edited
     * @param dto dto containing new data
     * @return 201 when updated or 409
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUser(@PathParam("id") Long id, UserDTO dto) {
        try {
            UserDTO updated = userFacade.update(id, dto);
            return Response.created(URI.create(USERS_URI_PREFIX + updated.getId())).build();
        } catch (DataAccessException e) {
            LOG.error("Error while updating user.", e);
            throw new RestDataAccessException(e);
        }
    }
}
