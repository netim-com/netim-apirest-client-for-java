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
    "name",
    "ipv4",
    "ipv6",
    "isLinked"
})

public class StructHostInfo implements StructInterface {

    @JsonProperty("name")
    private String name;
    @JsonProperty("ipv4")
    private String[] ipv4;
    @JsonProperty("ipv6")
    private String[] ipv6;
    @JsonProperty("isLinked")
    private Boolean isLinked;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("ipv4")
    public String[] setIpv4() {
        return ipv4;
    }

    @JsonProperty("ipv4")
    public void setIpv4(String[] ipv4) {
        this.ipv4 = ipv4;
    }

	@JsonProperty("ipv6")
    public String[] setIpv6() {
        return ipv6;
    }

    @JsonProperty("ipv6")
    public void setIpv6(String[] ipv6) {
        this.ipv6 = ipv6;
    }

	@JsonProperty("isLinked")
	public Boolean getIsLinked() {
		return isLinked;
	}

	@JsonProperty("isLinked")
	public void setIsLinked(Boolean isLinked) {
		this.isLinked = isLinked;
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