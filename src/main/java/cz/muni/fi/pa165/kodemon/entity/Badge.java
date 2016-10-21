package cz.muni.fi.pa165.kodemon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity that represents a badge a trainer can earn from a stadium.
 *
 * A trainer earns a badge, by defeating stadium leader.
 *
 * @author Oliver Roch
 */

@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @OneToOne
    @NotNull
    private Stadium stadium;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Badge() {}

    /**
     * Constructor which initialize a stadium of the badge
     *
     * @param stadium where can this badge be earned
     */
    public Badge(@NotNull Stadium stadium) {
        this.stadium = stadium;
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

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Badge)) return false;

        Badge badge = (Badge) o;

        if (getId() != null ? !getId().equals(badge.getId()) : badge.getId() != null) return false;
        if (getName() != null ? !getName().equals(badge.getName()) : badge.getName() != null) return false;
        return getStadium() != null ? getStadium().equals(badge.getStadium()) : badge.getStadium() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStadium() != null ? getStadium().hashCode() : 0);
        return result;
    }
}
