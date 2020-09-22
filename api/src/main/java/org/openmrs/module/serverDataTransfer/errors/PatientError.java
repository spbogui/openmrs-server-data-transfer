package org.openmrs.module.serverDataTransfer.errors;

public class PatientError {
    private PatientResourceError error;

    public PatientResourceError getError() {
        return error;
    }

    public void setError(PatientResourceError error) {
        this.error = error;
    }
}
