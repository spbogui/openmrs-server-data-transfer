package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.EncounterProvider;

import java.io.Serializable;

public class EncounterProviderResource implements Serializable {
    private String provider;
    private String encounterRole;

    public EncounterProviderResource() {
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getEncounterRole() {
        return encounterRole;
    }

    public void setEncounterRole(String encounterRole) {
        this.encounterRole = encounterRole;
    }

    public void setEncounterProvider(EncounterProvider encounterProvider) {
        setEncounterRole(encounterProvider.getEncounterRole().getUuid());
        setProvider(encounterProvider.getProvider().getUuid());
    }
}
