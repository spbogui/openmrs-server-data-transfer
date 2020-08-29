package org.openmrs.module.serverDataTransfer;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "server_transfer_server")
@Entity
public class Server extends BaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private Integer serverId;

    @Column(name = "url", nullable = false, unique = true)
    private String serverUrl;

    @Column(name = "server_username", nullable = false)
    private String username;

    @Column(name = "server_user_password", nullable = false)
    private String password;

    public Server() {
    }

    @Override
    public Integer getId() {
        return getServerId();
    }

    @Override
    public void setId(Integer id) {
        this.setServerId(id);
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
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

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
