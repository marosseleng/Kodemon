package cz.muni.fi.pa165.kodemon.entity;

import cz.muni.fi.pa165.kodemon.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity that represents a pokemon gym.
 *
 * Each gym has a leader, which can be challenged by other trainers that want to earn a badge.
 *
 * @author Miso Romanek
 */

@Entity
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String city;

    @Enumerated
    private PokemonType type;

    @OneToOne
    @NotNull
    private Trainer trainer;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Gym() {}

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
    public boolean equals(Object o) {
        if (this == object) return true;
        if (object == null || !(object instanceof Trainer)) {
            return false;
        }

        Gym gym = (Gym) object;

        return !(!getId().equals(gym.getId()) ||
                !getCity().equals(gym.getCity()) ||
                !getType().equals(gym.getType()) ||
                !getTrainer().equals(gym.getTrainer()) ||
                !trainer.equals(gym.trainer));
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getTrainer().hashCode();
        result = 31 * result + trainers.hashCode();
        return result;
    }
}
