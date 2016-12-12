package com.kodemon.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * User DTO class
 *
 * @author Matej Poklemba
 */
public class UserDTO {
    private Long id;
    @JsonProperty(required = true)
    private String userName;
    @JsonProperty(required = true)
    private String firstName;
    @JsonProperty(required = true)
    private String lastName;
    @JsonProperty(required = true)
    private Date dateOfBirth;
    @JsonProperty(required = false, defaultValue = "[]")
    private Set<BadgeDTO> badges = new HashSet<>();
    @JsonProperty(required = false, defaultValue = "[]")
    private List<PokemonDTO> pokemons = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<BadgeDTO> getBadges() {
        return badges;
    }

    public void addBadge(BadgeDTO badge) {
        badges.add(badge);
    }

    public List<PokemonDTO> getPokemons() {
        return pokemons;
    }

    public void addPokemon(PokemonDTO pokemon) {
        pokemons.add(pokemon);
    }

    public boolean removePokemon(PokemonDTO pokemon) {
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
        result = 31 * result + getPokemons().hashCode();
        result = 31 * result + getBadges().hashCode();
        return result;
    }
}
