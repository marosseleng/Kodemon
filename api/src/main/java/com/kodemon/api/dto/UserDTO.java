package com.kodemon.api.dto;

import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Pokemon;

import java.util.*;

/**
 * User DTO class
 *
 * @author Matej Poklemba
 */
public class UserDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Set<Badge> badges = new HashSet<>();
    private List<Pokemon> pokemons = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Badge> getBadges() {
        return Collections.unmodifiableSet(badges);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public List<Pokemon> getPokemons() {
        return Collections.unmodifiableList(pokemons);
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
    }

    public boolean removePokemon(Pokemon pokemon) {
        return pokemons.remove(pokemon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserDTO)) {
            return false;
        }

        UserDTO trainer = (UserDTO) o;

        return !(!getUserName().equals(trainer.getUserName()) ||
                !getFirstName().equals(trainer.getFirstName()) ||
                !getLastName().equals(trainer.getLastName()) ||
                !getDateOfBirth().equals(trainer.getDateOfBirth()) ||
                !getBadges().equals(trainer.getBadges()) ||
                !getPokemons().equals(trainer.getPokemons()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getDateOfBirth().hashCode();
        result = 31 * result + getBadges().hashCode();
        result = 31 * result + getPokemons().hashCode();
        return result;
    }
}
