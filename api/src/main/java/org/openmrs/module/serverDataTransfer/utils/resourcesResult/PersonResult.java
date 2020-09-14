package org.openmrs.module.serverDataTransfer.utils.resourcesResult;

import java.util.Date;

public class PersonResult {
    private String display;
    private String gender;
    private Date birthdate;
    private PreferredNameResult preferredName;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public PreferredNameResult getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(PreferredNameResult preferredName) {
        this.preferredName = preferredName;
    }
}
