package GUI;

import backend.SessionHandler;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

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
