package com.kodemon.api.dto;

import com.kodemon.persistence.enums.PokemonType;

import java.util.Objects;

/**
 * DTO for the representation of Gym
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public class GymDTO {

    private String city;
    private PokemonType type;
    private UserDTO trainer;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public UserDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(UserDTO trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GymDTO)) return false;
        GymDTO gymDTO = (GymDTO) o;
        return Objects.equals(getCity(), gymDTO.getCity()) &&
                getType() == gymDTO.getType() &&
                Objects.equals(getTrainer(), gymDTO.getTrainer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getType(), getTrainer());
    }
}
