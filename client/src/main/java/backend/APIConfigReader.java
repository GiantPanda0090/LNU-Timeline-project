package backend;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class APIConfigReader {

    public static API read() {

        Configuration props;

        try {
            props = new PropertiesConfiguration("api-configuration.cfg.properties");
        } catch (ConfigurationException e) {
            System.out.println("Can find file");
            props = new PropertiesConfiguration();
        }

        API api = new API();
        api.setHost(props.getString("HOST"));
        api.setPort(props.getString("PORT"));

        return api;
    }

    public static void write(String host, String port){
        // Implement this function
    }

    public static void main(String[] args) {
        API api = new APIConfigReader().read();
        System.out.println(api.getHost());
        System.out.println(api.getPort());

        APIConfigReader apiConfigReader = new APIConfigReader();
        apiConfigReader.write("TestHost", "TestPort");

        API api1 = new APIConfigReader().read();
        System.out.println(api1.getHost());
        System.out.println(api1.getPort());
    }
}
