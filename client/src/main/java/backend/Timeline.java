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
* public String getUrl()
* public void setUrl(String url)
* public int getId()
* public void setId(int id)
* public int getUser()
* public void setUser(int user)
* public String getTimeline_title()
* public void setTimeline_title(String timeline_title)
* public String getTimeline_description()
* public void setTimeline_description(String timeline_description)
* public LocalDateTime getTimeline_start_datetime()
* public void setTimeline_start_datetime(String timeline_start_datetime)
* public LocalDateTime getTimeline_stop_datetime()
* public void setTimeline_stop_datetime(String timeline_stop_datetime)
* public String getCreated()
* public void setCreated(String created)
* public String getModified()
* public void setModified(String modified)
*/

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by nils on 2015-04-05.
 */
public class Timeline {
/*
FIELD
 */
    String url;
    int id;
    int user;
    String timeline_title;
    String timeline_description;
    String timeline_start_datetime;
    String timeline_stop_datetime;
    String created;
    String modified;
/*
    CONSTRUCTOR
    */

    public Timeline() {
    }

    /* METHOD */
    //*******************************************************************************************************************
    //******************************************************************************************************************\

    //return current url
    public String getUrl() {
        return url;
    }

    //******************************************************************************************************************

    // update current url
    public void setUrl(String url) {
        this.url = url;
    }

    //******************************************************************************************************************

    // return current id
    public int getId() {
        return id;
    }

    //******************************************************************************************************************

    //update current id
    public void setId(int id) {
        this.id = id;
    }

    //******************************************************************************************************************\

    // return current user
    public int getUser() {
        return user;
    }

    //******************************************************************************************************************\

    // update current user
    public void setUser(int user) {
        this.user = user;
    }

    //******************************************************************************************************************\

    //return current timeline title
    public String getTimeline_title() {
        return timeline_title;
    }

    //******************************************************************************************************************\

    // update current timeline title
    public void setTimeline_title(String timeline_title) {
        this.timeline_title = timeline_title;
    }

    //******************************************************************************************************************\

    // return current timeline description
    public String getTimeline_description() {
        return timeline_description;
    }

    //******************************************************************************************************************\

    //update current timeline description
    public void setTimeline_description(String timeline_description) {
        this.timeline_description = timeline_description;
    }

    //******************************************************************************************************************\

    /**
     *
     * This function returns a LocalDateTime format
     *
     * @return LocalDateTime
     */
    public LocalDateTime getTimeline_start_datetime() {
        String[] dateTimeValuesSplitedUp = timeline_start_datetime.split("[-TZ: .]");
        return LocalDateTime.of(
                Integer.parseInt(dateTimeValuesSplitedUp[0]),
                Integer.parseInt(dateTimeValuesSplitedUp[1]),
                Integer.parseInt(dateTimeValuesSplitedUp[2]),
                Integer.parseInt(dateTimeValuesSplitedUp[3]),
                Integer.parseInt(dateTimeValuesSplitedUp[4]),
                Integer.parseInt(dateTimeValuesSplitedUp[5])
        );
    }

    //******************************************************************************************************************\

    //update the timelind start time and date
    public void setTimeline_start_datetime(String timeline_start_datetime) {
        this.timeline_start_datetime = timeline_start_datetime;
    }

    //******************************************************************************************************************\


    /**
     *
     * This function returns a LocalDateTime format
     *
     * @return LocalDateTime
     */
    public LocalDateTime getTimeline_stop_datetime() {
        String[] dateTimeValuesSplitedUp = timeline_stop_datetime.split("[-TZ: .]");
        return LocalDateTime.of(
                Integer.parseInt(dateTimeValuesSplitedUp[0]),
                Integer.parseInt(dateTimeValuesSplitedUp[1]),
                Integer.parseInt(dateTimeValuesSplitedUp[2]),
                Integer.parseInt(dateTimeValuesSplitedUp[3]),
                Integer.parseInt(dateTimeValuesSplitedUp[4]),
                Integer.parseInt(dateTimeValuesSplitedUp[5])
        );
    }

    //******************************************************************************************************************\

    // update the stop time and date of the timeline
    public void setTimeline_stop_datetime(String timeline_stop_datetime) {
        this.timeline_stop_datetime = timeline_stop_datetime;
    }

    //******************************************************************************************************************\

    /*
    * return
     */
    public String getCreated() {
        return created;
    }
// update created
    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    //update modified
    public void setModified(String modified) {
        this.modified = modified;
    }
}
//end of the class
//*******************************************************************************************************************
//******************************************************************************************************************\