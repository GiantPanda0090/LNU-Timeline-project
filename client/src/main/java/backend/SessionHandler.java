
package backend;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
* BACKEND
*/

/* METHOD INDEX */
/* USE CTRL+F TO SEARCH METHOD IN IDE */
/*
 * public User getActiveUser()
 * public int getTimeline_id()
 * public void setTimeline_id(int timeline_id)
 * public int getEvent_id()
 * public void setEvent_id(int event_id)
 * public Boolean loginUser(String username, String password)
 * public void getUser(String username)
 * public void updateUser(String firstname, String lastname)
 * public void getTimelines()
 * public void createTimeline(String title, String description, LocalDateTime startTimeIn, LocalDateTime stopTimeIn)
 * public void updateTimeline(String title, String description, LocalDateTime startDateTimeIn, LocalDateTime stopDateTimeIn)
 * public void deleteTimeline()
 * public void getEvents()
 * public void createEvent(String title, String description, LocalDateTime startDateTime, LocalDateTime stopDatetime)
 * public void updateEvent(String title, String description, LocalDateTime startDateTimeIn, LocalDateTime stopDateTimeIn)
 * public void deleteEvent()
 * public void updateAPIconfig()
 * public Timeline getActiveTimeline()
 */


import GUI.LogFX;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.log4j.Logger;


/**
 * This class is made to handling a session.
 * A session is to get the logged in user.
 * Get timelines and events for that user.
 *
 * methodsNeeded
 * [] getUser(username)
 * [] getTimeline(user) <- only the timelines for the spicific user
 * [] getEvents(timelines) <- events that belongs to the user timelines
 * [] updateTimeline(timeline)
 * [] createTimeline(timeline)
 * [] deleteTimeline(timeline)
 *
 */



public class SessionHandler {

    /*
     * Logging
     */

    //private static final Logger LOG = Logger.getLogger(SessionHandler.class);
    public static LogFX LOG = new LogFX("SessionHandler.class");

 //LogFX consollog = new LogFX(SessionHandler.class);

    /*
     * Reading from config file
     */
    private API apiConfig = APIConfigReader.read();

    /*
     * token it the logged in users authentication token
     * the token should be sent in any request that requires when
     * authorized resources are requested
     */
    String token;

    /*
     * user is the user that is logged in to the session
     */
    User user;

    public User getActiveUser() {
        return user;
    }
    //******************************************************************************************************************
    /*
         * timeline_id is the id of the currently selected timeline
         * This id is used to request events or if event is created
         * this value is used to assign the event to a timeline
         */
    public int timeline_id;
    public int getTimeline_id() {
        return timeline_id;
    }
    public void setTimeline_id(int timeline_id) {
        this.timeline_id = timeline_id;
        LOG.info("Timeline_id updated.\n\tNew id: "+timeline_id+"\n\tTimeline start datetime: "+getActiveTimeline().getTimeline_start_datetime()+"\n\tTimeline stop datetime: "+getActiveTimeline().getTimeline_stop_datetime());

    }
//******************************************************************************************************************
    /**
     * This is for handeling the current active event.
     */
    private int event_id;
    public int getEvent_id() {
        return event_id;
    }
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    //******************************************************************************************************************
    /*
     * An arraylist with the users timelines
     */
    public ArrayList<Timeline> timelineArrayList = new ArrayList<Timeline>();


    /*
     * An arraylist with all the events that belongs to a timeline that is connected to a user
     */
    public ArrayList<Event> eventArrayList = new ArrayList<Event>();

/*
CONSTRUCTOR
 */
    // Empty contructor
    public SessionHandler() {}


    /*
     * This function tries to log in the user
     * Username and password as arguments
     * If login successfully getUser(username) is called.
     * getUsername() applies the token to the user.
     * This token is sent within the HTTP head in every request made to the API
     */
    public Boolean loginUser(String username, String password){
        /*
        *communicate with the server start
         */
        //communication initailization
     // Generate the url that will be requested to the API
        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api-token-auth/";
        //initialize the http client
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        //initlaize localDateTime and set it as NOW
        LocalDateTime localDateTime = LocalDateTime.now();

        try {
           //JASON service initialized and set the username ad password
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            //request http and set in the url that generated before
            HttpPost request = new HttpPost(url);
            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");

            //add JSon objeect to the response
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            // export  all the info of EntityUtils (entity) into string
            String content = EntityUtils.toString(entity);

            //response code from http server intialized
            int response_code = response.getStatusLine().getStatusCode();
            //communication initialization done
            //main purpose start
            /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            if (response_code == 200){
                 // log generated
                LOG.info("User successfully signed in");

                JSONObject result = new JSONObject(content);
                token = result.get("token").toString();
                getUser(username);

                return true;
            }

            if (response_code != 200){
                LOG.info("User login failure");
            }
        }
//exception catched error loged
        catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
        }

