package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonName;
import org.openmrs.module.serverDataTransfer.utils.Tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PersonResource implements Serializable {
    private String gender;
    private Integer age;
    private String birthdate;
    private boolean birthdateEstimated;
    private boolean dead;
    private String deathDate;
    private String causeOfDeath;
    private Set<NameResource> names;
    //private Set<AddressResource> addresses;
    private Set<AttributeResource> attributes;
    private String uuid;

    public PersonResource() {
        names = new HashSet<NameResource>(Collections.<NameResource>emptyList());
        // addresses = new HashSet<AddressResource>(Collections.<AddressResource>emptyList());
        attributes = new HashSet<AttributeResource>(Collections.<AttributeResource>emptyList());
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

    public Set<NameResource> getNames() {
        return names;
    }

    public void setNames(Set<NameResource> names) {
        this.names = names;
    }

//    public Set<AddressResource> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(Set<AddressResource> addresses) {
//        this.addresses = addresses;
//    }

    public Set<AttributeResource> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeResource> attributes) {
        this.attributes = attributes;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PersonResource setPerson(Person person) {
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

        for (PersonName personName : person.getNames()){
            NameResource nameResource = new NameResource();
            nameResource.setPersonName(personName);
            addName(nameResource);
        }
//        if (person.getAddresses() != null) {
//            for (PersonAddress personAddress : person.getAddresses()) {
//                AddressResource addressResource = new AddressResource();
//                addressResource.setPersonAddress(personAddress);
//                addAddress(addressResource);
//            }
//        } else {
////            setAddresses(null);
//        }
        if (person.getAttributes() != null) {
            for (PersonAttribute personAttribute : person.getAttributes()) {
                AttributeResource attributeResource = new AttributeResource();
                attributeResource.setPersonAttribute(personAttribute);
                addAttribute(attributeResource);
            }
        } else {
            setAttributes(null);
        }
        setUuid(person.getUuid());
        return this;
    }

    private void addAttribute(AttributeResource attributeResource) {
        this.getAttributes().add(attributeResource);
    }

    public void addName(NameResource nameResource) {
        this.getNames().add(nameResource);
    }

//    public void addAddress(AddressResource addressResource) {
//        this.getAddresses().add(addressResource);
//    }
}
