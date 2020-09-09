package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.Patient;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PatientAction implements Serializable {
    private Set<String> actions;
    private String patientUuid;

    public PatientAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public String getPatientUuid() {
        return patientUuid;
    }

    public void setPatientUuid(String patientUuid) {
        this.patientUuid = patientUuid;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
