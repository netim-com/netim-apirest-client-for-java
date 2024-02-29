package com.netim.structs;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"IDSession",
"timeLogin",
"timeLastActivity",
"lang",
"sync"
})
public class StructSessionInfo implements StructInterface {
    
    @JsonProperty("IDSession")
    private String IDSession;
    @JsonProperty("timeLogin")
    private Integer timeLogin;
    @JsonProperty("timeLastActivity")
    private Integer timeLastActivity;
    @JsonProperty("preferences")
	private Map<String, Object> preferences = new HashMap<String, Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("IDSession")
    public String getIDSession() {
        return IDSession;
    }

    @JsonProperty("IDSession")
    public void setIDSession(String IDSession) {
        this.IDSession = IDSession;
    }

    @JsonProperty("timeLogin")
    public Integer getTimeLogin() {
        return timeLogin;
    }

    @JsonProperty("timeLogin")
    public void setTimeLogin(Integer timeLogin) {
        this.timeLogin = timeLogin;
    }

    @JsonProperty("timeLastActivity")
    public Integer getTimeLastActivity() {
        return timeLastActivity;
    }

    @JsonProperty("timeLastActivity")
    public void setTimeLastActivity(Integer timeLastActivity) {
        this.timeLastActivity = timeLastActivity;
    }

    @JsonProperty("preferences")
	public Object getPreferences() {
        return this.preferences;
    }

    public String getPreference(String key) {
		if (this.preferences.containsKey(key)) {
        	return this.preferences.get(key).toString();

		} else {
			return "";
		}
    }

	@JsonProperty("preferences")
    public void setPreference(String key, String value) {
		this.preferences.put(key, (Object) value);
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
