package com.kodemon.persistence.dao;

import com.kodemon.persistence.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Data Access Object for the Trainer entity.
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Repository
public interface TrainerDao extends JpaRepository<Trainer, Long> {

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
     * @param lastName  {@link Trainer}'s lastName to search by
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
}
