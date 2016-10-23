package cz.muni.fi.pa165.kodemon.entity;

import cz.muni.fi.pa165.kodemon.enums.PokemonType;

import javax.persistence.*;
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

    @Column(nullable = false)
    @NotNull
    private String name;

    private String nickname;

    @Column(nullable = false)
    @NotNull
    private PokemonType type;

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
    public Pokemon(@NotNull PokemonType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Pokemon pokemon = (Pokemon) object;

        if (level != pokemon.level) return false;
        if (!id.equals(pokemon.id)) return false;
        if (!name.equals(pokemon.name)) return false;
        if (nickname != null ? !nickname.equals(pokemon.nickname) : pokemon.nickname != null) return false;
        return type == pokemon.type;

    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + level;
        return result;
    }
}
