package com.kodemon.rest.resources;

import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.dto.UserRegisterDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.util.Constants;
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
import java.util.Date;

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
            LOG.debug("Listing all users.", userName);
            return Response.ok(userFacade.findAllUsers()).build();
        } else {
            LOG.debug("Listing users with username={}.", userName);
            return Response.ok(userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(userName)).build();
        }
    }

    /**
     * Creates an user
     *
     * @param dto object containing user details and password
     * @return 201 if created, 400 if some data was missing, 409 if creation was unsuccessful
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserRegisterDTO dto) {
        String userName = dto.getUserName();
        if (userName == null) {
            badRequest("userName", "was null");
        } else if (userName.length() < Constants.MIN_USERNAME_LENGTH) {
            badRequest("userName", "was shorter than " + Constants.MIN_USERNAME_LENGTH + " characters");
        }

        String firstName = dto.getFirstName();
        if (firstName == null || firstName.isEmpty()) {
            badRequest("firstName", "was null or empty");
        }

        String lastName = dto.getLastName();
        if (lastName == null || lastName.isEmpty()) {
            badRequest("lastName", "was null or empty");
        }

        String password = dto.getPassword();
        if (password == null) {
            badRequest("password", "was null");
        } else if (password.length() < Constants.MIN_PASSWORD_LENGTH) {
            badRequest("password", "was shorter than " + Constants.MIN_PASSWORD_LENGTH + " characters");
        }

        PokemonName pokemon = dto.getPokemon();
        if (pokemon == null) {
            badRequest("pokemon", "was null");
        } else if (!PokemonName.getInitialPokemon().contains(pokemon)) {
            badRequest("pokemon", "was invalid. Must be one of " + PokemonName.getInitialPokemon());
        }

        Date dateOfBirth = dto.getDateOfBirth();
        if (dateOfBirth == null) {
            badRequest("dateOfBirth", "was null");
        } else if (System.currentTimeMillis() <= dateOfBirth.getTime()) {
            badRequest("dateOfBirth", "was >= current time");
        }

        try {
            LOG.debug("Registering user with username {}", userName);
            UserDTO created = userFacade.register(dto);
            return Response.created(URI.create(USERS_URI_PREFIX + created.getId())).build();
        } catch (DataAccessException e) {
            LOG.error("Error while registering user.", e);
            throw new RestDataAccessException(e);
        }
    }

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

    private void badRequest(String field, String error) {
        LOG.error("Error while registering user: {}", composeErrorMessage(field, error));
//        throw new WebApplicationException(composeErrorMessage(field, error), Response.Status.BAD_REQUEST);
        throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(composeErrorMessage(field, error)).build());
    }

    private String composeErrorMessage(String field, String error) {
        return "Invalid field: " + field + "; Error: " + error + ".";
    }
}
