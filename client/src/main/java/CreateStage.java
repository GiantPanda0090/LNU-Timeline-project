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

    //PopOver newTimeorEvent;
    PopOver ConfigPop;
    PopOver regPop;
    double top = 0;
    ArrayList<Timeline> labelList = new ArrayList<Timeline>();
    ListView<Label> list = new ListView<Label>();
    ObservableList<Label> items = FXCollections.observableArrayList();
    UserRegistration newUser;

    Insets labelInsets;


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
        final Button plus = new Button("New");
        plus.setId("newTimelineButton");
        plus.setMinWidth(100);

        // Parent for timeline view
        ScrollPane timelinePane = new ScrollPane();
        timelinePane.setId("scrollpane");
        timelinePane.setPrefSize(1000, 800);

        // Config button (just a button right now)
        final Button config = new Button("Config");
        config.setId("configButton");
        config.setMinWidth(100);

        // Listview for timelines
        ListView<String> labelList = new ListView<String>();
        ObservableList<String> listtest = FXCollections.observableArrayList("test", "funkar");
        labelList.setItems(listtest);

        // Search field
        TextField search = new TextField();
        search.setPromptText("Search your timeline");

        search.setFont(new Font("System", 12));
        search.setMaxWidth(200);
        search.setMaxHeight(30);

        final VBox timelineList = new VBox();
        timelineList.setSpacing(12);
        timelineList.setId("listBox");
        timelineList.setPadding(new Insets(30, 30, 30, 30));
        timelineList.setVgrow(plus, Priority.ALWAYS);
        timelineList.setVgrow(search, Priority.ALWAYS);

        final VBox timelineView = new VBox();
        timelineView.setPadding(new Insets(0, 40, 100, 40));

        HBox bannerView = new HBox();
        bannerView.setMinWidth(200);

        HBox rightFill = new HBox();
        rightFill.setMinWidth(40);

        HBox bottomFill = new HBox();
        bottomFill.setMinHeight(200);
        bottomFill.setMaxHeight(10);
        HBox configbox = new HBox();
        configbox.setPadding(new Insets(10, 10, 10, 10));
        configbox.setSpacing(5.0);
        configbox.getChildren().addAll(plus, config);

        // Listview for timelines


        // Get user Timelines
        sessionHandler.getTimelines();
        int size = sessionHandler.timelineArrayList.size();


        for (int i = 0; i < size; i++) {
            Label timelineLabel = new Label(sessionHandler.timelineArrayList.get(i).getTimeline_title());
            items.add(i, timelineLabel);
            timelineLabel.setId("timelineLabel");
            top += 100;
        }

        list.setItems(items);

        // Search field
        final TextField Search = new TextField();
        Search.setPromptText("Search your timeline");
        ;

        Search.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Label l = items.get(johanSearch(Search.getText()));
                list.scrollTo(l);
            }
        });


        //HBox root = new HBox();
        //root.setPadding(new Insets(10, 10, 10, 10));
        //root.setSpacing(2);
        //root.getChildren().addAll(search, list);

        search.setFont(new Font("System", 12));
        search.setMaxWidth(200);
        search.setMaxHeight(30);

        bannerView.getChildren().addAll(logo);
        timelineList.getChildren().addAll(configbox, Search, list);
        timelineView.getChildren().addAll(timelinePane);

        bpane.setTop(bannerView);
        bpane.setLeft(timelineList);
        bpane.setCenter(timelineView);

        stage.setScene(new Scene(bpane, 1280, 600));
        stage.setMinWidth(900);
        stage.setMinHeight(400);


        stage.close();
        stage.show();

                   /*
                   ...
                   ...
                   DATA FOR PLUS SIGN ICON, IE MAKING A NEW TIMELINE
                   ...
                   ...
                    */
