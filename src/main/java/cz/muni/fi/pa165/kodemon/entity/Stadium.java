package cz.muni.fi.pa165.kodemon.entity;

import cz.muni.fi.pa165.kodemon.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity that represents a pokemon stadium.
 *
 * Each stadium has a leader, which can be challenged by other trainers that want to earn a badge.
 *
 * @author Michal Romanek
 */

@Entity
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String city;

    private PokemonType type;

    @OneToOne
    @NotNull
    private Trainer trainer;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Stadium() {}

    /**
     * Constructor which initializes a stadium.
     *
     * @param trainer that is a leader of this stadium
     */
    public Stadium(@NotNull Trainer trainer) {
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

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Stadium stadium = (Stadium) object;

        if (!id.equals(stadium.id)) return false;
        if (!city.equals(stadium.city)) return false;
        if (type != stadium.type) return false;
        return trainer.equals(stadium.trainer);
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + trainer.hashCode();
        return result;
    }
}
