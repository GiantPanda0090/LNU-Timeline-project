package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
    private static final Logger LOG = Logger.getLogger(SessionHandler.class);


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
    }


    /*
     * An arraylist with the users timelines
     */
    public ArrayList<Timeline> timelineArrayList = new ArrayList<Timeline>();


    /*
     * An arraylist with all the events that belongs to a timeline that is connected to a user
     */
    public ArrayList<Event> eventArrayList = new ArrayList<Event>();


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

        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api-token-auth/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        LocalDateTime localDateTime = LocalDateTime.now();

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            HttpPost request = new HttpPost(url);

            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");

            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            String content = EntityUtils.toString(entity);

            int response_code = response.getStatusLine().getStatusCode();

            if (response_code == 200){

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

        catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
        }

        finally {
            httpClient.getConnectionManager().shutdown();
        }
        return false;
    }

    // Get user
    public void getUser(String username) {

        User[] userList;

        try {

            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/users/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            ObjectMapper mapper = new ObjectMapper();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);

            int response_code = httpURLConnection.getResponseCode();


            if (response_code == 200) {

                LOG.info("User found and assigned to user session");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();


                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }


                userList = mapper.readValue(response.toString(), User[].class);

                for (User u : userList) {
                    if (u.getUsername().equals(username)) {
                        user = u;
                    }
                }

            }

            else {
                LOG.error("Something went wrong when trying to get user.\n\t Response code: " + response_code);
            }
            httpURLConnection.disconnect();
        }

        catch (MalformedURLException e){
            e.printStackTrace();
            LOG.error(e);
        }

        catch (IOException e){
            e.printStackTrace();
            LOG.error(e);
        }

    }

    public void getTimelines(){

        Timeline[] timelineList;

        try {
            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/timelines/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            ObjectMapper mapper = new ObjectMapper();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);

            int response_code = httpURLConnection.getResponseCode();

            // If response if OK!
            if ( response_code == 200) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                timelineList = mapper.readValue(response.toString(), Timeline[].class);

                for (Timeline t : timelineList) {
                    if (t.getUser() == user.getId()) {
                        timelineArrayList.add(t);
                    }
                }
                LOG.info("Timelines requested succesfully");
            }
            // If respones is NOT OK!
            else {
                LOG.error("Not possible to get timelines.\n\tResponse code: " + response_code);
            }

            httpURLConnection.disconnect();
        }

        catch (MalformedURLException e){
            e.printStackTrace();
            LOG.error(e);
        }

        catch (IOException e){
            e.printStackTrace();
            LOG.error(e);
        }
    }

    public void createTimeline(String title, String description){

        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/timelines/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        LocalDateTime localDateTime = LocalDateTime.now();
        //System.out.println(localDateTime);

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user", user.getId());
            jsonObject.put("timeline_title", title);
            jsonObject.put("timeline_description", description);
            jsonObject.put("timeline_start_datetime", localDateTime.now());
            jsonObject.put("timeline_stop_datetime", localDateTime.now());

            HttpPost request = new HttpPost(url);

            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);


            int response_code = response.getStatusLine().getStatusCode();

            if (response_code == 201){
                LOG.info("Timeline created");
            }

            else {
                LOG.info("Something went wrong when createing timeline.\n\tResponse code: "+response_code);
            }
        }

        catch (Exception e) {
            LOG.error(e);
        }

        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void updateTimeline(String title, String description){
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        LocalDateTime localDateTime = LocalDateTime.now();

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("timeline_title", title);
            jsonObject.put("timeline_description", description);
            jsonObject.put("timeline_start_datetime", localDateTime.now());
            jsonObject.put("timeline_stop_datetime", localDateTime.now());

            HttpPut request = new HttpPut("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/timelines/"+timeline_id+"/");

            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            int response_code = response.getStatusLine().getStatusCode();

            if (response_code == 200){
                LOG.info("Timeline updated!");
            }

            else {
                LOG.info("Something went wrong when updating timeline.\n\tResponse code: "+response_code);
            }
        }

        catch (Exception e) {
            LOG.error(e);
        }

        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void getEvents(){
        Event[] eventList;

        try {
            URL url = new URL("http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/events/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            ObjectMapper mapper = new ObjectMapper();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token " + token);

            int response_code = httpURLConnection.getResponseCode();

            if (response_code == 200) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                eventList = mapper.readValue(response.toString(), Event[].class);

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

            httpURLConnection.disconnect();
        }

        catch (MalformedURLException e){
            LOG.error(e);
        }

        catch (IOException e){
            LOG.error(e);
        }

        catch (Exception e){
            LOG.error(e);
        }
    }

    public void createEvent(String title, String description){
        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/api/v1/events/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        LocalDateTime localDateTime = LocalDateTime.now();

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event_title", title);
            jsonObject.put("event_description", description);
            jsonObject.put("event_start_datetime", localDateTime.now());
            jsonObject.put("event_stop_datetime", localDateTime.now());
            jsonObject.put("timeline", timeline_id);


            HttpPost request = new HttpPost(url);

            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.addHeader("Authorization", "Token " + token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            int response_code = response.getStatusLine().getStatusCode();

            if (response_code == 201){
                LOG.info("Event created successfully!");
            }

            else {
                LOG.info("Something went wrong when trying to update event.\n\tResponse code: " +response_code);
            }

            /* Save this if we are handeling JSON responses later
            System.out.println(response);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            JSONObject result = new JSONObject(content);
            System.out.println(result.get("detail"));
            */

        }

        catch (Exception e) {
            LOG.error(e);
        }

        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    // Example usage
    public static void main(String[] args) {
        SessionHandler sessionHandler = new SessionHandler();

        if(sessionHandler.loginUser("austin", "password")){

        }


        //sessionHandler.getTimelines();
        //sessionHandler.createTimeline("Johns title", "Johns description");

        sessionHandler.setTimeline_id(5);
        sessionHandler.updateTimeline("test", "test");
        sessionHandler.getEvents();
        sessionHandler.createEvent("event test", "event desc");

        sessionHandler.setTimeline_id(6);
        sessionHandler.createEvent("event test", "event desc");

        sessionHandler.setTimeline_id(2);
        sessionHandler.createEvent("event test", "event desc");

        sessionHandler.setTimeline_id(2);
        sessionHandler.createEvent("event test", "event desc");

        //sessionHandler.setTimeline_id(7);
        // sessionHandler.updateTimeline("Austin confirming the update", "it is working");

    }
}