/*
        final GridPane popPane = new GridPane();
        popPane.setMinSize(300, 400);
        popPane.getStylesheets().add(this.getClass().getResource("popover.css").toExternalForm());

        // Name Label
        final TextField name = new TextField("Name your timeline");
        name.setMaxWidth(200);
        name.setMinHeight(50);
        name.setFont(new Font("System", 20));
        name.setId("nametime");
        popPane.setRowIndex(name, 0);
        popPane.setColumnIndex(name, 0);
        popPane.setMargin(name, new Insets(-300, 50, 0, 50));

        // first label
        Label firstLbl = new Label("Start date");
        firstLbl.setId("timelineLabel");
        firstLbl.setMinWidth(100);
        popPane.setRowIndex(firstLbl, 0);
        popPane.setColumnIndex(firstLbl, 0);
        popPane.setMargin(firstLbl, new Insets(-150, 0, 0, 50));

        // first datepicker
        DatePicker firstDate = new DatePicker();
        firstDate.setMaxWidth(150);
        popPane.setRowIndex(firstDate, 0);
        popPane.setColumnIndex(firstDate, 0);
        popPane.setMargin(firstDate, new Insets(-70, 0, 0, 50));

        // second label
        Label secondLbl = new Label("End date");
        secondLbl.setMinWidth(100);
        popPane.setRowIndex(secondLbl, 0);
        popPane.setColumnIndex(secondLbl, 0);
        popPane.setMargin(secondLbl, new Insets(30, 0, 0, 50));

        // second datePicker
        DatePicker secondDate = new DatePicker();
        secondDate.setMaxWidth(150);
        popPane.setRowIndex(secondDate, 0);
        popPane.setColumnIndex(secondDate, 0);
        popPane.setMargin(secondDate, new Insets(120, 0, 0, 50));

        // test OK rectangle
        final Rectangle okRect = new Rectangle(165, 100, Color.WHITE);
        popPane.setMargin(okRect, new Insets(300, 0, 0, 0));
        okRect.setStroke(Color.valueOf("C4C4C4"));
        okRect.setId("okrect");
        Text okText = new Text("Create");
        popPane.setMargin(okText, new Insets(300, 0, 0, 50));
        okText.setId("oktext");

        // test Cancel rectangle
        final Rectangle cancelRect = new Rectangle(160, 100, Color.WHITE);
        popPane.setMargin(cancelRect, new Insets(300, 0, 0, 165));
        cancelRect.setStroke(Color.valueOf("C4C4C4"));
        cancelRect.setId("cancelrect");
        Text cancelText = new Text("Cancel");
        popPane.setMargin(cancelText, new Insets(300, 0, 0, 220));
        cancelText.setId("canceltext");


        popPane.getChildren().addAll(firstDate, secondDate, firstLbl, secondLbl, name, okRect, cancelRect, okText, cancelText);
*/


                    /*
                    ...
                    EVENTS FOR PLUS SIGN
                    ...
                     */

        /**
         * Plus creates a new PopUp to add timeline or event(?)
         * createTimelinePane have following arguments:
         *   PopOver
         *   VBox
         *   double
         *   SessionHandler
         */
        plus.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                PopOver newTimeorEvent = new PopOver();
                newTimeorEvent.setOpacity(0.99);

                //newTimeorEvent.setContentNode(popPane);
                CreateTimelinePane createTimelinePane = new CreateTimelinePane();
                newTimeorEvent.setContentNode(createTimelinePane.createTimelinePane(newTimeorEvent, timelineView, top, sessionHandler));

                newTimeorEvent.show(plus);

                /*
                okRect.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent event) {
                        newTimeorEvent.hide();
                        Label timelineLabel = new Label(name.getText().toString());
                        timelineLabel.setId("timelineLabel");
                        timelineList.getChildren().addAll(timelineLabel);
                        top += 100;
                        sessionHandler.createTimeline(name.getText().toString(), "description");
                    }
                });
                */

            }
        });

/*
        cancelRect.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                newTimeorEvent.hide();
            }
        });
*/
        return stage;
    }

    // Alternate method for searching through the listview
    public int johanSearch(String str){
        int index = 0;
        for(int i = 0; i<items.size(); i++){
            if(items.get(i).getText().equals(str)){
                index = i;
            }
        }
        return index;
    }
}
