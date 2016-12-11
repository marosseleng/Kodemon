package com.kodemon.api.dto;

/**
 * User Authentication DTO class
 *
 * @author Matej Poklemba
 */
public class UserAuthDTO {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserAuthDTO)) {
            return false;
        }

        UserAuthDTO that = (UserAuthDTO) o;

        if (!getUserName().equals(that.getUserName())) return false;
        return getPassword().equals(that.getPassword());

    }

    @Override
    public int hashCode() {
        int result = getUserName().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}
