package backend;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN MARKUS WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
* BACKEND
*/

/* METHOD INDEX */
/* USE CTRL+F TO SEARCH METHOD IN IDE */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * UserRegistration class is to send user registration request to the API
 */
public class UserRegistration {

    public int response_code;
    /*
     * Reading from config file
     */
    private API apiConfig = APIConfigReader.read();

    /*
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(UserRegistration.class);

/*
*register function
* @param String username, String password1, String password2, String email
 */
    public void register(String username, String password1, String password2, String email){
        //url reigistered
        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/rest-auth/registration/";
// connection and communication to the server intialized
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {

            //Json object intialized and configured
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password1", password1);
            jsonObject.put("password2", password2);
            jsonObject.put("email", email);

            // http response intialized
            //@param url
            HttpPost request = new HttpPost(url);

            // request configure
            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            // response code intialized
            response_code = response.getStatusLine().getStatusCode();

             /*
            * Active different  event when different http server code been responsed from the server
            * two possible
            * Success 2xx with 201 received (CREATED)
            * With out Success 2xx and non- 200 received (issues detected)
             */
            if (response_code == 201){
                LOG.info("User registration sent");
            }
            //issues happend
            else {
                LOG.error("Something whent wrong in registration\nResponse code:"+response_code);
            }

        }

        // exception caught and logged( others)
        catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex);
        }

        // connection and communication terminated
        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

//end of the class
//*******************************************************************************************************************
//******************************************************************************************************************

    // Example usage
    public static void main(String[] args) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.register("austin", "password", "password", "ap222ti@student.lnu.se");
    }
}
