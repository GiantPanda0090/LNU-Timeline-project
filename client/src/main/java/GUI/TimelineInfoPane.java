package GUI;

import backend.SessionHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

/**
 * Created by Administrator on 30/04/2015.
 */
public class TimelineInfoPane {
    public Pane TimelineInfoPane(SessionHandler sessionHandlerIn,PopOver popOverIn , final ObservableList<Label> observableListIn,
                                 final ListView<Label> labelListView, ScrollPane mainTimelineScrollPaneIn){

        // sessionHandler from create stage
        final SessionHandler sessionHandler = sessionHandlerIn;

        final ListView<Label> timelineListView =  labelListView;
        final ObservableList<Label> observableList=  observableListIn;

        // popover from create stage
        final PopOver popOver = popOverIn;

        final ScrollPane mainTimelineScrollPane = mainTimelineScrollPaneIn;

        Text nameText = new Text(("Creator: " + String.valueOf(sessionHandler.getActiveUser().getFirst_name() + " " + (String.valueOf(sessionHandler.getActiveUser().getLast_name())))));
        Text userText = new Text("Username: " + String.valueOf(sessionHandler.getActiveUser().getUsername()));
        Text emailText = new Text(("E-Mail: " + String.valueOf(sessionHandler.getActiveUser().getEmail())));

        // canvas
        Pane pane = new Pane();
        pane.setMinSize(300, 200);
        //   pane.getStylesheets().addAll(this.getClass().getResource("color.css").toExternalForm());
        pane.getStylesheets().add("popover.css");

        // nodes
        final TextField titleTextField = new TextField();
        titleTextField.setText(sessionHandler.getActiveTimeline().getTimeline_title());
        final TextField descTextField = new TextField();
        descTextField.setText(sessionHandler.getActiveTimeline().getTimeline_description());


        final DatePicker firstDate = new DatePicker();
        firstDate.setValue(sessionHandler.getActiveTimeline().getTimeline_start_datetime().toLocalDate());
        final DatePicker secondDate = new DatePicker();
        secondDate.setValue(sessionHandler.getActiveTimeline().getTimeline_stop_datetime().toLocalDate());

        // labels
        final Label firstLbl = new Label("Start date");
        firstLbl.setId("timelineLabel");

        final Label secondLabel = new Label("End date");
       secondLabel.setId("timelineLabel");




        Button saveButton = new Button("Save");
        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20, 20, 20, 20));
        hBox.setSpacing(20);
        hBox.getChildren().addAll(saveButton, deleteButton, cancelButton);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(20);
        vBox.getChildren().addAll(userText,emailText,titleTextField,descTextField,firstLbl,firstDate,secondLabel,secondDate, hBox);

        pane.getChildren().addAll(vBox);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                sessionHandler.updateTimeline(titleTextField.getText(), descTextField.getText(), firstDate.getValue().atStartOfDay(), secondDate.getValue().atTime(23, 59));
                popOver.hide();
                GenerateTimelineList list = new GenerateTimelineList();
                list.generateTimelineList(sessionHandler, observableList, timelineListView);
                TimelineView timelineView = new TimelineView(sessionHandler, mainTimelineScrollPane);
                timelineView.drawDays();
                timelineView.addEventsDay();

            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                popOver.hide();
            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                sessionHandler.deleteTimeline();
                popOver.hide();
                GenerateTimelineList list= new GenerateTimelineList();
                list.generateTimelineList(sessionHandler, observableList, timelineListView);



            }



        });



        return pane;
    }
}
