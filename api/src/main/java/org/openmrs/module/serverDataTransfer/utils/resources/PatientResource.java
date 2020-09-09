package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PatientResource implements Serializable {
    /**
     * The uuid of the person which patient refer
     */
    private PersonResourceUpdate person;
    private Set<IdentifierResource> identifiers;

    public PatientResource() {
        identifiers = new HashSet<IdentifierResource>(Collections.<IdentifierResource>emptyList());
    }

    public PersonResourceUpdate getPerson() {
        return person;
    }

    public void setPerson(PersonResourceUpdate person) {
        this.person = person;
    }

    public Set<IdentifierResource> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Set<IdentifierResource> identifiers) {
        this.identifiers = identifiers;
    }

    public PatientResource setPatient(Patient patient) {
//        setPerson(patient.getUuid());
        Set<PatientIdentifier> patientIdentifiers = patient.getIdentifiers();
        for (PatientIdentifier patientIdentifier : patientIdentifiers) {
            IdentifierResource identifierResource = new IdentifierResource();
            identifierResource.setIdentifier(patientIdentifier);
            addIdentifier(identifierResource);
        }
//        PersonResource personResource = new PersonResource().setPerson(patient);
        setPerson(new PersonResourceUpdate().setPerson(patient));

        return this;
    }

    public void addIdentifier(IdentifierResource identifierResource) {
        getIdentifiers().add(identifierResource);
    }
}
