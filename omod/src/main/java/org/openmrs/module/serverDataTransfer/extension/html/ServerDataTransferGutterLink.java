package org.openmrs.module.serverDataTransfer.extension.html;

import org.openmrs.api.context.Context;
import org.openmrs.module.web.extension.LinkExt;

public class ServerDataTransferGutterLink extends LinkExt {

    @Override
    public String getLabel() {
        return Context.getMessageSourceService().getMessage("serverDataTransfer.gutterTitle");
    }

    @Override
    public String getUrl() {
        return "module/serverDataTransfer/manage.form";
    }

    @Override
    public String getRequiredPrivilege() {
        return "Manage Data Transfer";
    }
}
