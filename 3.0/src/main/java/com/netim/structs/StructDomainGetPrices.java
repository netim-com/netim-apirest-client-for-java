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
	"IsPremium",
	"Fee4Registration",
	"RegistrationDiscount",
	"RegistrationDiscountEndDate",
	"StandardFee4Registration",
	"Fee4Renewal",
	"RenewalDiscount",
	"RenewalDiscountEndDate",
	"StandardFee4Renewal",
	"Fee4Transfer",
	"TransferDiscount",
	"TransferDiscountEndDate",
	"StandardFee4Transfer",
	"Fee4Trade",
	"TradeDiscount",
	"TradeDiscountEndDate",
	"StandardFee4Trade",
	"Fee4TransferTrade",
	"TransferTradeDiscount",
	"TransferTradeDiscountEndDate",
	"StandardFee4TransferTrade",
	"Fee4Restore",
	"RestoreDiscount",
	"RestoreDiscountEndDate",
	"StandardFee4Restore",
	"Fee4Sunrise",
	"Fee4Sunrise2",
	"Fee4Sunrise3",
	"Fee4Landrush",
	"Fee4Landrush2",
	"Fee4Landrush3",
	"Fee4TrusteeService",
	"TrusteeServiceDiscount",
	"TrusteeServiceDiscountEndDate",
	"StandardFee4TrusteeService",
	"Fee4LocalContactService",
	"LocalContactServiceDiscount",
	"LocalContactServiceDiscountEndDate",
	"StandardFee4LocalContactService"
})

public class StructDomainGetPrices implements StructInterface {
    @JsonProperty("FeeCurrency")
    private String feeCurrency;
	@JsonProperty("IsPremium")
	private boolean IsPremium;
	@JsonProperty("Fee4Registration")
	private String fee4Registration;
	@JsonProperty("RegistrationDiscount")
	private boolean registrationDiscount;
	@JsonProperty("RegistrationDiscountEndDate")
	private String registrationDiscountEndDate;
	@JsonProperty("StandardFee4Registration")
	private String standardFee4Registration;
	@JsonProperty("Fee4Renewal")
	private String fee4Renewal;
	@JsonProperty("RenewalDiscount")
	private boolean renewalDiscount;
	@JsonProperty("RenewalDiscountEndDate")
	private String renewalDiscountEndDate;
	@JsonProperty("StandardFee4Renewal")
	private String standardFee4Renewal;
	@JsonProperty("Fee4Transfer")
	private String fee4Transfer;
	@JsonProperty("TransferDiscount")
	private boolean transferDiscount;
	@JsonProperty("TransferDiscountEndDate")
	private String transferDiscountEndDate;
	@JsonProperty("StandardFee4Transfer")
	private String standardFee4Transfer;
	@JsonProperty("Fee4Trade")
	private String fee4Trade;
	@JsonProperty("TradeDiscount")
	private boolean tradeDiscount;
	@JsonProperty("TradeDiscountEndDate")
	private String tradeDiscountEndDate;
	@JsonProperty("StandardFee4Trade")
	private String standardFee4Trade;
	@JsonProperty("Fee4TransferTrade")
	private String fee4TransferTrade;
	@JsonProperty("TransferTradeDiscount")
	private boolean transferTradeDiscount;
	@JsonProperty("TransferTradeDiscountEndDate")
	private String transferTradeDiscountEndDate;
	@JsonProperty("StandardFee4TransferTrade")
	private String standardFee4TransferTrade;
	@JsonProperty("Fee4Restore")
	private String fee4Restore;
	@JsonProperty("RestoreDiscount")
	private boolean restoreDiscount;
	@JsonProperty("RestoreDiscountEndDate")
	private String restoreDiscountEndDate;
	@JsonProperty("StandardFee4Restore")
	private String standardFee4Restore;
	@JsonProperty("Fee4Sunrise")
	private String fee4Sunrise;
	@JsonProperty("Fee4Sunrise2")
	private String fee4Sunrise2;
	@JsonProperty("Fee4Sunrise3")
	private String fee4Sunrise3;
	@JsonProperty("Fee4Landrush")
	private String fee4Landrush;
	@JsonProperty("Fee4Landrush2")
	private String fee4Landrush2;
	@JsonProperty("Fee4Landrush3")
	private String fee4Landrush3;
	@JsonProperty("Fee4TrusteeService")
	private String fee4TrusteeService;
	@JsonProperty("TrusteeServiceDiscount")
	private boolean trusteeServiceDiscount;
	@JsonProperty("TrusteeServiceDiscountEndDate")
	private String trusteeServiceDiscountEndDate;
	@JsonProperty("StandardFee4TrusteeService")
	private String standardFee4TrusteeService;
	@JsonProperty("Fee4LocalContactService")
	private String fee4LocalContactService;
	@JsonProperty("LocalContactServiceDiscount")
	private boolean localContactServiceDiscount;
	@JsonProperty("LocalContactServiceDiscountEndDate")
	private String localContactServiceDiscountEndDate;
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

