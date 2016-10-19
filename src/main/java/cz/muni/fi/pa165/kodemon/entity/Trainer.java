package cz.muni.fi.pa165.kodemon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by mseleng on 10/19/16.
 */
@Entity
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String firstName;

    @Column(nullable = false)
    @NotNull
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull
    private Date dateOfBirth;

    @OneToMany
    @NotNull
    private List<Badge> badges = new ArrayList<>();

    @OneToMany
    @NotNull
    private List<Pokemon> pokemons = new ArrayList<>();

    @OneToOne
    private Stadium homeStadium;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Badge> getAllBadges() {
        return Collections.unmodifiableList(badges);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public List<Pokemon> getAllPokemons() {
        return Collections.unmodifiableList(pokemons);
    }

    public Stadium getHomeStadium() {
        return homeStadium;
    }

    public void setHomeStadium(Stadium homeStadium) {
        this.homeStadium = homeStadium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Trainer)) {
            return false;
        }

        Trainer trainer = (Trainer) o;

        if (!getId().equals(trainer.getId()) ||
                !getFirstName().equals(trainer.getFirstName()) ||
                !getLastName().equals(trainer.getLastName()) ||
                !getDateOfBirth().equals(trainer.getDateOfBirth()) ||
                !badges.equals(trainer.badges) ||
                !pokemons.equals(trainer.pokemons)) {
            return false;
        }

        return getHomeStadium() != null ? getHomeStadium().equals(trainer.getHomeStadium()) : trainer.getHomeStadium() == null;

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getDateOfBirth().hashCode();
        result = 31 * result + badges.hashCode();
        result = 31 * result + pokemons.hashCode();
        result = 31 * result + (getHomeStadium() != null ? getHomeStadium().hashCode() : 0);
        return result;
    }
}
