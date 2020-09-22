package org.openmrs.module.serverDataTransfer.errors;

import java.util.List;

public class ObsFieldError {
    private List<ErrorMessage> person;
    private List<ErrorMessage> concept;
    private List<ErrorMessage> voided;
    private List<ErrorMessage> value;

    public List<ErrorMessage> getPerson() {
        return person;
    }

    public void setPerson(List<ErrorMessage> person) {
        this.person = person;
    }

    public List<ErrorMessage> getConcept() {
        return concept;
    }

    public void setConcept(List<ErrorMessage> concept) {
        this.concept = concept;
    }

    public List<ErrorMessage> getVoided() {
        return voided;
    }

    public void setVoided(List<ErrorMessage> voided) {
        this.voided = voided;
    }

    public List<ErrorMessage> getValue() {
        return value;
    }

    public void setValue(List<ErrorMessage> value) {
        this.value = value;
    }
}
