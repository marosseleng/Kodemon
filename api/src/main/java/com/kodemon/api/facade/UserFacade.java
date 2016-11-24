package com.kodemon.api.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;

import java.util.List;

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
    boolean register(UserDTO user, String password);

    /**
     * Authenticates the user with given credentials
     *
     * @param auth credentials to authenticate with
     */
    boolean login(UserAuthDTO auth);

    /**
     *
     * @param userName
     * @return
     */
    List<UserDTO> findUserByUserName(String userName);

    /**
     *
     * @return Set of use
     */
    List<UserDTO> findAllUsers();
}