	@JsonProperty("IsPremium")
	public boolean getIsPremium() {
		return IsPremium;
	}

	@JsonProperty("IsPremium")
	public void setIsPremium(boolean IsPremium) {
		this.IsPremium = IsPremium;
	}

	@JsonProperty("Fee4Registration")
	public String getFee4Registration() {
		return fee4Registration;
	}

	@JsonProperty("Fee4Registration")
	public void setFee4Registration(String fee4Registration) {
		this.fee4Registration = fee4Registration;
	}

	@JsonProperty("RegistrationDiscount")
	public boolean getRegistrationDiscount() {
		return registrationDiscount;
	}

	@JsonProperty("RegistrationDiscount")
	public void setRegistrationDiscount(boolean registrationDiscount) {
		this.registrationDiscount = registrationDiscount;
	}

	@JsonProperty("RegistrationDiscountEndDate")
	public String getRegistrationDiscountEndDate() {
		return registrationDiscountEndDate;
	}

	@JsonProperty("RegistrationDiscountEndDate")
	public void setRegistrationDiscountEndDate(String registrationDiscountEndDate) {
		this.registrationDiscountEndDate = registrationDiscountEndDate;
	}

	@JsonProperty("StandardFee4Registration")
	public String getStandardFee4Registration() {
		return standardFee4Registration;
	}

	@JsonProperty("StandardFee4Registration")
	public void setStandardFee4Registration(String standardFee4Registration) {
		this.standardFee4Registration = standardFee4Registration;
	}

	@JsonProperty("Fee4Renewal")
	public String getFee4Renewal() {
		return fee4Renewal;
	}

	@JsonProperty("Fee4Renewal")
	public void setFee4Renewal(String fee4Renewal) {
		this.fee4Renewal = fee4Renewal;
	}

	@JsonProperty("RenewalDiscount")
	public boolean getRenewalDiscount() {
		return renewalDiscount;
	}

	@JsonProperty("RenewalDiscount")
	public void setRenewalDiscount(boolean renewalDiscount) {
		this.renewalDiscount = renewalDiscount;
	}

	@JsonProperty("RenewalDiscountEndDate")
	public String getRenewalDiscountEndDate() {
		return renewalDiscountEndDate;
	}

	@JsonProperty("RenewalDiscountEndDate")
	public void setRenewalDiscountEndDate(String renewalDiscountEndDate) {
		this.renewalDiscountEndDate = renewalDiscountEndDate;
	}

	@JsonProperty("StandardFee4Renewal")
	public String getStandardFee4Renewal() {
		return standardFee4Renewal;
	}

	@JsonProperty("StandardFee4Renewal")
	public void setStandardFee4Renewal(String standardFee4Renewal) {
		this.standardFee4Renewal = standardFee4Renewal;
	}

	@JsonProperty("Fee4Transfer")
	public String getFee4Transfer() {
		return fee4Transfer;
	}

	@JsonProperty("Fee4Transfer")
	public void setFee4Transfer(String fee4Transfer) {
		this.fee4Transfer = fee4Transfer;
	}

	@JsonProperty("TransferDiscount")
	public boolean getTransferDiscount() {
		return transferDiscount;
	}

	@JsonProperty("TransferDiscount")
	public void setTransferDiscount(boolean transferDiscount) {
		this.transferDiscount = transferDiscount;
	}

	@JsonProperty("TransferDiscountEndDate")
	public String getTransferDiscountEndDate() {
		return transferDiscountEndDate;
	}

	@JsonProperty("TransferDiscountEndDate")
	public void setTransferDiscountEndDate(String transferDiscountEndDate) {
		this.transferDiscountEndDate = transferDiscountEndDate;
	}

