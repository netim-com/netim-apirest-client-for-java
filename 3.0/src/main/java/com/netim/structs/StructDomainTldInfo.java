package com.netim.structs;

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
    "tld",
    "Country",
    "Extensions",
    "PeriodCreate",
    "PeriodRenew",
    "DelaiRenewBeforeExpiration",
    "DelaiRenewAfterExpiration",
    "DelaiRestoreAfterDelete",
    "HasEppCode",
    "HasRegistrarLock",
    "HasAutorenew",
    "HasWhoisPrivacy",
    "HasMultipleCheck",
    "HasImmediateDelete",
    "HasTrusteeService",
    "HasLocalContactService",
    "HasZonecheck",
    "HasDnsSec",
    "Informations"
})

public class StructDomainTldInfo {

    @JsonProperty("tld")
    private String tld;
    @JsonProperty("Country")
    private Object country;
    @JsonProperty("Extensions")
    private List<DomainTldInfoExtension> extensions = null;
    @JsonProperty("PeriodCreate")
    private String periodCreate;
    @JsonProperty("PeriodRenew")
    private String periodRenew;
    @JsonProperty("DelaiRenewBeforeExpiration")
    private String delaiRenewBeforeExpiration;
    @JsonProperty("DelaiRenewAfterExpiration")
    private String delaiRenewAfterExpiration;
    @JsonProperty("DelaiRestoreAfterDelete")
    private String delaiRestoreAfterDelete;
    @JsonProperty("HasEppCode")
    private Integer hasEppCode;
    @JsonProperty("HasRegistrarLock")
    private Integer hasRegistrarLock;
    @JsonProperty("HasAutorenew")
    private Integer hasAutorenew;
    @JsonProperty("HasWhoisPrivacy")
    private Integer hasWhoisPrivacy;
    @JsonProperty("HasMultipleCheck")
    private Integer hasMultipleCheck;
    @JsonProperty("HasImmediateDelete")
    private Integer hasImmediateDelete;
    @JsonProperty("HasTrusteeService")
    private Integer hasTrusteeService;
    @JsonProperty("HasLocalContactService")
    private Integer hasLocalContactService;
    @JsonProperty("HasZonecheck")
    private Integer hasZonecheck;
    @JsonProperty("HasDnsSec")
    private Integer hasDnsSec;
    @JsonProperty("Informations")
    private String informations;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("tld")
    public String getTld() {
        return tld;
    }

    @JsonProperty("tld")
    public void setTld(String tld) {
        this.tld = tld;
    }

    @JsonProperty("Country")
    public Object getCountry() {
        return country;
    }

    @JsonProperty("Country")
    public void setCountry(Object country) {
        this.country = country;
    }

    @JsonProperty("Extensions")
    public List<DomainTldInfoExtension> getExtensions() {
        return extensions;
    }

    @JsonProperty("Extensions")
    public void setExtensions(List<DomainTldInfoExtension> extensions) {
        this.extensions = extensions;
    }

    @JsonProperty("PeriodCreate")
    public String getPeriodCreate() {
        return periodCreate;
    }

    @JsonProperty("PeriodCreate")
    public void setPeriodCreate(String periodCreate) {
        this.periodCreate = periodCreate;
    }

    @JsonProperty("PeriodRenew")
    public String getPeriodRenew() {
        return periodRenew;
    }

    @JsonProperty("PeriodRenew")
    public void setPeriodRenew(String periodRenew) {
        this.periodRenew = periodRenew;
    }

    @JsonProperty("DelaiRenewBeforeExpiration")
    public String getDelaiRenewBeforeExpiration() {
        return delaiRenewBeforeExpiration;
    }

    @JsonProperty("DelaiRenewBeforeExpiration")
    public void setDelaiRenewBeforeExpiration(String delaiRenewBeforeExpiration) {
        this.delaiRenewBeforeExpiration = delaiRenewBeforeExpiration;
    }

    @JsonProperty("DelaiRenewAfterExpiration")
    public String getDelaiRenewAfterExpiration() {
        return delaiRenewAfterExpiration;
    }

