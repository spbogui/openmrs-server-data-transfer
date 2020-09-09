package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.module.serverDataTransfer.utils.resources.EncounterResource;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EncounterResourceAction implements Serializable {
    private Set<String> actions;
    private EncounterResource encounterResource;

    public EncounterResourceAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public EncounterResource getEncounterResource() {
        return encounterResource;
    }

    public void setEncounterResource(EncounterResource encounterResource) {
        this.encounterResource = encounterResource;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