	@JsonProperty("StandardFee4Transfer")
	public String getStandardFee4Transfer() {
		return standardFee4Transfer;
	}

	@JsonProperty("StandardFee4Transfer")
	public void setStandardFee4Transfer(String standardFee4Transfer) {
		this.standardFee4Transfer = standardFee4Transfer;
	}

	@JsonProperty("Fee4Trade")
	public String getFee4Trade() {
		return fee4Trade;
	}

	@JsonProperty("Fee4Trade")
	public void setFee4Trade(String fee4Trade) {
		this.fee4Trade = fee4Trade;
	}

	@JsonProperty("TradeDiscount")
	public boolean getTradeDiscount() {
		return tradeDiscount;
	}

	@JsonProperty("TradeDiscount")
	public void setTradeDiscount(boolean tradeDiscount) {
		this.tradeDiscount = tradeDiscount;
	}

	@JsonProperty("TradeDiscountEndDate")
	public String getTradeDiscountEndDate() {
		return tradeDiscountEndDate;
	}

	@JsonProperty("TradeDiscountEndDate")
	public void setTradeDiscountEndDate(String tradeDiscountEndDate) {
		this.tradeDiscountEndDate = tradeDiscountEndDate;
	}

	@JsonProperty("StandardFee4Trade")
	public String getStandardFee4Trade() {
		return standardFee4Trade;
	}

	@JsonProperty("StandardFee4Trade")
	public void setStandardFee4Trade(String standardFee4Trade) {
		this.standardFee4Trade = standardFee4Trade;
	}

	@JsonProperty("Fee4TransferTrade")
	public String getFee4TransferTrade() {
		return fee4TransferTrade;
	}

	@JsonProperty("Fee4TransferTrade")
	public void setFee4TransferTrade(String fee4TransferTrade) {
		this.fee4TransferTrade = fee4TransferTrade;
	}

	@JsonProperty("TransferTradeDiscount")
	public boolean getTransferTradeDiscount() {
		return transferTradeDiscount;
	}

	@JsonProperty("TransferTradeDiscount")
	public void setTransferTradeDiscount(boolean transferTradeDiscount) {
		this.transferTradeDiscount = transferTradeDiscount;
	}

	@JsonProperty("TransferTradeDiscountEndDate")
	public String getTransferTradeDiscountEndDate() {
		return transferTradeDiscountEndDate;
	}

	@JsonProperty("TransferTradeDiscountEndDate")
	public void setTransferTradeDiscountEndDate(String transferTradeDiscountEndDate) {
		this.transferTradeDiscountEndDate = transferTradeDiscountEndDate;
	}

	@JsonProperty("StandardFee4TransferTrade")
	public String getStandardFee4TransferTrade() {
		return standardFee4TransferTrade;
	}

	@JsonProperty("StandardFee4TransferTrade")
	public void setStandardFee4TransferTrade(String standardFee4TransferTrade) {
		this.standardFee4TransferTrade = standardFee4TransferTrade;
	}

	@JsonProperty("Fee4Restore")
	public String getFee4Restore() {
		return fee4Restore;
	}

	@JsonProperty("Fee4Restore")
	public void setFee4Restore(String fee4Restore) {
		this.fee4Restore = fee4Restore;
	}

	@JsonProperty("RestoreDiscount")
	public boolean getRestoreDiscount() {
		return restoreDiscount;
	}

	@JsonProperty("RestoreDiscount")
	public void setRestoreDiscount(boolean restoreDiscount) {
		this.restoreDiscount = restoreDiscount;
	}

	@JsonProperty("RestoreDiscountEndDate")
	public String getRestoreDiscountEndDate() {
		return restoreDiscountEndDate;
	}

	@JsonProperty("RestoreDiscountEndDate")
	public void setRestoreDiscountEndDate(String restoreDiscountEndDate) {
		this.restoreDiscountEndDate = restoreDiscountEndDate;
	}

	@JsonProperty("StandardFee4Restore")
	public String getStandardFee4Restore() {
		return standardFee4Restore;
	}

	@JsonProperty("StandardFee4Restore")
	public void setStandardFee4Restore(String standardFee4Restore) {
		this.standardFee4Restore = standardFee4Restore;
	}

	@JsonProperty("Fee4Sunrise")
	public String getFee4Sunrise() {
		return fee4Sunrise;
	}

