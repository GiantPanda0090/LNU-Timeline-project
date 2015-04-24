import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

/**
 * Created by Johan on 2015-04-23.
 */
public class CreateEventPane {

    public Pane createEventPane(SessionHandler sessionHandlerIn, PopOver popOverIn, final ScrollPane scrollPaneIn){
        final SessionHandler sessionHandler = sessionHandlerIn;
        final PopOver popOver = popOverIn;
        Pane pane = new Pane();
        pane.setMinSize(300, 400);
        pane.getStylesheets().addAll(this.getClass().getResource("css.css").toExternalForm());

        final TextField textFieldName = new TextField("Name your event");

        final DatePicker firstDatePicker = new DatePicker();
        final DatePicker secondDatePicker = new DatePicker();

        Label firstLabel = new Label("First date");
        Label secondLabel = new Label("End date");

        final TextArea eventDescTextArea = new TextArea("Event description...");

        Button okButton = new Button("Create");
        Button cancelButton = new Button("Cancel");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(okButton, cancelButton);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(textFieldName, firstLabel, firstDatePicker, secondLabel, secondDatePicker, eventDescTextArea, hBox);
        pane.getChildren().addAll(vBox);

        okButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                popOver.hide();
                sessionHandler.createEvent(textFieldName.getText(), eventDescTextArea.getText(), firstDatePicker.getValue().atStartOfDay(), secondDatePicker.getValue().atTime(23, 59));
                TimelineView timelineView = new TimelineView(sessionHandler, scrollPaneIn);
                timelineView.drawDays();
                timelineView.addEventsDay();
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                textFieldName.setText("Name your event");
                eventDescTextArea.setText("Event description...");
                popOver.hide();
            }
        });
        return pane;

    }


}
