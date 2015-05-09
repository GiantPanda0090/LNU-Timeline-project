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
* public static API read()
* public static void write(String host, String port)
*/
import GUI.LogFX;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class APIConfigReader {

    /*
     * LogFX intialized
     */
    //depricated
   //private static final Logger LOG = Logger.getLogger(SessionHandler.class);

   public static final LogFX LOG = new LogFX("APIConfigReader.class");
    /* METHOD */
    //*******************************************************************************************************************
    //******************************************************************************************************************
    /*
     * Read from config file
     */
    public static API read() {

        //initialize configuration in public so everything can access it
        Configuration props;

        try {
            //set a configureation into properties configuration
            props = new PropertiesConfiguration("api-configuration.cfg.properties");
            //exception caught
        } catch (ConfigurationException e) {
            //if exception was caught do rest of the staff
            System.out.println("Can find file");
            //set a new configuraation
            props = new PropertiesConfiguration();
        }

        /*
        *new api intialize and configured
       * @param setHost(String) setPort(String) from API
         */
        API api = new API();
        api.setHost(props.getString("HOST"));
        api.setPort(props.getString("PORT"));
        return api;
    }

    //*******************************************************************************************************************

    /*
     * Write to API config file
     * @param String hose, String port
     */
    public static void write(String host, String port){
        try{
            //initialize a new properties configuraation
            PropertiesConfiguration config = new PropertiesConfiguration("api-configuration.cfg.properties");
            //update the host to he HOST and port to the PORT
            config.setProperty("HOST", host);
            config.setProperty("PORT", port);
            // save all have been updated
            config.save("api-configuration.cfg.properties");
        }

        //exception caught and logged
        catch (ConfigurationException e){
            LOG.error(e);
        }

    }
    //end of the class
//*********************************************************************
// ********************************************************************
    // Example usage
    public static void main(String[] args) {
        API api = new APIConfigReader().read();
        APIConfigReader apiConfigReader = new APIConfigReader();
        apiConfigReader.write("testHost", "testPort");
    }
}