        finally {
            httpClient.getConnectionManager().shutdown();
            /*
* end of the communication
 */
        }
        return false;
    }



    //******************************************************************************************************************
/*
*lead user to the currect session
*@param username inputed to the method
 */
    // Get user
    public void getUser(String username) {

        User[] userList;
/*
*communicate with the server start
 */
        try {
            //communication initialized
            //url generated and connection initailize
            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/users/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            ObjectMapper mapper = new ObjectMapper();
            //connection config
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);
           //initialized response code for http server
            int response_code = httpURLConnection.getResponseCode();
           //end of initialization
            //main purpose start
             /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            if (response_code == 200) {
                //ok responsed and start lead user to the currect session
                LOG.info("User found and assigned to user session");

                //intialize empty StringBuffer for loading user session
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                 //loading start until nothing else can be load
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

               //
                userList = mapper.readValue(response.toString(), User[].class);
                //
                for (User u : userList) {
                    if (u.getUsername().equals(username)) {
                        user = u;
                    }
                }
            }
// eroor detected.......... http response code are not 200
            else {
                LOG.strerror("Something went wrong when trying to get user.\n\t Response code: " + response_code);
            }
            httpURLConnection.disconnect();
        }
//excaption catched(wrong formate)
        catch (MalformedURLException e){
            e.printStackTrace();
            LOG.error(e);
        }
//excaption catched(IO)
        catch (IOException e){
            e.printStackTrace();
            LOG.error(e);
        }

    }
//********************************************************************************************************************
    /*
    *update user info
    *@param input  String firstname and String last name
     */
    // Not working, response with 400
    public void updateUser(String firstname, String lastname){
        //initialize connection and communication
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {
            //initialize and config JsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first_name", firstname);
            jsonObject.put("last_name", lastname);

            //deprecated
            //jsonObject.put("id", user.getId());
            //

            // url genrated and set it inside the request
            HttpPut request = new HttpPut("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/users/"+user.getId()+"/");

            //config request
            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            //response  code initailize and genrated
            int response_code = response.getStatusLine().getStatusCode();

            /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            if (response_code == 200){
                LOG.info("User updated!");
            }

            else {
                LOG.info("Something went wrong when updating user.\n\tResponse code: " + response_code);
            }
        }
       //excaption catched
        catch (Exception e) {
            LOG.error(e);
        }

        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    /*
    *request timelines from server
     */
    public void getTimelines(){

        Timeline[] timelineList;

        //reset previous timeline request
        if ( timelineArrayList.size() > 0){
            timelineArrayList.clear();
        }

        try {
            //server communication and connection start
            //URL generated
            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/timelines/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //
            ObjectMapper mapper = new ObjectMapper();

            //config html connection
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);

            //response code initialized and generated
            int response_code = httpURLConnection.getResponseCode();

            /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            //main purpose start
            // If response if OK!
            if ( response_code == 200) {
                // intiate a empty box to get ready to load contant
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                //load contant util nothing more can be loaded
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                //
                timelineList = mapper.readValue(response.toString(), Timeline[].class);

                //
                for (Timeline t : timelineList) {
                    if (t.getUser() == user.getId()) {
                        timelineArrayList.add(t);
                    }
                }
                LOG.info("Timelines requested succesfully");
            }
            // If respones is NOT OK!
            else {
                LOG.strerror("Not possible to get timelines.\n\tResponse code: " + response_code);
            }

            //  connection terminated
            httpURLConnection.disconnect();
        }

        // exception caught(wrong url)
        catch (MalformedURLException e){
            e.printStackTrace();
            LOG.error(e);
        }

        // exception caught (IO)
        catch (IOException e){
            e.printStackTrace();
            LOG.error(e);
        }
    }
