package com.example.uniprogramming.security.models;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {
    @NotBlank(message = "Enter your username")
    @Size(min=6, message="Username must be at least 6 characters long")
    private String username;
    @NotBlank(message = "Enter your password")
    @Size(min=6, message = "Password must be at least 8 characters long")
    private String password;
    @NotBlank(message = "Re-enter your password")
    private String repassword;
    @NotBlank(message = "Enter your first name")
    private String name;
    @NotBlank(message = "Enter your last name")
    private String surname;
    @NotBlank(message = "Enter your date of birth")
    private String dateOfBirth;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "UserDTD{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repassword='" + repassword + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
