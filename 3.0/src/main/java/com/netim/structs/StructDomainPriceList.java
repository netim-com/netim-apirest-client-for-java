package com.netim.structs;

import java.util.HashMap;
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
"Fee4Registration",
"Fee4Renewal",
"Fee4Restore",
"Fee4Transfer",
"Fee4Trade"
})

public class StructDomainPriceList {

    @JsonProperty("tld")
    private String tld;
    @JsonProperty("Fee4Registration")
    private String fee4Registration;
    @JsonProperty("Fee4Renewal")
    private String fee4Renewal;
    @JsonProperty("Fee4Restore")
    private String fee4Restore;
    @JsonProperty("Fee4Transfer")
    private String fee4Transfer;
    @JsonProperty("Fee4Trade")
    private String fee4Trade;
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

    @JsonProperty("Fee4Restore")
    public String getFee4Restore() {
        return fee4Restore;
    }

    @JsonProperty("Fee4Restore")
    public void setFee4Restore(String fee4Restore) {
        this.fee4Restore = fee4Restore;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}