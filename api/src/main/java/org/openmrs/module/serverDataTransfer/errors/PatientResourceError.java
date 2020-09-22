package org.openmrs.module.serverDataTransfer.errors;

public class PatientResourceError extends ParentResourceError {
    private PatientFieldError fieldErrors;

    public PatientFieldError getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(PatientFieldError fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
