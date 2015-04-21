import backend.SessionHandler;
import backend.Timeline;
import backend.UserRegistration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;

/**
 * Created by Johan on 2015-04-20.
 */
public class CreateStage {
    SessionHandler sessionHandler;
    public CreateStage(SessionHandler session){
        sessionHandler = session;
    };

    double top = 0;
    //ArrayList<Timeline> labelList = new ArrayList<Timeline>();
    ListView<Label> timelineListView = new ListView<Label>();
    ObservableList<Label> timelineObservableList = FXCollections.observableArrayList();

    public Stage start() throws Exception {
        Stage stage = new Stage();
        final BorderPane bpane = new BorderPane();
        bpane.setPadding(new Insets(40, 40, 40, 40));
        stage.setMinWidth(800);
        stage.setMinHeight(100);

        bpane.setId("gpanemain");
        bpane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());

        final Label logo = new Label("MiTime");
        logo.setId("labelLogo");

        // Label
        Label label = new Label("MiTime");
        label.setId("createlabelLogo");

        // Plus icon (as of right now, just a button)
        final Button plusButton = new Button("New");
        plusButton.setId("newTimelineButton");
        plusButton.setMinWidth(100);

        /**
         *  eventScrollPane, this is the area where all events for a timeline is displayed
         */
        ScrollPane eventScrollPane = new ScrollPane();
        eventScrollPane.setId("scrollpane");
        eventScrollPane.setPrefSize(1000, 800);

        // Config button (just a button right now)
        final Button configButton = new Button("Config");
        configButton.setId("configButton");
        configButton.setMinWidth(100);

        // Search field
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search your timeline");

        searchTextField.setFont(new Font("System", 12));
        searchTextField.setMaxWidth(200);
        searchTextField.setMaxHeight(30);

        final VBox timelineVBox = new VBox();
        timelineVBox.setSpacing(12);
        timelineVBox.setId("listBox");
        timelineVBox.setPadding(new Insets(30, 30, 30, 30));
        timelineVBox.setVgrow(plusButton, Priority.ALWAYS);
        timelineVBox.setVgrow(searchTextField, Priority.ALWAYS);

        final VBox eventVBox = new VBox();
        eventVBox.setPadding(new Insets(0, 40, 100, 40));

        HBox bannerHBox = new HBox();
        bannerHBox.setMinWidth(200);

        HBox rightFill = new HBox();
        rightFill.setMinWidth(40);

        HBox bottomFill = new HBox();
        bottomFill.setMinHeight(200);
        bottomFill.setMaxHeight(10);
        HBox configbox = new HBox();
        configbox.setPadding(new Insets(10, 10, 10, 10));
        configbox.setSpacing(5.0);
        configbox.getChildren().addAll(plusButton, configButton);


        // Get user Timelines
        sessionHandler.getTimelines();
        int size = sessionHandler.timelineArrayList.size();


        for (int i = 0; i < size; i++) {
            Label timelineLabel = new Label(sessionHandler.timelineArrayList.get(i).getTimeline_title());
            timelineObservableList.add(i, timelineLabel);
            timelineLabel.setId("timelineLabel");
            top += 100;
        }

        timelineListView.setItems(timelineObservableList);

        // Search field
        final TextField searchTextField1 = new TextField();
        searchTextField1.setPromptText("Search your timeline");

        searchTextField1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Label l = timelineObservableList.get(johanSearch(searchTextField1.getText()));
                timelineListView.scrollTo(l);
            }
        });


        searchTextField.setFont(new Font("System", 12));
        searchTextField.setMaxWidth(200);
        searchTextField.setMaxHeight(30);

        bannerHBox.getChildren().addAll(logo);
        timelineVBox.getChildren().addAll(configbox, searchTextField1, timelineListView);
        eventVBox.getChildren().addAll(eventScrollPane);

        bpane.setTop(bannerHBox);
        bpane.setLeft(timelineVBox);
        bpane.setCenter(eventVBox);

        stage.setScene(new Scene(bpane, 1280, 600));
        stage.setMinWidth(900);
        stage.setMinHeight(400);



        /**
         * Plus creates a new PopUp to add timeline or event(?)
         * createTimelinePane have following arguments:
         *   PopOver
         *   VBox
         *   double
         *   SessionHandler
         */

        // TODO: Are we using a observable list for timelines? or how do we manage to update new timelines from the API?
        /*
         *
         */
        plusButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                PopOver newTimeorEvent = new PopOver();
                newTimeorEvent.setOpacity(0.99);

                CreateTimelinePane createTimelinePane = new CreateTimelinePane();

                newTimeorEvent.setContentNode(
                        createTimelinePane.createTimelinePane(
                                newTimeorEvent,
                                eventVBox,
                                top,
                                sessionHandler
                        )
                );
                top += 100;

                newTimeorEvent.show(plusButton);

            }
        });

        return stage;
    }

    // Alternate method for searching through the listview
    public int johanSearch(String str){
        int index = 0;
        for(int i = 0; i<timelineObservableList.size(); i++){
            if(timelineObservableList.get(i).getText().equals(str)){
                index = i;
            }
        }
        return index;
    }

    private void reloadTimelines(ObservableList<String> observableList, SessionHandler sessionHandler){
        observableList.remove(0, observableList.size());
    }
}
