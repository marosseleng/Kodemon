package cz.muni.fi.pa165.kodemon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.*;

/**
 * An entity that represents a Pokemon trainer.
 * <p>
 * A trainer can:
 * <ul>
 *     <li>train one or many {@link Pokemon} and</li>
 *     <li>own zero or many {@link Badge}s.</li>
 * </ul>
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
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
    @Past
    @Column(nullable = false)
    @NotNull
    private Date dateOfBirth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trainer")
    @NotNull
    private Set<Badge> badges = new HashSet<>();

    /**
     * The list of {@link Pokemon} that belong to the Trainer.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trainer")
    @NotNull
    private List<Pokemon> pokemons = new ArrayList<>();

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
        return Collections.unmodifiableSet(badges);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public List<Pokemon> getPokemons() {
        return Collections.unmodifiableList(pokemons);
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
    }

    public boolean removePokemon(Pokemon pokemon) {
        return pokemons.remove(pokemon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Trainer)) {
            return false;
        }

        Trainer trainer = (Trainer) o;

        return !((getId() != null && !getId().equals(trainer.getId())) ||
                !getFirstName().equals(trainer.getFirstName()) ||
                !getLastName().equals(trainer.getLastName()) ||
                !getDateOfBirth().equals(trainer.getDateOfBirth()) ||
                !getBadges().equals(trainer.getBadges()) ||
                !getPokemons().equals(trainer.getPokemons()));
    }

    @Override
    public int hashCode() {
        int result = (getId() == null) ? 0 : getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getDateOfBirth().hashCode();
        result = 31 * result + getBadges().hashCode();
        result = 31 * result + getPokemons().hashCode();
        return result;
    }
}
