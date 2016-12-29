package com.kodemon.springmvc.validator;

import com.kodemon.api.dto.UserRegisterDTO;
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
            errors.rejectValue("pokemon", "UserRegisterDTOValidator.pokemon.null");
        } else if (!PokemonName.getInitialPokemon().contains(pokemonName)) {
            errors.rejectValue("pokemon", "UserRegisterDTOValidator.pokemon.invalid");
        }

        Date dob = dto.getDateOfBirth();
        if (dob == null) {
            errors.rejectValue("dateOfBirth", "UserRegisterDTOValidator.dateOfBirth.null");
        } else if (System.currentTimeMillis() <= dob.getTime()) {
            errors.rejectValue("dateOfBirth", "UserRegisterDTOValidator.dateOfBirth.invalid");
        }

        String password = dto.getPassword();
        if (password == null) {
            errors.rejectValue("password", "UserRegisterDTOValidator.password.null");
        } else if (password.length() < 6) {
            errors.rejectValue("password", "UserRegisterDTOValidator.password.short");
        }

        String lastName = dto.getLastName();
        if (lastName == null) {
            errors.rejectValue("lastName", "UserRegisterDTOValidator.lastName.null");
        } else if (lastName.isEmpty()) {
            errors.rejectValue("lastName", "UserRegisterDTOValidator.lastName.empty");
        }

        String firstName = dto.getFirstName();
        if (firstName == null) {
            errors.rejectValue("firstName", "UserRegisterDTOValidator.firstName.null");
        } else if (firstName.isEmpty()) {
            errors.rejectValue("firstName", "UserRegisterDTOValidator.firstName.empty");
        }

        String userName = dto.getUserName();
        if (userName == null) {
            errors.rejectValue("userName", "UserRegisterDTOValidator.userName.null");
        } else if (userName.length() < 4) {
            errors.rejectValue("userName", "UserRegisterDTOValidator.userName.short");
        }
    }
}
