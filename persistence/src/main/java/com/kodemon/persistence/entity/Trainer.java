package com.kodemon.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.*;

import static com.kodemon.persistence.util.Constants.MIN_USERNAME_LENGTH;

/**
 * An entity that represents a Pokemon trainer.
 * <p>
 * A trainer can:
 * <ul>
 * <li>train one or many {@link Pokemon} and</li>
 * <li>own zero or many {@link Badge}s.</li>
 * </ul>
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Entity
@Table(name = "TRAINER", schema = "APP")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    @Size(min = MIN_USERNAME_LENGTH)
    private String userName;

    private String pwdHash;

    @Column(nullable = false)
    @NotNull
    private String firstName;

    @Column(nullable = false)
    @NotNull
    private String lastName;

    @Column(nullable = false)
    @NotNull
    private boolean isAdmin = false;

    @Column(nullable = false)
    @NotNull
    private boolean isBlocked = false;

    @Temporal(TemporalType.DATE)
    @Past
    @Column(nullable = false)
    @NotNull
    private Date dateOfBirth;

    @OneToMany(fetch = FetchType.EAGER)
    @NotNull
    private Set<Badge> badges = new HashSet<>();

    /**
     * The list of {@link Pokemon} that belong to the Trainer.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trainer")
    @NotNull
    private List<Pokemon> pokemons = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @NotNull
    private List<Pokemon> activePokemons = new ArrayList<>();

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public Trainer() {
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
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

    public Set<Badge> getBadges() {
        return badges;
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
    }

    public boolean removePokemon(Pokemon pokemon) {
        return pokemons.remove(pokemon);
    }

    public List<Pokemon> getActivePokemons() {
        return activePokemons;
    }

    /*public void setActivePokemons(List<Pokemon> activePokemons) {
        this.activePokemons = activePokemons;
    }*/

    public void addActivePokemon(Pokemon pokemon) {
        this.activePokemons.add(pokemon);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Trainer)) {
            return false;
        }

        Trainer trainer = (Trainer) o;

        return !(!getUserName().equals(trainer.getUserName()) ||
                !getFirstName().equals(trainer.getFirstName()) ||
                !getLastName().equals(trainer.getLastName()) ||
                !getDateOfBirth().equals(trainer.getDateOfBirth()) ||
                !getBadges().equals(trainer.getBadges()) ||
                !getPokemons().equals(trainer.getPokemons()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getDateOfBirth().hashCode();
        result = 31 * result + getPokemons().hashCode();
        result = 31 * result + getBadges().hashCode();
        return result;
    }
}
