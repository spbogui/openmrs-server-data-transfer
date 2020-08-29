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

import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.api.ServerDataTransferService;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.ConceptResource1_8;

import java.util.Date;
import java.util.List;

/**
 *  Database methods for {@link ServerDataTransferService}.
 */
public interface ServerDataTransferDAO {
	
	/*
	 * Add DAO methods here
	 */
	void prepareDataToTransfer(Date lastTransferDate);

	Server createServer(Server server);
	Server updateServer(Server server);
	List<Server> getAllServer();
	void removeServer(Server server);
	Server getOneServer(Integer serverId);

	ServerDataTransfer createServerData(ServerDataTransfer data);
	ServerDataTransfer updateServerData(ServerDataTransfer data);
	List<ServerDataTransfer> getAllServerData();
	ServerDataTransfer getOneServerData(Integer serverDataId);

	boolean transferData();
}
