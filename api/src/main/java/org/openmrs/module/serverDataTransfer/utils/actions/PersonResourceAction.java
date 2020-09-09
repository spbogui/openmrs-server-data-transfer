package org.openmrs.module.serverDataTransfer.utils.actions;

import org.openmrs.module.serverDataTransfer.utils.resources.PersonResource;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PersonResourceAction implements Serializable {
    private Set<String> actions;
    private PersonResource personResource;

    public PersonResourceAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public PersonResource getPersonResource() {
        return personResource;
    }

    public void setPersonResource(PersonResource personResource) {
        this.personResource = personResource;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
