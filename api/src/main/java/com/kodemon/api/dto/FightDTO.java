package com.kodemon.api.dto;

import java.util.Date;

/**
 * @author Oliver Roch
 */
public class FightDTO {
    private UserDTO challenger;
    private GymDTO targetGym;
    private Date fightTime;
    private boolean wasChallengerSuccessful;

    public UserDTO getChallenger() {
        return challenger;
    }

    public void setChallenger(UserDTO challenger) {
        this.challenger = challenger;
    }

    public GymDTO getTargetGym() {
        return targetGym;
    }

    public void setTargetGym(GymDTO targetGym) {
        this.targetGym = targetGym;
    }

    public Date getFightTime() {
        return fightTime;
    }

    public void setFightTime(Date fightTime) {
        this.fightTime = fightTime;
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
        if (!(o instanceof FightDTO)) return false;

        FightDTO fightDTO = (FightDTO) o;

        if (isWasChallengerSuccessful() != fightDTO.isWasChallengerSuccessful()) return false;
        if (!getChallenger().equals(fightDTO.getChallenger())) return false;
        if (!getTargetGym().equals(fightDTO.getTargetGym())) return false;
        return getFightTime().equals(fightDTO.getFightTime());

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
