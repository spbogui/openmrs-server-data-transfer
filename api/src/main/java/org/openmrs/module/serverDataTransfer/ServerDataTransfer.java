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

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.BaseOpenmrsMetadata;

import javax.persistence.*;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
@Entity
@Table(name = "server_transfer_data_transfer")
public class ServerDataTransfer extends BaseOpenmrsObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transfer_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "server_id", nullable = false)
	private Server server;

	@Column(name = "transfer_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date transferDate;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "transfer_feedback")
	private String transferFeedback;

	@Column(name = "status")
	private String status;

	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	@Column(name = "uuid", nullable = false, unique = true)
	public String getUuid() {
		return super.getUuid();
	}

	@Override
	public void setUuid(String uuid) {
		super.setUuid(uuid);
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

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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
