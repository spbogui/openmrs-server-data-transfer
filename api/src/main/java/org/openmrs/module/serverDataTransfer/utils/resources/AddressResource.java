package org.openmrs.module.serverDataTransfer.utils.resources;

import org.openmrs.PersonAddress;

import java.io.Serializable;

public class AddressResource implements Serializable {
    private boolean preferred;
    private String address1;
    private String address2;
    private String cityVillage;
    private String stateProvince;
    private String country;
    private String postalCode;
    private String countyDistrict;
    private String address3;
    private String address4;
    private String address5;
    private String address6;
//    private Date startDate;
//    private Date endDate;
    private String latitude;
    private String longitude;
    private String uuid;

    public AddressResource() {
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountyDistrict() {
        return countyDistrict;
    }

    public void setCountyDistrict(String countyDistrict) {
        this.countyDistrict = countyDistrict;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getAddress6() {
        return address6;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AddressResource setPersonAddress(PersonAddress personAddress) {
        setPreferred(personAddress.getPreferred());
        if (personAddress.getAddress1() != null)
            setAddress1(personAddress.getAddress1());
        if (personAddress.getAddress2() != null)
            setAddress2(personAddress.getAddress2());
        if (personAddress.getCountry() != null)
            setCountry(personAddress.getCountry());
        if (personAddress.getPostalCode() != null)
            setPostalCode(personAddress.getPostalCode());
        if (personAddress.getCountyDistrict() != null)
            setCountyDistrict(personAddress.getCountyDistrict());
        if (personAddress.getAddress3() != null)
            setAddress3(personAddress.getAddress3());
        if (personAddress.getAddress4() != null)
            setAddress4(personAddress.getAddress4());
        if (personAddress.getAddress5() != null)
            setAddress5(personAddress.getAddress5());
        if (personAddress.getAddress6() != null)
            setAddress6(personAddress.getAddress6());
//        if (personAddress.getStartDate() != null)
//            setStartDate(personAddress.getStartDate());
//        if (personAddress.getEndDate() != null)
//            setEndDate(personAddress.getEndDate());
        if (personAddress.getLatitude() != null)
            setLatitude(personAddress.getLatitude());
        if (personAddress.getLongitude() != null)
            setLatitude(personAddress.getLatitude());

        setUuid(personAddress.getUuid());

        return this;
    }
}
