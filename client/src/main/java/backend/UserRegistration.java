package backend;

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
 *
 */
public class UserRegistration {

    /*
     * Reading from config file
     */
    private API apiConfig = APIConfigReader.read();

    /*
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(UserRegistration.class);


    public void register(String username, String password1, String password2, String email){
        String url = "http://"+apiConfig.getHost()+":"+apiConfig.getPort()+"/rest-auth/registration/";

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password1", password1);
            jsonObject.put("password2", password2);
            jsonObject.put("email", email);

            HttpPost request = new HttpPost(url);

            request.addHeader("User-Agent", "Timeline-Client");
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonObject.toString()));
            HttpResponse response = httpClient.execute(request);

            int response_code = response.getStatusLine().getStatusCode();

            if (response_code == 201){
                LOG.info("User registration sent");
            }
            else {
                LOG.error("Something whent wrong in registration\nResponse code:"+response_code);
            }

        }

        catch (Exception ex) {
            ex.printStackTrace();
            LOG.error(ex);
        }

        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    // Example usage
    public static void main(String[] args) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.register("austin", "password", "password", "ap222ti@student.lnu.se");
    }
}
