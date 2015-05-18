import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

import java.time.LocalDate;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN MARKUS WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

/**
 * Created by Johan on 2015-04-23.
 * This pane will be displayed when an event is clicked
 */
public class EventInfoPane {
    public Pane eventInfoPane(SessionHandler sessionHandlerIn, ScrollPane mainTimelineScrollPaneIn, PopOver popOverIn){

       // sessionHandler from create stage
        final SessionHandler sessionHandler = sessionHandlerIn;

        final ScrollPane mainTimelineScrollPane = mainTimelineScrollPaneIn;

        // popover from create stage
        final PopOver popOver = popOverIn;

        // canvas
        Pane pane = new Pane();
        pane.setMinSize(100, 200);
        pane.getStylesheets().addAll(this.getClass().getResource("css.css").toExternalForm());


        // nodes
        final TextField titleTextField = new TextField();
        titleTextField.setText(sessionHandler.getActiveEvent().getEvent_title());
        final TextField descTextField = new TextField();
        descTextField.setText(sessionHandler.getActiveEvent().getEvent_description());


        final DatePicker firstDate = new DatePicker();
        firstDate.setValue(sessionHandler.getActiveEvent().getEvent_start_datetime().toLocalDate());
        final DatePicker secondDate = new DatePicker();
        secondDate.setValue(sessionHandler.getActiveEvent().getEvent_stop_datetime().toLocalDate());

        Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {

                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {

                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        LocalDate.from(sessionHandler.getActiveTimeline().getTimeline_start_datetime().toLocalDate()))
                                        ) {
                                    setDisable(true);
                                  //  setStyle("-fx-background-color: #ffc0cb;");
                                }
                                if (item.isAfter(
                                        LocalDate.from(sessionHandler.getActiveTimeline().getTimeline_stop_datetime().toLocalDate()))
                                        ) {
                                    setDisable(true);
                                  //  setStyle("-fx-background-color: #ffc0cb;");
                                }

                            }
                        };
                    }
                };
        firstDate.setDayCellFactory(dayCellFactory);
        secondDate.setDayCellFactory(dayCellFactory);



        // first label
        final Label firstLbl = new Label("Start date");
        firstLbl.setId("timelineLabel");

        // second label
        final Label secondLbl = new Label("End date");
        secondLbl.setId("timelineLabel");


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
        vBox.getChildren().addAll(titleTextField, descTextField,firstLbl, firstDate,secondLbl, secondDate,hBox);

        pane.getChildren().addAll(vBox);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                sessionHandler.updateEvent(titleTextField.getText(), descTextField.getText(), firstDate.getValue().atStartOfDay(), secondDate.getValue().atTime(23, 59));
                popOver.hide();
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
                sessionHandler.deleteEvent();
                popOver.hide();
                TimelineView timelineView = new TimelineView(sessionHandler, mainTimelineScrollPane);
                timelineView.drawDays();
                timelineView.addEventsDay();
            }
        });



        return pane;
    }
}
