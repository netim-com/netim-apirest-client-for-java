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
    "firstName",
    "lastName",
    "bodyForm",
    "bodyName",
    "address1",
    "address2",
    "zipCode",
    "area",
    "city",
    "country",
    "phone",
    "fax",
    "email",
    "language",
    "isOwner",
    "tmName",
    "tmNumber",
    "tmType",
    "companyNumber",
    "vatNumber",
    "birthDate",
    "birthZipCode",
    "birthCity",
    "birthCountry",
    "idNumber",
    "additional"
})

public class StructSSLInfo implements StructInterface {

    @JsonProperty("IDSSL")
    private String IDSSL;
    @JsonProperty("prod")
    private String prod;
    @JsonProperty("commonName")
    private List<String> commonName;
    @JsonProperty("alternativeNames")
    private List<String> alternativeNames;
    @JsonProperty("dateCreation")
    private String dateCreation;
    @JsonProperty("dateEmission")
    private String dateEmission;
    @JsonProperty("dateExpiration")
    private String dateExpiration;
    @JsonProperty("certificate")
    private String certificate;
    @JsonProperty("csr")
    private String csr;
    @JsonProperty("privateKey")
    private String privateKey;
    @JsonProperty("status")
    private String status;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonProperty("IDSSL")
    public String getIDSSL() {
        return this.IDSSL;
    }
    @JsonProperty("IDSSL")
    public void setIDSSL(String IDSSL) {
        this.IDSSL = IDSSL;
    }
    @JsonProperty("prod")
    public String getProd() {
        return this.prod;
    }
    @JsonProperty("prod")
    public void setProd(String prod) {
        this.prod = prod;
    }
    @JsonProperty("commonName")
    public List<String> getCommonName() {
        return this.commonName;
    }
    @JsonProperty("commonName")
    public void setCommonName(List<String> commonName) {
        this.commonName = commonName;
    }
    @JsonProperty("alternativeNames")
    public List<String> getAlternativeNames() {
        return this.alternativeNames;
    }
    @JsonProperty("alternativeNames")
    public void setAlternativeNames(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }
    @JsonProperty("dateCreation")
    public String getDateCreation() {
        return this.dateCreation;
    }
    @JsonProperty("dateCreation")
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
    @JsonProperty("dateEmission")
    public String getDateEmission() {
        return this.dateEmission;
    }
    @JsonProperty("dateEmission")
    public void setDateEmission(String dateEmission) {
        this.dateEmission = dateEmission;
    }
    @JsonProperty("dateExpiration")
    public String getDateExpiration() {
        return this.dateExpiration;
    }
    @JsonProperty("dateExpiration")
    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
    @JsonProperty("certificate")
    public String getCertificate() {
        return this.certificate;
    }
    @JsonProperty("certificate")
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
    @JsonProperty("csr")
    public String getCsr() {
        return this.csr;
    }
    @JsonProperty("csr")
    public void setCsr(String csr) {
        this.csr = csr;
    }
    @JsonProperty("privateKey")
    public String getPrivateKey() {
        return this.privateKey;
    }
    @JsonProperty("privateKey")
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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
