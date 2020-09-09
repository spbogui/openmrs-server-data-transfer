package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.PersonAttribute;

import java.io.Serializable;

public class AttributeResource implements Serializable {
    /**
     * The uuid of the attribute type
     */
    private String attributeType;
    private String value;
    /**
     * the uuid of the hidrated object
     */
    private String hydratedObject;
    private String uuid;

    public AttributeResource() {
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHydratedObject() {
        return hydratedObject;
    }

    public void setHydratedObject(String hydratedObject) {
        this.hydratedObject = hydratedObject;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AttributeResource setPersonAttribute(PersonAttribute personAttribute) {
        setAttributeType(personAttribute.getAttributeType().getUuid());
        if (personAttribute.getHydratedObject()!= null)
            setHydratedObject(personAttribute.getHydratedObject().toString());
        setUuid(personAttribute.getUuid());
        setValue(personAttribute.getValue());

        return this;
    }


}
