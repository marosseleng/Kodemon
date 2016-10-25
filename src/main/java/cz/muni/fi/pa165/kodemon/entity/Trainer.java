package cz.muni.fi.pa165.kodemon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * An entity that represents a Pokemon trainer.
 * <p>
 * A trainer can:
 * <ul>
 *     <li>train one or many {@link Pokemon} and</li>
 *     <li>own zero or many {@link Badge}s.</li>
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
    @Past
    @Column(nullable = false)
    @NotNull
    private Date dateOfBirth;

    @OneToMany
    @NotNull
    private List<Badge> badges = new ArrayList<>();

    /**
     * The list of (maximum 6) {@link Pokemon} that the Trainer has by his side.
     */
    @OneToMany
    @NotNull
    @Size(max = 6)
    private List<Pokemon> activePokemon = new ArrayList<>();

    /**
     * The list of {@link Pokemon} that belong to the Trainer, but are not currently by his side.
     */
    @OneToMany
    @NotNull
    private List<Pokemon> inventoryPokemon = new ArrayList<>();

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
        this.activePokemon.add(initialPokemon);
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

    public List<Badge> getBadges() {
        return Collections.unmodifiableList(badges);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public List<Pokemon> getActivePokemon() {
        return Collections.unmodifiableList(activePokemon);
    }

    public void addActivePokemon(Pokemon pokemon) {
        activePokemon.add(pokemon);
    }

    public boolean removeActivePokemon(Pokemon pokemon) {
        return activePokemon.remove(pokemon);
    }

    public List<Pokemon> getInventoryPokemon() {
        return Collections.unmodifiableList(inventoryPokemon);
    }

    public void addInventoryPokemon(Pokemon pokemon) {
        inventoryPokemon.add(pokemon);
    }

    public boolean removeInventoryPokemon(Pokemon pokemon) {
        return inventoryPokemon.remove(pokemon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Trainer)) {
            return false;
        }

        Trainer trainer = (Trainer) o;

        return !(!getId().equals(trainer.getId()) ||
                !getFirstName().equals(trainer.getFirstName()) ||
                !getLastName().equals(trainer.getLastName()) ||
                !getDateOfBirth().equals(trainer.getDateOfBirth()) ||
                !getBadges().equals(trainer.getBadges()) ||
                !getActivePokemon().equals(trainer.getActivePokemon()) ||
                !getInventoryPokemon().equals(trainer.getInventoryPokemon()));
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getDateOfBirth().hashCode();
        result = 31 * result + getBadges().hashCode();
        result = 31 * result + getActivePokemon().hashCode();
        result = 31 * result + getInventoryPokemon().hashCode();
        return result;
    }
}
