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
/*
* public String getHost()
* public void setHost(String host)
* public String getPort()
* public void setPort(String port)
*/

/**
 * Created by nils on 2015-04-16.
 * This class return API information from server to the client
 */
public class API {
    /*
    FIELD
     */
    /*
    * Initialize and state the variable
    * host = host name.(String)
    * port = port name (String)
    */
    private String host;
    private String port;
    /* METHOD */
    //*******************************************************************************************************************
    //******************************************************************************************************************

/*
*  Return
*/
    //reeturn current host
    public String getHost() {
        return host;
    }

    //******************************************************************************************************************\

    //update the host by string host
    public void setHost(String host) {
        this.host = host;
    }

    //******************************************************************************************************************\

    //return current port
    public String getPort() {
        return port;
    }

    //******************************************************************************************************************\

    //update the port by string port
    public void setPort(String port) {
        this.port = port;
    }
}

//end of the class
//******************************************************************************************************************\
//******************************************************************************************************************\