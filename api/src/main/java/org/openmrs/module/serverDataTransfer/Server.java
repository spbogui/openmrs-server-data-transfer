package org.openmrs.module.serverDataTransfer;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.util.Security;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "server_transfer_server")
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private Integer serverId;

    @Column(name = "server_name", nullable = false, unique = true)
    private String serverName;

    @Column(name = "url", nullable = false, unique = true)
    private String serverUrl;

    @Column(name = "server_username", nullable = false)
    private String username;

    @Column(name = "server_user_password", nullable = false)
    private String password;

    @Column(name = "server_connected")
    private boolean connected;

    @Column(name = "uuid", length = 38, unique = true, nullable = false)
    private String uuid = UUID.randomUUID().toString();

    public Server() {
    }
//
//    @Override
//    public Integer getId() {
//        return getServerId();
//    }
//
//    @Override
//    public void setId(Integer id) {
//        this.setServerId(id);
//    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
//        return Security.decrypt(password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
//        this.password = Security.encrypt(password);
    }
}
