import backend.SessionHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/



/**
 * Created by Johan on 2015-04-20.
 */
public class CreateStage {
    SessionHandler sessionHandler;
    //TimelineView timelineView;
    int top1 ;

    public CreateStage(SessionHandler session){
        sessionHandler = session;
    };

    double top = 0;
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

        final Label logo = new Label("  MiTime");
        logo.setId("createlabelLogo");

        // Label
        Label label = new Label("MiTime");
        label.setId("createlabelLogo");

        // Plus icon (as of right now, just a button)
        final Button plusButton = new Button("New Timeline");
        plusButton.setId("newTimelineButton");
        plusButton.setMinWidth(100);

        final Button newEventButton = new Button("New Event");
        newEventButton.setId("eventButton");
        newEventButton.setMinWidth(100);

        /**
         *  eventScrollPane, this is the area where all events for a timeline is displayed
         */
        final ScrollPane eventScrollPane = new ScrollPane();
        eventScrollPane.setId("scrollpane");
        //eventScrollPane.setFitToWidth(true);

        eventScrollPane.setPrefSize(1000, 800);
        eventScrollPane.setFitToHeight(true);
        eventScrollPane.setPrefWidth(150);
        eventScrollPane.setPrefHeight(600);
        //eventScrollPane.setFitToHeight(true);



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
        eventVBox.setPadding(new Insets(5, 0, 0, 10));
        eventVBox.setSpacing(5.0);
        eventVBox.setPrefHeight(100);

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
        configbox.getChildren().addAll(plusButton, newEventButton);

        GenerateTimelineList generateTimelineList = new GenerateTimelineList();
        generateTimelineList.generateTimelineList(sessionHandler, timelineObservableList, timelineListView);

        /**
         * UserInfoButton
         * Show logged in user.
         * When button pressed, open new window to show more info about the user.
         */
        StackPane stackImg = new StackPane();
        //Image  userImg = new Image("https://cloud.githubusercontent.com/assets/11654901/7285016/c781d3d6-e941-11e4-8bf4-dac8f4ba90b6.png");
        final Image  userImg = new Image("profile.png");
        ImageView  userInfoButton  = new ImageView();
        userInfoButton.setImage(userImg);
        userInfoButton.setFitWidth(60);
        userInfoButton.setPreserveRatio(true);
        userInfoButton.setSmooth(true);
        userInfoButton.setCache(true);
        stackImg.getChildren().addAll(userInfoButton);
        stackImg.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(stackImg, Priority.ALWAYS);

        userInfoButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                UserInfoStage userInfoStage = new UserInfoStage();
                userInfoStage.userInfoStage(sessionHandler).show();
        }
    });
                /*
        Button userInfoButton = new Button(sessionHandler.getUser().getUsername());
        userInfoButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                UserInfoStage userInfoStage = new UserInfoStage();
                userInfoStage.userInfoStage(sessionHandler).show();
            }
        });
*/


        // Search field
        final TextField searchTextField1 = new TextField();
        searchTextField1.setPromptText("  Search your timeline");
        searchTextField1.setId("search");
        searchTextField1.textProperty().addListener(
                new ChangeListener() {
                    public void changed(ObservableValue observable,
                                        Object oldVal, Object newVal) {
                        handleSearchByKey2((String)oldVal, (String)newVal);
                    }
                });


        searchTextField.setFont(new Font("System", 12));
        searchTextField.setMaxWidth(200);
        searchTextField.setMaxHeight(30);

        bannerHBox.getChildren().addAll(logo,stackImg);
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
        final PopOver newTimeorEvent = new PopOver();
        newTimeorEvent.setOpacity(0.99);
        CreateTimelinePane createTimelinePane = new CreateTimelinePane();
        newTimeorEvent.setContentNode(createTimelinePane.createTimelinePane(newTimeorEvent, sessionHandler, timelineObservableList, timelineListView));

        plusButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                if (!newTimeorEvent.isShowing()) {
                    newTimeorEvent.show(plusButton);
                }
            }
        });


           timelineListView.setOnMousePressed(new EventHandler<MouseEvent>() {

               public void handle(MouseEvent click) {
                   if (click.getClickCount() == 1) {
                       sessionHandler.setTimeline_id(Integer.parseInt(timelineListView.getSelectionModel().getSelectedItem().getId()));
                       TimelineView timelineView = new TimelineView(sessionHandler, eventScrollPane);
                       timelineView.drawDays();
                       timelineView.addEventsDay();


                   }
                   else if(click.getClickCount() == 2) {
                       sessionHandler.setTimeline_id(Integer.parseInt(timelineListView.getSelectionModel().getSelectedItem().getId()));
                       PopOver popOver = new PopOver();
                       TimelineInfoPane TimelineInfoPane = new TimelineInfoPane();
                       Pane pane = TimelineInfoPane.TimelineInfoPane(sessionHandler, popOver, timelineObservableList, timelineListView, eventScrollPane);
                       popOver.setContentNode(pane);
                       popOver.show(timelineListView);

                   }
           }
       });


        final PopOver newEvent = new PopOver();
        CreateEventPane createEventPane = new CreateEventPane();
        newEvent.setContentNode(createEventPane.createEventPane(sessionHandler, newEvent, eventScrollPane));
        newEventButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if (!newEvent.isShowing()) {
                    newEvent.show(newEventButton);
                }

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

    public void handleSearchByKey2(String oldVal, String newVal) {
        // If the number of characters in the text box is less than last time
        // it must be because the user pressed delete
        if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
            // Restore the lists original set of entries
            // and start from the beginning
            timelineListView.setItems( timelineObservableList );
        }

        // Break out all of the parts of the search text
        // by splitting on white space
        String[] parts = newVal.toUpperCase().split(" ");


        // Filter out the entries that don't contain the entered text
        ObservableList<Label> subentries = FXCollections.observableArrayList();
        for ( Label entry: timelineListView.getItems() ) {
            boolean match = true;

            String entrytest =  entry.toString();

            for ( String part: parts ) {
                // The entry needs to contain all portions of the
                // search string *but* in any order
                if ( ! ((String) entrytest).toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }

            if ( match ) {

                subentries.add(entry);
            }
        }
        timelineListView.setItems(subentries);
    }
    private void reloadTimelines(ObservableList<String> observableList, SessionHandler sessionHandler){
        observableList.remove(0, observableList.size());
    }
}
