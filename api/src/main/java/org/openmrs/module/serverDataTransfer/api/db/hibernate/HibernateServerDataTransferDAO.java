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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.api.db.ServerDataTransferDAO;
import org.openmrs.module.serverDataTransfer.utils.*;
import org.openmrs.module.serverDataTransfer.utils.actions.*;
import org.openmrs.module.serverDataTransfer.utils.enums.Action;
import org.openmrs.module.serverDataTransfer.utils.enums.Status;
import org.openmrs.module.serverDataTransfer.utils.resources.*;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.EncounterResourceResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.EncounterResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.PatientResult;
import org.openmrs.notification.Alert;
import org.openmrs.notification.AlertRecipient;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.Security;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

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
	public void prepareData(Server server) {
		if (server != null) {
			int count = 0;
			int mod = 10000;
			Date lastDate = new Date();
			Date firstDate = getLatestDatePoint(getOneServer(1));
			boolean canCreate = false;

			System.out.println("Loading data form database ");

			String whereString = "e.dateCreated <= :lastDate OR e.dateVoided <= :lastDate";
			if (firstDate != null) {
				whereString = "(e.dateCreated BETWEEN :lastDate AND :firstDate) OR (e.dateVoided BETWEEN :lastDate AND :firstDate)";
			}

			String changedQueryString = (firstDate == null) ? " OR e.dateChanged<= :lastDate" : " OR (e.dateCreated BETWEEN :lastDate AND :firstDate)";
			Query encounterQuery = sessionFactory.getCurrentSession().createQuery("FROM Encounter e WHERE " + whereString + changedQueryString)
					.setParameter("lastDate", lastDate);

			Query patientQuery = sessionFactory.getCurrentSession().createQuery("FROM Patient e WHERE " + whereString + changedQueryString + "  AND e.patientId NOT IN (SELECT u.person.personId FROM User u)")
					.setParameter("lastDate", lastDate);

			Query patientIdentifierQuery = sessionFactory.getCurrentSession().createQuery("FROM PatientIdentifier e WHERE " + whereString + changedQueryString)
					.setParameter("lastDate", lastDate);

			if (firstDate != null) {
//				obsQuery.setParameter("firstDate", firstDate);
				encounterQuery.setParameter("firstDate", firstDate);
				patientQuery.setParameter("firstDate", firstDate);
				patientIdentifierQuery.setParameter("firstDate", firstDate);
			}

//			DataTransferResourceModel resourceModel = new DataTransferResourceModel();
			DataTransferModelUUID resourceModel = new DataTransferModelUUID();

			List<Patient> people = (List<Patient>) patientQuery.list();

			for (PatientIdentifier patientIdentifier: (List<PatientIdentifier>) patientIdentifierQuery.list()) {
				if (!people.contains(patientIdentifier.getPatient())){
					people.add(patientIdentifier.getPatient());
				}
			}

			System.out.println("Number of patient to import : " + people.size());

			if (!people.isEmpty()) {
				count = 0;

				System.out.println("Beginning the creation of encounters to transfer at : " + new Date().toString());
				for (Patient patient : people) {
					count++;

//					PatientAction patientResourceAction = new PatientAction();
					PersonAction personResourceAction = new PersonAction();

					if (firstDate == null) {
						personResourceAction.addAction(Action.SAVE.name());
//						patientResourceAction.addAction(Action.SAVE.name());
						if (patient.getDateVoided() != null) {
//							patientResourceAction.addAction(Action.DELETE.name());
							personResourceAction.addAction(Action.DELETE.name());
						}
					} else {
						if (patient.getDateCreated().after(firstDate)){
//							patientResourceAction.addAction(Action.SAVE.name());
							personResourceAction.addAction(Action.SAVE.name());
						}
						if (patient.getDateChanged().after(firstDate)) {
//							patientResourceAction.addAction(Action.UPDATE.name());
							personResourceAction.addAction(Action.UPDATE.name());
						}
						if (patient.getDateVoided().after(firstDate)) {
							personResourceAction.addAction(Action.DELETE.name());
//							patientResourceAction.addAction(Action.DELETE.name());
						}
					}
					personResourceAction.setPersonUuid(patient.getUuid());
//					patientResourceAction.setPatientUuid(patient.getUuid());

//					resourceModel.addPatientResourceAction(patientResourceAction);
					resourceModel.addPersonResourceAction(personResourceAction);

					if (resourceModel.getResourceSize() > mod || count == people.size()) {
						System.out.println("Saving data at : " + new Date().toString());
						ServerDataTransfer serverDataTransfer = new ServerDataTransfer();
						serverDataTransfer.setContent(Tools.createByteFromObject(resourceModel));
						serverDataTransfer.setDateCreated(lastDate);
						serverDataTransfer.setServer(server);
						createServerData(serverDataTransfer);

						resourceModel.clearResource();
					}
				}
			}

			System.out.println("Beginning the creation of encounters to transfer at : " + new Date().toString());
			count = 0;
			List<Encounter> encounters = (List<Encounter>) encounterQuery.list();
			for (Encounter encounter : encounters) {
				count++;

				EncounterAction encounterResourceAction = new EncounterAction();
//				EncounterResourceAction encounterResourceAction = new EncounterResourceAction();

				if (firstDate == null) {
					encounterResourceAction.addAction(Action.SAVE.name());
				} else {
					// Obs resources
					if (encounter.getDateCreated().after(firstDate)){
						encounterResourceAction.addAction(Action.SAVE.name());
					}
					if (encounter.getDateChanged().after(firstDate)) {
						encounterResourceAction.addAction(Action.UPDATE.name());
					}
					if (encounter.getDateVoided().after(firstDate)) {
						encounterResourceAction.addAction(Action.DELETE.name());
					}
				}
				encounterResourceAction.setEncounterUuid(encounter.getUuid());

				resourceModel.addEncounterResourceAction(encounterResourceAction);

				count = resourceModel.getResourceSize();

				if (resourceModel.getResourceSize() > mod || count == encounters.size()) {
					System.out.println("Saving data at : " + new Date().toString());
					ServerDataTransfer serverDataTransfer = new ServerDataTransfer();
					serverDataTransfer.setContent(Tools.createByteFromObject(resourceModel));
					serverDataTransfer.setDateCreated(lastDate);
					serverDataTransfer.setServer(server);
					createServerData(serverDataTransfer);

					resourceModel.clearResource();
				}
			}

			System.out.println("Beginning the creation of obs to transfer" + new Date().toString());
			boolean stop = false;
			int i = 0;
			int j = 99999;
			while (!stop) {
				stop = true;
				Query obsQuery = sessionFactory.getCurrentSession().createQuery("SELECT e FROM Obs e WHERE  e.encounter = NULL AND " + whereString + "")
						.setParameter("lastDate", lastDate);
				if (firstDate != null) {
					obsQuery.setParameter("firstDate", firstDate);
				}
				for (Obs obs : (List<Obs>) obsQuery.setFirstResult(i).setMaxResults(j).list())
				{
					ObsAction obsResourceAction = new ObsAction();

					if (firstDate == null) {
						obsResourceAction.addAction(Action.SAVE.name());
					} else {
						// Obs resources
						if (obs.getDateCreated().after(firstDate)){
							obsResourceAction.addAction(Action.SAVE.name());
						}
						if (obs.getDateChanged().after(firstDate)) {
							obsResourceAction.addAction(Action.UPDATE.name());
						}
						if (obs.getDateVoided().after(firstDate)) {
							obsResourceAction.addAction(Action.DELETE.name());
						}
					}
					obsResourceAction.setObs(obs.getUuid());

					resourceModel.addObsResourceAction(obsResourceAction);

					if (resourceModel.getResourceSize() > mod) {
						System.out.println("Saving data at : " + new Date().toString());
						ServerDataTransfer serverDataTransfer = new ServerDataTransfer();
						serverDataTransfer.setContent(Tools.createByteFromObject(resourceModel));
						serverDataTransfer.setDateCreated(lastDate);
						serverDataTransfer.setServer(server);
						createServerData(serverDataTransfer);
						resourceModel.clearResource();
					}
					stop = false;
				}

				i = j + 1;
				j += 100000;
			}

			if (resourceModel.getResourceSize() != 0) {
				System.out.println("Saving data at : " + new Date().toString());
				ServerDataTransfer serverDataTransfer = new ServerDataTransfer();
				serverDataTransfer.setContent(Tools.createByteFromObject(resourceModel));
				serverDataTransfer.setDateCreated(lastDate);
				serverDataTransfer.setServer(server);
				createServerData(serverDataTransfer);
			}
		}

		System.out.println("Ended the creation of data to transfer at : " + new Date().toString());
	}

	private Date getLatestDatePoint(Server server){
		ServerDataTransfer serverDataTransfer = (ServerDataTransfer) sessionFactory.getCurrentSession()
				.createQuery("FROM ServerDataTransfer s WHERE s.server = :server  ORDER BY dateCreated DESC")
				.setParameter("server", server)
				.setMaxResults(1).uniqueResult();
		if (serverDataTransfer != null) {
			return serverDataTransfer.getDateCreated();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepareDataToTransfer(Integer serverId) {
//		Date lastDate = new Date();
//		Date firstDate = getLatestDatePoint(getOneServer(1));
//		boolean canCreate = false;
//
//		String whereQuery = "e.dateCreated <= :lastDate OR e.dateCreated <= :lastDate OR e.dateVoided <= :lastDate";
//		if (firstDate != null) {
//			whereQuery = "(e.dateCreated BETWEEN :lastDate AND :firstDate) OR (e.dateCreated BETWEEN :lastDate AND :firstDate) OR (e.dateVoided BETWEEN :lastDate AND :firstDate)";
//		}
//
//		if (getOneServerData(1) != null) {
//			return;
//		}
//
//		Query personQuery = sessionFactory.getCurrentSession()
//				.createQuery("FROM Person e WHERE " + whereQuery)
//				.setParameter("lastDate", lastDate);
//
//		Query personAddressQuery = sessionFactory.getCurrentSession()
//				.createQuery("FROM PersonAddress e WHERE " + whereQuery)
//				.setParameter("lastDate", lastDate);
//
//		Query personAttributeQuery = sessionFactory.getCurrentSession()
//				.createQuery("FROM PersonAttribute e WHERE " + whereQuery)
//				.setParameter("lastDate", lastDate);
//
//		Query patientQuery = sessionFactory.getCurrentSession()
//				.createQuery("FROM Patient e WHERE " + whereQuery)
//				.setParameter("lastDate", lastDate);
//
//		Query patientIdentifierQuery = sessionFactory.getCurrentSession()
//				.createQuery("FROM PatientIdentifier e WHERE " + whereQuery)
//				.setParameter("lastDate", lastDate);
//
//		Query encounterQuery = sessionFactory.getCurrentSession()
//				.createQuery("FROM Encounter e WHERE " + whereQuery)
//				.setParameter("lastDate", lastDate);
//
//		Query obsQuery = sessionFactory.getCurrentSession()
//				.createQuery("FROM Obs e WHERE " + whereQuery)
//				.setParameter("lastDate", lastDate);
//
//		if (firstDate != null) {
//			personQuery.setParameter("firstDate", firstDate);
//			personAddressQuery.setParameter("firstDate", firstDate);
//			personAttributeQuery.setParameter("firstDate", firstDate);
//			patientQuery.setParameter("firstDate", firstDate);
//			patientIdentifierQuery.setParameter("firstDate", firstDate);
//			encounterQuery.setParameter("firstDate", firstDate);
//			obsQuery.setParameter("firstDate", firstDate);
//		}
//
//		DataTransferModel dataTransferModel = new DataTransferModel(lastDate);
//
//		List<Person> persons = personQuery.list();
//		if (!persons.isEmpty()) {
//			canCreate = true;
//			Set<PersonAction> personActions = new HashSet<PersonAction>(Collections.<PersonAction>emptySet());
//			for (Person p :
//					persons) {
//				PersonAction personAction = new PersonAction();
//				if (firstDate == null) {
//					personAction.setActions(Action.SAVE.name());
//				} else {
//					if (p.getDateCreated().after(firstDate))
//						personAction.setActions(Action.SAVE.name());
//					else {
//						if (p.getDateChanged().after(firstDate))
//							personAction.setActions(Action.DELETE.name());
//						else
//							personAction.setActions(Action.SAVE.name());
//					}
//				}
//				personAction.setPerson(p);
//				personActions.add(personAction);
//			}
//
//			dataTransferModel.setPersonActions(personActions);
//		}
//
//		List<PersonAddress> personAddresses = personAddressQuery.list();
//		if (!personAddresses.isEmpty()) {
//			canCreate = true;
//			Set<AddressAction> addressActions = new HashSet<AddressAction>(Collections.<AddressAction>emptySet());
//			for (PersonAddress p :
//					personAddresses) {
//				AddressAction addressAction = new AddressAction();
//				if (firstDate == null) {
//					addressAction.setAction(Action.SAVE.name());
//				} else {
//					if (p.getDateChanged().after(firstDate))
//						addressAction.setAction(Action.SAVE.name());
//					else {
//						if (p.getDateChanged().after(firstDate))
//							addressAction.setAction(Action.DELETE.name());
//						else
//							addressAction.setAction(Action.SAVE.name());
//					}
//				}
//				addressAction.setPersonAddress(p);
//				addressActions.add(addressAction);
//			}
//			dataTransferModel.setAddressActions(addressActions);
//		}
//
//		List<PersonAttribute> personAttributes = personAttributeQuery.list();
//		if (!personAttributes.isEmpty()) {
//			canCreate = true;
//			Set<PatientAttributeAction> patientAttributeActions = new HashSet<PatientAttributeAction>(Collections.<PatientAttributeAction>emptySet());
//			for (PersonAttribute p :
//					personAttributes) {
//				PatientAttributeAction patientAttributeAction = new PatientAttributeAction();
//				if (firstDate == null) {
//					patientAttributeAction.setAction(Action.SAVE.name());
//				} else {
//					if (p.getDateChanged().after(firstDate))
//						patientAttributeAction.setAction(Action.SAVE.name());
//					else {
//						if (p.getDateChanged().after(firstDate))
//							patientAttributeAction.setAction(Action.DELETE.name());
//						else
//							patientAttributeAction.setAction(Action.SAVE.name());
//					}
//				}
//				patientAttributeAction.setPersonAttribute(p);
//				patientAttributeActions.add(patientAttributeAction);
//			}
//			dataTransferModel.setPatientAttributeActions(patientAttributeActions);
//		}
//
//		List<Patient> patients = patientQuery.list();
//		if (!patients.isEmpty()) {
//			canCreate = true;
//			Set<PatientAction> patientActions = new HashSet<PatientAction>(Collections.<PatientAction>emptySet());
//			for (Patient p :
//					patients) {
//				PatientAction patientAction = new PatientAction();
//				if (firstDate == null) {
//					patientAction.setAction(Action.SAVE.name());
//					continue;
//				} else {
//					if (p.getDateCreated().after(firstDate))
//						patientAction.setAction(Action.SAVE.name());
//					else {
//						if (p.getDateChanged().after(firstDate))
//							patientAction.setAction(Action.DELETE.name());
//						else
//							patientAction.setAction(Action.SAVE.name());
//					}
//				}
//				patientAction.setPatient(p);
//				patientActions.add(patientAction);
//			}
//			dataTransferModel.setPatientActions(patientActions);
//		}
//
//
//		List<PatientIdentifier> patientIdentifiers = patientIdentifierQuery.list();
//		if (!patientIdentifiers.isEmpty()) {
//			canCreate = true;
//			Set<IdentifierAction> identifierActions = new HashSet<IdentifierAction>(Collections.<IdentifierAction>emptySet());
//			for (PatientIdentifier p :
//					patientIdentifiers) {
//				IdentifierAction identifierAction = new IdentifierAction();
//				if (firstDate == null) {
//					identifierAction.setAction(Action.SAVE.name());
//				} else {
//					if (p.getDateCreated().after(firstDate))
//						identifierAction.setAction(Action.SAVE.name());
//					else {
//						if (p.getDateChanged().after(firstDate))
//							identifierAction.setAction(Action.DELETE.name());
//						else
//							identifierAction.setAction(Action.SAVE.name());
//					}
//				}
//				identifierAction.setPatientIdentifier(p);
//				identifierActions.add(identifierAction);
//			}
//			dataTransferModel.setIdentifierActions(identifierActions);
//		}
//
//		List<Encounter> encounters = encounterQuery.list();
//		if (!encounters.isEmpty()) {
//			canCreate = true;
//			Set<EncounterAction> encounterActions = new HashSet<EncounterAction>(Collections.<EncounterAction>emptySet());
//			for (Encounter e :
//					encounters) {
//				EncounterAction encounterAction = new EncounterAction();
//				if (firstDate == null) {
//					encounterAction.setAction(Action.SAVE.name());
//				} else {
//					if (e.getDateCreated().after(firstDate))
//						encounterAction.setAction(Action.SAVE.name());
//					else {
//						if (e.getDateChanged().after(firstDate))
//							encounterAction.setAction(Action.DELETE.name());
//						else
//							encounterAction.setAction(Action.SAVE.name());
//					}
//				}
//				encounterAction.setEncounter(e);
//				encounterActions.add(encounterAction);
//			}
//			dataTransferModel.setEncounterActions(encounterActions);
//		}
//
//		List<Obs> obsList = obsQuery.list();
//		if (!obsList.isEmpty()) {
//			canCreate = true;
//			Set<ObsAction> obsActions = new HashSet<ObsAction>(Collections.<ObsAction>emptySet());
//			for (Obs o :
//					obsList) {
//				ObsAction obsAction = new ObsAction();
//				if (firstDate == null) {
//					obsAction.setAction(Action.SAVE.name());
//				} else {
//					if (o.getDateCreated().after(firstDate))
//						obsAction.setAction(Action.SAVE.name());
//					else {
//						if (o.getDateChanged().after(firstDate))
//							obsAction.setAction(Action.DELETE.name());
//						else
//							obsAction.setAction(Action.SAVE.name());
//					}
//				}
//				obsAction.setObs(o);
//				obsActions.add(obsAction);
//			}
//			dataTransferModel.setObsActions(obsActions);
//		}
//		if (canCreate) {
//
//			ServerDataTransfer serverDataTransfer = new ServerDataTransfer();
//
//			Server server = getOneServer(serverId);
//			if (server == null) {
//				server = new Server();
//				server.setServerName("SIGDEP 2 Serveur");
//				server.setPassword("password");
//				server.setServerUrl("http://openmrs.server.ci");
//				server.setUsername("username");
//				createServer(server);
//			}
//
//			serverDataTransfer.setContent(Tools.createByteFromObject(dataTransferModel));
//			serverDataTransfer.setDateCreated(lastDate);
//			serverDataTransfer.setServer(server);
//
//			createServerData(serverDataTransfer);
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//				String json = mapper.writeValueAsString(dataTransferModel);
//				System.out.println("ResultingJSONString = " + json);
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//		}

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
	public List<ServerDataTransfer> getAllServerDataByServer(Integer serverId) {
		return sessionFactory.getCurrentSession().createQuery("FROM ServerDataTransfer e WHERE e.server.serverId = :serverId")
				.setParameter("serverId", serverId).list();
	}

	@Override
	public List<ServerDataTransfer> getAllServerDataNoTransferByServer(Integer serverId) {
		return sessionFactory.getCurrentSession().createQuery("FROM ServerDataTransfer e " +
				"WHERE e.server.serverId = :serverId AND (e.status IN ('"+ Status.FAILED.name() +"', '"+ Status.NOT_SENT.name() +"', '"+ Status.CONNEXION_FAILED.name() +"', '"+ Status.SENDING.name() +"')) ORDER BY e.dateCreated ASC ")
				.setParameter("serverId", serverId)
				.list();
	}

	@Override
	public List<ServerDataTransfer> getAllServerDataTransferredByServer(Integer serverId) {
		return sessionFactory.getCurrentSession().createQuery("FROM ServerDataTransfer e " +
				"WHERE e.server.serverId = :serverId AND (e.status IN ('"+ Status.SENT.name() +"')) ORDER BY e.dateCreated ASC ")
				.setParameter("serverId", serverId)
				.list();
	}

	@Override
	public List<ServerDataTransfer> getAllServerDataSendingByServer() {
		return sessionFactory.getCurrentSession().createQuery("FROM ServerDataTransfer e " +
				"WHERE e.server.serverId = :serverId AND (e.status IN ('"+ Status.SENDING.name() +"'))")
				.list();
	}

	@Override
	public ServerDataTransfer getOneServerData(Integer serverDataId) {
		return (ServerDataTransfer) sessionFactory.getCurrentSession().get(ServerDataTransfer.class, serverDataId);
	}

	@Override
	public boolean transferData(Server server, String endPoint, ServerDataTransfer serverDataTransfer) throws IOException {
		String url = server.getServerUrl();
		String user = server.getUsername();
		String pass = server.getPassword();

		if (!testServerDetails(url, user, pass)) {
			return false;
		}
		serverDataTransfer.setStatus(Status.SENDING.name());
		createServerData(serverDataTransfer);

		DataTransferModelUUID data = Tools.createDataTransferModelUUIDFromByte(serverDataTransfer.getContent());

		String payload = "";
		boolean canContinue = true;
		String info = "";

		StringBuilder restLink;
		int count = 0;

		if (data != null) {
			System.out.println("-------------- Resource to insert size : " + data.getResourceSize());

			Set<PersonAction> personActions = new HashSet<PersonAction>(data.getPersonActions());
			Set<EncounterAction> encounterActions = new HashSet<EncounterAction>(data.getEncounterActions());
			Set<ObsAction> obsActions = new HashSet<ObsAction>(data.getObsActions());

			System.out.println("-------------------------------- Exporting person info ----------------------------- " + count);

			for (PersonAction personAction : personActions) {

				count++;

				// System.out.println("Person uuid : " + personAction.getPersonUuid());
				Person person = Context.getPersonService().getPersonByUuid(personAction.getPersonUuid());
				Patient patient = Context.getPatientService().getPatient(person.getPersonId());

				String personInfo = person.getPersonName() + " Gender : " + person.getGender();
				personInfo +=  ", identifier : '"+patient.getPatientIdentifier();
				if (person.getBirthdate() != null) {
					personInfo += "', birth date : " + Tools.formatDateToString(person.getBirthdate(), "dd/MM/yyyy");
				} else {
					personInfo += "', birth date : No birth date";
				}
				if (patient.getPatientIdentifier() != null) {
					System.out.println("Current patient id : " + patient.getPatientIdentifier().getIdentifier());
					// getResource(url, user, pass, "/patient", person.getUuid());
					List<PatientResult> patientResult = findPatientOnServer(patient.getPatientIdentifier().getIdentifier(), server);
					if (patientResult != null && patientResult.size() != 0) {
						if (!patient.getUuid().equals(patientResult.get(0).getUuid())) {
							canContinue = false;
							info = "("+ count + ")" + "Exporting stopped on sending name for [ "+ personInfo + "] on server  : [An other patient have the same identifier on server. " +
									"Click here to <a class=\"button\" href=\"/module/serverDataTransfer/transfer.form?import="+ patient.getPatientIdentifier().getIdentifier() + "\">Import remote patient info</a>"  ;
							break;
						}
					}
				} else {
					System.out.println("Current patient with identifier not checked : " + personInfo + " Person id : " + patient.getPatientId());
//					break;
				}

				restLink = new StringBuilder(endPoint + "/patient");

				if (canContinue && !getResource(url, user, pass, restLink.toString(), personAction.getPersonUuid()).contains("error")) {
//					System.out.println("--------------------------PATIENT UPDATE--------------------------");

					if (person.getVoided()) {
						payload = deleteData(url, user, pass, restLink.toString(), personAction.getPersonUuid());

						if (payload.contains("error")) {
							// System.out.println("Payload person for [" + personAction.getPersonUuid() + "] " + Action.DELETE.name() + " : " + payload);
							info = "("+ count + ")" + "Exporting stopped on voiding info for [ "+ personInfo + "] in server  : ";
							canContinue = false;
							break;
						}
						payload = "";
					} else {
						// Updating person
						String personLink = endPoint + "/person/" + personAction.getPersonUuid();
						payload = postData(url, user, pass, personLink, Tools.objectToString(new PersonResourceUpdate().setPerson(person)));
						if (payload.contains("error")) {
							// System.out.println("Payload person [" + personAction.getPersonUuid() + "] for " + Action.UPDATE.name() + " : " + payload);
							info = "("+ count + ")" + "Exporting stopped on sending [ "+ personInfo + "] on server  : ";
							canContinue = false;
						}

						if (canContinue) {
							for (PersonName personName : person.getNames()) {

								String nameLink = personLink + "/name";
								if (!getResource(url, user, pass, nameLink, personName.getUuid()).contains("error")) {
									nameLink += "/" + personName.getUuid();
								}
								// System.out.println("Name link : " + nameLink);
								payload = postData(url, user, pass, nameLink, Tools.objectToString(new NameResource().setPersonName(personName)));
								// System.out.println("Exporting person to save or update name with identifier : " + patient.getPatientIdentifier());

								if (payload.contains("error")) {
									// System.out.println("Payload name for " + Action.UPDATE.name() + " : " + payload);
									info = "("+ count + ")" + "Exporting stopped on sending name for [ "+ personInfo + "] on server  : ";
									canContinue = false;
									break;
								}
								payload = "";
							}
						}
						if (canContinue) {
							for (PersonAddress personAddress : person.getAddresses()) {
								String addressLink = personLink + "/address";
								// System.out.println("Exporting person to save or update address with identifier : " + patient.getPatientIdentifier());

								if (!getResource(url, user, pass, addressLink, personAddress.getUuid()).contains("error")) {
									addressLink += "/" + personAddress.getUuid();
									payload = postData(url, user, pass, addressLink, Tools.objectToString(new AddressResource().setPersonAddress(personAddress)));
								} else {
									payload = postData(url, user, pass, addressLink, Tools.objectToString(new AddressResource().setPersonAddress(personAddress)));
								}
								if (payload.contains("error")) {
									System.out.println("Payload address for [" + personAddress.getUuid() + "] : " + payload);
									info = "("+ count + ")" + "Exporting stopped on sending address for [ "+ personInfo + "] in server  : ";
									canContinue = false;
									break;
								}
								payload = "";
							}

						}
						if (canContinue) {
							for (PersonAttribute personAttribute : person.getAttributes()) {
								// System.out.println("Exporting person to save or update attribute with identifier : " + patient.getPatientIdentifier());

								String attributeLink = personLink + "/attribute";
								if (!getResource(url, user, pass, attributeLink, personAttribute.getUuid()).contains("error")) {
									attributeLink += "/" + personAttribute.getUuid();
									payload = postData(url, user, pass, attributeLink, Tools.objectToString(new AttributeResource().setPersonAttribute(personAttribute)));
								} else {
									payload = postData(url, user, pass, attributeLink, Tools.objectToString(new AttributeResource().setPersonAttribute(personAttribute)));
								}
								System.out.println("Attribute link : " + attributeLink);

								if (payload.contains("error")) {
									System.out.println("Payload attribute for [" + personAttribute.getUuid() + "] : " + payload);
									info = "("+ count + ")" + "Exporting stopped on sending attribute for [ "+ personInfo + "] on server  : ";
									canContinue = false;
									break;
								}
								payload = "";
							}
						}
						if (canContinue) {
							restLink = new StringBuilder(endPoint + "/patient/" + personAction.getPersonUuid() + "/identifier");
							for (PatientIdentifier identifier: patient.getIdentifiers()) {
								String idLink = restLink.toString();
								if (!getResource(url, user, pass, restLink.toString(), identifier.getUuid()).contains("error")) {
									idLink += "/" + identifier.getUuid();
								}
								payload = postData(url, user, pass, idLink, Tools.objectToString(new IdentifierResource().setIdentifier(identifier)));

								if (payload.contains("error")) {
									info = "Exporting stopped on updating patient with identifier [ " + patient.getPatientIdentifier() + "] on server  : ";
									canContinue = false;
									break;
								}
								payload = "";
							}
						}
					}

				} else {
					if (canContinue && !person.getVoided()) {
//						System.out.println("--------------------------PATIENT CREATE--------------------------");
						restLink = new StringBuilder(endPoint + "/patient");
						payload = postData(url, user, pass, restLink.toString(), Tools.objectToString(new PatientResource().setPatient(patient)));
						// System.out.println("Exporting person to save peron with identifier : " + patient.getPatientIdentifier());
						if (payload.contains("error")) {
							info = "("+ count + ")" + "Exporting stopped on sending person ["+ personInfo + "] in server : ";
							// System.out.println(Json.prettyPrint(Json.toJson(patient)));
							canContinue = false;
							break;
						}

						if (person.getAddresses().size() != 0) {
							String addressLink = endPoint + "/person/" + person.getUuid() + "/address";
							payload = postData(url, user, pass, addressLink, Tools.objectToString(new AddressResource().setPersonAddress(person.getPersonAddress())));
							if (payload.contains("error")) {
								info = "("+ count + ")" + "Exporting stopped on sending person address ["+ personInfo + "] in server : ";
								canContinue = false;
								break;
							}
						}

						if (person.getAttributes().size() != 0) {
							String addressLink = endPoint + "/person/" + person.getUuid() + "/attribute";
							for (PersonAttribute personAttribute : person.getAttributes()) {
								payload = postData(url, user, pass, addressLink, Tools.objectToString(new AttributeResource().setPersonAttribute(personAttribute)));
								if (payload.contains("error")) {
									info = "(" + count + ")" + "Exporting stopped on sending person attribute [" + personInfo + "] in server : ";
									canContinue = false;
									break;
								}
							}
						}

						payload = "";
					}
				}
				if (canContinue)
					data.getPersonActions().remove(personAction);

			}

			if (canContinue) {
				System.out.println("-------------------------------- Adding Encounters ----------------------------- " + count);
				for (EncounterAction encounterAction : encounterActions) {

					restLink = new StringBuilder(endPoint + "/encounter");
					Encounter encounter = Context.getEncounterService().getEncounterByUuid(encounterAction.getEncounterUuid());
					System.out.println("--------------------------------" + encounter.getEncounterType().getName() + "----------------------------- ");
					if (!getResource(url, user, pass, restLink.toString(), encounter.getUuid()).contains("error")) {
						if (encounter.getVoided()) {
							payload = deleteData(url, user, pass, restLink.toString(), encounterAction.getEncounterUuid());
							if (payload.contains("error")) {
								info = "Exporting stopped on deleting encounter [" + encounter .getEncounterType().getName()
										+ "] on [" + encounter.getEncounterDatetime() + "]  for patient with identifier [ "
										+ encounter.getPatient().getPatientIdentifier() + "] on server  : ";
								canContinue = false;
								break;
							}
						} else {
							restLink.append("/").append(encounterAction.getEncounterUuid());
							payload = postData(url, user, pass, restLink.toString(), Tools.objectToString(new EncounterResource().setEncounter(encounter)));
							if (payload.contains("error")) {
								info = "Exporting stopped on updating encounter [" + encounter .getEncounterType().getName()
										+ "] on [" + encounter.getEncounterDatetime() + "]  for patient with identifier [ "
										+ encounter.getPatient().getPatientIdentifier() + "] on server  : ";
								canContinue = false;
								break;
							}
						}
					} else {
						if (!encounter.getVoided()) {
							payload = postData(url, user, pass, restLink.toString(), Tools.objectToString(new EncounterResource().setEncounter(encounter)));
							if (payload.contains("error")) {
								info = "Exporting stopped on saving encounter [" + encounter.getEncounterType().getName() +
										"] on [" + encounter.getEncounterDatetime() + "]  for patient with identifier [ "
										+ encounter.getPatient().getPatientIdentifier() + "] on server  : ";
								canContinue = false;
								break;
							}
						}
					}
					count++;
					payload = "";
					data.getEncounterActions().remove(encounterAction);
				}
			}

			if (canContinue) {
				System.out.println("-------------------------------- Sending Obs informations ----------------------------- ");

				for (ObsAction obsAction : obsActions) {
					restLink = new StringBuilder(endPoint + "/obs");
					Obs obs = Context.getObsService().getObsByUuid(obsAction.getObs());
					if (!getResource(url, user, pass, endPoint + "/obs", obs.getUuid()).contains("error")) {
						if (obs.getVoided()) {
							payload = deleteData(url, user, pass, restLink.toString(), obs.getUuid());
							if (payload.contains("error")) {
//								System.out.println("Payload obs for " + serverAction + " : " + payload);
								info = "Exporting stopped on sending obs [" + obs.getObsDatetime().toString() + "] on [" + obs.getConcept().getName(Locale.FRENCH).getName() + "]  for patient with name [ "+ obs.getPerson().getPersonName() + "] on server  : ";
								canContinue = false;
								break;
							}
						} else {
							restLink.append("/").append( obsAction.getObs());
							payload = postData(url, user, pass, restLink.toString(), Tools.objectToString(new ObsResource().setObs(obs)));
							if (payload.contains("error")) {
								info = "Exporting stopped on updating obs [" + obs.getObsDatetime().toString() + "] on [" + obs.getConcept().getName(Locale.FRENCH).getName() + "]  for patient with name [ "+ obs.getPerson().getPersonName() + "] on server  : ";
								canContinue = false;
								break;
							}
						}
					} else {
						if (!obs.getVoided()) {
							payload = postData(url, user, pass, restLink.toString(), Tools.objectToString(new ObsResource().setObs(obs)));
							if (payload.contains("error")) {
								info = "Exporting stopped on saving obs [" + obs.getObsDatetime().toString() + "] on [" + obs.getConcept().getName(Locale.FRENCH).getName() + "]  for patient with name [ " + obs.getPerson().getPersonName() + "] on server  : ";
								canContinue = false;
								break;
							}
						}
					}

					payload = "";
					data.getObsActions().remove(obsAction);
				}
			}

			String message = "";
			if (canContinue) {
				System.out.println("-------------------------------- All import achieved successfully ----------------------------- ");
				int total =  personActions.size() + encounterActions.size() +  obsActions.size();
				message = "Data sent successfully to server with info [Total exported : " + total + "] : " +
						"<ul>" +
						"<li>Patient info exported : <strong>" + personActions.size() + "</strong></li>" +
						"<li>Encounter info exported <strong>: " + encounterActions.size() + "</strong></li>" +
						"<li>Obs info exported : <strong>" + obsActions.size() + "</strong></li>";

				serverDataTransfer.setContent(null);
				serverDataTransfer.setStatus(Status.SENT.name());
			} else {
				serverDataTransfer.setContent(Tools.createByteFromObject(data));
				serverDataTransfer.setStatus(Status.FAILED.name());
				createServerData(serverDataTransfer);
				message = info;

				if (!payload.isEmpty()) {
					ExportSummary responseError = Tools.stringToExportSummaryError(payload);
					if (responseError.getError() != null) {
						message += responseError.getError().getMessage();
						System.out.println(" DETAILS : " + responseError.getError().getDetail());
					}
				}
			}

			serverDataTransfer.setTransferFeedback(message);
			serverDataTransfer.setTransferDate(new Date());

			createServerData(serverDataTransfer);

			Alert alert = new Alert();
			alert.setText(message);
			Set<AlertRecipient> recipients = new HashSet<AlertRecipient>();
			AlertRecipient recipient =new AlertRecipient();

			recipient.setRecipient(Context.getUserService().getUserByUsername("admin"));
			recipients.add(recipient);

			if (!Context.getAuthenticatedUser().getUsername().equals("admin")) {
				AlertRecipient recipientOther =new AlertRecipient();
				recipientOther.setRecipient(Context.getAuthenticatedUser());
				recipients.add(recipientOther);
			}

			alert.setRecipients(recipients);
			Context.getAlertService().createAlert(alert);
		}
		return canContinue;
	}

	@Override
	public String postDataToServer(Server server, String endPoint, DataTransferResourceModel data) {
		String url = server.getServerUrl();
		String user = server.getUsername();
		String pass = server.getPassword();

		String payload = "";

		if (data != null) {
			for (PersonResourceAction personResourceAction : data.getPersonResourceActions()) {
				endPoint += endPoint + "/person";
				for (String serverAction : personResourceAction.getActions()) {
					if (serverAction.equals(Action.UPDATE.name()) || serverAction.equals(Action.SAVE.name())) {
						if (serverAction.equals(Action.UPDATE.name())) {
							endPoint += endPoint + "/" + personResourceAction.getPersonResource().getUuid();
						}
						payload = postData(url, user, pass, endPoint, Tools.objectToString(personResourceAction.getPersonResource()));
					} else {
						payload = deleteData(url, user, pass, endPoint, personResourceAction.getPersonResource().getUuid());
					}
				}
			}

			for (PatientResourceAction patientResourceAction : data.getPatientResourceActions()) {
				endPoint += endPoint + "/patient";
				for (String serverAction : patientResourceAction.getActions()) {
					if (serverAction.equals(Action.UPDATE.name()) || serverAction.equals(Action.SAVE.name())) {
						if (serverAction.equals(Action.UPDATE.name())) {
							endPoint += endPoint + "/" + patientResourceAction.getPatientResources().getPerson();
						}
						payload = postData(url, user, pass, endPoint, Tools.objectToString(patientResourceAction.getPatientResources()));
					} else {
						payload = deleteData(url, user, pass, endPoint, patientResourceAction.getPatientResources().getPerson().getUuid());
					}
				}
			}

			for (EncounterResourceAction encounterResourceAction : data.getEncounterResourceActions()) {
				endPoint += endPoint + "/encounter";
				for (String serverAction : encounterResourceAction.getActions()) {
					if (serverAction.equals(Action.UPDATE.name()) || serverAction.equals(Action.SAVE.name())) {
						if (serverAction.equals(Action.UPDATE.name())) {
							endPoint += endPoint + "/" + encounterResourceAction.getEncounterResource().getUuid();
						}
						payload = postData(url, user, pass, endPoint, Tools.objectToString(encounterResourceAction.getEncounterResource()));
					} else {
						payload = deleteData(url, user, pass, endPoint, encounterResourceAction.getEncounterResource().getUuid());
					}
				}
			}

			for (ObsResourceAction obsResourceAction : data.getObsResourceActions()) {
				endPoint += endPoint + "/obs";
				for (String serverAction : obsResourceAction.getActions()) {
					if (serverAction.equals(Action.UPDATE.name()) || serverAction.equals(Action.SAVE.name())) {
						if (serverAction.equals(Action.UPDATE.name())) {
							endPoint += endPoint + "/" + obsResourceAction.getObsResource().getUuid();
						}
						payload = postData(url, user, pass, endPoint, Tools.objectToString(obsResourceAction.getObsResource()));
					} else {
						payload = deleteData(url, user, pass, endPoint, obsResourceAction.getObsResource().getUuid());
					}
				}
			}
		}

		return payload;
	}

	@Override
	public String postData(String url, String user, String pass, String endPoint, String data) {

		DefaultHttpClient client = null;
		String payload = "";

		try {
			URL serverUrl = new URL(url);

			String host = serverUrl.getHost();
			int port = serverUrl.getPort();

			HttpHost targetHost = new HttpHost(host, port, serverUrl.getProtocol());
			client = new DefaultHttpClient();
			BasicHttpContext localContext = new BasicHttpContext();

			Credentials creds = new UsernamePasswordCredentials(user, pass);

			HttpPost httpPost = new HttpPost(serverUrl + Tools.WS_REST_V1 + endPoint);
			System.out.println("Post Resource link : " + serverUrl.toString() + Tools.WS_REST_V1 + endPoint);

			Header bsPost = new BasicScheme().authenticate(creds, httpPost, localContext);
			httpPost.addHeader("Authorization", bsPost.getValue());
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Accept", "application/json");

			httpPost.setEntity(new StringEntity(data));

			HttpResponse response = client.execute(targetHost, httpPost, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				payload = EntityUtils.toString(entity);
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}

		if (payload.contains("error"))
			System.out.println(payload);

		return payload;
	}

	@Override
	public String deleteData(String url, String user, String pass, String endPoint, String resourceUuid) {

		DefaultHttpClient client = null;

		String payload = "";

		try {
			URL serverUrl = new URL(url);
			String host = serverUrl.getHost();
			int port = serverUrl.getPort();

			HttpHost targetHost = new HttpHost(host, port, serverUrl.getProtocol());
			client = new DefaultHttpClient();
			BasicHttpContext localContext = new BasicHttpContext();

			Credentials creds = new UsernamePasswordCredentials(user, pass);

			HttpDelete httpDelete = new HttpDelete(serverUrl + Tools.WS_REST_V1 + endPoint + "/" + resourceUuid);
			System.out.println("Delete Resource link : " + serverUrl.toString() + Tools.WS_REST_V1 + endPoint + "/"  + resourceUuid);
			Header bsDelete = new BasicScheme().authenticate(creds, httpDelete, localContext);
			httpDelete.addHeader("Authorization", bsDelete.getValue());

			HttpResponse response = client.execute(targetHost, httpDelete, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				payload = EntityUtils.toString(entity);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		if (payload.contains("error"))
			System.out.println(payload);

		return payload;
	}

	private String getResource(String url, String user, String pass, String endpoint, String uuid) {

		DefaultHttpClient client = null;
		String payload = "";

		HttpParams params = new BasicHttpParams();;
//		params.setParameter("identifier", "4370/OP/09/00079");
		try {
			URL serverUrl = new URL(url);
			String host = serverUrl.getHost();
			int port = serverUrl.getPort();

			HttpHost targetHost = new HttpHost(host, port, serverUrl.getProtocol());
			client = new DefaultHttpClient();
			BasicHttpContext localContext = new BasicHttpContext();
			client.setParams(params);


			Credentials creds = new UsernamePasswordCredentials(user, pass);

			HttpGet http = new HttpGet(serverUrl + "/ws/rest/v1"+ endpoint + (uuid != null ? "/" + uuid : null));

			Header bsGet = new BasicScheme().authenticate(creds, http, localContext);
			http.addHeader("Authorization", bsGet.getValue());
			http.addHeader("Content-Type", "application/json");
			http.addHeader("Accept", "application/json");

			HttpResponse response = client.execute(targetHost, http, localContext);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
//				System.out.println(entity.getContent());
				payload = EntityUtils.toString(entity);
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}

		return payload;
	}

	public String getResourceFromRemote(String url, String user, String pass, Map<String, Object> params, String endpoint) {
		DefaultHttpClient client = null;
		String payload = "";
		Patient patient = null;

		try {
			URL serverUrl = new URL(url);
			String host = serverUrl.getHost();
			int port = serverUrl.getPort();

			HttpHost targetHost = new HttpHost(host, port, serverUrl.getProtocol());
			client = new DefaultHttpClient();
			BasicHttpContext localContext = new BasicHttpContext();

			Credentials creds = new UsernamePasswordCredentials(user, pass);

			URIBuilder uriBuilder = new URIBuilder(serverUrl.toString() + endpoint);
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
			}
			HttpGet http = new HttpGet(uriBuilder.build());

			Header bsGet = new BasicScheme().authenticate(creds, http, localContext);
			http.addHeader("Authorization", bsGet.getValue());
			http.addHeader("Content-Type", "application/json");
			http.addHeader("Accept", "application/json");

			HttpResponse response = client.execute(targetHost, http, localContext);

			HttpEntity entity = response.getEntity();

			if (entity != null) {
//				System.out.println(entity.getContent());
				payload = EntityUtils.toString(entity);
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}

		return payload;
	}

	@Override
	public boolean testServerDetails(String url, String user, String pass) {
		URL testURL;
		boolean success = true;

		// Check if the URL makes sense
		try {
			testURL = new URL(url + Tools.WS_REST_V1 + "/session"); // Add the root API
			// endpoint to the URL
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}

		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 2000);

		HttpHost targetHost = new HttpHost(testURL.getHost(), testURL.getPort(), testURL.getProtocol());
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);

		BasicHttpContext localContext = new BasicHttpContext();

		try {
			HttpGet httpGet = new HttpGet(testURL.toURI());
			Credentials creds = new UsernamePasswordCredentials(user, pass);
			Header bs = new BasicScheme().authenticate(creds, httpGet, localContext);
			httpGet.addHeader("Authorization", bs.getValue());
			httpGet.addHeader("Content-Type", "application/json");
			httpGet.addHeader("Accept", "application/json");

			//execute the test query
			HttpResponse response = httpclient.execute(targetHost, httpGet, localContext);

			if (response.getStatusLine().getStatusCode() != 200) {
				success = false;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			success = false;
		}
		finally {
			httpclient.getConnectionManager().shutdown();
		}

		return success;
	}

	@Override
	public List<Patient> getPatientWithNoAge() {
		return (List<Patient>) sessionFactory.getCurrentSession().createQuery("SELECT p FROM Patient p WHERE p.patientId IN (SELECT pe.personId FROM Person pe WHERE pe.birthdate IS NULL) AND p.voided = false AND p.patientId NOT IN (SELECT u.person.personId FROM User u)").list();
	}

	@Override
	public List<Patient> getPatientWithNoName() {
		return (List<Patient>) sessionFactory.getCurrentSession().createQuery("SELECT p FROM Patient p WHERE p.patientId NOT IN (SELECT pn.person.personId FROM PersonName pn) AND p.patientId NOT IN (SELECT u.person.personId FROM User u) ").list();
	}

	@Override
	public List<Patient> getPatientDeathWithNoCauseOfDeath() {
		return (List<Patient>) sessionFactory.getCurrentSession().createQuery("SELECT p FROM Patient p WHERE p.patientId IN (SELECT pn.personId FROM Person pn WHERE pn.dead = true AND pn.causeOfDeath IS NULL) AND p.patientId NOT IN (SELECT u.person.personId FROM User u)").list();
	}

	@Override
	public List<Patient> getPatentWithNonUniqueIdentifier() {
		return (List<Patient>) sessionFactory.getCurrentSession().createQuery("" +
				"SELECT p FROM Patient p, PatientIdentifier pi WHERE p.patientId = pi.patient.patientId AND identifier IN (SELECT pi2.identifier FROM PatientIdentifier pi2 WHERE pi2.voided = 0 GROUP BY pi2.identifier HAVING COUNT(pi2.patient) > 1) AND p.patientId NOT IN (SELECT u.person.personId FROM User u)").list();
	}

	@Override
	public List<Patient> getPatientWithoutGender() {
		return (List<Patient>) sessionFactory.getCurrentSession().createQuery("SELECT p FROM Patient p WHERE p.patientId IN (SELECT pe.personId FROM Person pe WHERE pe.gender IS NULL) AND p.patientId NOT IN (SELECT u.person.personId FROM User u)").list();
	}

	@Override
	public List<PatientResult> findPatientOnServer(String identifier, Server server) throws IOException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("identifier", identifier);
		params.put("v", "full");

		String patientJson = getResourceFromRemote(server.getServerUrl(), server.getUsername(), server.getPassword(), params, Tools.WS_REST_V1 + "/patient");
		if (!patientJson.contains("error")) {
			JsonNode resultNode = Json.parse(patientJson);
			ResourceResult result = Json.fromJson(resultNode, ResourceResult.class);

			return new ArrayList<PatientResult>(result.getResults());
		}
		else
			return new ArrayList<PatientResult>();
	}

	@Override
	public EncounterResult getLatestAdmission(String patientUuid, Server server) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("patient", patientUuid);
		params.put("encounterType", "8d5b27bc-c2cc-11de-8d13-0010c6dffd0f");
		params.put("v", "default");
		String resourceResult = getResourceFromRemote(server.getServerUrl(), server.getUsername(), server.getPassword(), params, Tools.WS_REST_V1 + "/encounter");
		JsonNode resultNode = Json.parse(resourceResult);
		EncounterResourceResult encounterResourceResult = Json.fromJson(resultNode, EncounterResourceResult.class);
		EncounterResult encounterResult = new EncounterResult();
		for (EncounterResult result : encounterResourceResult.getResults()) {
			if (encounterResult.getEncounterDatetime() == null) {
				encounterResult = result;
			} else {
				if (encounterResult.getEncounterDatetime().before(result.getEncounterDatetime())) {
					encounterResult = result;
				}
			}
		}
		return encounterResult;
	}

	@Override
	public EncounterResult getLatestOutFromCare(String patientUuid, Server server) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("patient", patientUuid);
		params.put("encounterType", "abe5b173-0a3b-42eb-865b-f95b645864c7");
		params.put("v", "default");
		String resourceResult = getResourceFromRemote(server.getServerUrl(), server.getUsername(), server.getPassword(), params, Tools.WS_REST_V1 + "/encounter");
		JsonNode resultNode = Json.parse(resourceResult);
		EncounterResourceResult encounterResourceResult = Json.fromJson(resultNode, EncounterResourceResult.class);
		EncounterResult encounterResult = new EncounterResult();
		for (EncounterResult result : encounterResourceResult.getResults()) {
			if (encounterResult.getEncounterDatetime() == null) {
				encounterResult = result;
			} else {
				if (encounterResult.getEncounterDatetime().before(result.getEncounterDatetime())) {
					encounterResult = result;
				}
			}
		}
		return encounterResult;
	}

}
