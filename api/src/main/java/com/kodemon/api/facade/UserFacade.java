package com.kodemon.api.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.dto.UserRegisterDTO;

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
     *  @param user     User details
     *
     */
    UserDTO register(UserRegisterDTO user);

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

    /**
     * Updates the user with the given id
     *
     * @param id      id of the user to update
     * @param userDTO new data of the user
     * @return updated DTO
     */
    UserDTO update(Long id, UserDTO userDTO);

    /**
     * Deletes the user with the given id
     *
     * @param id id of the user we want to delete
     */
    void delete(Long id);

    /**
     * Block or unblock account of user
     *
     * @param id of user to be blocked or unblocked
     */
    void setBlocked(Long id, boolean blocked);
}
