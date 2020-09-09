package org.openmrs.module.serverDataTransfer.utils.resourcesResult;

public class IdentifierResult {
    private String display;
    private boolean preferred;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
}
