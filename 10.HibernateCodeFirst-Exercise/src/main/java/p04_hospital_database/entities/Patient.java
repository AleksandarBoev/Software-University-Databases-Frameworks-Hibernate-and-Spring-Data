package p04_hospital_database.entities;
//https://www.cs.mun.ca/java-api-1.5/guide/jdbc/blob.html

import p04_hospital_database.root_entities.BaseId;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;

//@Entity
@Table(name = "patients")
public class Patient extends BaseId {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private Date dateOfBirth;
    private Blob picture;
    private Boolean medicallyInsured;
    private List<Visitation> visitationsList;

    public Patient() {

    }

    public Patient(String firstName, String lastName, String address, String email,
                   Date dateOfBirth, Blob picture, Boolean medicallyInsured) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setEmail(email);
        this.setDateOfBirth(dateOfBirth);
        this.setPicture(picture);
        this.setMedicallyInsured(medicallyInsured);
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.checkIfStringIsEmpty(firstName, "First name");

        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.checkIfStringIsEmpty(lastName, "Last name");

        this.lastName = lastName;
    }

    @Column(nullable = false)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.checkIfStringIsEmpty(address, "Address");

        this.address = address;
    }

    @Column
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.checkIfStringIsEmpty(email, "Email");

        this.email = email;
    }

    @Column
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column
    @Lob
    public Blob getPicture() {
        return this.picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    @Column(name = "medically_insured", nullable = false)
    public Boolean getMedicallyInsured() {
        return this.medicallyInsured;
    }

    public void setMedicallyInsured(Boolean medicallyInsured) {
        this.medicallyInsured = medicallyInsured;
    }

    @OneToMany(mappedBy = "patient")
    public List<Visitation> getVisitationsList() {
        return this.visitationsList;
    }

    public void setVisitationsList(List<Visitation> visitationsList) {
        this.visitationsList = visitationsList;
    }

    private void checkIfStringIsEmpty(String word, String parameterName) {
        if ("".equals(word))
            throw new IllegalArgumentException(parameterName + " can't be empty!");
    }
}
