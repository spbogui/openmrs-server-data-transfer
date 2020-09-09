package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.PersonAddress;

import java.io.Serializable;

public class AddressAction implements Serializable {
    private String action;
    private PersonAddress personAddress;

    public AddressAction() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PersonAddress getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(PersonAddress personAddress) {
        this.personAddress = personAddress;
    }
}
