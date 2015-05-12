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
* public int getId()
* public void setId(int id)
* public String getCreated()
* public void setCreated(String created)
* public String getModified()
* public String getModified()
* public String getEvent_title()
* public void setEvent_title(String event_title)
* public String getEvent_description()
* public void setEvent_description(String event_description)
* public LocalDateTime getEvent_start_datetime()
* public void setEvent_start_datetime(String event_start_datetime)
* public LocalDateTime getEvent_stop_datetime()
* public void setEvent_stop_datetime(String event_stop_datetime)
* public int getTimeline()
* public void setTimeline(int timeline)
*/

import java.time.LocalDateTime;

/**
 * Created by nils on 2015-04-06.
 */
public class Event {
    /*
    *field
     */
    int id;
    String created;
    String modified;
    String event_title;
    String event_description;
    String event_start_datetime;
    String event_stop_datetime;
    int timeline;
/*
*constructor
*/
    public Event() {}


    /* METHOD */
    //*******************************************************************************************************************
    //******************************************************************************************************************

    //get event id
    public int getId() {
        return id;
    }

    //*******************************************************************************************************************

    // set event id
    public void setId(int id) {
        this.id = id;
    }
    //*******************************************************************************************************************

    // get current created string
    public String getCreated() {
        return created;
    }

    //*******************************************************************************************************************

    //update created
    public void setCreated(String created) {
        this.created = created;
    }
    //*******************************************************************************************************************

    //get current modified string
    public String getModified() {
        return modified;
    }

    //*******************************************************************************************************************

    //update  modified
    public void setModified(String modified) {
        this.modified = modified;
    }

    //*******************************************************************************************************************

    //get current event title
    public String getEvent_title() {
        return event_title;
    }

    //*******************************************************************************************************************

    // update event title
    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    //*******************************************************************************************************************

    //get current event description
    public String getEvent_description() {
        return event_description;
    }
    //*******************************************************************************************************************

    //update current event description
    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    //*******************************************************************************************************************

    // get current event start time and date
    public LocalDateTime getEvent_start_datetime() {
        String[] dateTimeValuesSplitedUp = event_start_datetime.split("[-TZ: .]");
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

    // update event time and date
    public void setEvent_start_datetime(String event_start_datetime) {
        this.event_start_datetime = event_start_datetime;
    }

    //*******************************************************************************************************************

    // get event stop time and date
    public LocalDateTime getEvent_stop_datetime() {
        String[] dateTimeValuesSplitedUp = event_stop_datetime.split("[-TZ: .]");
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

    //update event date and time
    public void setEvent_stop_datetime(String event_stop_datetime) {
        this.event_stop_datetime = event_stop_datetime;
    }

    //*******************************************************************************************************************

    // get time line
    public int getTimeline() {
        return timeline;
    }

    //******************************************************************************************************************\

// update timeline
    //@param int timeline
    public void setTimeline(int timeline) {
        this.timeline = timeline;
    }
}

//class ended
//*******************************************************************************************************************
//*******************************************************************************************************************
