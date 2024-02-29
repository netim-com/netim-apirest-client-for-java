package com.netim.structs;

import com.netim.structs.StructInterface;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ID_OPE",
    "STATUS",
    "MESSAGE",
    "TYPE",
    "DATE"
})

public class StructOperationResponse implements StructInterface {
    @JsonProperty("ID_OPE")
    private Integer idOpe;
    @JsonProperty("STATUS")
    private String status;
    @JsonProperty("MESSAGE")
    private String message;
    @JsonProperty("TYPE")
    private String type;
    @JsonProperty("DATE")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
	@JsonProperty("DATA")
	private String data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ID_OPE")
    public Integer getIdOpe() {
        return idOpe;
    }

    @JsonProperty("ID_OPE")
    public void setIdOpe(Integer idOpe) {
        this.idOpe = idOpe;
    }

    @JsonProperty("STATUS")
    public String getStatus() {
        return status;
    }

    @JsonProperty("STATUS")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("MESSAGE")
    public String getMessage() {
        return message;
    }

    @JsonProperty("MESSAGE")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("TYPE")
    public String getType() {
        return type;
    }

    @JsonProperty("TYPE")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("DATE")
    public Date getDate() {
        return date;
    }

    @JsonProperty("DATE")
    public void setDate(Date date) {
        this.date = date;
    }

	@JsonProperty("DATA")
    public String getData() {
        return data;
    }

    @JsonProperty("DATA")
    public void setDate(String data) {
        this.data = data;
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