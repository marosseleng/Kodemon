package com.kodemon.api.dto;

import com.kodemon.persistence.enums.PokemonName;

import java.util.Objects;

/**
 * DTO representing Pokemon
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public class PokemonDTO {
    private UserDTO trainer;
    private PokemonName name;
    private String nickname;
    private int level;

    public UserDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(UserDTO trainer) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokemonDTO)) return false;
        PokemonDTO that = (PokemonDTO) o;
        return getLevel() == that.getLevel() &&
                Objects.equals(getTrainer(), that.getTrainer()) &&
                getName() == that.getName() &&
                Objects.equals(getNickname(), that.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainer(), getName(), getNickname(), getLevel());
    }
}
