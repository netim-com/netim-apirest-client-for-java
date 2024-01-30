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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"idContact",
    "firstName",
    "lastName",
    "bodyForm",
    "bodyName",
    "address1",
    "address2",
    "zipCode",
    "area",
    "city",
    "country",
    "phone",
    "fax",
    "email",
    "language",
    "isOwner",
    "tmName",
    "tmNumber",
    "tmType",
    "companyNumber",
    "vatNumber",
    "birthDate",
    "birthZipCode",
    "birthCity",
    "birthCountry",
    "idNumber",
    "additional"
})

public class StructContact implements StructInterface {

	@JsonProperty("idContact ")
	private String idContact;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("bodyForm")
    private String bodyForm;
    @JsonProperty("bodyName")
    private String bodyName;
    @JsonProperty("address1")
    private String address1;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("zipCode")
    private String zipCode;
    @JsonProperty("area")
    private String area;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("fax")
    private String fax;
    @JsonProperty("email")
    private String email;
    @JsonProperty("language")
    private String language;
    @JsonProperty("isOwner")
    private int isOwner;
    @JsonProperty("tmName")
    private String tmName;
    @JsonProperty("tmNumber")
    private String tmNumber;
    @JsonProperty("tmType")
    private String tmType;
    @JsonProperty("tmDate")
    private String tmDate;
    @JsonProperty("companyNumber")
    private String companyNumber;
    @JsonProperty("vatNumber")
    private String vatNumber;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("birthZipCode")
    private String birthZipCode;
    @JsonProperty("birthCity")
    private String birthCity;
    @JsonProperty("birthCountry")
    private String birthCountry;
    @JsonProperty("idNumber")
    private String idNumber;
    @JsonProperty("additional")
    private Map<String, Object> additional;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /*public StructContact(String firstName, String lastName, String bodyForm, String bodyName, String address1, String address2,
    String zipCode, String area, String city, String country, String phone, String fax, String email, String language, int isOwner,
    String tmName = "", String tmNumber = "", String tmType = "", String tmDate = "", String companyNumber = "", String vatNumber = "",
    String birthDate = "", String birthZipCode = "", String birthCity = "", String birthCountry = "", String idNumber = "", Map<String,Object> additional = array())
    {
        this.firstName = firstName;
    }*/


    public StructContact(String firstName, String lastName, String bodyForm, String bodyName, String address1, String address2, String zipCode, String area, String city, String country, String phone, String fax, String email, String language, int isOwner, String tmName, String tmNumber, String tmType, String tmDate, String companyNumber, String vatNumber, String birthDate, String birthZipCode, String birthCity, String birthCountry, String idNumber, Map<String,Object> additional) {
		this.firstName = firstName;
        this.lastName = lastName;
        this.bodyForm = bodyForm;
        this.bodyName = bodyName;
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
        this.area = area;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.language = language;
        this.isOwner = isOwner;
        this.tmName = tmName;
        this.tmNumber = tmNumber;
        this.tmType = tmType;
        this.tmDate = tmDate;
        this.companyNumber = companyNumber;
        this.vatNumber = vatNumber;
        this.birthDate = birthDate;
        this.birthZipCode = birthZipCode;
        this.birthCity = birthCity;
        this.birthCountry = birthCountry;
        this.idNumber = idNumber;
        this.additional = additional;
    }

    public StructContact(String firstName, String lastName, String bodyForm, String bodyName, String address1, String address2, String zipCode, String area, String city, String country, String phone, String fax, String email, String language, int isOwner)
    {
        this(firstName, lastName, bodyForm, bodyName, address1, address2, zipCode, area, city, country, phone, fax, email, language, isOwner, "", "", "", "", "", "", "", "", "", "", "", new HashMap<String, Object>());
    }

    public StructContact()
    {
        this("", "", "", "", "", "", "", "", "", "", "", "", "", "", 0, "", "", "", "", "", "", "", "", "", "", "", new HashMap<String, Object>());
    }


