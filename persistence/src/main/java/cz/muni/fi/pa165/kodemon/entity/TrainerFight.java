package cz.muni.fi.pa165.kodemon.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity represents result of fight between challenging trainer and gym leader.
 * Used to store fight history in database.
 *
 * If challenging trainer defeats gym leader, he earns the badge of gym.
 *
 * @author Oliver Roch
 */

@Entity
@Table(name = "TRAINER_FIGHT", schema = "APP")
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

    @Column(nullable = false)
    @NotNull
    private Date fightTime;

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

    public Date getFightTime() {
        return fightTime;
    }

    public void setFightTime(Date fightTime) {
        this.fightTime = fightTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainerFight)) return false;

        TrainerFight that = (TrainerFight) o;

        if (isWasChallengerSuccessful() != that.isWasChallengerSuccessful()) return false;
        if (!getChallenger().equals(that.getChallenger())) return false;
        if (!getTargetGym().equals(that.getTargetGym())) return false;
        return getFightTime().equals(that.getFightTime());

    }

    @Override
    public int hashCode() {
        int result = getChallenger().hashCode();
        result = 31 * result + getTargetGym().hashCode();
        result = 31 * result + getFightTime().hashCode();
        result = 31 * result + (isWasChallengerSuccessful() ? 1 : 0);
        return result;
    }
}
