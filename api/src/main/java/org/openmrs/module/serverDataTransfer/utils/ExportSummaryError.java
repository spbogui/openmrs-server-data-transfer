package org.openmrs.module.serverDataTransfer.utils;

import java.io.Serializable;

public class ExportSummaryError implements Serializable {
    private String message;
    private String code;
    private String detail;

    public ExportSummaryError() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
