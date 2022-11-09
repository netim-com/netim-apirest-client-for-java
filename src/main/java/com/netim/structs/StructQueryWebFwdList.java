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
    "FQDN",
    "type",
    "target",
    "install",
    "options",
})   

public class StructQueryWebFwdList implements StructInterface {
    @JsonProperty("FQDN")
    private String fqdn;
    @JsonProperty("type")
    private String type;
    @JsonProperty("target")
    private String target;
    @JsonProperty("install")
    private Integer install;
    @JsonProperty("options")
    private StructOptionsFwd options;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("FQDN")
    public String getFqdn() {
        return this.fqdn;
    }

    @JsonProperty("FQDN")
    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    @JsonProperty("type")
    public String getType() {
        return this.type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("target")
    public String getTarget() {
        return this.target;
    }

    @JsonProperty("target")
    public void setTarget(String target) {
        this.target = target;
    }

    @JsonProperty("install")
    public Integer getInstall() {
        return this.install;
    }

    @JsonProperty("install")
    public void setInstall(Integer install) {
        this.install = install;
    }

    @JsonProperty("options")
    public StructOptionsFwd getOptions() {
        return this.options;
    }

    @JsonProperty("options")
    public void setOptions(StructOptionsFwd options) {
        this.options = options;
    }

    @JsonAnySetter
    public Map<String,Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(Map<String,Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}
