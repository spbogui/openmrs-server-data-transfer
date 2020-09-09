package org.openmrs.module.serverDataTransfer.utils;

import org.openmrs.module.serverDataTransfer.utils.actions.*;
import org.openmrs.module.serverDataTransfer.utils.resources.*;

import java.io.Serializable;
import java.util.*;

public class DataTransferResourceModel implements Serializable {
    private Date creationDate;
    private Set<PersonResourceAction> personResourceActions;
    private Set<PatientResourceAction> patientResourceActions;
    private Set<EncounterResourceAction> encounterResourceActions;
    private Set<ObsResourceAction> obsResourceActions;

    public DataTransferResourceModel() {
        creationDate = new Date();
        personResourceActions = new HashSet<PersonResourceAction>(Collections.<PersonResourceAction>emptyList());
        patientResourceActions = new HashSet<PatientResourceAction>(Collections.<PatientResourceAction>emptyList());
        encounterResourceActions = new HashSet<EncounterResourceAction>(Collections.<EncounterResourceAction>emptyList());
        obsResourceActions = new HashSet<ObsResourceAction>(Collections.<ObsResourceAction>emptyList());
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<PersonResourceAction> getPersonResourceActions() {
        return personResourceActions;
    }

    public void setPersonResourceActions(Set<PersonResourceAction> personResourceActions) {
        this.personResourceActions = personResourceActions;
    }

    public Set<PatientResourceAction> getPatientResourceActions() {
        return patientResourceActions;
    }

    public void setPatientResourceActions(Set<PatientResourceAction> patientResourceActions) {
        this.patientResourceActions = patientResourceActions;
    }

    public Set<EncounterResourceAction> getEncounterResourceActions() {
        return encounterResourceActions;
    }

    public void setEncounterResourceActions(Set<EncounterResourceAction> encounterResourceActions) {
        this.encounterResourceActions = encounterResourceActions;
    }

    public Set<ObsResourceAction> getObsResourceActions() {
        return obsResourceActions;
    }

    public void setObsResourceActions(Set<ObsResourceAction> obsResourceActions) {
        this.obsResourceActions = obsResourceActions;
    }

    public void addPersonResourceAction(PersonResourceAction personResourceAction) {
        getPersonResourceActions().add(personResourceAction);
    }

    public void addPatientResourceAction(PatientResourceAction patientResourceAction) {
        getPatientResourceActions().add(patientResourceAction);
    }

    public void addEncounterResourceAction(EncounterResourceAction encounterResourceAction) {
        getEncounterResourceActions().add(encounterResourceAction);
    }

    public void addObsResourceAction(ObsResourceAction obsResourceAction) {
        getObsResourceActions().add(obsResourceAction);
    }
}
