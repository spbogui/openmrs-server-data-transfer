package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.module.serverDataTransfer.utils.Tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PersonResourceUpdate implements Serializable {
    private String gender;
    private Integer age;
    private String birthdate;
    private boolean birthdateEstimated;
    private boolean dead;
    private String deathDate;
    private String causeOfDeath;
    private String uuid;

    public PersonResourceUpdate() {
        gender = "";
        birthdateEstimated = false;
        dead = false;
        causeOfDeath = "";
        uuid = "";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isBirthdateEstimated() {
        return birthdateEstimated;
    }

    public void setBirthdateEstimated(boolean birthdateEstimated) {
        this.birthdateEstimated = birthdateEstimated;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PersonResourceUpdate setPerson(Person person) {
        setGender(person.getGender());
        setAge(person.getAge());
        if (person.getBirthdate() != null)
            setBirthdate(Tools.formatDateToString(person.getBirthdate(), Tools.DATE_FORMAT_YYYY_MM_DD));
        setBirthdateEstimated(person.getBirthdateEstimated());
//        setDead(person.getDead());
        if (person.getDeathDate() != null)
            setDeathDate(Tools.formatDateToString(person.getDeathDate(), Tools.DATE_FORMAT_YYYY_MM_DD));
        if (person.getCauseOfDeath() != null)
            setCauseOfDeath(person.getCauseOfDeath().getUuid());

        setUuid(person.getUuid());
        return this;
    }

}
