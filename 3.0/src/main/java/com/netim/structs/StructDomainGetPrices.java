package com.netim.structs;

import com.netim.structs.StructInterface;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "FeeCurrency",
    "Fee4Registration",
	"RegistrationPromo",
	"RegistrationPromoUntil",
    "Fee4Renewal",
	"RenewalPromo",
	"RenewalPromoUntil",
    "Fee4Transfer",
	"TransferPromo",
	"TransferPromoUntil",
    "Fee4Trade",
	"TradePromo",
	"TradePromoUntil",
    "Fee4TransferTrade",
	"TransferTradePromo",
	"TransferTradePromoUntil",
    "Fee4Restore",
	"RestorePromo",
	"RestorePromoUntil",
    "Fee4TrusteeService",
	"TrusteeServicePromo",
	"TrusteeServicePromoUntil",
    "Fee4LocalContactService",
	"LocalContactServicePromo",
	"LocalContactServicePromoUntil",
    "IsPremium",
})

public class StructDomainGetPrices implements StructInterface {
    @JsonProperty("FeeCurrency")
    private String feeCurrency;
    @JsonProperty("Fee4Registration")
    private double fee4Registration;
    @JsonProperty("Fee4Renewal")
    private double fee4Renewal;
    @JsonProperty("Fee4Transfer")
    private double fee4Transfer;
    @JsonProperty("Fee4Trade")
    private double fee4Trade;
    @JsonProperty("Fee4TransferTrade")
    private double fee4TransferTrade;
    @JsonProperty("Fee4Restore")
    private double fee4Restore;
    @JsonProperty("Fee4TrusteeService")
    private double fee4TrusteeService;
    @JsonProperty("Fee4LocalContactService")
    private double fee4LocalContactService;
    @JsonProperty("IsPremium")
    private int isPremium;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("FeeCurrency")
    public String getFeeCurrency() {
        return feeCurrency;
    }

    @JsonProperty("FeeCurrency")
    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    @JsonProperty("Fee4Registration")
    public double getFee4Registration() {
        return fee4Registration;
    }

    @JsonProperty("Fee4Registration")
    public void setFee4Registration(double fee4Registration) {
        this.fee4Registration = fee4Registration;
    }

    @JsonProperty("Fee4Renewal")
    public double getFee4Renewal() {
        return fee4Renewal;
    }

    @JsonProperty("Fee4Renewal")
    public void setFee4Renewal(double fee4Renewal) {
        this.fee4Renewal = fee4Renewal;
    }

    @JsonProperty("Fee4Transfer")
    public double getFee4Transfer() {
        return fee4Transfer;
    }

    @JsonProperty("Fee4Transfer")
    public void setFee4Transfer(double fee4Transfer) {
        this.fee4Transfer = fee4Transfer;
    }

    @JsonProperty("Fee4Trade")
    public double getFee4Trade() {
        return fee4Trade;
    }

    @JsonProperty("Fee4Trade")
    public void setFee4Trade(double fee4Trade) {
        this.fee4Trade = fee4Trade;
    }

    @JsonProperty("Fee4TransferTrade")
    public double getFee4TransferTrade() {
        return fee4TransferTrade;
    }

    @JsonProperty("Fee4TransferTrade")
    public void setFee4TransferTrade(double fee4TransferTrade) {
        this.fee4TransferTrade = fee4TransferTrade;
    }

    @JsonProperty("Fee4Restore")
    public double getFee4Restore() {
        return fee4Restore;
    }

    @JsonProperty("Fee4Restore")
    public void setFee4Restore(double fee4Restore) {
        this.fee4Restore = fee4Restore;
    }

    @JsonProperty("Fee4TrusteeService")
    public double getFee4TrusteeService() {
        return fee4TrusteeService;
    }

    @JsonProperty("Fee4TrusteeService")
    public void setFee4TrusteeService(double fee4TrusteeService) {
        this.fee4TrusteeService = fee4TrusteeService;
    }

    @JsonProperty("Fee4LocalContactService")
    public double getFee4LocalContactService() {
        return fee4LocalContactService;
    }

    @JsonProperty("Fee4LocalContactService")
    public void setFee4LocalContactService(double fee4LocalContactService) {
        this.fee4LocalContactService = fee4LocalContactService;
    }

    @JsonProperty("IsPremium")
    public int getIsPremium() {
        return isPremium;
    }

    @JsonProperty("IsPremium")
    public void setIsPremium(int isPremium) {
        this.isPremium = isPremium;
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
