package org.openmrs.module.serverDataTransfer.utils;

import org.openmrs.Patient;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.PatientResult;

import java.util.ArrayList;
import java.util.List;

public class ResourceResult {
    List<PatientResult> results = new ArrayList<PatientResult>();

    public List<PatientResult> getResults() {
        return results;
    }

    public void setResults(List<PatientResult> results) {
        this.results = results;
    }
}