//***************************************************************************************************************************************************************
    /*
    *Genarate a timeline
    * @param input  String title String description LocalDateTime startTimeIn LocalDateTime stopTimeIn
     */
    public void createTimeline(String title, String description, LocalDateTime startTimeIn, LocalDateTime stopTimeIn){

        //start server connected and communication
        //Url generated
        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/timelines/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        //deprecate
        //LocalDateTime localDateTime = LocalDateTime.now();
        //
        try {

            //Json initialized and configed
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", user.getId());
            jsonObject.put("timeline_title", title);
            jsonObject.put("timeline_description", description);
            jsonObject.put("timeline_start_datetime", startTimeIn);
            jsonObject.put("timeline_stop_datetime", stopTimeIn);

            // request intialized
            HttpPost request = new HttpPost(url);

            // config request
            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);


            //HTTP esponse code intialized
            int response_code = response.getStatusLine().getStatusCode();

             /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 201 received (CREATED)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            if (response_code == 201){
                LOG.info("Timeline created");
            }

            // ISSUE HAPPEND
            else {
                LOG.info("Something went wrong when createing timeline.\n\tResponse code: " + response_code);
            }
        }

        //catch exception
        catch (Exception e) {
            LOG.error(e);
        }

        // connection terminated
        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
    //***************************************************************************************************************************
    /*
    *update the time line(config)
    *@param String title, String description, LocalDateTime startDateTimeIn, LocalDateTime stopDateTimeIn
     */
    public void updateTimeline(String title, String description, LocalDateTime startDateTimeIn, LocalDateTime stopDateTimeIn){
        // server connection and communication initialize
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        //local date and time set as now
        LocalDateTime localDateTime = LocalDateTime.now();

        try {

            // json object initialized and configed
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("timeline_title", title);
            jsonObject.put("timeline_description", description);
            jsonObject.put("timeline_start_datetime", startDateTimeIn);
            jsonObject.put("timeline_stop_datetime", stopDateTimeIn);

            // request intialized and url inputed
            HttpPut request = new HttpPut("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/timelines/"+timeline_id+"/");

            // request  configured
            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            // response code intialized and configured
            int response_code = response.getStatusLine().getStatusCode();

             /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            //main purpose start
            if (response_code == 200){
                LOG.info("Timeline updated!");
            }

            //issue happend
            else {
                LOG.info("Something went wrong when updating timeline.\n\tResponse code: "+response_code);
            }
        }

        //exce?ption catched
        catch (Exception e) {
            LOG.error(e);
        }

        finally {
            // server conection shut down
            httpClient.getConnectionManager().shutdown();
        }
    }

//*************************************************************************************************************************

/*
*delete a time line from client  and server
 */
    public void deleteTimeline(){

        try {
            //url intialized
            // server connection and communication initialized
            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/timelines/"+timeline_id+"/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //connection configed
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);
            System.out.println(httpURLConnection.getResponseCode());

            //response code initialized
            int response_code = httpURLConnection.getResponseCode();

             /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            //main purpose start
            // If response if OK!
            if ( response_code == 200) {

                //intialize a empty box and ready to load
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                //load until nothing else can be loaded
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                LOG.info("Timeline is removed");
            }
            // If respones is NOT OK!
            else {
                LOG.strerror("Not possible to remove timeline.\n\tResponse code: " + response_code);
            }

            // connection treminated
            httpURLConnection.disconnect();
        }

        //exce?tion catched and logged(url wrong formate)
        catch (MalformedURLException e){
            e.printStackTrace();
            LOG.error(e);
        }

        //exaption catched and logged(IO)
        catch (IOException e){
            e.printStackTrace();
            LOG.error(e);
        }
    }

//**************************************************************************************************************************************************

