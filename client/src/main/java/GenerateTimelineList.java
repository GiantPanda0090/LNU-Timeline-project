import backend.SessionHandler;
import backend.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Created by nils on 2015-04-21.
 */
public class GenerateTimelineList {

    public void generateTimelineList(
            SessionHandler sessionHandler,
            ObservableList<Label> observableList,
            ListView<Label> labelListView
    )
    {
        // Get timelines for the user
        sessionHandler.getTimelines();

        // Remove everything from observable list
        observableList.clear();

        // Add them back
        for (int i = 0; i < sessionHandler.timelineArrayList.size(); i++) {
            Label timelineLabel = new Label(sessionHandler.timelineArrayList.get(i).getTimeline_title());
            observableList.add(i, timelineLabel);
            timelineLabel.setId(Integer.toString(sessionHandler.timelineArrayList.get(i).getId()));

        }

        labelListView.setItems(observableList);
    }
}
