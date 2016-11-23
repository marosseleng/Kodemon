package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.interfaces.TrainerService;
import com.kodemon.service.util.PasswordStorage;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the {@link TrainerService}
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    @Inject
    private TrainerDao trainerDao;

    @Override
    public boolean register(Trainer trainer, String password) {
        try {
            trainer.setPwdHash(PasswordStorage.createHash(password));
        } catch (PasswordStorage.CannotPerformOperationException e) {
            return false;
        }
        try {
            trainerDao.save(trainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean login(String userName, String password) {
        List<Trainer> matchedTrainers = trainerDao.findByUserName(userName);
        if (matchedTrainers.size() != 1) {
            return false;
        }
        try {
            return PasswordStorage.verifyPassword(password, matchedTrainers.get(0).getPwdHash());
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
            return false;
        }
    }

    @Override
    public void addBadge(Badge badge, Trainer trainer) {
        trainer.addBadge(badge);
        trainerDao.save(trainer);
    }

    @Override
    public void addPokemon(Pokemon pokemon, Trainer trainer) {
        trainer.addPokemon(pokemon);
        trainerDao.save(trainer);
    }

    @Override
    public void save(Trainer trainer) {
        trainerDao.save(trainer);
    }

    @Override
    public void delete(Trainer trainer) {
        trainerDao.delete(trainer);
    }

    @Override
    public List<Trainer> findByUserName(String userName) {
        return trainerDao.findByUserName(userName);
    }

    @Override
    public List<Trainer> findByUserNameLike(String userName) {
        return trainerDao.findByUserNameLike(userName);
    }

    @Override
    public List<Trainer> findByUserNameStartingWith(String prefix) {
        return trainerDao.findByUserNameStartingWith(prefix);
    }

    @Override
    public List<Trainer> findByUserNameEndingWith(String postfix) {
        return trainerDao.findByUserNameEndingWith(postfix);
    }

    @Override
    public List<Trainer> findByUserNameContaining(String string) {
        return trainerDao.findByUserNameContaining(string);
    }

    @Override
    public List<Trainer> findByFirstNameAndLastName(String firstName, String lastName) {
        return trainerDao.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Trainer> findByFirstName(String firstName) {
        return trainerDao.findByFirstName(firstName);
    }

    @Override
    public List<Trainer> findByLastName(String lastName) {
        return trainerDao.findByLastName(lastName);
    }

    @Override
    public List<Trainer> findByDateOfBirth(Date dob) {
        return trainerDao.findByDateOfBirth(dob);
    }

    @Override
    public List<Trainer> findByDateOfBirthBetween(Date from, Date till) {
        return trainerDao.findByDateOfBirthBetween(from, till);
    }

    @Override
    public List<Trainer> findAll() {
        return trainerDao.findAll();
    }
}
