package org.openmrs.module.serverDataTransfer.utils.actions;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EncounterAction implements Serializable {
    private Set<String> actions;
    private String encounterUuid;

    public EncounterAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public String getEncounterUuid() {
        return encounterUuid;
    }

    public void setEncounterUuid(String encounterUuid) {
        this.encounterUuid = encounterUuid;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
