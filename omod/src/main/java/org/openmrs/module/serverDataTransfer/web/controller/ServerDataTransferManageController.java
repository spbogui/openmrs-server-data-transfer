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
package org.openmrs.module.serverDataTransfer.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.ServerDataTransfer;
import org.openmrs.module.serverDataTransfer.api.ServerDataTransferService;
import org.openmrs.module.serverDataTransfer.utils.Json;
import org.openmrs.module.serverDataTransfer.utils.Tools;
import org.openmrs.module.serverDataTransfer.utils.enums.Status;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.EncounterResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.IdentifierResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.ObsResult;
import org.openmrs.module.serverDataTransfer.utils.resourcesResult.PatientResult;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The main controller.
 */
@Controller
public class  ServerDataTransferManageController {

	protected final Log log = LogFactory.getLog(getClass());

	private ServerDataTransferService getService() {
		return Context.getService(ServerDataTransferService.class);
	}

	@RequestMapping(value = "/module/serverDataTransfer/manage.form", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		if (Context.isAuthenticated()) {

			model.addAttribute("serverList", getService().getAllServer());
		}
		model.addAttribute("showLogin", Context.getAuthenticatedUser() == null);
	}

	@RequestMapping(value = "/module/serverDataTransfer/server.form", method = RequestMethod.GET)
	public void editServer(HttpServletRequest request,
						   @RequestParam(required = false, defaultValue = "") Integer serverId,
						   ModelMap model) {
		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();

			Server server;
			if (serverId == null) {
				server = new Server();
			} else {
				server = getService().getOneServer(serverId);
				if (server == null) {
					server = new Server();
				}
			}
			model.addAttribute("serverForm", server);
		}
		model.addAttribute("showLogin", Context.getAuthenticatedUser() == null);
	}

	@RequestMapping(value = "/module/serverDataTransfer/server.form", method = RequestMethod.POST)
	public String onPostServer(HttpServletRequest request, Server serverForm, BindingResult result, ModelMap model) {
		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();

			String mode = "save";
			if (serverForm.getServerUrl() != null) {
				mode = "edit";
			}
			serverForm.setConnected(true);

			if (!getService().testServerDetails(serverForm.getServerUrl(), serverForm.getUsername(), serverForm.getPassword())){
				session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Impossible de se connecter au serveur ! Veuillez vérifiez vos informations de connexion ");
			} else {
				if (getService().createServer(serverForm) != null) {

					if (mode.equals("save")) {
						session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Serveur enregistré avec succès ");
					} else {
						session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Serveur mis à jour avec succès ");
					}
					return "redirect:/module/serverDataTransfer/manage.form";
				}
			}
		}
		model.addAttribute("showLogin", Context.getAuthenticatedUser() == null);
		return null;
	}

	@RequestMapping(value = "/module/serverDataTransfer/transfer.form", method = RequestMethod.GET)
	public String transfer(HttpServletRequest request, @RequestParam(defaultValue = "") Integer serverId,
						 @RequestParam(required = false, defaultValue = "") String action,
						 ModelMap model) throws IOException {
		if(Context.isAuthenticated()) {
			HttpSession session = request.getSession();

			List<Patient> patientsWithoutName = getService().getPatientWithNoName();
			List<Patient> patientsWithoutBirthDate = getService().getPatientWithNoAge();
			List<Patient> patientsWithoutDeadInfo = getService().getPatientDeathWithNoCauseOfDeath();
			List<Patient> patientsWithoutUniqueIdentifier = getService().getPatentWithNonUniqueIdentifier();
			List<Patient> patientsWithoutGender = getService().getPatientWithoutGender();

			String mode = "dataQuality";

			if (patientsWithoutBirthDate.size() == 0 && patientsWithoutDeadInfo.size() == 0 &&
					patientsWithoutName.size() == 0 && patientsWithoutUniqueIdentifier.size() == 0 && patientsWithoutGender.size() == 0) {
				mode = "transfer";
			}

			Server server = getService().getOneServer(serverId);
			if (!getService().testServerDetails(server.getServerUrl(), server.getUsername(), server.getPassword())){
				session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, server.getServerName() + " n'est pas disponible. Veuillez vérifier les paramètres de connexion ou votre installation réseau !");
			}

			List<ServerDataTransfer> dataTransfers = getService().getAllServerDataNoTransferByServer(serverId);

			if (!action.isEmpty() && mode.equals("transfer")) {
				if (action.equals("preparing")) {
					getService().prepareData(server);
					return "redirect:/module/serverDataTransfer/transfer.form?serverId=" + serverId;
				} else if (action.equals("transfer")) {

					List<ServerDataTransfer> dataTransfersToTransfer = getService().getAllServerDataNoTransferByServer(serverId);
					boolean response = false;

					for (ServerDataTransfer dataTransfer : dataTransfersToTransfer) {
						response = getService().transferData(server, "", dataTransfer);
						if (!response)
							break;
					}
					if (response) {
						session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Le transfert des données s'est déroulé avec succès ");
					} else {
						session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Le transfert des données a échoué ");
					}
					return "redirect:/module/serverDataTransfer/transfer.form?serverId=" + serverId;

				} else if (action.equals("history")) {
					dataTransfers = getService().getAllServerDataTransferredByServer(serverId);
				}
			}

			if (mode.equals("dataQuality")) {
				model.addAttribute("patientsWithoutName", patientsWithoutName);
				model.addAttribute("patientsWithoutBirthDate", patientsWithoutBirthDate);
				model.addAttribute("patientsWithoutDeadInfo", patientsWithoutDeadInfo);
				model.addAttribute("patientsWithoutUniqueIdentifier", patientsWithoutUniqueIdentifier);
				model.addAttribute("patientsWithoutGender", patientsWithoutGender);
			}

			model.addAttribute("transferList", dataTransfers);

			model.addAttribute("server", server);
			model.addAttribute("mode", mode);
			model.addAttribute("action", action);

		}

		model.addAttribute("showLogin", Context.getAuthenticatedUser() == null);

		return null;
	}

	@RequestMapping(value = "/module/serverDataTransfer/transfer.form", method = RequestMethod.POST)
	public String transferPost(ModelMap model) {
		return null;
	}

	@RequestMapping(value = "/module/serverDataTransfer/patient-info.form", method = RequestMethod.GET)
	public String patientInfo(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer serverId,
							@RequestParam(required = false, defaultValue = "") String identifier,
							@RequestParam(required = false, defaultValue = "") String transferIn,
							ModelMap model) throws IOException {
		if (Context.isAuthenticated()) {
			if (serverId != 0 && !identifier.isEmpty()) {
				Server server = getService().getOneServer(serverId);

				String patientInfo = "Ce patient n'existe pas sur le serveur : " + server.getServerName();

				List<PatientResult> patients = getService().findPatientOnServer(identifier, server);
				if (patients.size() != 0) {

					PatientResult retrievedPatient = patients.get(0);

					boolean canTransferIn = false;

					if (!transferIn.isEmpty()) {
						Patient patient = new Patient();
						for (IdentifierResult identifierResult : retrievedPatient.getIdentifiers()) {

							patient.addIdentifier(new PatientIdentifier(identifierResult.getDisplay().split("=")[1].trim(),
									Context.getPatientService().getPatientIdentifierType(identifierResult.getDisplay().split("=")[0].trim()),
									Context.getLocationService().getDefaultLocation()));
						}

						patient.setUuid(retrievedPatient.getUuid());
						patient.addName(retrievedPatient.getPerson().getPreferredName().getPersonName());
						patient.setBirthdate(retrievedPatient.getPerson().getBirthdate());
						patient.setGender(retrievedPatient.getPerson().getGender());
						patient.setDead(false);
						patient.setCreator(Context.getAuthenticatedUser());
						patient.setPersonCreator(patient.getCreator());
						patient.setDateCreated(new Date());
						patient.setPersonDateCreated(patient.getDateCreated());
						patient.setVoided(false);
						patient.setPersonVoided(patient.getVoided());

						Context.getPatientService().savePatient(patient);

						return "redirect:/patientDashboard.form?patientId=" + patient.getPatientId();
					}
					EncounterResult latestAdmissionInfo = getService().getLatestAdmission(retrievedPatient.getUuid(), getService().getOneServer(1));
					EncounterResult latestOutOfCareInfo = getService().getLatestOutFromCare(retrievedPatient.getUuid(), getService().getOneServer(1));

					if (latestOutOfCareInfo.getEncounterDatetime() != null) {
						if (latestAdmissionInfo.getEncounterDatetime().before(latestOutOfCareInfo.getEncounterDatetime())) {
							for (ObsResult obsResult : latestOutOfCareInfo.getObs()) {
								if (obsResult.getDisplay().contains("DÉCÉDÉ: Oui")) {
									patientInfo = "Le patient est décédé";
									break;
								}
								if (obsResult.getDisplay().contains("Patient transféré: true")){
									patientInfo = "Le patient est transférés de puis le site d'origine depuis le " + Tools.formatDateToString(latestOutOfCareInfo.getEncounterDatetime(), Tools.DATE_FORMAT_DD_MM_YYYY) ;
									break;
								}
							}
						}
					} else {
						String currentLocation = Context.getAdministrationService().getGlobalProperty("default_location");
						String patientLocation = latestAdmissionInfo.getLocation().getDisplay();


						if (currentLocation.equals(patientLocation))
							patientInfo = "Le patient est Pris en charge sur votre site";
						else {
							patientInfo = "Le patient est Pris en charge sur le site : " + patientLocation;
							canTransferIn = true;
						}
					}

					List<ObsResult> obsResults = new ArrayList<ObsResult>();
					List<String> conceptNames = new ArrayList<String>();

					String conceptsResumeString = Context.getAdministrationService().getGlobalProperty("serverDataTransfer.transferResumeDataConcepts");
					String[] conceptResumeStringList =  conceptsResumeString.split(",");
					for (int i = 0; i < conceptResumeStringList.length; i++) {
						conceptNames.add(Context.getConceptService().getConcept(Integer.parseInt(conceptResumeStringList[i])).getName(Locale.FRENCH).getName());
					}

					List<String> nameVisited = new ArrayList<String>();
					for (ObsResult obsResult : latestAdmissionInfo.getObs()) {
//					String conceptString = Context.getConceptService().getConcept(obsResult.getDisplay().split(":")[0])
//							.getName(Locale.FRENCH).getConcept().getConceptId().toString();
						String conceptString = obsResult.getDisplay().split(":")[0];
						if (conceptNames.contains(conceptString) && !nameVisited.contains(obsResult.getDisplay())) {
							nameVisited.add(obsResult.getDisplay());
							obsResults.add(obsResult);
						}
					}

					//patients.get(0).
					model.addAttribute("patientFound", patients.get(0));
					model.addAttribute("latestAdmissionInfo", latestAdmissionInfo);
					model.addAttribute("currentServer", server);
					model.addAttribute("obsResults", obsResults);
					model.addAttribute("canTransferIn", canTransferIn);
				}
			}

			model.addAttribute("currentIdentifier", identifier);
			model.addAttribute("servers", getService().getAllServer());
		}
		model.addAttribute("showLogin", Context.getAuthenticatedUser() == null);

		return null;
	}

}