/*
*request a event from server
 */
    public void getEvents(){

        //reset the previous loaded timeline and ready to load new timeline
        if (eventArrayList.size() > 0){
            eventArrayList.clear();
        }

        Event[] eventList;

        try {
            //url initialized
            //server connection and communication initialized
            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/events/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //
            ObjectMapper mapper = new ObjectMapper();

            //config the connection
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);

            //response code initialized
            int response_code = httpURLConnection.getResponseCode();

            /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            //main purpose start
            if (response_code == 200) {
               // empty box intialized and ready to get loaded
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                //load until nothing else can be loaded
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                //read the what inside the box
                eventList = mapper.readValue(response.toString(), Event[].class);

                //
                for (Event e : eventList) {
                    if (e.timeline == timeline_id) {
                        eventArrayList.add(e);
                    }
                }
                LOG.info("Events request succesfully");
            }

            else {
                LOG.info("Something went wrong when trying to get events");
            }

            //connection terminated
            httpURLConnection.disconnect();
        }

        //wrong url formate detected and logged
        catch (MalformedURLException e){
            LOG.error(e);
        }

        // exception caught and logged(IO)
        catch (IOException e){
            LOG.error(e);
        }

        //other exception caughted
        catch (Exception e){
            LOG.error(e);
        }
    }
    //******************************************************************************************************************
    /*
    *create a event
    *@param String title, String description, LocalDateTime startDateTime, LocalDateTime stopDatetime
     */
    public void createEvent(String title, String description, LocalDateTime startDateTime, LocalDateTime stopDatetime){
        //url intialized
        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/events/";
        //connection and communication intilized
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        //deprecated
        //LocalDateTime localDateTime = LocalDateTime.now();
        //

        try {

            //Json intilized and configured
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event_title", title);
            jsonObject.put("event_description", description);
            jsonObject.put("event_start_datetime", startDateTime);
            jsonObject.put("event_stop_datetime", stopDatetime);
            jsonObject.put("timeline", timeline_id);

            // request initialized
            HttpPost request = new HttpPost(url);

            //request configured
            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            //response code initialized
            int response_code = response.getStatusLine().getStatusCode();
            /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            //main purpose start
            if (response_code == 201){
                LOG.info("Event created successfully!");
            }

            else {
                LOG.info("Something went wrong when trying to update event.\n\tResponse code: " +response_code);
            }

            // currently deprecate
            /* Save this if we are handeling JSON responses later
            System.out.println(response);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            JSONObject result = new JSONObject(content);
            System.out.println(result.get("detail"));
            */

        }

        //exception caought
        catch (Exception e) {
            LOG.error(e);
        }

        // connection terminated
        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
    //********************************************************************************************************************
    /*
    * update event from server
    * @param String title, String description, LocalDateTime startDateTimeIn, LocalDateTime stopDateTimeIn
     */
    public void updateEvent(String title, String description, LocalDateTime startDateTimeIn, LocalDateTime stopDateTimeIn){

        //connecction and communication initialized
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {

            //json intialized and configure
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event_title", title);
            jsonObject.put("event_description", description);
            jsonObject.put("event_start_datetime", startDateTimeIn);
            jsonObject.put("event_stop_datetime", stopDateTimeIn);
            jsonObject.put("timeline", getActiveTimeline().getId());

            //request intialize and configure
            HttpPut request = new HttpPut("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/events/"+event_id+"/");
            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            //response code intialize
            int response_code = response.getStatusLine().getStatusCode();

             /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            //main purpose start

            if (response_code == 200){
                LOG.info("Event updated!");
            }

            else {
                LOG.info("Something went wrong when updating event.\n\tResponse code: "+response_code);
            }
        }

        // exception caught
        catch (Exception e) {
            LOG.error(e);
        }

        // connection terminated
        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

//***********************************************************************************************************************
    /*
    *delete a event from client and server
     */
    public void deleteEvent(){


        try {
            //url initialized
            // server connection and communication initialize
            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/events/"+event_id+"/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            //config connection
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);

            //response code intialize
            int response_code = httpURLConnection.getResponseCode();

             /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 200 received (OK)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            //main purpose start
            if (response_code == 200) {

                //empty  box initialize and ready to load
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                // load until nothing else can be loaded
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }


                LOG.info("Event is removed");
            }

            //issues happend
            else {
                LOG.info("Something went wrong when trying to remove event");
            }

            // connection terminated
            httpURLConnection.disconnect();
        }

        //excaption caught and logged(url wrong formate)
        catch (MalformedURLException e){
            LOG.error(e);
        }

        // excpation caught and logged(IO)
        catch (IOException e){
            LOG.error(e);
        }

        //exception caught and logged(others)
        catch (Exception e){
            LOG.error(e);
        }
    }
//**********************************************************************************************************




    /*
    // This function read values from API config file
    // This function is called if API HOST and PORT updated
    */

    public void updateAPIconfig(){
        //update api config setting on the client from server
        apiConfig = new APIConfigReader().read();
        LOG.info("API settings reloaded in sessionHandler.\n\tNew host:"+apiConfig.getHost()+"\n\tNew port:"+apiConfig.getPort());

    }
//*************************************************************************************************************************************************************
    /*
    *load required time line from server into local arraylist
     */
    public Timeline getActiveTimeline(){
        //getTimelines();
        for(Timeline t: timelineArrayList){
           // if (timeline_id == t.getId()){
               if (t.getId() == getTimeline_id()){

                return t;

            }
        }
// can not find anything
        return null;
    }
//*********************************************************************************************************
    /*
    *load requried event from server
     */
    public Event getActiveEvent(){
        for (Event e: eventArrayList){
            if (e.getId() == getEvent_id()){
                return e;
            }
        }
        //event do not find
        return null;
    }



    //end of the session handler
    //*************************************************************************************************************
    //****************************************************************************************************
    // Example usage
    public static void main(String[] args) {
        SessionHandler sessionHandler = new SessionHandler();

        if(sessionHandler.loginUser("user", "password")){

        }

    }
}
