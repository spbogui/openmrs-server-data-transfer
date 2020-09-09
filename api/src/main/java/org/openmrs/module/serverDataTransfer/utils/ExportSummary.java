package org.openmrs.module.serverDataTransfer.utils;

import java.io.Serializable;

public class ExportSummary implements Serializable {
    private ExportSummaryError error;

    public ExportSummary() {
    }

    public ExportSummaryError getError() {
        return error;
    }

    public void setError(ExportSummaryError error) {
        this.error = error;
    }
}
