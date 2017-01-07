package com.kodemon.api.facade;

import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.dto.UserRegisterDTO;

import java.util.Collection;
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
     */
    UserDTO register(UserRegisterDTO user);

    /**
     * Authenticates the user with given credentials
     *
     * @param auth credentials to authenticate with
     */
    boolean login(UserAuthDTO auth);

    /**
     * Finds users with the given username
     *
     * @param userName username to find
     * @return Collection of Users
     */
    Collection<UserDTO> findUserByUserNameIgnoringCaseIncludeSubstrings(String userName);

    /**
     * Finds users with the given username. Matching exactly this username, neither ignoring case, nor finding substrings.
     *
     * @param userName username to find
     * @return Collection of Users (ideally of size()==1, when any)
     */
    Collection<UserDTO> findUserByUserNameExactMatch(String userName);

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

    /**
     * Set first six pokemons of trainer
     *
     * @param id of trainer, whose pokemons will be reordered
     * @param pokemonIndices list of six indices of pokemons, which will be set as first six
     */
    void chooseActivePokemons(Long id, List<Integer> pokemonIndices);
}
