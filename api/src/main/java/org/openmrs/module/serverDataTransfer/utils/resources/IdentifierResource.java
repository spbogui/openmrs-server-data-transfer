package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.PatientIdentifier;

import java.io.Serializable;

public class IdentifierResource implements Serializable {
    private String identifier;
    private String identifierType;
    private String location;
    private boolean preferred;
    private String uuid;

    public IdentifierResource() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public IdentifierResource setIdentifier(PatientIdentifier patientIdentifier) {
        setIdentifier(patientIdentifier.getIdentifier());
        setIdentifierType(patientIdentifier.getIdentifierType().getUuid());
        setPreferred(patientIdentifier.getPreferred());
        setLocation(patientIdentifier.getLocation().getUuid());
        setUuid(patientIdentifier.getUuid());

        return this;
    }
}
