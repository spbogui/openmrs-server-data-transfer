package org.openmrs.module.serverDataTransfer.utils.resourcesResult;

import java.util.Date;
import java.util.List;

public class EncounterResult {
    private String uuid;
    private Date encounterDatetime;
    private LocationResult location;
    private List<ObsResult> obs;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(Date encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }

    public LocationResult getLocation() {
        return location;
    }

    public void setLocation(LocationResult location) {
        this.location = location;
    }

    public List<ObsResult> getObs() {
        return obs;
    }

    public void setObs(List<ObsResult> obs) {
        this.obs = obs;
    }
}
