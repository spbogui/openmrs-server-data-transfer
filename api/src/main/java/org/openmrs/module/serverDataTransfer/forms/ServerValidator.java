package org.openmrs.module.serverDataTransfer.forms;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ServerValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ServerForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ServerForm form = (ServerForm) o;
        if (form == null) {
            errors.reject("serverDataTransfer", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "serverName", "Ce champ est requi");
            ValidationUtils.rejectIfEmpty(errors, "serverUrl", "Ce champ est requi");
            ValidationUtils.rejectIfEmpty(errors, "username", "Ce champ est requi");
            ValidationUtils.rejectIfEmpty(errors, "password", "Ce champ est requi");
        }
    }
}
