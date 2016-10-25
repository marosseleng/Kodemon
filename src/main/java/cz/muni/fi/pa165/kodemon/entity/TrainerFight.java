package cz.muni.fi.pa165.kodemon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity represents result of fight between challenging trainer and gym leader.
 * Used to store fight history in database.
 *
 * If challenging trainer defeats gym leader, he earns the badge of gym.
 *
 * @author Oliver Roch
 */

@Entity
public class TrainerFight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    private Trainer challenger;

    @NotNull
    @ManyToOne
    private Gym gym;

    private boolean wasChallengerSuccessful;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public TrainerFight() {
    }
}
