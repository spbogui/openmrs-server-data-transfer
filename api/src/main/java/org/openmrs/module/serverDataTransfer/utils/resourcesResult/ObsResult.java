package org.openmrs.module.serverDataTransfer.utils.resourcesResult;

public class ObsResult {
    private String display;
    private String uuid;
    private ConceptResult concept;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ConceptResult getConcept() {
        return concept;
    }

    public void setConcept(ConceptResult concept) {
        this.concept = concept;
    }
}
