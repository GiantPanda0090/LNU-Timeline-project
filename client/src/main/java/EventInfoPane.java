import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

/**
 * Created by Johan on 2015-04-23.
 * This pane will be displayed when an event is clicked
 */
public class EventInfoPane {
    public Pane eventInfoPane(SessionHandler sessionHandlerIn, PopOver popOverIn){

       // sessionHandler from create stage
        final SessionHandler sessionHandler = sessionHandlerIn;

        // popover from create stage
        final PopOver popOver = popOverIn;

        // canvas
        Pane pane = new Pane();
        pane.setMinSize(300, 200);
        pane.getStylesheets().addAll(this.getClass().getResource("css.css").toExternalForm());

        // nodes
        final TextField titleTextField = new TextField();
        titleTextField.setText(sessionHandler.getActiveEvent().getEvent_title());
        final TextField descTextField = new TextField();
        descTextField.setText(sessionHandler.getActiveEvent().getEvent_title());

        final DatePicker firstDate = new DatePicker();
        firstDate.setValue(sessionHandler.getActiveEvent().getEvent_start_datetime().toLocalDate());
        final DatePicker secondDate = new DatePicker();
        secondDate.setValue(sessionHandler.getActiveEvent().getEvent_stop_datetime().toLocalDate());

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
        vBox.getChildren().addAll(titleTextField, descTextField, firstDate, secondDate,hBox);

        pane.getChildren().addAll(vBox);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sessionHandler.updateEvent(titleTextField.getText(), descTextField.getText(), firstDate.getValue().atStartOfDay(), secondDate.getValue().atTime(23,59));
                popOver.hide();
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popOver.hide();
            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sessionHandler.deleteEvent();
            }
        });



        return pane;
    }
}
