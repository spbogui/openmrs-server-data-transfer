package org.openmrs.module.serverDataTransfer.utils;

import org.openmrs.module.serverDataTransfer.utils.actions.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DataTransferModelUUID implements Serializable {
    private Date creationDate;
    private Set<PersonAction> personActions;
    private Set<PatientAction> patientActions;
    private Set<EncounterAction> encounterActions;
    private Set<ObsAction> obsActions;

    public DataTransferModelUUID() {
        personActions = new HashSet<PersonAction>(Collections.<PersonAction>emptyList());
        patientActions = new HashSet<PatientAction>(Collections.<PatientAction>emptyList());
        encounterActions = new HashSet<EncounterAction>(Collections.<EncounterAction>emptyList());
        obsActions = new HashSet<ObsAction>(Collections.<ObsAction>emptyList());
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<PersonAction> getPersonActions() {
        return personActions;
    }

    public void setPersonActions(Set<PersonAction> personActions) {
        this.personActions = personActions;
    }

    public Set<PatientAction> getPatientActions() {
        return patientActions;
    }

    public void setPatientActions(Set<PatientAction> patientActions) {
        this.patientActions = patientActions;
    }

    public Set<EncounterAction> getEncounterActions() {
        return encounterActions;
    }

    public void setEncounterActions(Set<EncounterAction> encounterActions) {
        this.encounterActions = encounterActions;
    }

    public Set<ObsAction> getObsActions() {
        return obsActions;
    }

    public void setObsActions(Set<ObsAction> obsActions) {
        this.obsActions = obsActions;
    }

    public void addPersonResourceAction(PersonAction personResourceAction) {
        getPersonActions().add(personResourceAction);
    }

    public void addPatientResourceAction(PatientAction patientResourceAction) {
        getPatientActions().add(patientResourceAction);
    }

    public void addEncounterResourceAction(EncounterAction encounterResourceAction) {
        getEncounterActions().add(encounterResourceAction);
    }

    public void addObsResourceAction(ObsAction obsResourceAction) {
        getObsActions().add(obsResourceAction);
    }

    public int getResourceSize() {
        return getEncounterActions().size() + getPersonActions().size()
                + getPatientActions().size() + getObsActions().size();
    }

    public void clearResource() {
        getObsActions().clear();
        getEncounterActions().clear();
        getPatientActions().clear();
        getPersonActions().clear();
        setCreationDate(new Date());
    }

//    public String getSizeByResource() {
//        return "Total : " + getResourceSize() + ", Obs : " + getObsActions().size() + ", Encounters " + getEncounterActions().size() + ", " +
//                "Patients : " + getPatientActions().size() + ", Persons : " + getPersonActions().size();
//    }
}
