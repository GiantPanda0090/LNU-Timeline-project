package backend;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by nils on 2015-04-05.
 */
public class Timeline {

    String url;
    int id;
    int user;
    String timeline_title;
    String timeline_description;
    String timeline_start_datetime;
    String timeline_stop_datetime;
    String created;
    String modified;

    //Empty constructor
    public Timeline() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getTimeline_title() {
        return timeline_title;
    }

    public void setTimeline_title(String timeline_title) {
        this.timeline_title = timeline_title;
    }

    public String getTimeline_description() {
        return timeline_description;
    }

    public void setTimeline_description(String timeline_description) {
        this.timeline_description = timeline_description;
    }


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

    public void setTimeline_start_datetime(String timeline_start_datetime) {
        this.timeline_start_datetime = timeline_start_datetime;
    }


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

    public void setTimeline_stop_datetime(String timeline_stop_datetime) {
        this.timeline_stop_datetime = timeline_stop_datetime;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
