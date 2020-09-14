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
package org.openmrs.module.serverDataTransfer.api.db;

import org.openmrs.Patient;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.api.ServerDataTransferService;
import org.openmrs.module.serverDataTransfer.utils.DataTransferModelUUID;
import org.openmrs.module.serverDataTransfer.utils.DataTransferResourceModel;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.EncounterResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.PatientResult;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.ConceptResource1_8;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *  Database methods for {@link ServerDataTransferService}.
 */
public interface ServerDataTransferDAO {
	
	/*
	 * Add DAO methods here
	 */
	void prepareDataToTransfer(Integer serverId);

	void prepareData(Server server);

	Server createServer(Server server);
	Server updateServer(Server server);
	List<Server> getAllServer();
	void removeServer(Server server);
	Server getOneServer(Integer serverId);

	ServerDataTransfer createServerData(ServerDataTransfer data);
	ServerDataTransfer updateServerData(ServerDataTransfer data);
	List<ServerDataTransfer> getAllServerData();
	List<ServerDataTransfer> getAllServerDataByServer(Integer serverId);
	List<ServerDataTransfer> getAllServerDataNoTransferByServer(Integer serverId);
	List<ServerDataTransfer> getAllServerDataTransferredByServer(Integer serverId);
	ServerDataTransfer getOneServerData(Integer serverDataId);

    boolean transferData(Server server, String endPoint, ServerDataTransfer data) throws IOException;

    String postDataToServer(Server server, String endPoint, DataTransferResourceModel data);

	String postData(String url, String username, String password, String resource, String data);

	String deleteData(String url, String username, String password, String resource, String resourceUuid);

	public boolean testServerDetails(String url, String user, String pass);

	List<Patient> getPatientWithNoAge();
	List<Patient> getPatientWithNoName();
	List<Patient> getPatientDeathWithNoCauseOfDeath();
	List<Patient> getPatentWithNonUniqueIdentifier();
	List<Patient> getPatientWithoutGender();

	List<PatientResult> findPatientOnServer(String identifier, Server server) throws IOException;
	EncounterResult getLatestAdmission(String patientUuid, Server server) throws IOException;
	EncounterResult getLatestOutFromCare(String patientUuid, Server server) throws IOException;
	List<ServerDataTransfer> getAllServerDataSendingByServer();
}
