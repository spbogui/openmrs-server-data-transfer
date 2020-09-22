package org.openmrs.module.serverDataTransfer.errors;

public class ObsResourceError extends ParentResourceError {
    ObsFieldError fieldErrors;

    public ObsFieldError getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(ObsFieldError fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
