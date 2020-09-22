package org.openmrs.module.serverDataTransfer.errors;

public class ObsError {
    private ObsResourceError error;

    public ObsResourceError getError() {
        return error;
    }

    public void setError(ObsResourceError error) {
        this.error = error;
    }
}
