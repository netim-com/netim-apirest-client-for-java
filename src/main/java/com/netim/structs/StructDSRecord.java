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


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "key",
    "type",
    "digest"
})

public class StructDSRecord implements StructInterface {
    @JsonProperty("key")
    private Integer key;
    @JsonProperty("type")
    private Integer type;
    @JsonProperty("digest")
    private String digest;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public StructDSRecord(){}

    public StructDSRecord(int key, int type, String digest)
    {
        this.key = key;
        this.type = type;
        this.digest = digest;
    }

    @JsonProperty("key")
    public Integer getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(Integer key) {
        this.key = key;
    }

    @JsonProperty("type")
    public Integer getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonProperty("digest")
    public String getDigest() {
        return digest;
    }

    @JsonProperty("digest")
    public void setDigest(String digest) {
        this.digest = digest;
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