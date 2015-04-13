package backend;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * UserRegistration class is to send user registration request to the API
 *
 */
public class UserRegistration {

    public void register(String username, String password1, String password2, String email){
        String url = "http://127.0.0.1:8000/rest-auth/registration/";

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


            System.out.println(response.toString());
            HttpEntity entity = response.getEntity();

            int response_code = response.getStatusLine().getStatusCode();
            System.out.println(response_code);

            String content = EntityUtils.toString(entity);

            if (entity != null){
                System.out.println(entity.toString());
                JSONObject result = new JSONObject(content);
                String status = result.get("status").toString();
                System.out.println(status);
            }

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

        finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    // Example usage
    public static void main(String[] args) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.register("john", "password", "password", "jh222jx@student.lnu.se");
    }
}
