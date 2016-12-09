package com.kodemon.api.dto;

import java.util.Objects;

/**
 * DTO representing badge
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public class BadgeDTO {
    private Long id;
    private String name;
    private GymDTO gym;
    private UserDTO trainer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GymDTO getGym() {
        return gym;
    }

    public void setGym(GymDTO gym) {
        this.gym = gym;
    }

    public UserDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(UserDTO trainer) {
        this.trainer = trainer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BadgeDTO)) return false;
        BadgeDTO badgeDTO = (BadgeDTO) o;
        return Objects.equals(getName(), badgeDTO.getName()) &&
                Objects.equals(getGym(), badgeDTO.getGym()) &&
                Objects.equals(getTrainer().getUserName(), badgeDTO.getTrainer().getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGym(), getTrainer().getUserName());
    }
}
