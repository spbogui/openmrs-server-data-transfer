package org.openmrs.module.serverDataTransfer.utils.resourcesResult;

import java.util.List;

public class PatientResult {
    private String uuid;
    private String display;
    private PersonResult person;
    private List<IdentifierResult> identifiers;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public PersonResult getPerson() {
        return person;
    }

    public void setPerson(PersonResult person) {
        this.person = person;
    }

    public List<IdentifierResult> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<IdentifierResult> identifiers) {
        this.identifiers = identifiers;
    }
}
