package com.kodemon.springmvc.validator;

import com.kodemon.api.dto.UserRegisterDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.enums.PokemonName;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * Validator for the user registration
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public class UserRegisterDTOValidator implements Validator {

    private UserFacade userFacade;

    public UserRegisterDTOValidator(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof UserRegisterDTO)) {
            return;
        }

        UserRegisterDTO dto = (UserRegisterDTO) target;
        PokemonName pokemonName = dto.getPokemon();
        if (pokemonName == null) {
            errors.rejectValue("pokemon", "UserRegisterDTOValidator.pokemon.null", "Pokemon cannot be null");
        } else if (!PokemonName.getInitialPokemon().contains(pokemonName)) {
            errors.rejectValue("pokemon", "UserRegisterDTOValidator.pokemon.invalid", "Invalid pokemon selected. Select one of given");
        }

        Date dob = dto.getDateOfBirth();
        if (dob == null) {
            errors.rejectValue("dateOfBirth", "UserRegisterDTOValidator.dateOfBirth.null", "Day of birth cannot be null");
        } else if (System.currentTimeMillis() <= dob.getTime()) {
            errors.rejectValue("dateOfBirth", "UserRegisterDTOValidator.dateOfBirth.invalid", "Invalid day of birth");
        }

        String password = dto.getPassword();
        if (password == null) {
            errors.rejectValue("password", "UserRegisterDTOValidator.password.null", "Password cannot be null");
        } else if (password.length() < 6) {
            errors.rejectValue("password", "UserRegisterDTOValidator.password.short", "Password needs to be at least 6 characters long");
        }

        String lastName = dto.getLastName();
        if (lastName == null) {
            errors.rejectValue("lastName", "UserRegisterDTOValidator.lastName.null", "Last name cannot be null");
        } else if (lastName.isEmpty()) {
            errors.rejectValue("lastName", "UserRegisterDTOValidator.lastName.empty", "Last name cannot be empty");
        }

        String firstName = dto.getFirstName();
        if (firstName == null) {
            errors.rejectValue("firstName", "UserRegisterDTOValidator.firstName.null", "First name cannot be null");
        } else if (firstName.isEmpty()) {
            errors.rejectValue("firstName", "UserRegisterDTOValidator.firstName.empty", "First name cannot be empty");
        }

        String userName = dto.getUserName();
        if (userName == null) {
            errors.rejectValue("userName", "UserRegisterDTOValidator.userName.null", "User name cannot be null");
        } else if (userName.length() < 4) {
            errors.rejectValue("userName", "UserRegisterDTOValidator.userName.short", "User name needs to be at least 4 characters long");
        } else if (!userFacade.findUserByUserNameExactMatch(userName).isEmpty()) {
            errors.rejectValue("userName", "UserRegisterDTOValidator.userName.taken", "User name is already taken");
        }
    }
}
