package com.kodemon.service.implementations;

import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.service.interfaces.PokemonService;
import com.kodemon.service.interfaces.TrainerService;
import com.kodemon.service.util.PasswordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final int INITIAL_POKEMON_LEVEL = 3;
    private static final Logger LOG = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private TrainerDao trainerDao;
    private PokemonService pokemonService;

    @Inject
    public TrainerServiceImpl(TrainerDao trainerDao, PokemonService pokemonService) {
        this.trainerDao = trainerDao;
        this.pokemonService = pokemonService;
    }

    @Override
    public Trainer register(
            String userName,
            String firstName,
            String lastName,
            Date dayOfBirth,
            PokemonName pokemon,
            String password) throws PasswordStorage.CannotPerformOperationException {
        if (!PokemonName.getInitialPokemon().contains(pokemon)) {
            LOG.error("Invalid initial Pokemon! Was: {}. Expected one of: {}.", pokemon, PokemonName.getInitialPokemon());
            throw new IllegalArgumentException(
                    "Invalid initial Pokemon! Was: " +
                            pokemon +
                            ". Expected one of " +
                            PokemonName.getInitialPokemon());
        }
        Trainer trainer = new Trainer();
        trainer.setDateOfBirth(dayOfBirth);
        trainer.setLastName(lastName);
        trainer.setFirstName(firstName);
        trainer.setUserName(userName);
        trainer.setPwdHash(PasswordStorage.createHash(password));
        Trainer saved = trainerDao.save(trainer);

        Pokemon initial = pokemonService.createPokemonWithName(pokemon);
        initial.setLevel(INITIAL_POKEMON_LEVEL);
        pokemonService.assignTrainerToPokemon(saved, initial);

        addPokemon(initial, saved);

        return saved;
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
            LOG.error("Error while verifying the password.", e);
            return false;
        }
    }

    @Override
    public Trainer addBadge(Badge badge, Trainer trainer) {
        trainer.addBadge(badge);
        return trainerDao.save(trainer);
    }

    @Override
    public Trainer addPokemon(Pokemon pokemon, Trainer trainer) {
        trainer.addPokemon(pokemon);
        return trainerDao.save(trainer);
    }

    @Override
    public Trainer save(Trainer trainer) {
        return trainerDao.save(trainer);
    }

    @Override
    public void delete(Trainer trainer) {
        trainerDao.delete(trainer);
    }

    @Override
    public void delete(Long id) {
        delete(findById(id));
    }

    @Override
    public void setBlocked(Long id, boolean blocked) {
        Trainer toBeBlocked = findById(id);
        toBeBlocked.setBlocked(blocked);
        trainerDao.save(toBeBlocked);
    }

    @Override
    public Trainer update(Long id, Trainer trainer) {
        Trainer found = findById(id);
        String firstName = trainer.getFirstName();
        if (firstName != null &&
                !firstName.equals(found.getFirstName()) &&
                !firstName.isEmpty()) {
            found.setFirstName(firstName);
        }
        String lastName = trainer.getLastName();
        if (lastName != null &&
                !lastName.equals(found.getFirstName()) &&
                !lastName.isEmpty()) {
            found.setLastName(lastName);
        }
        Date dob = trainer.getDateOfBirth();
        if (dob != null && !dob.equals(found.getDateOfBirth())) {
            found.setDateOfBirth(dob);
        }
        return save(found);
    }

    @Override
    public Trainer findById(Long id) {
        return trainerDao.findOne(id);
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
    public List<Trainer> findByUserNameIgnoreCaseContaining(String string) {
        return trainerDao.findByUserNameIgnoreCaseContaining(string);
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