	@JsonProperty("Fee4Sunrise")
	public void setFee4Sunrise(String fee4Sunrise) {
		this.fee4Sunrise = fee4Sunrise;
	}

	@JsonProperty("Fee4Sunrise2")
	public String getFee4Sunrise2() {
		return fee4Sunrise2;
	}

	@JsonProperty("Fee4Sunrise2")
	public void setFee4Sunrise2(String fee4Sunrise2) {
		this.fee4Sunrise2 = fee4Sunrise2;
	}

	@JsonProperty("Fee4Sunrise3")
	public String getFee4Sunrise3() {
		return fee4Sunrise3;
	}

	@JsonProperty("Fee4Sunrise3")
	public void setFee4Sunrise3(String fee4Sunrise3) {
		this.fee4Sunrise3 = fee4Sunrise3;
	}

	@JsonProperty("Fee4Landrush")
	public String getFee4Landrush() {
		return fee4Landrush;
	}

	@JsonProperty("Fee4Landrush")
	public void setFee4Landrush(String fee4Landrush) {
		this.fee4Landrush = fee4Landrush;
	}

	@JsonProperty("Fee4Landrush2")
	public String getFee4Landrush2() {
		return fee4Landrush2;
	}

	@JsonProperty("Fee4Landrush2")
	public void setFee4Landrush2(String fee4Landrush2) {
		this.fee4Landrush2 = fee4Landrush2;
	}

	@JsonProperty("Fee4Landrush3")
	public String getFee4Landrush3() {
		return fee4Landrush3;
	}

	@JsonProperty("Fee4Landrush3")
	public void setFee4Landrush3(String fee4Landrush3) {
		this.fee4Landrush3 = fee4Landrush3;
	}

	@JsonProperty("Fee4TrusteeService")
	public String getFee4TrusteeService() {
		return fee4TrusteeService;
	}

	@JsonProperty("Fee4TrusteeService")
	public void setFee4TrusteeService(String fee4TrusteeService) {
		this.fee4TrusteeService = fee4TrusteeService;
	}

	@JsonProperty("TrusteeServiceDiscount")
	public boolean getTrusteeServiceDiscount() {
		return trusteeServiceDiscount;
	}

	@JsonProperty("TrusteeServiceDiscount")
	public void setTrusteeServiceDiscount(boolean trusteeServiceDiscount) {
		this.trusteeServiceDiscount = trusteeServiceDiscount;
	}

	@JsonProperty("TrusteeServiceDiscountEndDate")
	public String getTrusteeServiceDiscountEndDate() {
		return trusteeServiceDiscountEndDate;
	}

	@JsonProperty("TrusteeServiceDiscountEndDate")
	public void setTrusteeServiceDiscountEndDate(String trusteeServiceDiscountEndDate) {
		this.trusteeServiceDiscountEndDate = trusteeServiceDiscountEndDate;
	}

	@JsonProperty("StandardFee4TrusteeService")
	public String getStandardFee4TrusteeService() {
		return standardFee4TrusteeService;
	}

	@JsonProperty("StandardFee4TrusteeService")
	public void setStandardFee4TrusteeService(String standardFee4TrusteeService) {
		this.standardFee4TrusteeService = standardFee4TrusteeService;
	}

	@JsonProperty("Fee4LocalContactService")
	public String getFee4LocalContactService() {
		return fee4LocalContactService;
	}

	@JsonProperty("Fee4LocalContactService")
	public void setFee4LocalContactService(String fee4LocalContactService) {
		this.fee4LocalContactService = fee4LocalContactService;
	}

	@JsonProperty("LocalContactServiceDiscount")
	public boolean getLocalContactServiceDiscount() {
		return localContactServiceDiscount;
	}

	@JsonProperty("LocalContactServiceDiscount")
	public void setLocalContactServiceDiscount(boolean localContactServiceDiscount) {
		this.localContactServiceDiscount = localContactServiceDiscount;
	}

	@JsonProperty("LocalContactServiceDiscountEndDate")
	public String getLocalContactServiceDiscountEndDate() {
		return localContactServiceDiscountEndDate;
	}

	@JsonProperty("LocalContactServiceDiscountEndDate")
	public void setLocalContactServiceDiscountEndDate(String localContactServiceDiscountEndDate) {
		this.localContactServiceDiscountEndDate = localContactServiceDiscountEndDate;
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
