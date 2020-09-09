package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.PersonName;

import java.io.Serializable;

public class NameResource implements Serializable {
    private String givenName;
    private String middleName;
    private String familyName;
    private String familyName2;
    private boolean preferred;
    private String prefix;
    private String familyNamePrefix;
    private String familyNameSuffix;
    private String degree;
    private String uuid;

    public NameResource() {
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName2() {
        return familyName2;
    }

    public void setFamilyName2(String familyName2) {
        this.familyName2 = familyName2;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFamilyNamePrefix() {
        return familyNamePrefix;
    }

    public void setFamilyNamePrefix(String familyNamePrefix) {
        this.familyNamePrefix = familyNamePrefix;
    }

    public String getFamilyNameSuffix() {
        return familyNameSuffix;
    }

    public void setFamilyNameSuffix(String familyNameSuffix) {
        this.familyNameSuffix = familyNameSuffix;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public NameResource setPersonName(PersonName personName) {
        setGivenName(personName.getGivenName());
        setFamilyName(personName.getFamilyName());
        setPreferred(personName.getPreferred());

        if (personName.getMiddleName() != null)
            setMiddleName(personName.getMiddleName());

        if (personName.getFamilyName2() != null)
            setFamilyName2(personName.getFamilyName2());

        if (personName.getPrefix() != null)
            setPrefix(personName.getPrefix());

        if (personName.getFamilyNamePrefix() != null)
            setFamilyNamePrefix(personName.getFamilyNamePrefix());

        if (personName.getFamilyNameSuffix() != null)
        setFamilyNameSuffix(personName.getFamilyNameSuffix());

        if (personName.getDegree() != null)
            setDegree(personName.getDegree());
        setUuid(personName.getUuid());

        return this;
    }
}
