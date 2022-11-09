package com.netim.structs;

import com.netim.structs.StructInterface;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "domain",
    "dateCreate",
    "dateExpiration",
    "dateMinRenew",
    "dateMaxRenew",
    "dateMaxRestore",
    "status",
    "idOwner",
    "idAdmin",
    "idTech",
    "idBilling",
    "domainIsLock",
    "whoisPrivacy",
    "autoRenew",
    "ns",
    "IsSigned",
    "HasDNS4Service",
    "authID",
    "DNSSEC"
})

public class StructDomainInfo implements StructInterface {

    @JsonProperty("domain")
    private String domain;
    @JsonProperty("dateCreate")
    private Date dateCreate;
    @JsonProperty("dateExpiration")
    private Date dateExpiration;
    @JsonProperty("dateMinRenew")
    private Date dateMinRenew;
    @JsonProperty("dateMaxRenew")
    private Date dateMaxRenew;
    @JsonProperty("dateMaxRestore")
    private Date dateMaxRestore;
    @JsonProperty("status")
    private String status;
    @JsonProperty("idOwner")
    private String idOwner;
    @JsonProperty("idAdmin")
    private String idAdmin;
    @JsonProperty("idTech")
    private String idTech;
    @JsonProperty("idBilling")
    private String idBilling;
    @JsonProperty("domainIsLock")
    private Integer domainIsLock;
    @JsonProperty("whoisPrivacy")
    private Integer whoisPrivacy;
    @JsonProperty("autoRenew")
    private Integer autoRenew;
    @JsonProperty("ns")
    private List<String> ns = null;
    @JsonProperty("IsSigned")
    private Integer isSigned;
    @JsonProperty("HasDNS4Service")
    private Integer hasDNS4Service;
    @JsonProperty("authID")
    private String authID;
    @JsonProperty("DNSSEC")
    private Map<String, String> DNSSEC;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    @JsonProperty("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @JsonProperty("dateCreate")
    public Date getDateCreate() {
        return dateCreate;
    }

    @JsonProperty("dateCreate")
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @JsonProperty("dateExpiration")
    public Date getDateExpiration() {
        return dateExpiration;
    }

    @JsonProperty("dateExpiration")
    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    @JsonProperty("dateMinRenew")
    public Date getDateMinRenew() {
        return dateMinRenew;
    }

    @JsonProperty("dateMinRenew")
    public void setDateMinRenew(Date dateMinRenew) {
        this.dateMinRenew = dateMinRenew;
    }

    @JsonProperty("dateMaxRenew")
    public Date getDateMaxRenew() {
        return dateMaxRenew;
    }

    @JsonProperty("dateMaxRenew")
    public void setDateMaxRenew(Date dateMaxRenew) {
        this.dateMaxRenew = dateMaxRenew;
    }

    @JsonProperty("dateMaxRestore")
    public Date getDateMaxRestore() {
        return dateMaxRestore;
    }

    @JsonProperty("dateMaxRestore")
    public void setDateMaxRestore(Date dateMaxRestore) {
        this.dateMaxRestore = dateMaxRestore;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("idOwner")
    public String getIdOwner() {
        return idOwner;
    }

    @JsonProperty("idOwner")
    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    @JsonProperty("idAdmin")
    public String getIdAdmin() {
        return idAdmin;
    }

    @JsonProperty("idAdmin")
    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    @JsonProperty("idTech")
    public String getIdTech() {
        return idTech;
    }

    @JsonProperty("idTech")
    public void setIdTech(String idTech) {
        this.idTech = idTech;
    }

    @JsonProperty("idBilling")
    public String getIdBilling() {
        return idBilling;
    }

    @JsonProperty("idBilling")
    public void setIdBilling(String idBilling) {
        this.idBilling = idBilling;
    }

    @JsonProperty("domainIsLock")
    public Integer getDomainIsLock() {
        return domainIsLock;
    }

    @JsonProperty("domainIsLock")
    public void setDomainIsLock(Integer domainIsLock) {
        this.domainIsLock = domainIsLock;
    }

    @JsonProperty("whoisPrivacy")
    public Integer getWhoisPrivacy() {
        return whoisPrivacy;
    }

    @JsonProperty("whoisPrivacy")
    public void setWhoisPrivacy(Integer whoisPrivacy) {
        this.whoisPrivacy = whoisPrivacy;
    }

    @JsonProperty("autoRenew")
    public Integer getAutoRenew() {
        return autoRenew;
    }

    @JsonProperty("autoRenew")
    public void setAutoRenew(Integer autoRenew) {
        this.autoRenew = autoRenew;
    }

    @JsonProperty("ns")
    public List<String> getNs() {
        return ns;
    }

    @JsonProperty("ns")
    public void setNs(List<String> ns) {
        this.ns = ns;
    }

    @JsonProperty("IsSigned")
    public Integer getIsSigned() {
        return isSigned;
    }

    @JsonProperty("IsSigned")
    public void setIsSigned(Integer isSigned) {
        this.isSigned = isSigned;
    }

    @JsonProperty("HasDNS4Service")
    public Integer getHasDNS4Service() {
        return hasDNS4Service;
    }

    @JsonProperty("HasDNS4Service")
    public void setHasDNS4Service(Integer hasDNS4Service) {
        this.hasDNS4Service = hasDNS4Service;
    }

    @JsonProperty("authID")
    public String getAuthID() {
        return authID;
    }

    @JsonProperty("authID")
    public void setAuthID(String authID) {
        this.authID = authID;
    }

    @JsonProperty("DNSSEC")
    public Map<String, String> getDNSSEC() {
        return DNSSEC;
    }

    @JsonProperty("DNSSEC")
    public void setDNSSEC(Map<String,String> DNSSEC) {
        this.DNSSEC = DNSSEC;
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