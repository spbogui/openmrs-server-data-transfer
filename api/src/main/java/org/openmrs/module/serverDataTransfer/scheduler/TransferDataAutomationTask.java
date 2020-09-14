package org.openmrs.module.serverDataTransfer.scheduler;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.api.ServerDataTransferService;
import org.openmrs.module.serverDataTransfer.utils.enums.Status;
import org.openmrs.scheduler.tasks.AbstractTask;

import java.io.IOException;
import java.util.List;

public class TransferDataAutomationTask extends AbstractTask {
    @Override
    public void execute() {
        boolean canPrepareData = false;
        boolean canContinue = true;


        if (Context.getService(ServerDataTransferService.class).getAllServerDataSendingByServer().size() != 0) {

            List<Patient> patientsWithoutName = Context.getService(ServerDataTransferService.class).getPatientWithNoName();
            List<Patient> patientsWithoutBirthDate = Context.getService(ServerDataTransferService.class).getPatientWithNoAge();
            List<Patient> patientsWithoutDeadInfo = Context.getService(ServerDataTransferService.class).getPatientDeathWithNoCauseOfDeath();
            List<Patient> patientsWithoutUniqueIdentifier = Context.getService(ServerDataTransferService.class).getPatentWithNonUniqueIdentifier();
            List<Patient> patientsWithoutGender = Context.getService(ServerDataTransferService.class).getPatientWithoutGender();

            if (patientsWithoutBirthDate.size() == 0 && patientsWithoutDeadInfo.size() == 0 &&
                    patientsWithoutName.size() == 0 && patientsWithoutUniqueIdentifier.size() == 0 && patientsWithoutGender.size() == 0) {
                canPrepareData = true;
            }

            if (canPrepareData) {
                for (Server server : Context.getService(ServerDataTransferService.class).getAllServer()) {
                    if (!Context.getService(ServerDataTransferService.class).testServerDetails(server.getServerUrl(), server.getUsername(), server.getPassword()))
                    {
                        return;
                    }
                    List<ServerDataTransfer> serverDataTransfers = Context.getService(ServerDataTransferService.class).getAllServerDataNoTransferByServer(server.getServerId());

                    if (serverDataTransfers != null && serverDataTransfers.size() != 0) {
                        for (ServerDataTransfer data : serverDataTransfers) {
                            try {
                                canContinue = Context.getService(ServerDataTransferService.class).transferData(server, "", data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (!canContinue) {
                                break;
                            }
                        }
                    } else {
                        Context.getService(ServerDataTransferService.class).prepareData(server);
                    }
                }
            }
        }

    }
}
