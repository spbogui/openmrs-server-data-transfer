package org.openmrs.module.serverDataTransfer.errors;

public class EncounterResourceError extends ParentResourceError {
    private EncounterFieldError fieldErrors;

    public EncounterFieldError getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(EncounterFieldError fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
