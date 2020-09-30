package org.openmrs.module.serverDataTransfer.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openmrs.module.serverDataTransfer.Server;
import org.openmrs.module.serverDataTransfer.errors.EncounterError;
import org.openmrs.module.serverDataTransfer.errors.ObsError;
import org.openmrs.module.serverDataTransfer.errors.PatientError;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static String DATE_FORMAT_YYYY_MM_DD_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_YYYY_MM_DD_H_M_SZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

    public static String INVALID_MIDDLE_NAME = "MiddleName.invalid";
    public static String INVALID_FAMILY_NAME = "FamilyName.invalid";
    public static String INVALID_GIVEN_NAME = "GivenName.invalid";

    public static String WS_REST_V1 = "/ws/rest/v1";

    public static DataTransferResourceModel createObjectFromByte(byte[] yourBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
        ObjectInput in = null;
        DataTransferResourceModel dataTransferModel = new DataTransferResourceModel();
        try {
            in = new ObjectInputStream(bis);
            dataTransferModel = (DataTransferResourceModel) in.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch ( ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return dataTransferModel;
    }

    public static DataTransferModelUUID createDataTransferModelUUIDFromByte(byte[] yourBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
        ObjectInput in = null;
        DataTransferModelUUID dataTransferModel = new DataTransferModelUUID();
        try {
            in = new ObjectInputStream(bis);
            dataTransferModel = (DataTransferModelUUID) in.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch ( ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return dataTransferModel;
    }

    public static byte[] createByteFromObject(DataTransferModelUUID dataTransferModel) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        byte[] yourBytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(dataTransferModel);
            out.flush();
            yourBytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return yourBytes;
    }

    /**
     * Crunchify's isAlive Utility
     *
     * @param hostName
     * @param port
     * @return boolean - true/false
     */
    public static boolean isSocketAlive(String hostName, int port) {
        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

        log("hostName: " + hostName + ", port: " + port);
        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isAlive = true;

        } catch (SocketTimeoutException exception) {
            System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
        } catch (IOException exception) {
            System.out.println(
                    "IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
        }
        return isAlive;
    }

    public static String objectToString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(object);
            // System.out.println("ResultingJSONString = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static ExportSummary stringToExportSummaryError(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNodeRoot = mapper.readTree(json);

        System.out.println(jsonNodeRoot.get("globalErrors"));

        ExportSummary exportSummary = null;
        try {
            exportSummary = mapper.readValue(json, ExportSummary.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exportSummary;
    }

    public static ObsError stringToExportObsError(String json) throws IOException {
        return Json.fromJson(Json.parse(json), ObsError.class);
    }

    public static EncounterError stringToExportEncounterError(String json) throws IOException {
        return Json.fromJson(Json.parse(json), EncounterError.class);
    }

    public static PatientError stringToExportPatientError(String json) throws IOException {
        return Json.fromJson(Json.parse(json), PatientError.class);
    }

    public static boolean netIsAvailable(Server server) {
        String serverUrl = server.getServerUrl().split(":")[0] + ":" + server.getServerUrl().split(":")[1] ;
        try {
            final URL url = new URL(serverUrl);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    // Simple log utility
    private static void log(String string) {
        System.out.println(string);
    }

    // Simple log utility returns boolean result
    private static void log(boolean isAlive) {
        System.out.println("isAlive result: " + isAlive + "\n");
    }

    public static String formatDateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
