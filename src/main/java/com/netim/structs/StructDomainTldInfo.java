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
    "FeeCurrency",
    "Fee4Registration",
    "Fee4Renewal",
    "Fee4Transfer",
    "Fee4Trade",
    "Fee4Restore",
    "Fee4TrusteeService",
    "Fee4LocalContactService",
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
    @JsonProperty("FeeCurrency")
    private String feeCurrency;
    @JsonProperty("Fee4Registration")
    private String fee4Registration;
    @JsonProperty("Fee4Renewal")
    private String fee4Renewal;
    @JsonProperty("Fee4Transfer")
    private String fee4Transfer;
    @JsonProperty("Fee4Trade")
    private String fee4Trade;
    @JsonProperty("Fee4Restore")
    private String fee4Restore;
    @JsonProperty("Fee4TrusteeService")
    private String fee4TrusteeService;
    @JsonProperty("Fee4LocalContactService")
    private String fee4LocalContactService;
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

    @JsonProperty("FeeCurrency")
    public String getFeeCurrency() {
        return feeCurrency;
    }

    @JsonProperty("FeeCurrency")
    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    @JsonProperty("Fee4Registration")
    public String getFee4Registration() {
        return fee4Registration;
    }

    @JsonProperty("Fee4Registration")
    public void setFee4Registration(String fee4Registration) {
        this.fee4Registration = fee4Registration;
    }

    @JsonProperty("Fee4Renewal")
    public String getFee4Renewal() {
        return fee4Renewal;
    }

    @JsonProperty("Fee4Renewal")
    public void setFee4Renewal(String fee4Renewal) {
        this.fee4Renewal = fee4Renewal;
    }

    @JsonProperty("Fee4Transfer")
    public String getFee4Transfer() {
        return fee4Transfer;
    }

    @JsonProperty("Fee4Transfer")
    public void setFee4Transfer(String fee4Transfer) {
        this.fee4Transfer = fee4Transfer;
    }

    @JsonProperty("Fee4Trade")
    public String getFee4Trade() {
        return fee4Trade;
    }

    @JsonProperty("Fee4Trade")
    public void setFee4Trade(String fee4Trade) {
        this.fee4Trade = fee4Trade;
    }

    @JsonProperty("Fee4Restore")
    public String getFee4Restore() {
        return fee4Restore;
    }

    @JsonProperty("Fee4Restore")
    public void setFee4Restore(String fee4Restore) {
        this.fee4Restore = fee4Restore;
    }

    @JsonProperty("Fee4TrusteeService")
    public String getFee4TrusteeService() {
        return fee4TrusteeService;
    }

    @JsonProperty("Fee4TrusteeService")
    public void setFee4TrusteeService(String fee4TrusteeService) {
        this.fee4TrusteeService = fee4TrusteeService;
    }

    @JsonProperty("Fee4LocalContactService")
    public String getFee4LocalContactService() {
        return fee4LocalContactService;
    }

    @JsonProperty("Fee4LocalContactService")
    public void setFee4LocalContactService(String fee4LocalContactService) {
        this.fee4LocalContactService = fee4LocalContactService;
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
