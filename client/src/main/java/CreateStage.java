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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;



/**
 * Created by Johan on 2015-04-20.
 */
public class CreateStage {
    SessionHandler sessionHandler;
    //TimelineView timelineView;
    VBox timeLineGridPane;
    int top1;

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
        final ScrollPane eventScrollPane = new ScrollPane();
        eventScrollPane.setId("scrollpane");
        //eventScrollPane.setFitToWidth(true);
        eventScrollPane.setPrefSize(1000, 800);
        eventScrollPane.setFitToHeight(true);
        eventScrollPane.setPrefWidth(150);
        eventScrollPane.setPrefHeight(600);
        //eventScrollPane.setFitToHeight(true);

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
        eventVBox.setPadding(new Insets(5, 0, 0, 10));
        eventVBox.setSpacing(5.0);

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

        GenerateTimelineList generateTimelineList = new GenerateTimelineList();
        generateTimelineList.generateTimelineList(sessionHandler, timelineObservableList, timelineListView);

        /**
         * UserInfoButton
         * Show logged in user.
         * When button pressed, open new window to show more info about the user.
         */
        StackPane stackImg = new StackPane();
        Image  userImg = new Image("https://cloud.githubusercontent.com/assets/11654901/7285016/c781d3d6-e941-11e4-8bf4-dac8f4ba90b6.png");
        ImageView  userInfoButton  = new ImageView();
        userInfoButton.setImage(userImg);
        userInfoButton.setFitWidth(40);
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
        searchTextField1.setPromptText("Search your timeline");

        searchTextField1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Label l = timelineObservableList.get(johanSearch(searchTextField1.getText()));
                timelineListView.scrollTo(l);
            }
        });

        timelineListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    System.out.print("clicked once");
                    //here we can add methods to when there is only one mouse clicked
                    if (mouseEvent.getClickCount() == 2) {
                        //here we can add methods to when there is a double click
                        System.out.println("Double clicked");
                    }
                }
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
        newTimeorEvent.setContentNode(createTimelinePane.createTimelinePane(newTimeorEvent, eventVBox, top, sessionHandler, timelineObservableList, timelineListView));

                        plusButton.setOnAction(new EventHandler<ActionEvent>() {

                            public void handle(ActionEvent event) {


                                if (!newTimeorEvent.isShowing()) {
                                    newTimeorEvent.show(plusButton);
                                }
                            }
                        });
        top1 = 1;
        timelineListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                sessionHandler.setTimeline_id(Integer.parseInt(timelineListView.getSelectionModel().getSelectedItem().getId()));
                TimelineView timelineView = new TimelineView(sessionHandler);
                timeLineGridPane = timelineView.drawDays();
                timelineView.addEventsDay();
                eventScrollPane.setContent(timeLineGridPane);
                top1++;
            }
        });



        configButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Not in use");
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
