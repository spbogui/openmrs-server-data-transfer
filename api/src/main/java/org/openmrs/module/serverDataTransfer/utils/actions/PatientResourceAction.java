package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.module.serverDataTransfer.utils.resources.PatientResource;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PatientResourceAction implements Serializable {
    private Set<String> actions;
    private PatientResource patientResources;

    public PatientResourceAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public PatientResource getPatientResources() {
        return patientResources;
    }

    public void setPatientResources(PatientResource patientResources) {
        this.patientResources = patientResources;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
