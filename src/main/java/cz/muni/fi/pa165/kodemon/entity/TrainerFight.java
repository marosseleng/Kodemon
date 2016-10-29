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
    private Gym targetGym;

    private boolean wasChallengerSuccessful;

    /**
     * Parameterless constructor for (not only) persistence purposes.
     */
    public TrainerFight() {
    }

    public Long getId() {
        return id;
    }

    public Trainer getChallenger() {
        return challenger;
    }

    public void setChallenger(Trainer challenger) {
        this.challenger = challenger;
    }

    public Gym getTargetGym() {
        return targetGym;
    }

    public void setTargetGym(Gym targetGym) {
        this.targetGym = targetGym;
    }

    public boolean isWasChallengerSuccessful() {
        return wasChallengerSuccessful;
    }

    public void setWasChallengerSuccessful(boolean wasChallengerSuccessful) {
        this.wasChallengerSuccessful = wasChallengerSuccessful;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainerFight)) return false;

        TrainerFight that = (TrainerFight) o;

        if (isWasChallengerSuccessful() != that.isWasChallengerSuccessful()) return false;
        if (!getId().equals(that.getId())) return false;
        if (!getChallenger().equals(that.getChallenger())) return false;
        return getTargetGym().equals(that.getTargetGym());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getChallenger().hashCode();
        result = 31 * result + getTargetGym().hashCode();
        result = 31 * result + (isWasChallengerSuccessful() ? 1 : 0);
        return result;
    }
}
