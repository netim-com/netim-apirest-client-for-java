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
import com.netim.structs.StructInterface;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "header",
    "protocol",
    "title",
    "parking",
})

public class StructOptionsFwd implements StructInterface {
    @JsonProperty("header")
    private Integer header;
    @JsonProperty("protocol")
    private String protocol;
    @JsonProperty("title")
    private String title;
    @JsonProperty("parking")
    private String parking;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public StructOptionsFwd() {
        this(null, null, null, null);
    }

    public StructOptionsFwd(Integer header, String protocol, String title, String parking) {
        this.header = header;
        this.protocol = protocol;
        if (this.protocol != null) this.protocol = this.protocol.toLowerCase();
        this.title = title;
        this.parking = parking;
    }

    @JsonProperty("header")
    public Integer getHeader() {
        return this.header;
    }

    @JsonProperty("header")
    public void setHeader(Integer header) {
        this.header = header;
    }

    @JsonProperty("protocol")
    public String getProtocol() {
        return this.protocol;
    }

    @JsonProperty("protocol")
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @JsonProperty("title")
    public String getTitle() {
        return this.title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("parking")
    public String getParking() {
        return this.parking;
    }

    @JsonProperty("parking")
    public void setParking(String parking) {
        this.parking = parking;
    }

    @JsonAnyGetter
    public Map<String,Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnyGetter
    public void setAdditionalProperties(Map<String,Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}
