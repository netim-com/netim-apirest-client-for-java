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
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "ipv4",
    "ipv6",
})

public class StructHostList implements StructInterface {
    @JsonProperty("name")
    private String name;
    @JsonProperty("ipv4")
    private List<String> ipv4;
    @JsonProperty("ipv6")
    private List<String> ipv6;
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
    public List<String> getIpv4() {
        return ipv4;
    }

    @JsonProperty("ipv4")
    public void setIpv4(List<String> ipv4) {
        this.ipv4 = ipv4;
    }

    @JsonProperty("ipv6")
    public List<String> getIpv6() {
        return ipv6;
    }

    @JsonProperty("ipv6")
    public void setIpv6(List<String> ipv6) {
        this.ipv6 = ipv6;
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