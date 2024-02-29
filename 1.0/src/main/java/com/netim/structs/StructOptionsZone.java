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
    "service",
    "protocol",
    "ttl",
    "priority",
    "weight",
    "port",
})

public class StructOptionsZone implements StructInterface {
    @JsonProperty("service")
    private String service;
    @JsonProperty("protocol")
    private String protocol;
    @JsonProperty("ttl")
    private Integer ttl;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("weight")
    private Integer weight;
    @JsonProperty("port")
    private Integer port;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public StructOptionsZone()
    { 
        this(null, null, null, null, null, null);
    }

    public StructOptionsZone(String service, String protocol, Integer ttl, Integer priority, Integer weight, Integer port)
    { 
        this.service = service;
        this.protocol = protocol;
        this.ttl = ttl;
        this.priority = priority;
        this.weight = weight;
        this.port = port;
    }
    
    @JsonProperty("service")
    public String getService() {
        return this.service;
    }

    @JsonProperty("service")
    public void setService(String service) {
        this.service = service;
    }

    @JsonProperty("protocol")
    public String getProtocol() {
        return this.protocol;
    }

    @JsonProperty("protocol")
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @JsonProperty("ttl")
    public Integer getTtl() {
        return this.ttl;
    }

    @JsonProperty("ttl")
    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    @JsonProperty("priority")
    public Integer getPriority() {
        return this.priority;
    }

    @JsonProperty("priority")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @JsonProperty("weight")
    public Integer getWeight() {
        return this.weight;
    }

    @JsonProperty("weight")
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @JsonProperty("port")
    public Integer getPort() {
        return this.port;
    }

    @JsonProperty("port")
    public void setPort(Integer port) {
        this.port = port;
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
