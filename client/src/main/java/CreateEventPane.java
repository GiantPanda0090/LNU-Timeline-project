import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

/**
 * Created by Johan on 2015-04-23.
 */
public class CreateEventPane {

    // first datepicker
    final DatePicker firstDate = new DatePicker();

    // second datePicker
    final DatePicker secondDate = new DatePicker();


    // first label
    final Label firstLbl = new Label("Start date");

    public Label getSecondLabel() {
        return secondLabel;
    }

    public TextField getTextFieldName() {
        return textFieldName;
    }

    public TextField getDescTextField() {
        return descTextField;
    }

    public DatePicker getSecondDate() {
        return secondDate;
    }

    public DatePicker getFirstDate() {
        return firstDate;
    }

    public Label getFirstLbl() {
        return firstLbl;
    }

    // second label
    final Label secondLabel = new Label("End date");

    final TextField textFieldName = new TextField("Name your event");
    final TextField  descTextField = new TextField("Event description...");


    public Pane createEventPane(SessionHandler sessionHandlerIn, PopOver popOverIn, final ScrollPane scrollPaneIn){
        final SessionHandler sessionHandler = sessionHandlerIn;
        final PopOver popOver = popOverIn;
        popOver.setDetachedTitle("New Event");
        final GridPane popPane = new GridPane();
        popPane.setMinSize(300, 200);
        popPane.getStylesheets().add(this.getClass().getResource("popover.css").toExternalForm());

        firstLbl.setId("timelineLabel");

        secondLabel.setId("timelineLabel");

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

                            }
                        };
                    }
                };
        firstDate.setDayCellFactory(dayCellFactory);



        Callback<DatePicker, DateCell> dayCellFactory2 =
                new Callback<DatePicker, DateCell>() {

                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {

                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

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
        secondDate.setDayCellFactory(dayCellFactory2);




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