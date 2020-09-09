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
package org.openmrs.module.serverDataTransfer.api;

import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.utils.DataTransferResourceModel;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.EncounterResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.PatientResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(ServerDataTransferService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface ServerDataTransferService extends OpenmrsService {
     
	/*
	 * Add service methods here
	 * 
	 */
	void prepareData(Server server);
	void prepareDataToTransfer(Integer serverId);

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
	ServerDataTransfer getOneServerData(Integer serverDataId);

	boolean transferData(Server server, String endPoint, ServerDataTransfer data) throws IOException;

	String postDataToServer(Server server, String endPoint, DataTransferResourceModel data);
	String postData(String url, String username, String password, String resource, String data);
	String deleteData(String url, String username, String password, String resource, String resourceUuid);
	boolean testServerDetails(String url, String user, String pass);

	List<Patient> getPatientWithNoAge();
	List<Patient> getPatientWithNoName();
	List<Patient> getPatientDeathWithNoCauseOfDeath();
	List<Patient> getPatentWithNonUniqueIdentifier();

	List<Patient> getPatientWithoutGender();

	List<PatientResult> findPatientOnServer(String identifier, Server server) throws IOException;
	EncounterResult getLatestAdmission(String patientUuid, Server server) throws IOException;
	EncounterResult getLatestOutFromCare(String patientUuid, Server server) throws IOException;
}
