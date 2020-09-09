package org.openmrs.module.serverDataTransfer.utils;

import org.openmrs.module.serverDataTransfer.utils.actions.*;

import java.io.Serializable;
import java.util.*;

public class DataTransferModel implements Serializable {
    private Date creationDate;
    private Set<PersonAction> personActions = Collections.EMPTY_SET;
    private Set<AddressAction> addressActions = Collections.EMPTY_SET;
    private Set<PatientAction> patientActions = Collections.EMPTY_SET;
    private Set<IdentifierAction> identifierActions = Collections.EMPTY_SET;
    private Set<PatientAttributeAction> patientAttributeActions = Collections.EMPTY_SET;
    private Set<EncounterAction> encounterActions = Collections.EMPTY_SET;
    private Set<ObsAction> obsActions = Collections.EMPTY_SET;

    public DataTransferModel() {
    }

    public DataTransferModel(Date creationDate) {
        this.creationDate = creationDate;
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

    public Set<AddressAction> getAddressActions() {
        return addressActions;
    }

    public void setAddressActions(Set<AddressAction> addressActions) {
        this.addressActions = addressActions;
    }

    public Set<PatientAction> getPatientActions() {
        return patientActions;
    }

    public void setPatientActions(Set<PatientAction> patientActions) {
        this.patientActions = patientActions;
    }

    public Set<IdentifierAction> getIdentifierActions() {
        return identifierActions;
    }

    public void setIdentifierActions(Set<IdentifierAction> identifierActions) {
        this.identifierActions = identifierActions;
    }

    public Set<PatientAttributeAction> getPatientAttributeActions() {
        return patientAttributeActions;
    }

    public void setPatientAttributeActions(Set<PatientAttributeAction> patientAttributeActions) {
        this.patientAttributeActions = patientAttributeActions;
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
}
