import backend.SessionHandler;
import backend.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

import javax.management.StringValueExp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

/**
 * Created by Johan on 2015-04-23.
 */
public class CreateEventPane {

    public Pane createEventPane(SessionHandler sessionHandlerIn, PopOver popOverIn, final ScrollPane scrollPaneIn){
        final SessionHandler sessionHandler = sessionHandlerIn;
        final PopOver popOver = popOverIn;
        popOver.setDetachedTitle("New Event");
        final GridPane popPane = new GridPane();
        popPane.setMinSize(300, 200);
        popPane.getStylesheets().add(this.getClass().getResource("popover.css").toExternalForm());

        final TextField textFieldName = new TextField("Name your event");
        final TextField  descTextField = new TextField("Event description...");

        // first label
        final Label firstLbl = new Label("Start date");
        firstLbl.setId("timelineLabel");

        // second label
        final Label secondLabel = new Label("End date");
        secondLabel.setId("timelineLabel");

        // first datepicker
       final DatePicker firstDate = new DatePicker();

        // second datePicker
        final DatePicker secondDate = new DatePicker();

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
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                if (item.isAfter(
                                        LocalDate.from(sessionHandler.getActiveTimeline().getTimeline_stop_datetime().toLocalDate()))
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }

                            }
                        };
                    }
                };
        firstDate.setDayCellFactory(dayCellFactory);
        secondDate.setDayCellFactory(dayCellFactory);




        Button okButton = new Button("Create");
        Button cancelButton = new Button("Cancel");


        HBox hBox = new HBox();

        hBox.setSpacing(20);
        hBox.getChildren().addAll(okButton,cancelButton);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(20);
        vBox.getChildren().addAll(textFieldName, descTextField,firstLbl, firstDate,secondLabel, secondDate, hBox);

        popPane.getChildren().addAll(vBox);

        okButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                popOver.hide();
                sessionHandler.createEvent(textFieldName.getText(), descTextField.getText(), firstDate.getValue().atStartOfDay().plusDays(0), secondDate.getValue().atTime(23, 59).plusDays(0));
                TimelineView timelineView = new TimelineView(sessionHandler, scrollPaneIn);
                timelineView.drawDays();
                timelineView.addEventsDay();
                textFieldName.setText("Name your event");
                descTextField.setText("Event description...");
                firstDate.setValue(null);
                secondDate.setValue(null);

            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                textFieldName.setText("Name your event");
                descTextField.setText("Event description...");
                firstDate.setValue(null);
                secondDate.setValue(null);
                popOver.hide();
            }
        });





        return popPane;

    }


}
