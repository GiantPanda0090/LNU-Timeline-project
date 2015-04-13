package backend;

/**
 * Created by nils on 2015-04-06.
 */
public class Event {
    int id;
    String created;
    String modified;
    String event_title;
    String event_description;
    String event_start_datetime;
    String event_stop_datetime;
    int timeline;

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_start_datetime() {
        return event_start_datetime;
    }

    public void setEvent_start_datetime(String event_start_datetime) {
        this.event_start_datetime = event_start_datetime;
    }

    public String getEvent_stop_datetime() {
        return event_stop_datetime;
    }

    public void setEvent_stop_datetime(String event_stop_datetime) {
        this.event_stop_datetime = event_stop_datetime;
    }

    public int getTimeline() {
        return timeline;
    }

    public void setTimeline(int timeline) {
        this.timeline = timeline;
    }
}
