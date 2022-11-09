package com.netim;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.netim.structs.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.AutoCloseable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class APIRest implements AutoCloseable
{
    private boolean _connected;
    public String _sessionID;

    private String _userID;
    private String _secret;
    private String _apiURL;

    private Object _lastRequestParams;
    private String _lastRequestRessource;
    private String _lastHttpVerb;
    private int _lastHttpStatus;
    private String _lastResponse;
    private String _lastError;
    private String _defaultLanguage;


    /**
     * Constructor for class APIRest
     *
     * @param userID the ID the client uses to connect to his NETIM account
     * @param secret the SECRET the client uses to connect to his NETIM account
     *	 
     * @throws Exception if userID, secret, url are empty or missing.
     * 
     */
    public APIRest(String userID, String secret) throws Exception
    {
        InputStream in = this.getClass().getResourceAsStream("/conf.xml");
        //File f = new File("./conf.xml");
        ObjectMapper objectMapper = new XmlMapper();
        TypeReference<HashMap<String, String>> typeRef 
                = new TypeReference<HashMap<String, String>>() {};
        //FileInputStream fs = new FileInputStream(f);
        HashMap<String, String> conf = objectMapper.readValue(in, typeRef);

        in.close();

        this._userID = userID;
        this._secret = secret;

        this.setConf(conf);
    }

    /**
     * Constructor for class APIRest
     *	 
     * @throws Exception if userID, secret, url are empty or missing in conf.
     * 
     */
    public APIRest() throws Exception
    {
        File f = new File("./conf.xml");
        ObjectMapper objectMapper = new XmlMapper();
        TypeReference<HashMap<String,String>> typeRef 
                = new TypeReference<HashMap<String,String>>() {};
        FileInputStream fs = new FileInputStream(f);
        HashMap<String,String> conf = objectMapper.readValue(fs, typeRef);

        fs.close();

        this._connected = false;
        this._sessionID = null;

        if(!conf.containsKey("login") || conf.get("login").isEmpty())
            throw new NetimAPIException("Missing or empty <login> in conf file.");
        if(!conf.containsKey("secret") || conf.get("secret").isEmpty())
            throw new NetimAPIException("Missing or empty <secret> in conf file.");
        
        this._userID = conf.get("login");
        this._secret = conf.get("secret");
        
        this.setConf(conf);
    }

    private void setConf(HashMap<String,String> conf) throws Exception
    {
        if(!conf.containsKey("url") || conf.get("url").isEmpty())
            throw new NetimAPIException("Missing or empty <url> in conf file.");
        this._apiURL = conf.get("url");
            
        
        if(conf.containsKey("language") && (conf.get("language").equals("EN") || conf.get("language").equals("FR")))
            this._defaultLanguage = conf.get("language");
        else
            this._defaultLanguage = "EN";
    }

    @Override
    public void close() throws NetimAPIException {
        if(this._connected)
            this.sessionClose();

    }

    /**
     * PRIVATE FUNCTIONS
     */
    private String _call(String ressource, HttpVerb httpVerb, Object params) throws NetimAPIException
    {
        this._lastRequestRessource = ressource;
        this._lastRequestParams = params;
        this._lastHttpVerb = httpVerb.toString();
        this._lastResponse = "";
        this._lastError = "";

        try {
            //login		
            if (!this._connected)
            {
                if(this.isSessionClose(ressource, httpVerb)) //If already disconnected, just return.
                    return null;
                else if (!this.isSessionOpen(ressource, httpVerb)) // If not connected and running sessionOpen, don't fall in an endless loop.
                    this.sessionOpen();
            }
            else if (this._connected && this.isSessionOpen(ressource, httpVerb))
                return null;
            
            //Call the REST ressource
            ObjectMapper mapper = new ObjectMapper();

            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, params);
            HttpResponse response;
            if(this.isSessionOpen(ressource, httpVerb))
            {
                String auth = this._userID + ":" + this._secret;
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this._apiURL + "session/"))
                    .method("POST", HttpRequest.BodyPublishers.ofString(""))
                    .header("Content-Type", "application/json")
                    .header("Accept-Language", this._defaultLanguage)
                    .header("Authorization", "Basic " + new String(encodedAuth))
                    .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            else
            {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this._apiURL + ressource))
                    .method(httpVerb.toString(), HttpRequest.BodyPublishers.ofString(sw.toString()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this._sessionID)
                    .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());                
            }
            int status_code = response.statusCode();

            this._lastHttpStatus = status_code;

            if(this.isSessionClose(ressource, httpVerb))
            {
                if(status_code == 200 || status_code == 401) 
                {
                    this._sessionID = null;
                    this._connected = false;
                    
                }
                else 
                {
                    String input = response.body().toString();

                    TypeReference<HashMap<String,String>> typeRef 
                        = new TypeReference<HashMap<String,String>>() {};
    
                    HashMap<String,String> res = mapper.readValue(input, typeRef);
    
                    this._lastError = res.get("message");
                    throw new NetimAPIException(res.get("message"));
                }
            }
            else if (this.isSessionOpen(ressource, httpVerb))
            {
                String input = response.body().toString();

                TypeReference<HashMap<String,String>> typeRef 
                    = new TypeReference<HashMap<String,String>>() {};

                HashMap<String,String> res = mapper.readValue(input, typeRef);
                if(status_code == 200)
                {
                    this._sessionID = res.get("access_token");
                    this._connected = true;
                }
                else 
                {
                    this._lastError = res.get("message");
                    throw new NetimAPIException(res.get("message"));
                }
            }

            if(status_code < 200 || status_code > 299) // Code doesn't start with "2xx"
            {
                if (status_code == 401)
                {
                    this._connected = false;
                }
                String input = response.body().toString();

                TypeReference<HashMap<String,String>> typeRef 
                    = new TypeReference<HashMap<String,String>>() {};

                HashMap<String,String> res = mapper.readValue(input, typeRef);
                
                this._lastError = res.get("message");
                throw new NetimAPIException(res.get("message"));
            }
            else
            {
                String input = response.body().toString();
                this._lastResponse = input;

                return input;
            }

        }
        catch (Exception e)
        {
            this._lastError = e.getMessage();
            throw new NetimAPIException(e);
        }
    }

    private <T> T adapt(String content, Class<T> type) throws NetimAPIException
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, type);
        }
        catch (Exception e)
        {
            this._lastError = e.getMessage();
            throw new NetimAPIException(e);
        }
    }

    /**
     * Launches a function of the API, abstracting the connect/disconnect part to one place
     *
     * 
     * <pre>{@code //Example 1: API command returning a StructOperationResponse
       return this.call("/contacts/"+ idContactToDelete, new HashMap(), HttpVerb.DELETE);}</pre>
     * 
     * <pre>{@code //Example 2: API command that takes many args
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("host", host);
        params.put("ipv4", ipv4);
        params.put("ipv6", ipv6);

        return this.call("/hosts", params, HttpVerb.POST);}</pre>
     *
     * @param ressource name of a ressource in the API
     * @param httpVerb the http verb for the request.
     * @param params the parameters of ressource in a Map or StructInterface object.
     * @param type the type or return wanted (String, StructSessionInfo, StructContact ...)
     *
     * @throws NetimAPIException
     *
     * @return The json returned by the call of ressource with parameters params and http verb httpVerb
     * @throws NetimAPIException
     * 
     * @see HttpVerb
     * @see https://support.netim.com/en/wiki/Category:Functions
     *
     */

     /** typed returns */
    public <T> T call(String ressource, HttpVerb httpVerb, Map params, Class<T> type) throws NetimAPIException
    {
        return this.adapt(this._call(ressource, httpVerb, params), type);
    }

    public <T> T call(String ressource, HttpVerb httpVerb, Class<T> type) throws NetimAPIException
    {
        return this.call(ressource, httpVerb, new HashMap<>(), type);
    }

    public <T> T call(String ressource, HttpVerb httpVerb, StructInterface params, Class<T> type) throws NetimAPIException
    {
        return this.adapt(this._call(ressource, httpVerb, params), type);
    }

    /** raw output */
    public String call(String ressource, HttpVerb httpVerb, Map params) throws NetimAPIException
    {
        return this._call(ressource, httpVerb, params);
    }

    public String call(String ressource, HttpVerb httpVerb) throws NetimAPIException
    {
        return this.call(ressource, httpVerb, new HashMap<>());
    }

    public String call(String ressource, HttpVerb httpVerb, StructInterface params) throws NetimAPIException
    {
        return this._call(ressource, httpVerb, params);
    }

    private boolean isSessionOpen(String ressource, HttpVerb httpVerb)
    {
        return ((ressource.equals("/session") || ressource.equals("/session/") || ressource.equals("session/") || ressource.equals("session")) && httpVerb == HttpVerb.POST);
    }

    private boolean isSessionClose(String ressource, HttpVerb httpVerb)
    {
        return ((ressource.equals("/session") || ressource.equals("/session/") || ressource.equals("session/") || ressource.equals("session")) && httpVerb == HttpVerb.DELETE);
    }

    /**
     * GETTERS
     */

    public Object getLastRequestParams()
    {
        return this._lastRequestParams;
    }

    public String getLastRequestRessource()
    {
        return this._lastRequestRessource;
    }

    public String getLastHttpVerb()
    {
        return this._lastHttpVerb;
    }

    public int getLastHttpStatus()
    {
        return this._lastHttpStatus;
    }

    public String getLastResponse()
    {
        return this._lastResponse;
    }

    public String getLastError()
    {
        return this._lastError;
    }

    /** 
    *   API FUNCTIONS
    */

    public void sessionOpen() throws NetimAPIException
    {
        //If lang is not a valid parameter, silently decide to stick with EN
        this.call("session/", HttpVerb.POST, Void.class);
    }

    public void sessionClose() throws NetimAPIException
    {
        this.call("session/", HttpVerb.DELETE, Void.class);
    }

    /**
     * Return the information of the current session. 
     * 
     * @return A structure StructSessionInfo
     * @throws NetimAPIException 
     * @see sessionInfo API https://support.netim.com/en/wiki/SessionInfo
     * @see StructSessionInfo
     */
    public StructSessionInfo sessionInfo() throws NetimAPIException
    {
        return call("session/", HttpVerb.GET, StructSessionInfo.class);
    }

    /**
     * Updates the settings of the current session. 
     * 
     * @param type Setting to be modified : lang
     *                                      sync
     * @param value New value of the Setting : lang = EN / FR
     *                                         sync = 0 (for asynchronous) / 1 (for synchronous) 
     * @throws NetimAPIException
     */
    public void sessionSetPreference(String type, String value) throws NetimAPIException
    {        
        HashMap<String, String> params = new HashMap<String,String>();
        params.put("type", type);
        params.put("value", value);
        call("session/", HttpVerb.PATCH, params, Void.class);
        
    }

    /**
     * Returns all active sessions linked to the reseller account. 
     * 
     * @return a list of StructSessionInfo
     * @throws NetimAPIException
     * @see queryAllSessions API https://support.netim.com/en/wiki/QueryAllSessions
     */
    public StructSessionInfo[] queryAllSessions() throws NetimAPIException
    {
        return call("sessions/", HttpVerb.GET, StructSessionInfo[].class);
    }

    /**
     * Returns a welcome message
     *
     * Example
    
        try
        {
            res = api.hello();
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
    *
    * @return string a welcome message
    * 
    * @throws NetimAPIException
    *
    * @see hello API http://support.netim.com/en/wiki/Hello
    */
    public String hello() throws NetimAPIException
    {
        return call("hello/", HttpVerb.GET, String.class);
    }

    /**
     * Returns the list of parameters reseller account
     *
     *
     * @return A structure of StructQueryResellerAccount containing the information
     * 
     * @throws NetimAPIException
     *
     * @see queryResellerAccount API https://support.netim.com/en/wiki/QueryResellerAccount
     */
    public StructQueryResellerAccount queryResellerAccount() throws NetimAPIException
    {
        return call("account/", HttpVerb.GET, StructQueryResellerAccount.class);
    }

    // -------------------------------------------------
    //CONTACT
    // -------------------------------------------------
        
    /**
     * Creates a contact
     *
     * Example1: non-owner
    
        //we create a contact as a non-owner 
        id = null;
        try
        {
            contact = new StructContact("barack", "obama", "IND", "", "", "1600 Pennsylvania Ave NW", "", "20500", "DC", "US", "Washington", "2024561111", "", "barack.obama@gov.us", "EN", 0);
            id = client.contactCreate(contact);
        }
        catch (NetimAPIexception exception)
        {
            //do something about the error
        }

        //continue processing
    
        * Example2: owner
        
        id = null;
        try
        {
            contact = new StructContact("bill", "gates", "IND", "", "", "1 hollywood bvd", "", "18022", "LA", "US", "Los Angeles", "2024531111", "", "bill.gates@microsoft.com", "EN", 1)
            id = client.contactCreate(contact);
        }
        catch (NetimAPIexception exception)
        {
            //do something about the error
        }

        //continue processing

     * @param contact the contact to create
     *
     * @throws NetimAPIException
     *
     * @return string the ID of the contact
     *
     * @see StructContact http://support.netim.com/en/wiki/StructContact
    */
    public String contactCreate(StructContact contact) throws NetimAPIException {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("contact", contact);
        return call("contact/", HttpVerb.POST, params, String.class);
    }

    /**
     * Returns all informations about a contact object
     *
     * Example:
    
        idContact = "BJ007";
        res = null;
        try 
        {
            res = client.contactInfo(idContact);
        }
        catch (NetimAPIexception exception)
        {
            //do something about the error
        }
        contactInfo = res;
        //continue processing
    
     * @param idContact ID of the contact to be queried
     *
     * @throws NetimAPIException
     *
     * @return StructContactReturn information on the contact
     *
     * @see contactInfo API http://support.netim.com/en/wiki/ContactInfo
     * @see StructContactReturn API http://support.netim.com/en/wiki/StructContactReturn
     */
    public StructContact contactInfo(String idContact) throws NetimAPIException {
        return call("contact/"+idContact, HttpVerb.GET, StructContact.class);
    }

    /**
     * Edit contact details
     *
     * Example: 
    
        //we update a contact as a non-owner 
        res = null;
        try
        {
            StructContact contact = new StructContact("donald", "trump", "IND", "", "1600 Pennsylvania Ave NW", "", "20500", "DC", "US", "Washington", "2024561111", "", "donald.trump@gov.us", "EN", 0);
            res = client.contactUpdate(idContact, contact);   
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing

     * @param idContact the ID of the contact to be updated
     * @param datas the contact object containing the new values
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see contactUpdate API http://support.netim.com/en/wiki/ContactUpdate
     */
    public StructOperationResponse contactUpdate(String idContact, StructContact contact) throws NetimAPIException {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("contact", contact);
        return call("contact/" + idContact, HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Deletes a contact object 
     *
     * Example1:
    
        contactID = "BJ007";
        res = null;
        try
        {
            res = client.contactDelete(contactID);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param idContact ID of the contact to be deleted
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see contactDelete API http://support.netim.com/en/wiki/ContactDelete
     * @see StructOperationResponse API http://support.netim.com/en/wiki/StructOperationResponse
     */
    public StructOperationResponse contactDelete(String idContact) throws NetimAPIException {
        return call("contact/" + idContact, HttpVerb.DELETE, StructOperationResponse.class);
    }

    /**
     * Query informations about the state of an operation
     *
     * Example
        
        domain = "myDomain.com";
        res = null;
        try
        {
            res = client.domainAuthID(domain, 0);
            try = 0;
            while(try < 10 && res.getStatus=="Pending")
            {	
                // The operation is pending, we will wait at most 10sec to see if the operation status change
                // and check every second if it changes
                TimeUnit.SECONDS.sleep(1); 
                try++;
                res = client.queryOpe()
            }
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param idOpe The id of the operation requested
     * 
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see queryOpe API http://support.netim.com/en/wiki/QueryOpe
     */
    public StructOperationResponse queryOpe(String idOpe) throws NetimAPIException {
        return call("operation/" + idOpe, HttpVerb.GET, StructOperationResponse.class);
    }

    /**
     * Cancel a pending operation
     * @warning Depending on the current status of the operation, the cancellation might not be possible
     * 
     * @param idOpe Tracking ID of the operation
     * 
     * @throws NetimAPIException
     *
     * @see cancelOpe http://support.netim.com/en/wiki/CancelOpe
     */
    public void cancelOpe(String idOpe) throws NetimAPIException {
        call("operation/" + idOpe + "/cancel/", HttpVerb.PATCH, Void.class);
    }

    public Map<String,Integer> queryOpeList (String tld) throws NetimAPIException {
        try {
            TypeReference<HashMap<String, Integer>> typeRef 
                = new TypeReference<HashMap<String, Integer>>() {};
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(call("tld/" + tld + "/operations/", HttpVerb.GET), typeRef);
        }
        catch (Exception e)
        {
            this._lastError = e.getMessage();
            throw new NetimAPIException(e);
        }
    }

    /**
     * Returns the list of pending operations processing 
     * 
     * @throws NetimAPIException
     * 
     * @return StructQueryOpePending[]  the list of pending operations processing 
     * 
     * @see queryOpePending API https://support.netim.com/en/wiki/QueryOpePending
     * 
     */
    public StructQueryOpePending[] queryOpePending() throws NetimAPIException {
        return call("operations/pending/", HttpVerb.GET, StructQueryOpePending[].class);
    }

    /**
     * Returns all contacts linked to the reseller account. 
     * 
     * The  symbol '*' equals  symbol '%' in SQL for the filter
     * 
     * @param filter The filter applies on the "field" 
     * @param field idContact / firstName / lastName / bodyForm / isOwner 
     * 
     * @throws NetimAPIException
     * 
     * @return StructContactList[] An array of StructContactList
     * 
     * @see queryContactList API https://support.netim.com/en/wiki/QueryContactList
     * 
     */
    public StructContactList[] queryContactList(String filter, String field) throws NetimAPIException {
        if(filter.isEmpty() && field.isEmpty())
            return call("contacts/", HttpVerb.GET, StructContactList[].class);
        else
            return call("contacts/" + field + "/" + filter + "/", HttpVerb.GET, StructContactList[].class);
    }

    public StructContactList[] queryContactList() throws NetimAPIException {
        return queryContactList("", "");
    }

    /**
     * Creates a new host at the registry
     *
     * @param host hostname
     * @param ipv4 Must contain ipv4 adresses
     * @param ipv6 Must contain ipv6 adresses
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see hostCreate API http://support.netim.com/en/wiki/HostCreate
     */
    public StructOperationResponse hostCreate(String host, List<String> ipv4, List<String> ipv6) throws NetimAPIException {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("host", host);
        params.put("ipv4", ipv4);
        params.put("ipv6", ipv6);

        return call("host/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Deletes an Host at the registry 
     *
     * @param host hostname to be deleted
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see hostDelete API http://support.netim.com/en/wiki/HostDelete
     */
    public StructOperationResponse hostDelete(String host)  throws NetimAPIException {
        return call("host/" + host, HttpVerb.DELETE, StructOperationResponse.class);
    }

    /**
     * Updates a host at the registry 
     *
     * @param host string hostname
     * @param ipv4 Must contain ipv4 adresses as strings
     * @param ipv6 Must contain ipv6 adresses as strings
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see hostUpdate API http://support.netim.com/en/wiki/HostUpdate
     */
    public StructOperationResponse hostUpdate(String host, List<String> ipv4, List<String> ipv6) throws NetimAPIException {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("ipv4", ipv4);
        params.put("ipv6", ipv6);

        return call("host/" + host, HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * @param filter The filter applies onto the host name 
     * 
     * @throws NetimAPIException
     *
     * @return array An array of StructHostList
     *
     * @see queryHostList API http://support.netim.com/en/wiki/QueryHostList
     */
    public StructHostList[] queryHostList(String filter) throws NetimAPIException {
        return call("hosts/" + filter, HttpVerb.GET, StructHostList[].class);
    }

    /**
     * Checks if domain names are available for registration   
     *
     *  
     * @param domain Domain names to be checked 
     * You can provide several domain names separated with semicolons. 
     * Caution : 
     *	- you can't mix different extensions during the same call 
     *	- all the extensions don't accept a multiple checkDomain. See HasMultipleCheck in Category:Tld
     *
     * @throws NetimAPIException
     *
     * @return An array of StructDomainCheckResponse
     * 
     * @see StructDomainCheckResponse http://support.netim.com/en/wiki/StructDomainCheckResponse
     * @see DomainCheck API http://support.netim.com/en/wiki/DomainCheck
     */
    public StructDomainCheckResponse[] domainCheck(String domain) throws NetimAPIException {
        return call("domain/" + domain + "/check/", HttpVerb.GET, StructDomainCheckResponse[].class);
    }

    /**
     * Requests a new domain registration 
     *
     * Example:
    
        String domain = "myDomain.com";
        String idOwner = "BJ008";
        String idAdmin = "BJ007";
        String idTech = "BJ007";
        String idBilling = "BJ007";
        String ns1 = "ns1.netim.com";
        String ns2 = "ns2.netim.com";
        String ns3 = "ns3.netim.com"; 
        String ns4 = "ns4.netim.com";
        String ns5 = "ns5.netim.com";
        int duration = 1;
        StructOperationResponse res = null;
        try
        {
            res = client.domainCreate(domain, idOwner, idAdmin, idTech, idBilling, ns1, ns2, ns3, ns4, ns5, duration);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing

        * @param domain the name of the domain to create
        * @param idOwner the id of the owner for the new domain
        * @param idAdmin the id of the admin for the new domain
        * @param idTech the id of the tech for the new domain
        * @param idBilling the id of the billing for the new domain
        *                          To get an ID, you can call contactCreate() with the appropriate information
        * @param ns1 the name of the first dns
        * @param ns2 the name of the second dns
        * @param ns3 the name of the third dns
        * @param ns4 the name of the fourth dns
        * @param ns5 the name of the fifth dns
        * @param duration how long the domain will be created
        * @param templateDNS OPTIONAL number of the template DNS created on netim.com/direct
        *
        * @throws NetimAPIException
        *
        * @return StructOperationResponse giving information on the status of the operation
        *
        * @see domainCreate API http://support.netim.com/en/wiki/DomainCreate 
        */
    public StructOperationResponse domainCreate(String domain, String idOwner, String idAdmin, String idTech, String idBilling, String ns1, String ns2, String ns3, String ns4, String ns5, int duration, Integer templateDNS) throws NetimAPIException {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("idOwner", idOwner);
        params.put("idAdmin", idAdmin);
        params.put("idTech", idTech);
        params.put("idBilling", idBilling);

        params.put("ns1", ns1);
        params.put("ns2", ns2);
        params.put("ns3", ns3);
        params.put("ns4", ns4);
        params.put("ns5", ns5);

        params.put("duration", String.valueOf(duration));

        if (templateDNS != null)
            params.put("templateDNS", String.valueOf(templateDNS));

        return this.call("domain/" + domain + "/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    public StructOperationResponse domainCreate(String domain, String idOwner, String idAdmin, String idTech, String idBilling, String ns1, String ns2, String ns3, String ns4, String ns5, int duration) throws NetimAPIException {
        return this.domainCreate(domain, idOwner, idAdmin, idTech, idBilling, ns1, ns2, ns3, ns4, ns5, duration, null);
    }

    public StructDomainInfo domainInfo(String domain) throws NetimAPIException {
        return this.call("domain/" + domain + "/info/", HttpVerb.GET, StructDomainInfo.class);
    }

    /**
     * Requests a new domain registration during a launch phase
     *
     * Example:
    
        String domain = "myDomain.com";
        String idOwner = "BJ008";
        String idAdmin = "BJ007";
        String idTech = "BJ007";
        String idBilling = "BJ007";
        String ns1 = "ns1.netim.com";
        String ns2 = "ns2.netim.com";
        String ns3 = "ns3.netim.com";
        String ns4 = "ns4.netim.com";
        String ns5 = "ns5.netim.com";
        int duration = 1;
        String phase = "GA";
        StructOperationResponse res = null;
        try
        {
            res = client.domainCreateLP(domain, idOwner, idAdmin, idTech, idBilling, ns1, ns2, ns3, ns4, ns5, duration, phase);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing

        * @param domain the name of the domain to create
        * @param idOwner the id of the owner for the new domain
        * @param idAdmin the id of the admin for the new domain
        * @param idTech the id of the tech for the new domain
        * @param idBilling the id of the billing for the new domain
        *                          To get an ID, you can call contactCreate() with the appropriate information
        * @param ns1 the name of the first dns
        * @param ns2 the name of the second dns
        * @param ns3 the name of the third dns
        * @param ns4 the name of the fourth dns
        * @param ns5 the name of the fifth dns
        * @param duration how long the domain will be created
        * @param phase the id of the launch phase
        *
        * @throws NetimAPIException
        *
        * @return StructOperationResponse giving information on the status of the operation
        *
        * @see domainCreateLP API http://support.netim.com/en/wiki/DomainCreateLP 
        */
    public StructOperationResponse domainCreateLP(String domain, String idOwner, String idAdmin, String idTech, String idBilling, String ns1, String ns2, String ns3, String ns4, String ns5, int duration, String phase) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("idOwner", idOwner);
        params.put("idAdmin", idAdmin);
        params.put("idTech", idTech);
        params.put("idBilling", idBilling);

        params.put("ns1", ns1);
        params.put("ns2", ns2);
        params.put("ns3", ns3);
        params.put("ns4", ns4);
        params.put("ns5", ns5);

        params.put("duration", String.valueOf(duration));

        params.put("launchPhase", phase);

        return this.call("/domain/" + domain + "/lp/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Deletes immediately a domain name 
     * 
     * Example:
    
        String domain = "myDomain.com";
        StructOperationResponse res = null;
        try
        {
            res = client.domainDelete(domain);
            //equivalent to res = client.domainDelete(domain, "NOW");
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing

        * @param domain the name of the domain to delete
        * @param typeDeletion OPTIONAL if the deletion is to be done now or not. Only supported value as of 2.0 is "NOW".
        *
        * @throws NetimAPIException
        *
        * @return StructOperationResponse giving information on the status of the operation
        *
        * @see domainDelete API http://support.netim.com/en/wiki/DomainDelete
        */
    public StructOperationResponse domainDelete(String domain, String typeDelete) throws NetimAPIException
    {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("typeDelete", typeDelete.toUpperCase());

        return this.call("/domain/" + domain + "/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    public StructOperationResponse domainDelete(String domain) throws NetimAPIException
    {
        return this.domainDelete(domain, "NOW");
    }

    /**
     * Requests the transfer of a domain name to Netim 
     *
     * Example:
    
        String domain = "myDomain.com";
        String authID = "qlskjdlqkxlxkjlqksjdlkj";
        String idOwner = "BJ008";
        String idAdmin = "BJ007";
        String idTech = "BJ007";
        String idBilling = "BJ007";
        String ns1 = "ns1.netim.com";
        String ns2 = "ns2.netim.com";
        String ns3 = "ns3.netim.com"; 
        String ns4 = "ns4.netim.com";
        String ns5 = "ns5.netim.com";

        StructOperationResponse res = null;
        try
        {
            res = client.domainTransferIn(domain, authID, idOwner, idAdmin, idTech, idBilling, ns1, ns2, ns3, ns4, ns5);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
        * @param domain name of the domain to transfer
        * @param authID authorisation code / EPP code (if applicable)
        * @param idOwner a valid idOwner. Can also be #AUTO#
        * @param idAdmin a valid idAdmin
        * @param idTech a valid idTech
        * @param idBilling a valid idBilling
        * @param ns1 the name of the first dns
        * @param ns2 the name of the second dns
        * @param ns3 the name of the third dns
        * @param ns4 the name of the fourth dns
        * @param ns5 the name of the fifth dns
        *
        * @throws NetimAPIException
        *
        * @return StructOperationResponse giving information on the status of the operation
        *
        * @see domainTransferIn API http://support.netim.com/en/wiki/DomainTransferIn
        */
    public StructOperationResponse domainTransferIn(String domain, String authID, String idOwner, String idAdmin, String idTech, String idBilling, String ns1, String ns2, String ns3, String ns4, String ns5) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("authID", authID);
        
        params.put("idOwner", idOwner);
        params.put("idAdmin", idAdmin);
        params.put("idTech", idTech);
        params.put("idBilling", idBilling);

        params.put("ns1", ns1);
        params.put("ns2", ns2);
        params.put("ns3", ns3);
        params.put("ns4", ns4);
        params.put("ns5", ns5);

        return this.call("/domain/" + domain + "/transfer/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Requests the transfer (with change of domain holder) of a domain name to Netim 
     *
     * Example:
    
        String domain = "myDomain.com";
        String authID = "qlskjdlqkxlxkjlqksjdlkj";
        String idOwner = "BJ008";
        String idAdmin = "BJ007";
        String idTech = "BJ007";
        String idBilling = "BJ007";
        String ns1 = "ns1.netim.com";
        String ns2 = "ns2.netim.com";
        String ns3 = "ns3.netim.com"; 
        String ns4 = "ns4.netim.com";
        String ns5 = "ns5.netim.com";

        StructOperationResponse res = null;
        try
        {
            res = client.domainTransferTrade(domain, authID, idOwner, idAdmin, idTech, idBilling, ns1, ns2, ns3, ns4, ns5);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
        * @param domain name of the domain to transfer
        * @param authID authorisation code / EPP code (if applicable)
        * @param idOwner a valid idOwner.
        * @param idAdmin a valid idAdmin
        * @param idTech a valid idTech
        * @param idBilling a valid idBilling
        * @param ns1 the name of the first dns
        * @param ns2 the name of the second dns
        * @param ns3 the name of the third dns
        * @param ns4 the name of the fourth dns
        * @param ns5 the name of the fifth dns
        *
        * @throws NetimAPIException
        *
        * @return StructOperationResponse giving information on the status of the operation
        *
        * @see domainTransferTrade API http://support.netim.com/en/wiki/domainTransferTrade
        */
    public StructOperationResponse domainTransferTrade(String domain, String authID, String idOwner, String idAdmin, String idTech, String idBilling, String ns1, String ns2, String ns3, String ns4, String ns5) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("authID", authID);
        
        params.put("idOwner", idOwner);
        params.put("idAdmin", idAdmin);
        params.put("idTech", idTech);
        params.put("idBilling", idBilling);

        params.put("ns1", ns1);
        params.put("ns2", ns2);
        params.put("ns3", ns3);
        params.put("ns4", ns4);
        params.put("ns5", ns5);

        return this.call("/domain/" + domain + "/transfer-trade/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Requests the internal transfer of a domain name from one Netim account to another. 
     *
     * Example:
    
        String domain = "myDomain.com";
        String authID = "qlskjdlqkxlxkjlqksjdlkj";
        String idAdmin = "BJ007";
        String idTech = "BJ007";
        String idBilling = "BJ007";
        String ns1 = "ns1.netim.com";
        String ns2 = "ns2.netim.com";
        String ns3 = "ns3.netim.com"; 
        String ns4 = "ns4.netim.com";
        String ns5 = "ns5.netim.com";

        StructOperationResponse res = null;
        try
        {
            res = client.domainTransferTrade(domain, authID, idAdmin, idTech, idBilling, ns1, ns2, ns3, ns4, ns5);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain to transfer
     * @param authID authorisation code / EPP code (if applicable)
     * @param idAdmin a valid idAdmin
     * @param idTech a valid idTech
     * @param idBilling a valid idBilling
     * @param ns1 the name of the first dns
     * @param ns2 the name of the second dns
     * @param ns3 the name of the third dns
     * @param ns4 the name of the fourth dns
     * @param ns5 the name of the fifth dns
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainInternalTransfer API http://support.netim.com/en/wiki/domainInternalTransfer
    */
    public StructOperationResponse domainInternalTransfer(String domain, String authID, String idAdmin, String idTech, String idBilling, String ns1, String ns2, String ns3, String ns4, String ns5) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("authID", authID);
        
        params.put("idAdmin", idAdmin);
        params.put("idTech", idTech);
        params.put("idBilling", idBilling);

        params.put("ns1", ns1);
        params.put("ns2", ns2);
        params.put("ns3", ns3);
        params.put("ns4", ns4);
        params.put("ns5", ns5);

        return this.call("/domain/" + domain + "/internal-transfer/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Renew a domain name for a new subscription period 
     *
     * Example
    
        String domain = "myDomain.com"
        int duration = 1;
        StructOperationResponse res = null;
        try
        {
            res = client.domainCreate(domain, duration);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing

     * @param domain the name of the domain to renew
     * @param duration the duration of the renewal expressed in year. Must be at least 1 and less than the maximum amount
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainRenew API  http://support.netim.com/en/wiki/DomainRenew
     */
    public StructOperationResponse domainRenew(String domain, int duration) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("duration", String.valueOf(duration));

        return this.call("domain/" + domain + "/renew/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Restores a domain name in quarantine / redemption status
     *
     * Example
    
        String domain = "myDomain.com";
        StructOperationResponse res = null;
        try
        {
            res = client.domainRestore(domain);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing

     * @param domain name of the domain
     *
     * @throws NetimAPIException
     *
     * @return a StructOperationResponse giving information on the status of the operation
     *
     * @see domainRestore API http://support.netim.com/en/wiki/DomainRestore
     */
    public StructOperationResponse domainRestore(String domain) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        return this.call("domain/" + domain + "/restore/", HttpVerb.PATCH, StructOperationResponse.class);
    }

    /**
     * Updates the settings of a domain name
     *
     * Example
        
        String domain = "myDomain.com";
        String codePref = "registrar_lock": //possible values are 'whois_privacy', 'registrar_lock', 'auto_renew', 'tag' or 'note'
        int value = 1; // 1 or 0
        StructOperationResponse res = null;
        try
        {
            res = client.domainSetPreference(domain, codePref, value);
            //equivalent to res = client.domainSetRegistrarLock(domain, value); each codePref has a corresponding helping function
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain
     * @param codePref setting to be modified. Accepted value are 'whois_privacy', 'registrar_lock', 'auto_renew', 'tag' or 'note'
     * @param value new value for the settings. 
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainSetPreference API http://support.netim.com/en/wiki/DomainSetPreference
     * @see domainSetWhoisPrivacy, domainSetRegistrarLock, domainSetAutoRenew, domainSetTag, domainSetNote
    */
    public StructOperationResponse domainSetPreference(String domain, String codePref, String value) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("codePref", codePref);
        params.put("value", value);

        return this.call("domain/" + domain + "/preference/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Requests the transfer of the ownership to another party
     *
     * Example
    
        String domain = "myDomain.com";
        String idOwner = "BJ008";
        StructOperationResponse res = null;
        try
        {
            res = client.domainTransferOwner(domain, idOwner);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain
     * @param idOwner id of the new owner
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainTransferOwner API http://support.netim.com/en/wiki/DomainTransferOwner
     * @see function createContact
     */
    public StructOperationResponse domainTransferOwner(String domain, String idOwner) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("idOwner", idOwner);

        return this.call("/domain/" + domain + "/transfer-owner/", HttpVerb.PUT, params, StructOperationResponse.class);
    }

    /**
     * Replaces the contacts of the domain (administrative, technical, billing) 
     *
     * Example
    
        String domain = "myDomain.com";
        String idAdmin = "BJ007";
        String idTech = "BJ007";
        String idBilling = "BJ007";
        StructOperationResponse res = null;
        try
        {
            res = client.domainChangeContact(domain, idAdmin, idTech, idBilling);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain
     * @param idAdmin id of the admin contact
     * @param idTech id of the tech contact
     * @param idBilling id of the billing contact
     * 
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainChangeContact API http://support.netim.com/en/wiki/DomainChangeContact
     * @see function createContact
    */
    public StructOperationResponse domainChangeContact(String domain, String idAdmin, String idTech, String idBilling) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("idAdmin", idAdmin);
        params.put("idTech", idTech);
        params.put("idBilling", idBilling);

        return this.call("/domain/" + domain + "/contacts/", HttpVerb.PUT, params, StructOperationResponse.class);
    }

    /**
     * Replaces the DNS servers of the domain (redelegation) 
     * 
     * Example
    <pre>{@code
        String domain = "myDomain.com";
        String ns1 = "ns1.netim.com";
        String ns2 = "ns2.netim.com";
        String ns3 = "ns3.netim.com";
        String ns4 = "ns4.netim.com";
        String ns5 = "ns5.netim.com";

        StructOperationResponse res = null;
        try
        {
            res = client.domainChangeDNS(domain, ns1, ns2, ns3, ns4, ns5);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing}</pre>
    
     * @param domain name of the domain
     * @param ns1 the name of the first dns
     * @param ns2 the name of the second dns
     * @param ns3 the name of the third dns
     * @param ns4 the name of the fourth dns
     * @param ns5 the name of the fifth dns
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainChangeDNS API http://support.netim.com/en/wiki/DomainChangeDNS
    */
    public StructOperationResponse domainChangeDNS(String domain, String ns1, String ns2, String ns3, String ns4, String ns5) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("ns1", ns1);
        params.put("ns2", ns2);
        params.put("ns3", ns3);
        params.put("ns4", ns4);
        params.put("ns5", ns5);

        return this.call("/domain/" + domain + "/dns/", HttpVerb.PUT, params, StructOperationResponse.class);
    }

    public StructOperationResponse domainChangeDNS(String domain, String ns1, String ns2) throws NetimAPIException
    {
        return this.domainChangeDNS(domain, ns1, ns2, "", "", "");
    }

    public StructOperationResponse domainChangeDNS(String domain, String ns1, String ns2, String ns3) throws NetimAPIException
    {
        return this.domainChangeDNS(domain, ns1, ns2, ns3, "", "");
    }

    public StructOperationResponse domainChangeDNS(String domain, String ns1, String ns2, String ns3, String ns4) throws NetimAPIException
    {
        return this.domainChangeDNS(domain, ns1, ns2, ns3, ns4, "");
    }

    /**
     * Allows to sign a domain name with DNSSEC if it uses NETIM DNS servers 
     * 
     * @param domain name of the domain
     * @param enable New signature value 0 : unsign
     * 										1 : sign 
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainSetDNSsec API http://support.netim.com/en/wiki/DomainSetDNSsec
     */
    public StructOperationResponse domainSetDNSSec(String domain, int enable) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("enable", String.valueOf(enable));
        return this.call("/domain/" + domain + "/dnssec/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Returns the authorization code to transfer the domain name to another registrar or to another client account 
     *
     * Example
    
        String domain = "myDomain.com";
        StructOperationResponse res = null;
        try
        {
            res = client.domainAuthID(domain, 0);
            //res = client.domainAuthID(domain, 1); to send the authID in an email to the registrant of the domain
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain to get the AuthID
     * @param sendToRegistrant recipient of the AuthID. Possible value are 0 for the reseller and 1 for the registrant
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainAuthID API http://support.netim.com/en/wiki/DomainAuthID
     */
    public StructOperationResponse domainAuthID(String domain, int sendToRegistrant) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sendtoregistrant", String.valueOf(sendToRegistrant));
        return this.call("/domain/" + domain + "/authid/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Release a domain name (managed by the reseller) to its registrant (who will become a direct customer at Netim) 
     *
     * Example
    
        String domain = "myDomain.com";
        StructOperationResponse res = null;
        try
        {
            res = client.domainRelease(domain);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain domain name to be released
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainRelease API http://support.netim.com/en/wiki/DomainRelease
     */
    public StructOperationResponse domainRelease(String domain) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        return this.call("/domain/" + domain + "/release/", HttpVerb.PATCH, StructOperationResponse.class);
    }

    /**
     * Adds a membership to the domain name 
     *
     * Example
    
        domain = "myDomain.com";
        token = "qmksjdmqsjdmkl"; //replace with your token here
        StructOperationResponse res = null;
        try
        {
            res = client.domainSetMembership(domain, token);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of domain
     * @param token membership number into the community
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainSetMembership API http://support.netim.com/en/wiki/DomainSetMembership
     */
    public StructOperationResponse domainSetMembership(String domain, String token) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", String.valueOf(token));
        return this.call("/domain/" + domain + "/membership/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Returns all available operations for a given TLD 
     * 
     * Example:
        
        StructDomainTldInfo res = null;
        try
        {
            res = client.domainTldInfo("COM"); //or 'com'
        }
        catch (NetimAPIexception exception)
        {
            //do something about the error
        }

        domainInfo = res;
        //continue processing
        
     * @param tld a valid tld without the dot before it
     *
     * @throws NetimAPIException
     *
     * @return StructDomainTldInfo information about the tld
     *
     * @see domainTldInfo API http://support.netim.com/fr/wiki/DomainTldInfo
     */
    public StructDomainTldInfo domainTldInfo(String tld) throws NetimAPIException
    {
        return this.call("/tld/" + tld + "/", HttpVerb.GET, StructDomainTldInfo.class);
    }

    /**
     * Allows to sign a domain name with DNSSEC if it doesn't use NETIM DNS servers 
     * 
     * @param domain name of the domain
     * @param DSRecords A StructDSRecord object 
     * @param flags
     * @param protocol
     * @param algo
     * @param pubKeys
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     * 
     * @see domainSetDNSSecExt API http://support.netim.com/en/wiki/DomainSetDNSSecExt
     */
    public StructOperationResponse domainSetDNSSecExt(String domain, List<StructDSRecord> DSRecords, int flags, int protocol, int algo, String pubKey) throws NetimAPIException
    {
        domain = domain.toLowerCase();

        var params = new HashMap<String, Object>();
        params.put("DSRecords", DSRecords);
        params.put("flags", flags);
        params.put("protocol", protocol);
        params.put("algo", algo);
        params.put("pubKey", pubKey);

        return this.call("/domain/" + domain + "/dnssec/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Returns whois informations on given domain
     * 
     * Example:
        
        String res = null;
        try
        {
            res = client.domainWhois("myDomain.com");
        }
        catch (NetimAPIexception exception)
        {
            //do something about the error
        }

        //continue processing
        
     * @param domain the domain's name
     *
     * @throws NetimAPIException
     *
     * @return information about the domain
     */
    public String domainWhois(String domain) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        return this.call("/domain/" + domain + "/whois/", HttpVerb.GET, String.class);
    }
    

    /**
     * Returns the list of all prices for each tld 
     * 
     * @throws NetimAPIException
     * 
     * @return StructDomainPriceList[] 
     * 
     * @see domainPriceList API http://support.netim.com/en/wiki/DomainPriceList
     */
    public Map<String, StructDomainPriceList> domainPriceList() throws NetimAPIException
    {
        try {
            TypeReference<HashMap<String, StructDomainPriceList>> typeRef 
                = new TypeReference<HashMap<String, StructDomainPriceList>>() {};
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(this.call("/tlds/price-list/", HttpVerb.GET), typeRef);
        }
        catch (Exception e)
        {
            this._lastError = e.getMessage();
            throw new NetimAPIException(e);
        }
    }

    /**
     * Allows to know a domain's price 
     * 
     * @param domain name of domain
     * @param authID authorisation code (optional)
     * 
     * @throws NetimAPIException
     * 
     * @return StructQueryDomainPrice
     * 
     * @see queryDomainPrice API https://support.netim.com/en/wiki/QueryDomainPrice
     * 
     */
    public StructQueryDomainPrice queryDomainPrice(String domain, String authID) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        domain = domain.toLowerCase();
        if(authID != null && !authID.isEmpty()) params.put("authId", authID);
        return this.call("/domain/" + domain + "/price/", HttpVerb.GET, params, StructQueryDomainPrice.class);
    }

    public StructQueryDomainPrice queryDomainPrice(String domain) throws NetimAPIException
    {
        return this.queryDomainPrice(domain, null);
    }

    /**
     * Allows to know if there is a claim on the domain name 
     * 
     * @param domain name of domain
     * 
     * @throws NetimAPIException
     * 
     * @return false: no claim ; true: at least one claim
     * 
     * @see queryDomainClaim API https://support.netim.com/en/wiki/QueryDomainClaim
     * 
     */
    public boolean queryDomainClaim(String domain) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        return this.call("/domain/" + domain + "/claim/", HttpVerb.GET, Boolean.class);
    }

    /**
     * Returns all domains linked to the reseller account.
     * 
     * @param filter Domain name
     * 
     * @throws NetimAPIException
     * 
     * @return array The filter applies onto the domain name
     *
     * @see queryDomainList API https://support.netim.com/en/wiki/QueryDomainList
     *
     */
    public StructDomainList[] queryDomainList(String filter) throws NetimAPIException
    {
        return this.call("/domains/" + filter, HttpVerb.GET, StructDomainList[].class);
    }

    /**
     * Resets all DNS settings from a template 
     * 
     * @param domain Domain name
     * @param numTemplate Template number
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse
     * 
     * @see domainZoneInit API https://support.netim.com/en/wiki/DomainZoneInit
     * 
     */
    public StructOperationResponse domainZoneInit(String domain, int numTemplate) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        var params = new HashMap<String, Object>();
        params.put("numTemplate", numTemplate);

        return this.call("/domain/" + domain + "/zone/init/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Creates a DNS record into the domain zonefile
     *
     * Example
    
        String domain = "myDomain.com";
        String  subdomain = "www";
        String type = "A";
        String value = "192.168.0.1";
        StructOptionsZone options = new StructOptionsZone(null, null, 3600, null, null, null);
        StructOperationResponse res = null;
        try
        {
            res = client.domainZoneCreate(domain, subdomain, type, value, options);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain
     * @param subdomain subdomain
     * @param type type of DNS record. Accepted values are: 'A', 'AAAA', 'MX, 'CNAME', 'TXT', 'NS' and 'SRV'
     * @param value value of the new DNS record
     * @param options contains multiple StructOptionsZone : settings of the new DNS record 
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainZoneCreate API http://support.netim.com/en/wiki/DomainZoneCreate
     * @see StructOptionsZone http://support.netim.com/en/wiki/StructOptionsZone
     */
    public StructOperationResponse domainZoneCreate(String domain, String subdomain, String type, String value, StructOptionsZone options) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        var params = new HashMap<String, Object>();
        params.put("subdomain", subdomain);
        params.put("type", type);
        params.put("value", value);
        params.put("options", options);


        return this.call("/domain/" + domain + "/zone/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Deletes a DNS record into the domain's zonefile 
     *
     * Example
    
        String domain = "myDomain.com"
        String subdomain = "www";
        String type = "A";
        String value = "192.168.0.1";
        StructOperationResponse res = null;
        try
        {
            res = client.domainZoneDelete(domain, subdomain, type, value);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain
     * @param subdomain subdomain
     * @param type type of DNS record. Accepted values are: 'A', 'AAAA', 'MX, 'CNAME', 'TXT', 'NS and 'SRV'
     * @param value value of the new DNS record
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     * 
     * @see domainZoneDelete API http://support.netim.com/en/wiki/DomainZoneDelete
     */
    public StructOperationResponse domainZoneDelete(String domain, String subdomain, String type, String value) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        var params = new HashMap<String, Object>();
        params.put("subdomain", subdomain);
        params.put("type", type);

        return this.call("/domain/" + domain + "/zone/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Resets the SOA record of a domain name 
     *
     * Example
    
        String domain = "myDomain.com";
        int ttl = 24;
        char ttlUnit = 'H';
        int refresh = 24;
        char refreshUnit = 'H';
        int retry = 24;
        char retryUnit = 'H';
        int expire = 24;
        char expireUnit = 'H';
        int minimum = 24;
        char minimumUnit = 'H';
        
        try
        {
            res = client.domainZoneInitSoa(domain, ttl, ttlUnit, refresh, refreshUnit, retry, retryUnit, expire, expireUnit, minimum, minimumUnit);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param domain name of the domain
     * @param ttl time to live
     * @param ttlUnit TTL unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param refresh Refresh delay
     * @param refreshUnit Refresh unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param retry Retry delay
     * @param retryUnit Retry unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param expire Expire delay
     * @param expireUnit Expire unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param minimum Minimum delay
     * @param minimumUnit Minimum unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     * 
     * @see domainZoneInitSoa API http://support.netim.com/en/wiki/DomainZoneInitSoa
     */
   public StructOperationResponse domainZoneInitSoa(String domain, int ttl, char ttlUnit, int refresh, char refreshUnit, int retry, char retryUnit, int expire, char expireUnit, int minimum, char minimumUnit) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        var params = new HashMap<String, Object>();
        params.put("ttl", ttl);
        params.put("ttlUnit", ttlUnit);
        params.put("refresh", refresh);
        params.put("refreshUnit", refreshUnit);
        params.put("retry", retry);
        params.put("retryUnit", retryUnit);
        params.put("expire", expire);
        params.put("expireUnit", expireUnit);
        params.put("minimum", minimum);
        params.put("minimumUnit", minimumUnit);

        return this.call("/domain/" + domain + "/zone/init-soa/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Returns all DNS records of a domain name 
     * 
     * @param domain Domain name
     * 
     * @throws NetimAPIException
     * 
     * @return An array of StructQueryZoneList
     *
     * @see StructQueryZoneList[] API https://support.netim.com/en/wiki/QueryZoneList
     *
     */
    public StructQueryZoneList[] queryZoneList(String domain) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        return this.call("/domain/" + domain + "/zone/", HttpVerb.GET, StructQueryZoneList[].class);
    }

    /**
     * Creates an email address forwarded to recipients
     *
     * Example
    
        String mailBox = "example@myDomain.com";
        String recipients = "address1@abc.com, address2@abc.com";
        StructOperationResponse res = null;
        try
        {
            res = client.domainMailFwdCreate(mailBox, recipients);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param mailBox email adress (or * for a catch-all)
     * @param recipients string list of email adresses (separated by commas)
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainMailFwdCreate API http://support.netim.com/en/wiki/DomainMailFwdCreate
     */
    public StructOperationResponse domainMailFwdCreate(String mailBox, String recipients) throws NetimAPIException
    {
        mailBox = mailBox.toLowerCase();
        var params = new HashMap<String, Object>();
        params.put("recipients", recipients);
        return this.call("/domain/" + mailBox + "/mail-forwarding/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Deletes an email forward
     *
     * Example
    
        String mailBox = "example@myDomain.com";

        StructOperationResponse res = null;
        try
        {
            res = client.domainMailFwdDelete(mailBox);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param mailBox email adress 
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainMailFwdDelete API http://support.netim.com/en/wiki/DomainMailFwdDelete
     */
    public StructOperationResponse domainMailFwdDelete(String mailBox) throws NetimAPIException
    {
        mailBox = mailBox.toLowerCase();
        return this.call("/domain/" + mailBox + "/mail-forwarding/", HttpVerb.DELETE, StructOperationResponse.class);
    }

    /**
     * Returns all email forwards for a domain name
     * 
     * @param domain Domain name
     * 
     * @throws NetimAPIException
     * 
     * @return array An array of StructQueryMailFwdList
     * 
     * @see queryMailFwdList API https://support.netim.com/en/wiki/QueryMailFwdList
     */
    public StructQueryMailFwdList[] queryMailFwdList(String domain) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        return this.call("/domain/" + domain + "/mail-forwardings/", HttpVerb.GET, StructQueryMailFwdList[].class);
    }

    /**
     * Creates a web forwarding 
     *
     * Example
    
        String fqdn = "subdomain.myDomain.com";
        String target = "myDomain.com";
        String type = "DIRECT";
        StructOptionsFwd options = new StructOptionsFwd(301, "ftp", "", "");
        
        StructOperationResponse res = null;
        try
        {
            res = client.domainWebFwdCreate(fqdn, target, type, options);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
    
     * @param fqdn hostname (fully qualified domain name)
     * @param target target of the web forwarding
     * @param type type of the web forwarding. Accepted values are: "DIRECT", "IP", "MASKED" or "PARKING"
     * @param options contains StructOptionsFwd : settings of the web forwarding. An array with keys: header, protocol, title and parking.
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainWebFwdCreate API http://support.netim.com/en/wiki/DomainWebFwdCreate
     * @see StructOptionsFwd http://support.netim.com/en/wiki/StructOptionsFwd
     */
    public StructOperationResponse domainWebFwdCreate(String fqdn, String target, String type, StructOptionsFwd options) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("target", target);
        params.put("type", type.toUpperCase());
        params.put("options", options);

        return this.call("/domain/" + fqdn + "/web-forwarding/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Removes a web forwarding 
     *
     * Example
        
        String fqdn = "subdomain.myDomain.com"
        StructOperationResponse res = null;
        try
        {
            res = client.domainWebFwdDelete(fqdn);
        }
        catch (NetimAPIexception exception)
        {
            //do something when operation had an error
        }
        //continue processing
        
     * @param fqdn hostname, a fully qualified domain name
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see domainWebFwdDelete API http://support.netim.com/en/wiki/DomainWebFwdDelete
     */
    public StructOperationResponse domainWebFwdDelete(String fqdn) throws NetimAPIException
    {
        return this.call("/domain/" + fqdn + "/web-forwarding/", HttpVerb.DELETE, StructOperationResponse.class);
    }

    /**
     * Return all web forwarding of a domain name 
     * 
     * @param domain Domain name
     * 
     * @throws NetimAPIException
     * 
     * @return array An array of StructQueryWebFwdList
     *
     * @see queryWebFwdList API https://support.netim.com/en/wiki/QueryWebFwdList
     * @see StructQueryWebFwdList https://support.netim.com/fr/wiki/StructQueryWebFwdList
     *
     */
    public StructQueryWebFwdList[] queryWebFwdList(String domain) throws NetimAPIException
    {
        domain = domain.toLowerCase();
        return this.call("/domain/" + domain + "/web-forwardings/", HttpVerb.GET, StructQueryWebFwdList[].class);
    }

    /**
     * Creates a SSL redirection 
     *		
     * @param prod certificate type 
     * @param duration period of validity (in years)
     * @param CSRInfo object containing informations about the CSR 
     * @param validation validation method of the CSR (either by email or file) : 	"file"
     *																						"email:admin@yourdomain.com"
     *																						"email:postmaster@yourdomain.com,webmaster@yourdomain.com" 
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see sslCreate API http://support.netim.com/en/wiki/SslCreate
     * @see StructCSR http://support.netim.com/en/wiki/StructCSR
     */
    public StructOperationResponse sslCreate(String prod, int duration, StructCSR CSRInfo, String validation) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("prod", prod);
        params.put("duration", duration);
        params.put("CSR", CSRInfo);
        params.put("validation", validation);

        return this.call("/ssl/", HttpVerb.POST, params, StructOperationResponse.class);
    }


    /**
     * Renew a SSL certificate for a new subscription period. 
     *		
     * @param IDSSL SSL certificate ID
     * @param duration period of validity after the renewal (in years). Only the value 1 is valid
     * 
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see sslRenew API http://support.netim.com/en/wiki/SslRenew
     */
    public StructOperationResponse sslRenew(String IDSSL, int duration) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("duration", duration);

        return this.call("/ssl/" + IDSSL + "/renew/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Revokes a SSL Certificate. 
     * 
     * @param IDSSL SSL certificate ID
     * 
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see sslRevoke API http://support.netim.com/en/wiki/SslRevoke
     */
    public StructOperationResponse sslRevoke(String IDSSL) throws NetimAPIException
    {
        return this.call("/ssl/" + IDSSL + "/", HttpVerb.DELETE, StructOperationResponse.class);
    }

    /**
     * Reissues a SSL Certificate. 
     * 
     * @param IDSSL SSL certificate ID
     * @param CSRInfo Object containing informations about the CSR
     * @param validation validation method of the CSR (either by email or file) : 	"file"
     *																				"email:admin@yourdomain.com"
     *																				"email:postmaster@yourdomain.com,webmaster@yourdomain.com"
     * 
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see sslReIssue API http://support.netim.com/en/wiki/SslReIssue
     * @see StructCSR http://support.netim.com/en/wiki/StructCSR
     */
    public StructOperationResponse sslReIssue(String IDSSL, StructCSR CSRInfo, String validation) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("CSR", CSRInfo);
        params.put("validation", validation);

        return this.call("/ssl/" + IDSSL + "/reissue/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Updates the settings of a SSL certificate. Currently, only the autorenew setting can be modified. 
     * 
     * @param IDSSL SSL certificate ID
     * @param codePref Setting to be modified (auto_renew/to_be_renewed)
     * @param value New value of the setting
     * 
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see sslReIssue API http://support.netim.com/en/wiki/SslSetPreference
     */
    public StructOperationResponse sslSetPreference(String IDSSL, String codePref, String value) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("codePref", codePref);
        params.put("value", value);

        return this.call("/ssl/" + IDSSL + "/preference/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Returns all the informations about a SSL certificate
     * 
     * @param IDSSL SSL certificate ID
     * 
     * @throws NetimAPIException
     * 
     * @return StructSSLInfo containing the SSL certificate informations 
     * 
     * @see sslInfo API http://support.netim.com/en/wiki/SslInfo
     */
    public StructSSLInfo sslInfo(String IDSSL) throws NetimAPIException
    {
        return this.call("/ssl/" + IDSSL + "/", HttpVerb.GET, StructSSLInfo.class);
    }

    /**
     * Creates a web hosting
     * 
     * @param fqdn Fully qualified domain of the main vhost. Warning, the secondary vhosts will always be subdomains of this FQDN
     * @param duration ID_TYPE_PROD of the hosting
     * @param cms 
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     * 
     */
    public StructOperationResponse webHostingCreate(String fqdn, String offer, int duration, Map<String, Object> cms) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("fqdn", fqdn);
        params.put("offer", offer);
        params.put("duration", duration);
        params.put("cms", cms);

        return this.call("/webhosting/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    public StructOperationResponse webHostingCreate(String fqdn, String offer, int duration) throws NetimAPIException
    {
        return this.webHostingCreate(fqdn, offer, duration, new HashMap<String, Object>());
    }

    /**
     * Get the unique ID of the hosting
     * 
     * @param fqdn Fully qualified domain of the main vhost.
     * 
     * @throws NetimAPIException
     * 
     * @return string the unique ID of the hosting
     */
    public String webHostingGetID(String fqdn) throws NetimAPIException
    {
        return this.call("/webhosting/get-id/" + fqdn, HttpVerb.GET, String.class);
    }

    /**
     * Get informations about web hosting (generic infos, MUTU platform infos, ISPConfig ...)
     * 
     * @param id Hosting id
     * @param additionalData determines which infos should be returned ("NONE", "ALL", "WEB", "VHOSTS", "SSL_CERTIFICATES",
     * "PROTECTED_DIRECTORIES", "DATABASES", "DATABASE_USERS", "FTP_USERS", "CRON_TASKS", "MAIL", "DOMAIN_MAIL")
     * 
     * @throws NetimAPIException
     * 
     * @return StructWebHostingInfo giving informations of the webhosting
     */
    public Map<String,Object> webHostingInfo(String id, List<String> additionalData) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("additionalData", additionalData);

        try {
            TypeReference<HashMap<String, Object>> typeRef 
                = new TypeReference<HashMap<String, Object>>() {};
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(this.call("/webhosting/" + id, HttpVerb.GET, params), typeRef);
        }
        catch (Exception e)
        {
            this._lastError = e.getMessage();
            throw new NetimAPIException(e);
        }
    }

    /**
     * Renew a webhosting
     * 
     * @param id Hosting id
     * @param duration Duration period (in months)
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingRenew(String id, int duration) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("duration", duration);

        return this.call("/webhosting/" + id + "/renew/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Updates a webhosting
     * 
     * @param id Hosting id
     * @param action Action name ("SetHold", "SetWebHold", "SetDBHold", "SetFTPHold", "SetMailHold", "SetPackage", "SetAutoRenew", "SetRenewReminder", "CalculateDiskUsage")
     * @param params ("value"=>true/false) for all except SetPackage : ("offer"=>"SHWEB"/"SHLITE"/"SHMAIL"/"SHPREMIUM"/"SHSTART") and CalculateDiskUsage: ()
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingUpdate(String id, String action, Map<String,Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id, HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Deletes a webhosting
     * 
     * @param id Hosting id
     * @param typeDelete Only "NOW" is allowed
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDelete(String id, String typeDelete) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("typeDelete", typeDelete);

        return this.call("/webhosting/" + id, HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Creates a vhost
     * 
     * @param id Hosting id
     * @param fqdn Fqdn of the vhost
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingVhostCreate(String id, String fqdn) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("fqdn", fqdn);

        return this.call("/webhosting/" + id + "/vhost/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Change settings of a vhost
     * 
     * @param id Hosting id
     * @param action Possible values :"SetStaticEngine", "SetPHPVersion",  "SetFQDN", "SetWebApplicationFirewall",
     * "ResetContent", "FlushLogs", "AddAlias", "RemoveAlias", "LinkSSLCert", "UnlinkSSLCert", "EnableLetsEncrypt",
     * "DisableLetsEncrypt", "SetRedirectHTTPS", "InstallWordpress", "InstallPrestashop", "SetHold"
     * @param fparams Depends of the action
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingVhostUpdate(String id, String action, Map<String,Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id + "/vhost/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Deletes a vhost
     * 
     * @param id Hosting id
     * @param fqdn of the vhost
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingVhostDelete(String id, String fqdn) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("fqdn", fqdn);

        return this.call("/webhosting/" + id + "/vhost/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Creates a mail domain
     * 
     * @param id Hosting id
     * @param domain
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDomainMailCreate(String id, String domain) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("domain", domain);

        return this.call("/webhosting/" + id + "/domain-mail/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Change settings of mail domain based on the specified action
     * 
     * @param id Hosting id
     * @param action Action name 
     * @param fparams Parameters of the action
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDomainMailUpdate(String id, String action, Map<String, Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id + "/domain-mail/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Deletes a mail domain
     * 
     * @param id Hosting id
     * @param domain 
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDomainMailDelete(String id, String domain) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("domain", domain);

        return this.call("/webhosting/" + id + "/domain-mail/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Creates a SSL certificate
     * 
     * @param id Hosting id
     * @param sslName Name of the certificate
     * @param crt Content of the .crt file
     * @param key Content of the .key file
     * @param ca Content of the .ca file
     * @param csr Content of the .csr file
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingSSLCertCreate(String id, String sslName, String crt, String key, String ca, String csr) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("sslName", sslName);
        params.put("crt", crt);
        params.put("key", key);
        params.put("ca", ca);
        params.put("csr", csr);

        return this.call("/webhosting/" + id + "/ssl/", HttpVerb.POST, params, StructOperationResponse.class);
    }
    
    public StructOperationResponse webHostingSSLCertCreate(String id, String sslName, String crt, String key, String ca) throws NetimAPIException
    {
        return this.webHostingSSLCertCreate(id, sslName, crt, key, ca, "");
    }
    
    /**
     * Delete a SSL certificate
     * 
     * @param id Hosting id
     * @param sslName Name of the certificate
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingSSLCertDelete(String id, String sslName) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("sslName", sslName);

        return this.call("/webhosting/" + id + "/ssl/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Creates a htpasswd protection on a directory
     * 
     * @param id Hosting id
     * @param fqdn FQDN of the vhost which you want to protect
     * @param pathSecured Path of the directory to protect starting from the selected vhost
     * @param authname Text shown by browsers when accessing the directory
     * @param username Login of the first user of the protected directory
     * @param password Password of the first user of the protected directory
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingProtectedDirCreate(String id, String fqdn, String pathSecured, String authname, String username, String password) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("fqdn", fqdn);
        params.put("pathSecured", pathSecured);
        params.put("authname", authname);
        params.put("username", username);
        params.put("password", password);

        return this.call("/webhosting/" + id + "/protected-dir/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Change settings of a protected directory
     * 
     * @param id Hosting id
     * @param action Name of the action to perform
     * @param fparams Parameters for the action (depends of the action)
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingProtectedDirUpdate(String id, String action, Map<String, Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id + "/protected-dir/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Remove protection of a directory
     * 
     * @param id Hosting id
     * @param fqdn Vhost's FQDN
     * @param pathSecured Path of the protected directory starting from the selected vhost
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingProtectedDirDelete(String id, String fqdn, String pathSecured) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("fqdn", fqdn);
        params.put("path", pathSecured);

        return this.call("/webhosting/" + id + "/protected-dir/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Creates a cron task
     * 
     * @param id Hosting id
     * @param fqdn Vhost's FDQN
     * @param path Path to the script starting from the vhost's directory
     * @param returnMethod "LOG", "MAIL" or "NONE"
     * @param returnTarget 	When returnMethod == "MAIL" : an email address
     * 		                When returnMethod == "LOG" : a path to a log file starting from the vhost's directory
     * @param mm
     * @param hh
     * @param jj
     * @param mmm
     * @param jjj
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingCronTaskCreate(String id, String fqdn, String path, String returnMethod, String returnTarget, String mm, String hh, String jj, String mmm, String jjj) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("fqdn", fqdn);
        params.put("path", path);
        params.put("returnMethod", returnMethod);
        params.put("returnTarget", returnTarget);
        params.put("mm", mm);
        params.put("hh", hh);
        params.put("jj", jj);
        params.put("mmm", mmm);
        params.put("jjj", jjj);

        return this.call("/webhosting/" + id + "/cron-task/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Change settings of a cron task
     * 
     * @param id Hosting id
     * @param action Name of the action to perform
     * @param fparams Parameters for the performed action
     *
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingCronTaskUpdate(String id, String action, Map<String, Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id + "/cron-task/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Delete a cron task
     * 
     * @param id Hosting id
     * @param idCronTask 
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingCronTaskDelete(String id, String idCronTask) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("idCronTask", idCronTask);

        return this.call("/webhosting/" + id + "/cron-task/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Create a FTP user
     * 
     * @param id Hosting id
     * @param username
     * @param password
     * @param rootDir User's root directory's path starting from the hosting root
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingFTPUserCreate(String id, String username, String password, String rootDir) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("rootDir", rootDir);

        return this.call("/webhosting/" + id + "/ftp-user/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Update a FTP user
     * 
     * @param id Hosting id
     * @param action Name of the action to perform
     * @param fparams Parameters for the action (depends of the action)
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingFTPUserUpdate(String id, String action, Map<String, Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id + "/ftp-user/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Delete a FTP user
     * 
     * @param id Hosting id
     * @param username
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingFTPUserDelete(String id, String username) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("username", username);

        return this.call("/webhosting/" + id + "/ftp-user/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Create a database
     * 
     * @param id Hosting id
     * @param dbName Name of the database (Must be preceded by the hosting id separated with a "_")
     * @param version Wanted SQL version (Optional, the newest version will be chosen if left empty)
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDBCreate(String id, String dbName, String version) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("dbName", dbName);
        params.put("version", version);

        return this.call("/webhosting/" + id + "/database/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    public StructOperationResponse webHostingDBCreate(String id, String dbName) throws NetimAPIException
    {
        return this.webHostingDBCreate(id, dbName, "");
    }

    /**
     * Update database settings
     * 
     * @param id Hosting id
     * @param action Name of the action to perform
     * @param fparams Parameters for the action (depends of the action)
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDBUpdate(String id, String action, Map<String, Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("fparams", fparams);

        return this.call("/webhosting/" + id + "/database/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Delete a database
     * 
     * @param id Hosting id
     * @param dbName Name of the database
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDBDelete(String id, String dbName) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("dbName", dbName);

        return this.call("/webhosting/" + id + "/database/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Create a database user
     * 
     * @param id Hosting id
     * @param username
     * @param password
     * @param internalAccess "RW", "RO" or "NO"
     * @param externalAccess "RW", "RO" or "NO"
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDBUserCreate(String id, String username, String password, String internalAccess, String externalAccess) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("internalAccess", internalAccess);
        params.put("externalAccess", externalAccess);

        return this.call("/webhosting/" + id + "/database-user/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Update database user's settings
     * 
     * @param id Hosting id
     * @param action Name of the action to perform
     * @param fparams Parameters for the action (depends of the action)
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDBUserUpdate(String id, String action, Map<String, Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id + "/database-user/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Delete a database user
     * 
     * @param id Hosting id
     * @param username
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingDBUserDelete(String id, String username) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("username", username);

        return this.call("/webhosting/" + id + "/database-user/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Create a mailbox
     * 
     * @param id Hosting id
     * @param email
     * @param password
     * @param quota Disk space allocated to this box in MB
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingMailCreate(String id, String email, String password, int quota) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", password);
        params.put("quota", quota);

        return this.call("/webhosting/" + id + "/mailbox/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Update mailbox' settings
     * 
     * @param id Hosting id
     * @param action Name of the action to perform
     * @param fparams Parameters for the action (depends of the action)
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingMailUpdate(String id, String action, Map<String, Object> fparams) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("action", action);
        params.put("params", fparams);

        return this.call("/webhosting/" + id + "/mailbox/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Delete a mailbox
     * 
     * @param id Hosting id
     * @param email
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingMailDelete(String id, String email) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("email", email);

        return this.call("/webhosting/" + id + "/mailbox/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Create a mail redirection
     * 
     * @param id Hosting id
     * @param source
     * @param destination
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingMailFwdCreate(String id, String source, List<String> destination) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("source", source);
        params.put("destination", destination);

        return this.call("/webhosting/" + id + "/mail-forwarding/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Delete a mail redirection
     * 
     * @param id Hosting id
     * @param source
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingMailFwdDelete(String id, String source) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        params.put("source", source);

        return this.call("/webhosting/" + id + "/mail-forwarding/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }

    /**
     * Resets all DNS settings from a template 
     * 
     * @param domain
     * @param profil
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingZoneInit(String fqdn, int profil) throws NetimAPIException
    {
        fqdn = fqdn.toLowerCase();

        var params = new HashMap<String, Object>();
        params.put("profil", profil);

        return this.call("/webhosting/" + fqdn + "/zone/init/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Resets the SOA record of a domain name for a webhosting
     * 
     * @param domain name of the domain
     * @param ttl time to live
     * @param ttlUnit TTL unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param refresh Refresh delay
     * @param refreshUnit Refresh unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param retry Retry delay
     * @param retryUnit Retry unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param expire Expire delay
     * @param expireUnit Expire unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     * @param minimum Minimum delay
     * @param minimumUnit Minimum unit. Accepted values are: 'S', 'M', 'H', 'D', 'W'
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingZoneInitSoa(String fqdn, int ttl, String ttlUnit, int refresh, String refreshUnit, int retry, String retryUnit, int expire, String expireUnit, int minimum, String minimumUnit) throws NetimAPIException
    {
        fqdn = fqdn.toLowerCase();

        var params = new HashMap<String, Object>();
        params.put("ttl", ttl);
        params.put("ttlUnit", ttlUnit);
        params.put("refresh", refresh);
        params.put("refreshUnit", refreshUnit);
        params.put("retry", retry);
        params.put("retryUnit", retryUnit);
        params.put("expire", expire);
        params.put("expireUnit", expireUnit);
        params.put("minimum", minimum);
        params.put("minimumUnit", minimumUnit);

        return this.call("/webhosting/" + fqdn + "/zone/init-soa/", HttpVerb.PATCH, params, StructOperationResponse.class);
    }

    /**
     * Returns all DNS records of a webhosting
     * 
     * @param domain Domain name
     * 
     * @throws NetimAPIException
     * 
     * @return StructQueryZoneList[]
     */
    public StructQueryZoneList[] webHostingZoneList(String fqdn) throws NetimAPIException
    {
        return this.call("/webhosting/" + fqdn + "/zone/", HttpVerb.GET, StructQueryZoneList[].class);
    }

    /**
     * Creates a DNS record into the webhosting domain zonefile
     *
     * @param domain name of the domain
     * @param subdomain subdomain
     * @param type type of DNS record. Accepted values are: 'A', 'AAAA', 'MX, 'CNAME', 'TXT', 'NS and 'SRV'
     * @param value value of the new DNS record
     * @param options StructOptionsZone : settings of the new DNS record 
     *
     * @throws NetimAPIException
     *
     * @return StructOperationResponse giving information on the status of the operation
     *
     * @see StructOptionsZone API http://support.netim.com/en/wiki/StructOptionsZone
     */
    public StructOperationResponse webHostingZoneCreate(String domain, String subdomain, String type, String value, StructOptionsZone options) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        String fqdn = subdomain.toLowerCase() + "." + domain.toLowerCase();
        params.put("type", type);
        params.put("value", value);
        params.put("options", options);

        return this.call("/webhosting/" + fqdn + "/zone/", HttpVerb.POST, params, StructOperationResponse.class);
    }

    /**
     * Deletes a DNS record into the webhosting domain zonefile
     * 
     * @param domain name of the domain
     * @param subdomain subdomain
     * @param type type of DNS record. Accepted values are: 'A', 'AAAA', 'MX, 'CNAME', 'TXT', 'NS and 'SRV'
     * @param value value of the new DNS record
     * 
     * @throws NetimAPIException
     * 
     * @return StructOperationResponse giving information on the status of the operation
     */
    public StructOperationResponse webHostingZoneDelete(String domain, String subdomain, String type, String value) throws NetimAPIException
    {
        var params = new HashMap<String, Object>();
        String fqdn = subdomain.toLowerCase() + "." + domain.toLowerCase();
        params.put("type", type);
        params.put("value", value);

        return this.call("/webhosting/" + fqdn + "/zone/", HttpVerb.DELETE, params, StructOperationResponse.class);
    }
}