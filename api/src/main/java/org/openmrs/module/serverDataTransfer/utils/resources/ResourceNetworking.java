package org.openmrs.module.serverDataTransfer.utils.resources;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.openmrs.module.serverDataTransfer.utils.Tools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ResourceNetworking {
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    private static final int TIMEOUT = 4000;

    private String host;
    private int port;
    private URL serverUrl;
    private String password;
    private String data;

    private DefaultHttpClient client;
    private BasicHttpContext localContext;
    private final HttpParams params = new BasicHttpParams();
    private Credentials creds;
    private Header header;
    private HttpHost httpHost;

    public ResourceNetworking(String url, String user, String password) throws MalformedURLException {
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
        serverUrl = new URL(url);
        port = serverUrl.getPort();
        host = serverUrl.getHost();
        client = new DefaultHttpClient(params);
        localContext = new BasicHttpContext();
        creds = new UsernamePasswordCredentials(user, password);
        httpHost = new HttpHost(host, port, serverUrl.getProtocol());
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public URL getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(URL serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DefaultHttpClient getClient() {
        return client;
    }

    public void setClient(DefaultHttpClient client) {
        this.client = client;
    }

    public BasicHttpContext getLocalContext() {
        return localContext;
    }

    public void setLocalContext(BasicHttpContext localContext) {
        this.localContext = localContext;
    }

    public HttpParams getParams() {
        return params;
    }

    public Credentials getCreds() {
        return creds;
    }

    public void setCreds(Credentials creds) {
        this.creds = creds;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public HttpHost getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(HttpHost httpHost) {
        this.httpHost = httpHost;
    }

    public String post(String endpoint, String data){
        String payload = "";
        try {
            HttpPost httpPost = new HttpPost(serverUrl + Tools.WS_REST_V1 + endpoint);
            Header authenticate = new BasicScheme().authenticate(creds, httpPost, localContext);
            httpPost.addHeader("Authorization", authenticate.getValue());
            httpPost.addHeader("Content-Type", APPLICATION_JSON_UTF8);
            httpPost.addHeader("Accept", APPLICATION_JSON);
            httpPost.setEntity(new StringEntity(data, "UTF-8"));
            HttpResponse response =  client.execute(httpHost, httpPost, localContext);
            if (response.getStatusLine().getStatusCode() != 200) {
                payload = "{\"error\":{\"message\": \"La connexion au serveur est interrompu ! veuillez vérifier votre connextion \"}}";
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    payload = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
        }

        return payload;
    }

    public String remove(String endpoint){
        String payload = "";
        try {
            HttpDelete httpDelete = new HttpDelete(serverUrl + Tools.WS_REST_V1 + endpoint);
            Header authenticate = new BasicScheme().authenticate(creds, httpDelete, localContext);
            httpDelete.addHeader("Authorization", authenticate.getValue());
            HttpResponse response =  client.execute(httpHost, httpDelete, localContext);
            if (response.getStatusLine().getStatusCode() != 200) {
                payload = "{\"error\":{\"message\": \"La connexion au serveur est interrompu ! veuillez vérifier votre connextion \"}}";
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    payload = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } finally {
            if (client != null && payload.contains("error")) {
                client.getConnectionManager().shutdown();
            }
        }

        return payload;
    }

    public String get(String endpoint) {
        String payload = "";
        try {
            HttpGet httpGet = new HttpGet(serverUrl + Tools.WS_REST_V1 + endpoint);
            Header authenticate = new BasicScheme().authenticate(creds, httpGet, localContext);
            httpGet.addHeader("Authorization", authenticate.getValue());
            httpGet.addHeader("Content-Type", APPLICATION_JSON_UTF8);
            httpGet.addHeader("Accept", APPLICATION_JSON);
            HttpResponse response =  client.execute(httpHost, httpGet, localContext);
            if (response.getStatusLine().getStatusCode() != 200) {
                payload = "{\"error\":{\"message\": \"La connexion au serveur est interrompu ! veuillez vérifier votre connexion \"}}";
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    payload = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } finally {
            if (client != null && payload.contains("error")) {
                client.getConnectionManager().shutdown();
            }
        }

        return payload;
    }

    public boolean test() {
        boolean success = true;
        try {
            HttpGet httpGet = new HttpGet(serverUrl + Tools.WS_REST_V1 + "/session");
            Header authenticate = new BasicScheme().authenticate(creds, httpGet, localContext);
            httpGet.addHeader("Authorization", authenticate.getValue());
            httpGet.addHeader("Content-Type", APPLICATION_JSON_UTF8);
            httpGet.addHeader("Accept", APPLICATION_JSON);
            HttpResponse response =  client.execute(httpHost, httpGet, localContext);
            if (response.getStatusLine().getStatusCode() != 200) {
                success = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            if (client != null && !success) {
                client.getConnectionManager().shutdown();
            }
        }
        return success;
    }

    public void close() {
        if (client != null) {
            client.getConnectionManager().shutdown();
        }
    }
}
