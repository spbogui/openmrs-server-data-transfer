package org.openmrs.module.serverDataTransfer.utils.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openmrs.Obs;
import org.openmrs.module.serverDataTransfer.utils.Json;
import org.openmrs.module.serverDataTransfer.utils.Tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class ObsResource implements Serializable {
    private String person;
    private String obsDatetime;
    private String concept;
    private String location;
    private String order;
    private String encounter;
    private String accessionNumber;
    private Set<String> groupMembers;
    private String valueCodedName;
    private String comment;
    private boolean voided;
    private String value;
//    private String formFieldNamespace;
    private String uuid;

    public ObsResource() {
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getObsDatetime() {
        return obsDatetime;
    }

    public void setObsDatetime(String obsDatetime) {
        this.obsDatetime = obsDatetime;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getEncounter() {
        return encounter;
    }

    public void setEncounter(String encounter) {
        this.encounter = encounter;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public Set<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Set<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getValueCodedName() {
        return valueCodedName;
    }

    public void setValueCodedName(String valueCodedName) {
        this.valueCodedName = valueCodedName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

//    public String getFormFieldNamespace() {
//        return formFieldNamespace;
//    }
//
//    public void setFormFieldNamespace(String formFieldNamespace) {
//        this.formFieldNamespace = formFieldNamespace;
//    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ObsResource setObs(Obs obs) throws JsonProcessingException {
        setPerson(obs.getPerson().getUuid());
        setObsDatetime(Tools.formatDateToString(obs.getObsDatetime(), Tools.DATE_FORMAT_YYYY_MM_DD_H_M_SZ));
        setConcept(obs.getConcept().getUuid());
        setLocation(obs.getLocation().getUuid());

        if (obs.getOrder() != null)
            setOrder(obs.getOrder().getUuid());

        if (obs.getEncounter() != null)
            setEncounter(obs.getEncounter().getUuid());

        if (obs.getAccessionNumber() != null)
            setAccessionNumber(obs.getAccessionNumber());

        if (obs.getGroupMembers().isEmpty()) {
            Set<String> orders = new java.util.HashSet<String>(Collections.<String>emptySet());
            for (Obs o : obs.getGroupMembers()) {
                orders.add(obs.getUuid());
            }
            setGroupMembers(orders);
        }

        if (obs.getValueCodedName() != null)
            setValueCodedName(obs.getValueCodedName().getName());

        if (obs.getComment() != null)
            setComment(obs.getComment());

        setVoided(obs.getVoided());

//        System.out.println("-------------------------------------------------------------------------");
        if (obs.getValueBoolean() != null){
//            System.out.println(obs.getValueBoolean().toString());
            setValue(obs.getValueBoolean().toString());
        }
        else if (obs.getValueCoded() != null) {
//            System.out.println(obs.getValueCoded().getUuid());
            setValue(obs.getValueCoded().getUuid());
        } else if (obs.getValueNumeric() != null) {
//            System.out.println(obs.getValueNumeric());
            setValue(obs.getValueNumeric().toString());
        } else if (obs.getValueText() != null) {
//            System.out.println(obs.getValueText());
            setValue(obs.getValueText().toString());
        } else if (obs.getValueDatetime() != null) {
//            System.out.println(Tools.formatDateToString(obs.getValueDatetime(), Tools.DATE_FORMAT_YYYY_MM_DD_H_M_S));
            setValue(Tools.formatDateToString(obs.getValueDatetime(), Tools.DATE_FORMAT_YYYY_MM_DD_H_M_S));
        } else if (obs.getValueDate() != null) {
//            System.out.println(Tools.formatDateToString(obs.getValueDate(), Tools.DATE_FORMAT_YYYY_MM_DD_H_M_S));
            setValue(Tools.formatDateToString(obs.getValueDate(), Tools.DATE_FORMAT_YYYY_MM_DD_H_M_S));
        }
        // System.out.println("-------------------------------------------------------------------------");

        setUuid(obs.getUuid());

//        System.out.println(Json.prettyPrint(Json.toJson(this)));

        return this;
    }
}
