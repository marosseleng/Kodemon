package com.kodemon.persistence.entity;

import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity that represents a Pokemon.
 * <p>
 * A pokemon can fight with other Pokemon, level up and evolve.
 *
 * @author Matej Poklemba
 */

@Entity
@Table(name = "POKEMON", schema = "APP")
public class Pokemon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // this is the BIDIRECTIONAL OWNING side
    private Trainer trainer;

    @Column(nullable = false)
    @NotNull
    private PokemonName name;

    private String nickname;

    @Min(1)
    private int level;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Pokemon() {
    }

    /**
     * A constructor to initialize a Pokemon with its {@link PokemonName}.
     *
     * @param name the Pokemon's name.
     */
    public Pokemon(@NotNull PokemonName name) {
        this.name = name;
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

    public PokemonName getName() {
        return name;
    }

    public void setName(PokemonName name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public PokemonType[] getType() {
        return name.getTypes();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || !(object instanceof Pokemon)) return false;

        Pokemon pokemon = (Pokemon) object;

        if (getLevel() != pokemon.getLevel()) return false;
        if (getTrainer() != null ? !getTrainer().getUserName().equals(pokemon.getTrainer().getUserName()) : pokemon.getTrainer() != null)
            return false;
        if (!getName().equals(pokemon.getName())) return false;
        if (getNickname() != null ? !getNickname().equals(pokemon.getNickname()) : pokemon.getNickname() != null)
            return false;
        return getType() == pokemon.getType();

    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getTrainer() != null ? getTrainer().getUserName().hashCode() : 0);
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getNickname() != null ? getNickname().hashCode() : 0);
        result = 31 * result + getType().hashCode();
        result = 31 * result + getLevel();
        return result;
    }
}
