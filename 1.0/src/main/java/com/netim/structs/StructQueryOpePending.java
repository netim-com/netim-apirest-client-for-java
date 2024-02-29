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
"id_ope",
"code_ope",
"date_ope",
"data_ope"
})

public class StructQueryOpePending implements StructInterface {
    @JsonProperty("id_ope")
    private String idOpe;
    @JsonProperty("code_ope")
    private String codeOpe;
    @JsonProperty("date_ope")
    private Date dateOpe;
    @JsonProperty("data_ope")
    private String dataOpe;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id_ope")
    public String getIdOpe() {
        return idOpe;
    }

    @JsonProperty("id_ope")
    public void setIdOpe(String idOpe) {
        this.idOpe = idOpe;
    }

    @JsonProperty("code_ope")
    public String getCodeOpe() {
        return codeOpe;
    }

    @JsonProperty("code_ope")
    public void setCodeOpe(String codeOpe) {
        this.codeOpe = codeOpe;
    }

    @JsonProperty("date_ope")
    public Date getDateOpe() {
        return dateOpe;
    }

    @JsonProperty("date_ope")
    public void setDateOpe(Date dateOpe) {
        this.dateOpe = dateOpe;
    }

    @JsonProperty("data_ope")
    public String getDataOpe() {
        return dataOpe;
    }

    @JsonProperty("data_ope")
    public void setDataOpe(String dataOpe) {
        this.dataOpe = dataOpe;
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
