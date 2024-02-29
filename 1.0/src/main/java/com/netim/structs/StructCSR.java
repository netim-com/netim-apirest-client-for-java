package com.netim.structs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CSR",
    "Country",
    "City",
    "Region",
    "Organisation",
    "Department",
    "EmailAddress",
    "CommonName",
    "AlternativeNames",
})

public class StructCSR implements StructInterface {

    @JsonProperty("CSR")
    private String CSR;
    @JsonProperty("Country")
    private String Country;
    @JsonProperty("City")
    private String City;
    @JsonProperty("Region")
    private String Region;
    @JsonProperty("Organisation")
    private String Organisation;
    @JsonProperty("Department")
    private String Department;
    @JsonProperty("EmailAddress")
    private String EmailAddress;
    @JsonProperty("CommonName")
    private List<String> CommonName;
    @JsonProperty("AlternativeNames")
    private List<String> AlternativeNames;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public StructCSR(String CSR, String Country, String City, String Region, String Organisation, String Department, String EmailAddress, List<String> CommonName, List<String> AlternativeNames) {
        this.CSR = CSR;
        this.Country = Country;
        this.City = City;
        this.Region = Region;
        this.Organisation = Organisation;
        this.Department = Department;
        this.EmailAddress = EmailAddress;
        this.CommonName = CommonName;
        this.AlternativeNames = AlternativeNames;
        this.additionalProperties = new HashMap<String, Object>();
    }

    public StructCSR() {
        this("", "", "", "", "", "", "", new ArrayList<String>(), new ArrayList<String>());
    }
    


    @JsonProperty("CSR")
    public String getCSR() {
        return this.CSR;
    }
    @JsonProperty("CSR")
    public void setCSR(String CSR) {
        this.CSR = CSR;
    }
    @JsonProperty("Country")
    public String getCountry() {
        return this.Country;
    }
    @JsonProperty("Country")
    public void setCountry(String Country) {
        this.Country = Country;
    }
    @JsonProperty("City")
    public String getCity() {
        return this.City;
    }
    @JsonProperty("City")
    public void setCity(String City) {
        this.City = City;
    }
    @JsonProperty("Region")
    public String getRegion() {
        return this.Region;
    }
    @JsonProperty("Region")
    public void setRegion(String Region) {
        this.Region = Region;
    }
    @JsonProperty("Organisation")
    public String getOrganisation() {
        return this.Organisation;
    }
    @JsonProperty("Organisation")
    public void setOrganisation(String Organisation) {
        this.Organisation = Organisation;
    }
    @JsonProperty("Department")
    public String getDepartment() {
        return this.Department;
    }
    @JsonProperty("Department")
    public void setDepartment(String Department) {
        this.Department = Department;
    }
    @JsonProperty("EmailAddress")
    public String getEmailAddress() {
        return this.EmailAddress;
    }
    @JsonProperty("EmailAddress")
    public void setEmailAddress(String EmailAddress) {
        this.EmailAddress = EmailAddress;
    }
    @JsonProperty("CommonName")
    public List<String> getCommonName() {
        return this.CommonName;
    }
    @JsonProperty("CommonName")
    public void setCommonName(List<String> CommonName) {
        this.CommonName = CommonName;
    }
    @JsonProperty("AlternativeNames")
    public List<String> getAlternativeNames() {
        return this.AlternativeNames;
    }
    @JsonProperty("AlternativeNames")
    public void setAlternativeNames(List<String> AlternativeNames) {
        this.AlternativeNames = AlternativeNames;
    }
    
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
