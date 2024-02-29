package com.netim.structs;

import com.netim.structs.StructInterface;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"mailBox",
"recipients",
"install",
})

public class StructQueryMailFwdList implements StructInterface {
    @JsonProperty("mailBox")
    private String mailBox;
    @JsonProperty("recipients")
    private String recipients;
    @JsonProperty("install")
    private int install;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mailBox")
    public String getMailBox() {
        return this.mailBox;
    }

    @JsonProperty("mailBox")
    public void setMailBox(String mailBox) {
        this.mailBox = mailBox;
    }

    @JsonProperty("recipients")
    public String getRecipients() {
        return this.recipients;
    }

    @JsonProperty("recipients")
    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    @JsonProperty("install")
    public int getInstall() {
        return this.install;
    }

    @JsonProperty("install")
    public void setInstall(int install) {
        this.install = install;
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
