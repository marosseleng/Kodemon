package com.kodemon.api.dto;

/**
 * User Authentication DTO class
 *
 * @author Matej Poklemba
 */
public class UserAuthDTO {
    private Long userId;
    private String userName;
    private String pwdHash;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserAuthDTO)) {
            return false;
        }

        UserAuthDTO that = (UserAuthDTO) o;

        if (!getUserName().equals(that.getUserName())) return false;
        return getPwdHash().equals(that.getPwdHash());

    }

    @Override
    public int hashCode() {
        int result = getUserName().hashCode();
        result = 31 * result + getPwdHash().hashCode();
        return result;
    }
}
