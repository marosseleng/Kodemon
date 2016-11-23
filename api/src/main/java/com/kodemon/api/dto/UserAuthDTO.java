package com.kodemon.api.dto;

/**
 * User Authentication DTO class
 *
 * @author Matej Poklemba
 */
public class UserAuthDTO {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserAuthDTO)) {
            return false;
        }

        UserAuthDTO that = (UserAuthDTO) o;

        if (!userName.equals(that.userName)) return false;
        return pwdHash.equals(that.pwdHash);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + pwdHash.hashCode();
        return result;
    }
}
