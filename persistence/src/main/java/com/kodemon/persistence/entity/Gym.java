package com.kodemon.persistence.entity;

import com.kodemon.persistence.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity that represents a pokemon gym.
 * <p>
 * Each gym has a leader, which can be challenged by other trainers that want to earn a badge.
 *
 * @author Miso Romanek
 */

@Entity
@Table(name = "GYM", schema = "APP")
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String city;

    @Column(nullable = false)
    @NotNull
    private String badgeName;

    @Enumerated
    @NotNull
    private PokemonType type;

    @OneToOne
    @NotNull
    private Trainer trainer;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Gym() {
    }

    /**
     * Constructor which initializes a gym with its {@link Trainer}.
     *
     * @param trainer that is the leader of this gym.
     */
    public Gym(@NotNull Trainer trainer) {
        this.trainer = trainer;
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
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
                !getBadgeName().equals(gym.getBadgeName()) ||
                !getType().equals(gym.getType()) ||
                !getTrainer().equals(gym.getTrainer()));
    }

    @Override
    public int hashCode() {
        int result = 47;
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getBadgeName().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getTrainer().hashCode();
        return result;
    }
}
