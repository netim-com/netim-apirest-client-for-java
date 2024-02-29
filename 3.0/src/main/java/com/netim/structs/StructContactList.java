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
    "idContact",
    "firstName",
    "lastName",
    "bodyForm",
    "bodyName",
    "isOwner"
})

public class StructContactList implements StructInterface {
    @JsonProperty("idContact")
    private String idContact;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("bodyForm")
    private String bodyForm;
    @JsonProperty("bodyName")
    private String bodyName;
    @JsonProperty("isOwner")
    private boolean isOwner;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idContact")
    public String getIdContact() {
        return idContact;
    }

    @JsonProperty("idContact")
    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("bodyForm")
    public String getBodyForm() {
        return bodyForm;
    }

    @JsonProperty("bodyForm")
    public void setBodyForm(String bodyForm) {
        this.bodyForm = bodyForm;
    }

    @JsonProperty("bodyName")
    public String getBodyName() {
        return bodyName;
    }

    @JsonProperty("bodyName")
    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    @JsonProperty("isOwner")
    public boolean getIsOwner() {
        return isOwner;
    }

    @JsonProperty("isOwner")
    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
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
