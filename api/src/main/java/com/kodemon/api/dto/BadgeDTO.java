package com.kodemon.api.dto;

/**
 * DTO representing badge
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public class BadgeDTO {
    private Long id;
    private String name;
    private GymDTO gym;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BadgeDTO badgeDTO = (BadgeDTO) o;

        if (getId() != null ? !getId().equals(badgeDTO.getId()) : badgeDTO.getId() != null) return false;
        if (getName() != null ? !getName().equals(badgeDTO.getName()) : badgeDTO.getName() != null) return false;
        return getGym() != null ? getGym().equals(badgeDTO.getGym()) : badgeDTO.getGym() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getGym() != null ? getGym().hashCode() : 0);
        return result;
    }
}
