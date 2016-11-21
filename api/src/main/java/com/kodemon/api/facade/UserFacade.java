package com.kodemon.api.facade;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;

import java.util.Set;

/**
 * Created by mseleng on 11/19/16.
 */
public interface UserFacade {

    /**
     * Creates new user in the database.
     * Checks whether an user with that username exists, etc.
     *
     * @param user User details
     * @param pwdHash hash of user's password
     */
    boolean register(UserDTO user, String pwdHash);

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
    UserDTO findUserByUserName(String userName);

    /**
     *
     * @return Set of use
     */
    Set<UserDTO> findAllUsers();
}
