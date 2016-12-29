package com.kodemon.api.dto;

import com.kodemon.persistence.enums.PokemonName;

import java.util.Date;

/**
 * Created by mseleng on 12/29/16.
 */
public class UserRegisterDTO {

    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private PokemonName pokemon;
    private Date dateOfBirth;

    public UserRegisterDTO() {

    }

    public UserRegisterDTO(
            String userName,
            String firstName,
            String lastName,
            String password,
            PokemonName pokemon,
            Date dateOfBirth) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.pokemon = pokemon;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PokemonName getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonName pokemon) {
        this.pokemon = pokemon;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegisterDTO that = (UserRegisterDTO) o;

        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
            return false;
        if (getPokemon() != that.getPokemon()) return false;
        return getDateOfBirth() != null ? getDateOfBirth().equals(that.getDateOfBirth()) : that.getDateOfBirth() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getPokemon() != null ? getPokemon().hashCode() : 0);
        result = 31 * result + (getDateOfBirth() != null ? getDateOfBirth().hashCode() : 0);
        return result;
    }
}
