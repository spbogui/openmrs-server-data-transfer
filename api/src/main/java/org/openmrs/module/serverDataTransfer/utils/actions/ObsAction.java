package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.Obs;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ObsAction implements Serializable {
    private Set<String> actions;
    private String obs;

    public ObsAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
