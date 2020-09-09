package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.module.serverDataTransfer.utils.resources.ObsResource;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ObsResourceAction implements Serializable {
    private Set<String> actions;
    private ObsResource obsResource;

    public ObsResourceAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public ObsResource getObsResource() {
        return obsResource;
    }

    public void setObsResource(ObsResource obsResource) {
        this.obsResource = obsResource;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
