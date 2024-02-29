package com.netim.structs;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "BALANCE_AMOUNT",
    "BALANCE_LOW_LIMIT",
    "BALANCE_HARD_LIMIT",
    "DOMAIN_AUTO_RENEW",
    "HOSTING_AUTO_RENEW",
    "DEFAULT_OWNER",
    "DEFAULT_BILLING",
    "DEFAULT_TECH",
    "DEFAULT_ADMIN",
    "PREMIUM_PROCESSING",
    "DEFAULT_DNS_5",
    "DEFAULT_DNS_4",
    "DEFAULT_DNS_3",
    "DEFAULT_DNS_2",
    "DEFAULT_DNS_1",
    "DEFAULT_DNS_TEMPLATE"
})

public class StructQueryResellerAccount implements StructInterface {

    @JsonProperty("BALANCE_AMOUNT")
    private String balanceAmount;
    @JsonProperty("BALANCE_LOW_LIMIT")
    private String balanceLowLimit;
    @JsonProperty("BALANCE_HARD_LIMIT")
    private String balanceHardLimit;
    @JsonProperty("DOMAIN_AUTO_RENEW")
    private String domainAutoRenew;
    @JsonProperty("HOSTING_AUTO_RENEW")
    private String hostingAutoRenew;
    @JsonProperty("DEFAULT_OWNER")
    private String defaultOwner;
    @JsonProperty("DEFAULT_BILLING")
    private String defaultBilling;
    @JsonProperty("DEFAULT_TECH")
    private String defaultTech;
    @JsonProperty("DEFAULT_ADMIN")
    private String defaultAdmin;
    @JsonProperty("PREMIUM_PROCESSING")
    private String premiumProcessing;
    @JsonProperty("DEFAULT_DNS_5")
    private String defaultDns5;
    @JsonProperty("DEFAULT_DNS_4")
    private String defaultDns4;
    @JsonProperty("DEFAULT_DNS_3")
    private String defaultDns3;
    @JsonProperty("DEFAULT_DNS_2")
    private String defaultDns2;
    @JsonProperty("DEFAULT_DNS_1")
    private String defaultDns1;
    @JsonProperty("DEFAULT_DNS_TEMPLATE")
    private Map<String, String> defaultDnsTemplate  = new HashMap<String, String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("BALANCE_AMOUNT")
    public String getBalanceAmount() {
        return balanceAmount;
    }

    @JsonProperty("BALANCE_AMOUNT")
    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @JsonProperty("BALANCE_LOW_LIMIT")
    public String getBalanceLowLimit() {
        return balanceLowLimit;
    }

    @JsonProperty("BALANCE_LOW_LIMIT")
    public void setBalanceLowLimit(String balanceLowLimit) {
        this.balanceLowLimit = balanceLowLimit;
    }

    @JsonProperty("BALANCE_HARD_LIMIT")
    public String getBalanceHardLimit() {
        return balanceHardLimit;
    }

    @JsonProperty("BALANCE_HARD_LIMIT")
    public void setBalanceHardLimit(String balanceHardLimit) {
        this.balanceHardLimit = balanceHardLimit;
    }

    @JsonProperty("DOMAIN_AUTO_RENEW")
    public String getDomainAutoRenew() {
        return domainAutoRenew;
    }

    @JsonProperty("DOMAIN_AUTO_RENEW")
    public void setDomainAutoRenew(String domainAutoRenew) {
        this.domainAutoRenew = domainAutoRenew;
    }

    @JsonProperty("HOSTING_AUTO_RENEW")
    public String getHostingAutoRenew() {
        return hostingAutoRenew;
    }

    @JsonProperty("HOSTING_AUTO_RENEW")
    public void setHostingAutoRenew(String hostingAutoRenew) {
        this.hostingAutoRenew = hostingAutoRenew;
    }

    @JsonProperty("DEFAULT_OWNER")
    public String getDefaultOwner() {
        return defaultOwner;
    }

    @JsonProperty("DEFAULT_OWNER")
    public void setDefaultOwner(String defaultOwner) {
        this.defaultOwner = defaultOwner;
    }

    @JsonProperty("DEFAULT_BILLING")
    public String getDefaultBilling() {
        return defaultBilling;
    }

    @JsonProperty("DEFAULT_BILLING")
    public void setDefaultBilling(String defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    @JsonProperty("DEFAULT_TECH")
    public String getDefaultTech() {
        return defaultTech;
    }

    @JsonProperty("DEFAULT_TECH")
    public void setDefaultTech(String defaultTech) {
        this.defaultTech = defaultTech;
    }

    @JsonProperty("DEFAULT_ADMIN")
    public String getDefaultAdmin() {
        return defaultAdmin;
    }

    @JsonProperty("DEFAULT_ADMIN")
    public void setDefaultAdmin(String defaultAdmin) {
        this.defaultAdmin = defaultAdmin;
    }

    @JsonProperty("PREMIUM_PROCESSING")
    public String getPremiumProcessing() {
        return premiumProcessing;
    }

    @JsonProperty("PREMIUM_PROCESSING")
    public void setPremiumProcessing(String premiumProcessing) {
        this.premiumProcessing = premiumProcessing;
    }

    @JsonProperty("DEFAULT_DNS_5")
    public String getDefaultDns5() {
        return defaultDns5;
    }

    @JsonProperty("DEFAULT_DNS_5")
    public void setDefaultDns5(String defaultDns5) {
        this.defaultDns5 = defaultDns5;
    }

    @JsonProperty("DEFAULT_DNS_4")
    public String getDefaultDns4() {
        return defaultDns4;
    }

    @JsonProperty("DEFAULT_DNS_4")
    public void setDefaultDns4(String defaultDns4) {
        this.defaultDns4 = defaultDns4;
    }

    @JsonProperty("DEFAULT_DNS_3")
    public String getDefaultDns3() {
        return defaultDns3;
    }

    @JsonProperty("DEFAULT_DNS_3")
    public void setDefaultDns3(String defaultDns3) {
        this.defaultDns3 = defaultDns3;
    }

    @JsonProperty("DEFAULT_DNS_2")
    public String getDefaultDns2() {
        return defaultDns2;
    }

    @JsonProperty("DEFAULT_DNS_2")
    public void setDefaultDns2(String defaultDns2) {
        this.defaultDns2 = defaultDns2;
    }

    @JsonProperty("DEFAULT_DNS_1")
    public String getDefaultDns1() {
        return defaultDns1;
    }

    @JsonProperty("DEFAULT_DNS_1")
    public void setDefaultDns1(String defaultDns1) {
        this.defaultDns1 = defaultDns1;
    }

    @JsonProperty("DEFAULT_DNS_TEMPLATE")
    public Map<String,String> getDefaultDnsTemplate() {
        return defaultDnsTemplate;
    }

    @JsonProperty("DEFAULT_DNS_TEMPLATE")
    public void setDefaultDnsTemplate(Map<String,String> defaultDnsTemplate) {
        this.defaultDnsTemplate = defaultDnsTemplate;
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