package org.openmrs.module.serverDataTransfer.forms;

import org.openmrs.module.serverDataTransfer.Server;

import java.io.Serializable;
import java.util.UUID;

public class ServerForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer serverId;
    private String serverName;
    private String serverUrl;
    private String username;
    private String password;
    private boolean connected;
    private String uuid = UUID.randomUUID().toString();

    public ServerForm() {
    }

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

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Server getServer() {
        Server server = new Server();
        server.setServerId(getServerId());
        server.setPassword(getPassword());
        server.setServerName(getServerName());
        server.setServerUrl(getServerUrl());
        server.setUsername(getUsername());
        server.setConnected(isConnected());
        server.setUuid(getUuid());
        return server;
    }

    public void setServer(Server server) {
        setConnected(server.isConnected());
        setPassword(server.getPassword());
        setServerName(server.getServerName());
        setServerUrl(server.getServerUrl());
        setServerId(server.getServerId());
        setUsername(server.getUsername());
        setUuid(server.getUuid());
    }
}
