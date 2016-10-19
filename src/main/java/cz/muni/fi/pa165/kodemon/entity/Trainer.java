package cz.muni.fi.pa165.kodemon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * An entity that represents a Pokemon trainer.
 * <p>
 * A trainer can:
 * <ul>
 *     <li>train one or many {@link Pokemon},</li>
 *     <li>own zero or many {@link Badge}s and</li>
 *     <li>be the leader of zero or one {@link Stadium}.</li>
 * </ul>
 *
 * @author <a href="mailto:marosseleng@gmail.com">Maros Seleng</a>
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

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Trainer() { }

    /**
     * A constructor to initialize a Pokemon Trainer and assign his first {@link Pokemon} to him.
     *
     * @param initialPokemon a first Pokemon for this trainer.
     */
    public Trainer(@NotNull Pokemon initialPokemon) {
        this.pokemons.add(initialPokemon);
    }

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
