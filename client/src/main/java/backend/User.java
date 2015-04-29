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
* public String getUrl()
* public void setUrl(String url)
* public int getId()
* public void setId(int id)
* public String getUsername()
* public void setUsername(String username)
* public String getEmail()
* public void setEmail(String email)
* public String getFirst_name()
* public void setFirst_name(String first_name)
* public String getLast_name()
* public void setLast_name(String last_name)
* public LocalDateTime getDate_joined()
* public void setDate_joined(String date_joined)
* public String getLast_login()
* public void setLast_login(String last_login)
 */

import java.time.LocalDateTime;

/**
 * Created by nils on 2015-04-05.
 */
public class User {
/*
*field
 */
    String url;
    int id;
    String username;
    String email;
    String first_name;
    String last_name;
    String date_joined;
    String last_login;

    /*
    *constructor
     */
    public User() {
    }


    /* METHOD */
    //*******************************************************************************************************************
    //******************************************************************************************************************

    // return url addresss
    public String getUrl() {
        return url;
    }

    //*******************************************************************************************************************

    // update current url
    public void setUrl(String url) {
        this.url = url;
    }

    //*******************************************************************************************************************

    //return id
    public int getId() {
        return id;
    }

    //*******************************************************************************************************************

    // update current id
    public void setId(int id) {
        this.id = id;
    }

    //*******************************************************************************************************************

    // return username
    public String getUsername() {
        return username;
    }

    //*******************************************************************************************************************

    //update ccurrent username
    public void setUsername(String username) {
        this.username = username;
    }

    //*******************************************************************************************************************

    // return email
    public String getEmail() {
        return email;
    }

    //*******************************************************************************************************************

    //update current email
    public void setEmail(String email) {
        this.email = email;
    }

    //*******************************************************************************************************************

    //return first name
    public String getFirst_name() {
        return first_name;
    }

    //*******************************************************************************************************************

    //update current first name
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    //*******************************************************************************************************************

    //return last name
    public String getLast_name() {
        return last_name;
    }

    //*******************************************************************************************************************

    //update current last name
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    //*******************************************************************************************************************

    // return
    public LocalDateTime getDate_joined() {
        String[] dateTimeValuesSplitedUp = date_joined.split("[-TZ: .]");
        return LocalDateTime.of(
                Integer.parseInt(dateTimeValuesSplitedUp[0]),
                Integer.parseInt(dateTimeValuesSplitedUp[1]),
                Integer.parseInt(dateTimeValuesSplitedUp[2]),
                Integer.parseInt(dateTimeValuesSplitedUp[3]),
                Integer.parseInt(dateTimeValuesSplitedUp[4]),
                Integer.parseInt(dateTimeValuesSplitedUp[5])
        );
    }

    //*******************************************************************************************************************

    //update
    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    //*******************************************************************************************************************

    //return last login
    public String getLast_login() {
        return last_login;
    }

    //*******************************************************************************************************************

    //update last log in
    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }
}

//end of the class
 //*******************************************************************************************************************
//******************************************************************************************************************\