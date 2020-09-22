package org.openmrs.module.serverDataTransfer.errors;

public class EncounterError {
    private EncounterResourceError error;

    public EncounterResourceError getError() {
        return error;
    }

    public void setError(EncounterResourceError error) {
        this.error = error;
    }
}
