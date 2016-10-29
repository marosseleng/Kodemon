package cz.muni.fi.pa165.kodemon.entity;

import cz.muni.fi.pa165.kodemon.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity that represents a Pokemon.
 *
 * A pokemon can fight with other Pokemon, level up and evolve.
 *
 * @author Matej Poklemba
 */

@Entity
public class Pokemon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // this is the BIDIRECTIONAL OWNING side
    private Trainer trainer;

    @Column(nullable = false)
    @NotNull
    private String name;

    private String nickname;

    @Enumerated
    private PokemonType type;

    @Min(1)
    private int level;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Pokemon() {}

    /**
     * A constructor to initialize a Pokemon with its {@link PokemonType}.
     *
     * @param type the Pokemon's type.
     */
    public Pokemon(PokemonType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || !(object instanceof Pokemon)) return false;

        Pokemon pokemon = (Pokemon) object;

        if (getLevel() != pokemon.getLevel()) return false;
        if (!getId().equals(pokemon.getId())) return false;
        if (getTrainer() != null ? !getTrainer().equals(pokemon.getTrainer()) : pokemon.getTrainer() != null) return false;
        if (!getName().equals(pokemon.getName())) return false;
        if (getNickname() != null ? !getNickname().equals(pokemon.getNickname()) : pokemon.getNickname() != null) return false;
        return getType() == pokemon.getType();

    }

    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getId().hashCode();
        result = 31 * result + (getTrainer() != null ? getTrainer().hashCode() : 0);
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getNickname() != null ? getNickname().hashCode() : 0);
        result = 31 * result + getType().hashCode();
        result = 31 * result + getLevel();
        return result;
    }
}