    @JsonProperty("DelaiRenewAfterExpiration")
    public void setDelaiRenewAfterExpiration(String delaiRenewAfterExpiration) {
        this.delaiRenewAfterExpiration = delaiRenewAfterExpiration;
    }

    @JsonProperty("DelaiRestoreAfterDelete")
    public String getDelaiRestoreAfterDelete() {
        return delaiRestoreAfterDelete;
    }

    @JsonProperty("DelaiRestoreAfterDelete")
    public void setDelaiRestoreAfterDelete(String delaiRestoreAfterDelete) {
        this.delaiRestoreAfterDelete = delaiRestoreAfterDelete;
    }

    @JsonProperty("HasEppCode")
    public Integer getHasEppCode() {
        return hasEppCode;
    }

    @JsonProperty("HasEppCode")
    public void setHasEppCode(Integer hasEppCode) {
        this.hasEppCode = hasEppCode;
    }

    @JsonProperty("HasRegistrarLock")
    public Integer getHasRegistrarLock() {
        return hasRegistrarLock;
    }

    @JsonProperty("HasRegistrarLock")
    public void setHasRegistrarLock(Integer hasRegistrarLock) {
        this.hasRegistrarLock = hasRegistrarLock;
    }

    @JsonProperty("HasAutorenew")
    public Integer getHasAutorenew() {
        return hasAutorenew;
    }

    @JsonProperty("HasAutorenew")
    public void setHasAutorenew(Integer hasAutorenew) {
        this.hasAutorenew = hasAutorenew;
    }

    @JsonProperty("HasWhoisPrivacy")
    public Integer getHasWhoisPrivacy() {
        return hasWhoisPrivacy;
    }

    @JsonProperty("HasWhoisPrivacy")
    public void setHasWhoisPrivacy(Integer hasWhoisPrivacy) {
        this.hasWhoisPrivacy = hasWhoisPrivacy;
    }

    @JsonProperty("HasMultipleCheck")
    public Integer getHasMultipleCheck() {
        return hasMultipleCheck;
    }

    @JsonProperty("HasMultipleCheck")
    public void setHasMultipleCheck(Integer hasMultipleCheck) {
        this.hasMultipleCheck = hasMultipleCheck;
    }

    @JsonProperty("HasImmediateDelete")
    public Integer getHasImmediateDelete() {
        return hasImmediateDelete;
    }

    @JsonProperty("HasImmediateDelete")
    public void setHasImmediateDelete(Integer hasImmediateDelete) {
        this.hasImmediateDelete = hasImmediateDelete;
    }

    @JsonProperty("HasTrusteeService")
    public Integer getHasTrusteeService() {
        return hasTrusteeService;
    }

    @JsonProperty("HasTrusteeService")
    public void setHasTrusteeService(Integer hasTrusteeService) {
        this.hasTrusteeService = hasTrusteeService;
    }

    @JsonProperty("HasLocalContactService")
    public Integer getHasLocalContactService() {
        return hasLocalContactService;
    }

    @JsonProperty("HasLocalContactService")
    public void setHasLocalContactService(Integer hasLocalContactService) {
        this.hasLocalContactService = hasLocalContactService;
    }

    @JsonProperty("HasZonecheck")
    public Integer getHasZonecheck() {
        return hasZonecheck;
    }

    @JsonProperty("HasZonecheck")
    public void setHasZonecheck(Integer hasZonecheck) {
        this.hasZonecheck = hasZonecheck;
    }

    @JsonProperty("HasDnsSec")
    public Integer getHasDnsSec() {
        return hasDnsSec;
    }

    @JsonProperty("HasDnsSec")
    public void setHasDnsSec(Integer hasDnsSec) {
        this.hasDnsSec = hasDnsSec;
    }

    @JsonProperty("Informations")
    public String getInformations() {
        return informations;
    }

    @JsonProperty("Informations")
    public void setInformations(String informations) {
        this.informations = informations;
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
