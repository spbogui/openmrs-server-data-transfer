package org.openmrs.module.serverDataTransfer.utils.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openmrs.Encounter;
import org.openmrs.EncounterProvider;
import org.openmrs.Obs;
import org.openmrs.module.serverDataTransfer.utils.Tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EncounterResource implements Serializable {
    private String patient;
    private String encounterType;
    private String encounterDatetime;
    private String location;
    private String form;
    private Set<EncounterProviderResource> encounterProviders;
    private Set<ObsResource> obs;
    private String uuid;

    public EncounterResource() {
        obs  = new HashSet<ObsResource>(Collections.<ObsResource>emptyList());
        encounterProviders = new HashSet<EncounterProviderResource>(Collections.<EncounterProviderResource>emptyList());
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(String encounterType) {
        this.encounterType = encounterType;
    }

    public String getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(String encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Set<EncounterProviderResource> getEncounterProviders() {
        return encounterProviders;
    }

    public void setEncounterProviders(Set<EncounterProviderResource> encounterProviders) {
        this.encounterProviders = encounterProviders;
    }


    public Set<ObsResource> getObs() {
        return obs;
    }

    public void setObs(Set<ObsResource> obs) {
        this.obs = obs;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public EncounterResource setEncounter(Encounter encounter) throws JsonProcessingException {
        setEncounterDatetime(Tools.formatDateToString(encounter.getEncounterDatetime(), Tools.DATE_FORMAT_YYYY_MM_DD));
        setEncounterType(encounter.getEncounterType().getUuid());
        setPatient(encounter.getPatient().getUuid());
        setLocation(encounter.getLocation().getUuid());
        setForm(encounter.getForm().getUuid());
        setUuid(encounter.getUuid());

        Set<Obs> obsList = encounter.getAllObs();
        for (Obs obs: obsList) {
            ObsResource obsResource = new ObsResource();
            obsResource.setObs(obs);
            this.addObsResource(obsResource);
        }

        Set<EncounterProvider> encounterProviders = encounter.getEncounterProviders();
        for (EncounterProvider encounterProvider : encounterProviders) {
            EncounterProviderResource encounterProviderResource = new EncounterProviderResource();
            encounterProviderResource.setEncounterProvider(encounterProvider);
            addEncounterProviderResource(encounterProviderResource);
        }
        return this;
    }

    public void addObsResource(ObsResource obsResource) {
        obs.add(obsResource);
    }

    public void addEncounterProviderResource(EncounterProviderResource encounterProviderResource) {
        encounterProviders.add(encounterProviderResource);
    }
}
