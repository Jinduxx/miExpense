package com.example.miexpense.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "users_email_contraint", columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstname", nullable = false, columnDefinition = "VARCHAR(150)")
    private String firstname;

    @Column(name = "lastname", nullable = false, columnDefinition = "VARCHAR(150)")
    private String lastname;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(150)")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(45)")
    private String password;

    @Column(name = "dateOfBirth", nullable = false, columnDefinition = "VARCHAR(45)")
    private String dob;

    @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(45)")
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }


}
