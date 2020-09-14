package org.openmrs.module.serverDataTransfer.utils.resourcesResult;

import org.openmrs.PersonName;

public class PreferredNameResult {
    private String uuid;
    private String givenName;
    private String middleName;
    private String familyName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public PersonName getPersonName() {
        PersonName personName = new PersonName();
        personName.setUuid(this.getUuid());
        personName.setGivenName(this.getGivenName());
        personName.setFamilyName(this.getFamilyName());
        personName.setMiddleName(this.getMiddleName());

        return personName;
    }
}
