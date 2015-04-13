package backend;

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

    public String getTimeline_start_datetime() {
        return timeline_start_datetime;
    }

    public void setTimeline_start_datetime(String timeline_start_datetime) {
        this.timeline_start_datetime = timeline_start_datetime;
    }

    public String getTimeline_stop_datetime() {
        return timeline_stop_datetime;
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
