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
package org.openmrs.module.serverDataTransfer;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.module.serverDataTransfer.utils.enums.Status;

import javax.persistence.*;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
@Entity
@Table(name = "server_transfer_data_transfer")
public class ServerDataTransfer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transfer_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "server_id", nullable = false)
	private Server server;

	@Column(name = "date_created", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "content")
	private byte[] content;

//	@Column(name = "content_type")
//	private String contentType;
//
//	@Column(name = "action", nullable = false)
//	private String action;

	@Column(name = "transfer_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transferDate;

	@Column(name = "transfer_feedback")
	private String transferFeedback;

	@Column(name = "status")
	private String status;

	@Column(name = "uuid", length = 38, unique = true, nullable = false)
	private String uuid = UUID.randomUUID().toString();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ServerDataTransfer() {
		this.status = Status.NOT_SENT.name();
		this.setUuid(UUID.randomUUID().toString());
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] patients) {
		this.content = patients;
	}

//	public String getContentType() {
//		return contentType;
//	}
//
//	public void setContentType(String contentType) {
//		this.contentType = contentType;
//	}
//
//	public String getAction() {
//		return action;
//	}
//
//	public void setAction(String action) {
//		this.action = action;
//	}

	public String getTransferFeedback() {
		return transferFeedback;
	}

	public void setTransferFeedback(String transferFeedback) {
		this.transferFeedback = transferFeedback;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
