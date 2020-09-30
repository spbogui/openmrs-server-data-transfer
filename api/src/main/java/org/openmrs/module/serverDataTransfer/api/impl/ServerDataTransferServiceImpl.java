/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.serverDataTransfer.api.impl;

import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.api.ServerDataTransferService;
import org.openmrs.module.serverDataTransfer.api.db.ServerDataTransferDAO;
import org.openmrs.module.serverDataTransfer.utils.DataTransferResourceModel;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.EncounterResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.PatientResult;

import java.io.IOException;
import java.util.List;

/**
 * It is a default implementation of {@link ServerDataTransferService}.
 */
public class ServerDataTransferServiceImpl extends BaseOpenmrsService implements ServerDataTransferService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private ServerDataTransferDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(ServerDataTransferDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public ServerDataTransferDAO getDao() {
	    return dao;
    }

    @Override
    public void prepareData(Server server) {
        dao.prepareData(server);
    }

    @Override
    public void prepareDataToTransfer(Integer serverId) {
        dao.prepareDataToTransfer(serverId);
    }

    @Override
    public Server createServer(Server server) {
        return dao.createServer(server);
    }

    @Override
    public Server updateServer(Server server) {
        return dao.updateServer(server);
    }

    @Override
    public List<Server> getAllServer() {
        return dao.getAllServer();
    }

    @Override
    public void removeServer(Server server) {
        dao.removeServer(server);
    }

    @Override
    public Server getOneServer(Integer serverId) {
        return dao.getOneServer(serverId);
    }

    @Override
    public ServerDataTransfer createServerData(ServerDataTransfer data) {
        return dao.createServerData(data);
    }

    @Override
    public ServerDataTransfer updateServerData(ServerDataTransfer data) {
        return dao.updateServerData(data);
    }

    @Override
    public List<ServerDataTransfer> getAllServerData() {
        return dao.getAllServerData();
    }

    @Override
    public List<ServerDataTransfer> getAllServerDataByServer(Integer serverId) {
        return dao.getAllServerDataByServer(serverId);
    }

    @Override
    public List<ServerDataTransfer> getAllServerDataNoTransferByServer(Integer serverId) {
        return dao.getAllServerDataNoTransferByServer(serverId);
    }

    @Override
    public List<ServerDataTransfer> getAllServerDataTransferredByServer(Integer serverId) {
        return dao.getAllServerDataTransferredByServer(serverId);
    }

    @Override
    public ServerDataTransfer getOneServerData(Integer serverDataId) {
        return dao.getOneServerData(serverDataId);
    }

    @Override
    public boolean transferData(Server server, String endPoint, ServerDataTransfer data) throws IOException, IllegalAccessException {
        return dao.transferData(server, endPoint, data);
    }

    @Override
    public String postDataToServer(Server server, String endPoint, DataTransferResourceModel data) {
        return null;
    }

    @Override
    public String postData(String url, String username, String password, String resource, String data) {
        return dao.postData(url, username, password, resource, data);
    }

    @Override
    public String deleteData(String url, String username, String password, String resource, String resourceUuid) {
        return dao.deleteData(url, username, password, resource, resourceUuid);
    }

    @Override
    public boolean testServerDetails(String url, String user, String pass) {
        return dao.testServerDetails(url, user, pass);
    }

    @Override
    public List<Patient> getPatientWithNoAge() {
        return dao.getPatientWithNoAge();
    }

    @Override
    public List<Patient> getPatientWithNoName() {
        return dao.getPatientWithNoName();
    }

    @Override
    public List<Patient> getPatientDeathWithNoCauseOfDeath() {
        return dao.getPatientDeathWithNoCauseOfDeath();
    }

    @Override
    public List<Patient> getPatentWithNonUniqueIdentifier() {
        return dao.getPatentWithNonUniqueIdentifier();
    }

    @Override
    public List<Patient> getPatientWithoutGender() {
        return dao.getPatientWithoutGender();
    }

    @Override
    public List<PatientResult> findPatientOnServer(String identifier, Server server) throws IOException {
        return dao.findPatientOnServer(identifier, server);
    }

    @Override
    public Patient findLocalPatientByIdentifier(String identifier) {
        return dao.findLocalPatientByIdentifier(identifier);
    }

    @Override
    public EncounterResult getLatestAdmission(String patientUuid, Server server) throws IOException {
        return dao.getLatestAdmission(patientUuid, server);
    }

    @Override
    public EncounterResult getLatestOutFromCare(String patientUuid, Server server) throws IOException {
        return dao.getLatestOutFromCare(patientUuid, server);
    }

    @Override
    public List<ServerDataTransfer> getAllServerDataSendingByServer() {
        return dao.getAllServerDataSendingByServer();
    }

}
