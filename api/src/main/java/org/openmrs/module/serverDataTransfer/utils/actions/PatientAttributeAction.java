package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.PersonAttribute;

import java.io.Serializable;

public class PatientAttributeAction implements Serializable {
    private String action;
    private PersonAttribute personAttribute;

    public PatientAttributeAction() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PersonAttribute getPersonAttribute() {
        return personAttribute;
    }

    public void setPersonAttribute(PersonAttribute personAttribute) {
        this.personAttribute = personAttribute;
    }
}
