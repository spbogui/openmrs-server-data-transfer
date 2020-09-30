package org.openmrs.module.serverDataTransfer.errors;

import org.openmrs.module.serverDataTransfer.utils.resources.AttributeResource;
import org.openmrs.module.serverDataTransfer.utils.resources.NameResource;

import java.util.List;

public class PatientFieldError {
    private List<ErrorMessage> gender;
    private List<ErrorMessage> age;
    private List<ErrorMessage> birthdate;
    private List<ErrorMessage> birthdateEstimated;
    private List<ErrorMessage> dead;
    private List<ErrorMessage> deathDate;
    private List<ErrorMessage> causeOfDeath;
    private List<ErrorMessage> names;
    private List<ErrorMessage> familyName;
    private List<ErrorMessage> givenName;
    private List<ErrorMessage> middleName;

    public List<ErrorMessage> getGender() {
        return gender;
    }

    public void setGender(List<ErrorMessage> gender) {
        this.gender = gender;
    }

    public List<ErrorMessage> getAge() {
        return age;
    }

    public void setAge(List<ErrorMessage> age) {
        this.age = age;
    }

    public List<ErrorMessage> getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(List<ErrorMessage> birthdate) {
        this.birthdate = birthdate;
    }

    public List<ErrorMessage> getBirthdateEstimated() {
        return birthdateEstimated;
    }

    public void setBirthdateEstimated(List<ErrorMessage> birthdateEstimated) {
        this.birthdateEstimated = birthdateEstimated;
    }

    public List<ErrorMessage> getDead() {
        return dead;
    }

    public void setDead(List<ErrorMessage> dead) {
        this.dead = dead;
    }

    public List<ErrorMessage> getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(List<ErrorMessage> deathDate) {
        this.deathDate = deathDate;
    }

    public List<ErrorMessage> getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(List<ErrorMessage> causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public List<ErrorMessage> getNames() {
        return names;
    }

    public void setNames(List<ErrorMessage> names) {
        this.names = names;
    }

    public List<ErrorMessage> getFamilyName() {
        return familyName;
    }

    public void setFamilyName(List<ErrorMessage> familyName) {
        this.familyName = familyName;
    }

    public List<ErrorMessage> getGivenName() {
        return givenName;
    }

    public void setGivenName(List<ErrorMessage> givenName) {
        this.givenName = givenName;
    }

    public List<ErrorMessage> getMiddleName() {
        return middleName;
    }

    public void setMiddleName(List<ErrorMessage> middleName) {
        this.middleName = middleName;
    }
}
