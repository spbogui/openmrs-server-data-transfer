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
package org.openmrs.module.serverDataTransfer.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.api.db.ServerDataTransferDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * It is a default implementation of  {@link ServerDataTransferDAO}.
 */
public class HibernateServerDataTransferDAO implements ServerDataTransferDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

	@Override
	public void prepareDataToTransfer(Date lastTransferDate) {
		List<Patient> patients = new ArrayList<>();

	}

	@Override
	public Server createServer(Server server) {
		sessionFactory.getCurrentSession().saveOrUpdate(server);
		return server;
	}

	@Override
	public Server updateServer(Server server) {
		sessionFactory.getCurrentSession().saveOrUpdate(server);
		return server;
	}

	@Override
	public List<Server> getAllServer() {
		return (List<Server>) sessionFactory.getCurrentSession().createCriteria(Server.class).list();
	}

	@Override
	public void removeServer(Server server) {
		sessionFactory.getCurrentSession().delete(server);
	}

	@Override
	public Server getOneServer(Integer serverId) {
		return (Server) sessionFactory.getCurrentSession().get(Server.class, serverId);
	}

	@Override
	public ServerDataTransfer createServerData(ServerDataTransfer data) {
		sessionFactory.getCurrentSession().saveOrUpdate(data);
		return data;
	}

	@Override
	public ServerDataTransfer updateServerData(ServerDataTransfer data) {
		sessionFactory.getCurrentSession().saveOrUpdate(data);
		return data;
	}

	@Override
	public List<ServerDataTransfer> getAllServerData() {
		return (List<ServerDataTransfer>) sessionFactory.getCurrentSession().createCriteria(ServerDataTransfer.class).list();
	}

	@Override
	public ServerDataTransfer getOneServerData(Integer serverDataId) {
		return (ServerDataTransfer) sessionFactory.getCurrentSession().get(Server.class, serverDataId);
	}

	@Override
	public boolean transferData() {

		return false;
	}
}
