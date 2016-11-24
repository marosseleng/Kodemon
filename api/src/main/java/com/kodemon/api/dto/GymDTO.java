package com.kodemon.api.dto;

import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;

import javax.validation.constraints.NotNull;

/**
 * DTO class for Gym.
 *
 * @author Miso Romanek
 */
public class GymDTO {

    private Long id;
    private String city;
    private PokemonType type;
    private Trainer trainer;

    public Long getId() {
        return id;
    }

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

    public Trainer getTrainer() {
        return trainer;
    }
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || !(object instanceof Gym)) {
            return false;
        }

        Gym gym = (Gym) object;

        return !(!getCity().equals(gym.getCity()) ||
                !getType().equals(gym.getType()) ||
                !getTrainer().equals(gym.getTrainer()));
    }

    @Override
    public int hashCode() {
        int result = 47;
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getTrainer().hashCode();
        return result;
    }
}
