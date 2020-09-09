package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.PatientIdentifier;

import java.io.Serializable;

public class IdentifierAction implements Serializable {
    private String action;
    private PatientIdentifier patientIdentifier;

    public IdentifierAction() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PatientIdentifier getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(PatientIdentifier patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }
}
