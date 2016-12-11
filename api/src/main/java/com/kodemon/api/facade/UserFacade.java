package com.kodemon.api.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;

import java.util.Collection;

/**
 * Facade providing access to the user administration
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface UserFacade {

    /**
     * Creates new user in the database.
     * Checks whether an user with that username exists, etc.
     *
     * @param user User details
     * @param password user's password
     */
    UserDTO register(UserDTO user, String password);

    /**
     * Authenticates the user with given credentials
     *
     * @param auth credentials to authenticate with
     */
    boolean login(UserAuthDTO auth);

    /**
     * Finds users with the given username (should be 1)
     *
     * @param userName username to find
     * @return Collection of Users
     */
    Collection<UserDTO> findUserByUserName(String userName);

    /**
     * Finds all users
     *
     * @return Collection of Users
     */
    Collection<UserDTO> findAllUsers();

    /**
     * Finds an user with the given id
     *
     * @param id id to look for
     * @return An user with the given id
     */
    UserDTO findOneUser(Long id);
}
