package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
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
    int timeline_id;

    /*
     * An arraylist with the users timelines
     */
    ArrayList<Timeline> timelineArrayList = new ArrayList<Timeline>();


    /*
     * An arraylist with all the events that belongs to a timeline that is connected to a user
     */
    ArrayList<Event> eventArrayList = new ArrayList<Event>();


    // Empty contructor
    public SessionHandler() {}


    public Boolean loginUser(String username, String password){
        String url = "http://herrlintech.se:8000/api-token-auth/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        LocalDateTime localDateTime = LocalDateTime.now();
        //System.out.println(localDateTime);

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
            //String content = EntityUtils.toString(entity);
            //System.out.println(content);

            if (entity != null){
                //System.out.println(entity.toString());
                JSONObject result = new JSONObject(content);
                token = result.get("token").toString();
                getUser(username);
                return true;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return false;
    }

    // Get user
    public void getUser(String username) {

        User[] userList;

        try {
            //URL url = new URL("http://herrlintech.se:8000/users/.json");
            URL url = new URL("http://herrlintech.se:8000/api/v1/users/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            ObjectMapper mapper = new ObjectMapper();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token "+token);

            int responseCore = httpURLConnection.getResponseCode();

            //System.out.println("Sending 'GET' request to UTL : " + url.toString());
            System.out.println("Responsecode : " + responseCore);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null){
                response.append(inputLine);
            }

            userList = mapper.readValue(response.toString(), User[].class);

            for (User u: userList){
                if (u.getUsername().equals(username)){
                    user = u;
                }
            }

            httpURLConnection.disconnect();
        }

        catch (MalformedURLException e){
            e.printStackTrace();
        }

        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void getTimelines(){

        Timeline[] timelineList;

        try {
            URL url = new URL("http://herrlintech.se:8000/api/v1/timelines/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            ObjectMapper mapper = new ObjectMapper();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token "+token);

            int responseCore = httpURLConnection.getResponseCode();

            //System.out.println("Sending 'GET' request to UTL : " + url.toString());
            System.out.println("Responsecode : " + responseCore);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null){
                response.append(inputLine);
            }

            timelineList = mapper.readValue(response.toString(), Timeline[].class);

            for ( Timeline t : timelineList){
                if (t.getUser() == user.getId()){
                    timelineArrayList.add(t);
                }
            }

            System.out.println(timelineArrayList.size() + " timelines found for the user");

            httpURLConnection.disconnect();
        }

        catch (MalformedURLException e){
            e.printStackTrace();
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createTimeline(String title, String description){

        String url = "http://herrlintech.se:8000/api/v1/timelines/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

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
            request.addHeader("Authorization", "Token "+token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            System.out.println(response);
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void getEvents(){
        Event[] eventList;

        try {
            URL url = new URL("http://herrlintech.se:8000/api/v1/events/.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            ObjectMapper mapper = new ObjectMapper();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Timeline-Java-Client");
            httpURLConnection.setRequestProperty("Authorization", "Token "+token);

            int responseCore = httpURLConnection.getResponseCode();

            //System.out.println("Sending 'GET' request to UTL : " + url.toString());
            System.out.println("Responsecode : " + responseCore);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null){
                response.append(inputLine);
            }

            eventList = mapper.readValue(response.toString(), Event[].class);

            for ( Event e : eventList){
                if (e.timeline == timeline_id){
                    eventArrayList.add(e);
                }
            }

            System.out.println(timelineArrayList.size() + " timelines found for the user");

            httpURLConnection.disconnect();
        }

        catch (MalformedURLException e){
            e.printStackTrace();
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createEvent(String title, String description){
        String url = "http://herrlintech.se:8000/api/v1/events/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

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
            request.addHeader("Authorization", "Token "+token);
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            System.out.println(response);
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }


    public String toString(){
        return user.getUsername();
    }

    // Example usage
    public static void main(String[] args) {
        SessionHandler sessionHandler = new SessionHandler();

        if(sessionHandler.loginUser("nils", "password")){
            System.out.println(sessionHandler.token);
        }


        //sessionHandler.createTimeline("Nils first timeline", "Nils first timeline description");

        sessionHandler.getTimelines();

        sessionHandler.timeline_id = 1;
        sessionHandler.createEvent("Nils first eventtitle", "Nils first event description");
        sessionHandler.createEvent("Nils first eventtitle", "Nils first event description");
        sessionHandler.createEvent("Nils first eventtitle", "Nils first event description");
        sessionHandler.createEvent("Nils first eventtitle", "Nils first event description");
        sessionHandler.createEvent("Nils first eventtitle", "Nils first event description");

        sessionHandler.getEvents();

        for (Event e : sessionHandler.eventArrayList){
            System.out.println(e.id);
        }

    }
}
