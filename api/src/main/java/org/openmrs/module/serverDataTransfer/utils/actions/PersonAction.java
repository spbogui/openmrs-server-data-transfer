package org.openmrs.module.serverDataTransfer.utils.actions;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PersonAction implements Serializable {
    private Set<String> actions;
    private String personUuid;

    public PersonAction() {
        actions = new HashSet<String>(Collections.<String>emptyList());
    }

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    public String getPersonUuid() {
        return personUuid;
    }

    public void setPersonUuid(String personUuid) {
        this.personUuid = personUuid;
    }

    public void addAction(String action) {
        getActions().add(action);
    }
}
