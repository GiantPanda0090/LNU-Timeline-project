package backend;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class APIConfigReader {

    /*
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(SessionHandler.class);


    /*
     * Read from config file
     */
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

    /*
     * Write to API config file
     */
    public static void write(String host, String port){
        try{
            PropertiesConfiguration config = new PropertiesConfiguration("api-configuration.cfg.properties");
            config.setProperty("HOST", host);
            config.setProperty("PORT", port);
            config.save("api-configuration.cfg.properties");
        }

        catch (ConfigurationException e){
            LOG.error(e);
        }

    }

    // Example usage
    public static void main(String[] args) {
        API api = new APIConfigReader().read();
        APIConfigReader apiConfigReader = new APIConfigReader();
        apiConfigReader.write("testHost", "testPort");
    }
}
