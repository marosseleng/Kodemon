package com.kodemon.service.interfaces;

import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;

import java.util.Date;
import java.util.List;

/**
 * Service handling both, Trainer and User
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public interface TrainerService {

    /**
     * Creates new user in the database.
     * Checks whether an user with that username exists, etc.
     *
     * @param trainer trainer to be registered(created)
     * @param pwdHash hash of user's password
     * @return {@code true} iff the user was successfully created, {@code false} otherwise
     */
    boolean register(Trainer trainer, String pwdHash);

    /**
     * Authenticates the user with given credentials
     *
     * @param userName userName to authenticate with
     * @param pwdHash hash of user's password
     * @return {@code true} iff authentication was successfull, {@code false} otherwise
     */
    boolean login(String userName, String pwdHash);

    /**
     * Adds the given badge to the given trainer and updates the trainer table
     * <p/>
     * This method does <b>not</b> assign the trainer to the badge.
     *
     * @param badge badge to add
     * @param trainer trainer that receives the badge
     */
    void addBadge(Badge badge, Trainer trainer);

    /**
     * Adds the given Pokemon to the given trainer and updates the trainer table
     * <p/>
     * This method does not assign the trainer to the Pokemon
     *
     * @param pokemon Pokemon to be added to trainer
     * @param trainer trainer that receives a new Pokemon
     */
    void addPokemon(Pokemon pokemon, Trainer trainer);

    /**
     * Saves the given trainer to the database.
     *
     * @param trainer trainer to be saved
     */
    void save(Trainer trainer);

    /**
     * Deletes the given trainer from the database.
     *
     * @param trainer trainer to be deleted
     */
    void delete(Trainer trainer);

    /**
     * Returns a {@link List} of {@link Trainer}s with the given username.
     *
     * @param userName Username to search by
     * @return List of Trainers with the given userName
     */
    List<Trainer> findByUserName(String userName);

    /**
     * Returns a {@link List} of {@link Trainer}s with username similar to the given one.
     * <p/>
     * Note: this method generates the following query: {@code … where x.userName like ?1}
     *
     * @param userName Username to search by
     * @return {@link List} of {@link Trainer}s with an userName similar to the given one
     */
    List<Trainer> findByUserNameLike(String userName);

    /**
     * Returns a {@link List} of {@link Trainer}s with username starting with the given prefix.
     * <p/>
     * Note: the prefix <b>must</b> be appended by {@code %} as the method generates the following query:
     * {@code … where x.userName like ?1}
     *
     * @param prefix Username prefix to search by
     * @return {@link List} of {@link Trainer}s with userNames prefixed with the given prefix
     */
    List<Trainer> findByUserNameStartingWith(String prefix);

    /**
     * Returns a {@link List} of {@link Trainer}s with username ending with the given postfix.
     * <p/>
     * Note: the postfix <b>must</b> be prepended by {@code %} as the method generates the following query:
     * {@code … where x.userName like ?1}
     *
     * @param postfix Username postfix to search by
     * @return {@link List} of {@link Trainer}s with userNames postfixed with the given postfix
     */
    List<Trainer> findByUserNameEndingWith(String postfix);

    /**
     * Returns a {@link List} of {@link Trainer}s with username containing the given string.
     * <p/>
     * Note: a parameter <b>must</b> be wrapped in {@code %} as the method generates the following query:
     * {@code … where x.userName like ?1}
     *
     * @param string String that should be contained in {@link Trainer}'s username
     * @return {@link List} of {@link Trainer}s with userNames which contain the given string
     */
    List<Trainer> findByUserNameContaining(String string);

    /**
     * Returns a {@link List} of {@link Trainer}s with the given first and last name.
     *
     * @param firstName {@link Trainer}'s firstName to search by
     * @param lastName {@link Trainer}'s lastName to search by
     * @return {@link List} of {@link Trainer}s with the given first and last names
     */
    List<Trainer> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Returns a {@link List} of {@link Trainer}s with the given firstName.
     *
     * @param firstName {@link Trainer}'s firstName to search by
     * @return {@link List} of {@link Trainer}s with the given firstName
     */
    List<Trainer> findByFirstName(String firstName);

    /**
     * Returns a {@link List} of {@link Trainer}s with the given firstName.
     *
     * @param lastName {@link Trainer}'s lastName to search by
     * @return {@link List} of {@link Trainer}s with the given lastName
     */
    List<Trainer> findByLastName(String lastName);

    /**
     * Returns a {@link List} of {@link Trainer}s with the given day of birth.
     *
     * @param dob Day of birth to search by
     * @return {@link List} of {@link Trainer}s with the given day of birth
     */
    List<Trainer> findByDateOfBirth(Date dob);

    /**
     * Returns a {@link List} of {@link Trainer}s that were born between two given dates
     *
     * @param from day of birth greater or equal to this
     * @param till day of birth less than or equal to this
     * @return {@link List} of {@link Trainer}s that were born between two given dates
     */
    List<Trainer> findByDateOfBirthBetween(Date from, Date till);

    /**
     * Returns a {@link List} of all {@link Trainer}s stored in database
     *
     * @return {@link List} of {@link Trainer}s in the database
     */
    List<Trainer> findAll();
}