	@JsonProperty("idContact")
	public String getIdContact() {
		return this.idContact;
	}
	@JsonProperty("idContact")
	public void setIdContact(String idContact) {
		this.idContact = idContact;
	}
    @JsonProperty("firstName")
    public String getFirstName() {
        return this.firstName;
    }
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @JsonProperty("lastName")
    public String getLastName() {
        return this.lastName;
    }
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @JsonProperty("bodyForm")
    public String getBodyForm() {
        return this.bodyForm;
    }
    @JsonProperty("bodyForm")
    public void setBodyForm(String bodyForm) {
        this.bodyForm = bodyForm;
    }
    @JsonProperty("bodyName")
    public String getBodyName() {
        return this.bodyName;
    }
    @JsonProperty("bodyName")
    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }
    @JsonProperty("address1")
    public String getAddress1() {
        return this.address1;
    }
    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    @JsonProperty("address2")
    public String getAddress2() {
        return this.address2;
    }
    @JsonProperty("address2")
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    @JsonProperty("zipCode")
    public String getZipCode() {
        return this.zipCode;
    }
    @JsonProperty("zipCode")
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    @JsonProperty("area")
    public String getArea() {
        return this.area;
    }
    @JsonProperty("area")
    public void setArea(String area) {
        this.area = area;
    }
    @JsonProperty("city")
    public String getCity() {
        return this.city;
    }
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }
    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }
    @JsonProperty("phone")
    public String getPhone() {
        return this.phone;
    }
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @JsonProperty("fax")
    public String getFax() {
        return this.fax;
    }
    @JsonProperty("fax")
    public void setFax(String fax) {
        this.fax = fax;
    }
    @JsonProperty("email")
    public String getEmail() {
        return this.email;
    }
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }
    @JsonProperty("language")
    public String getLanguage() {
        return this.language;
    }
    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }
    @JsonProperty("isOwner")
    public int getIsOwner() {
        return this.isOwner;
    }
    @JsonProperty("isOwner")
    public void setIsOwner(int isOwner) {
        this.isOwner = isOwner;
    }
    @JsonProperty("tmName")
    public String getTmName() {
        return this.tmName;
    }
    @JsonProperty("tmName")
    public void setTmName(String tmName) {
        this.tmName = tmName;
    }
    @JsonProperty("tmNumber")
    public String getTmNumber() {
        return this.tmNumber;
    }
    @JsonProperty("tmNumber")
    public void setTmNumber(String tmNumber) {
        this.tmNumber = tmNumber;
    }
    @JsonProperty("tmType")
    public String getTmType() {
        return this.tmType;
    }
    @JsonProperty("tmType")
    public void setTmType(String tmType) {
        this.tmType = tmType;
    }
    @JsonProperty("tmDate")
    public String getTmDate() {
        return this.tmDate;
    }
    @JsonProperty("tmDate")
    public void setTmDate(String tmDate) {
        this.tmDate = tmDate;
    }
    @JsonProperty("companyNumber")
    public String getCompanyNumber() {
        return this.companyNumber;
    }
    @JsonProperty("companyNumber")
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }
    @JsonProperty("vatNumber")
    public String getVatNumber() {
        return this.vatNumber;
    }
    @JsonProperty("vatNumber")
    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
    @JsonProperty("birthDate")
    public String getBirthDate() {
        return this.birthDate;
    }
    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    @JsonProperty("birthZipCode")
    public String getBirthZipCode() {
        return this.birthZipCode;
    }
    @JsonProperty("birthZipCode")
    public void setBirthZipCode(String birthZipCode) {
        this.birthZipCode = birthZipCode;
    }
    @JsonProperty("birthCity")
    public String getBirthCity() {
        return this.birthCity;
    }
    @JsonProperty("birthCity")
    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }
    @JsonProperty("birthCountry")
    public String getBirthCountry() {
        return this.birthCountry;
    }
    @JsonProperty("birthCountry")
    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }
    @JsonProperty("idNumber")
    public String getIdNumber() {
        return this.idNumber;
    }
    @JsonProperty("idNumber")
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    @JsonProperty("additional")
    public Map<String,Object> getAdditional() {
        return this.additional;
    }
    @JsonProperty("additional")
    public void setAdditional(Map<String,Object> additional) {
        this.additional = additional;
    }

    @JsonIgnore
    public void setAdditional(List<Object> additional) {
        this.additional = new HashMap<String,Object>();
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
