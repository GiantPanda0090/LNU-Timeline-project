import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        SessionHandler sessionHandler = sessionHandlerIn;

        // popover from create stage
        final PopOver popOver = popOverIn;

        // canvas
        Pane pane = new Pane();
        pane.setMinSize(300, 4000);
        pane.getStylesheets().addAll(this.getClass().getResource("css.css").toExternalForm());

        // nodes
        Label titleLabel = new Label(sessionHandler.getActiveEvent().getEvent_title());
        Label descLabel = new Label(sessionHandler.getActiveEvent().getEvent_description());

        Label firstDateLabel = new Label(sessionHandler.getActiveEvent().getEvent_start_datetime().toString());
        Label secondDateLabel = new Label(sessionHandler.getActiveEvent().getEvent_stop_datetime().toString());

        Button editButton = new Button("Edit");
        Button OKButton = new Button("Ok");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(OKButton, editButton);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(titleLabel, descLabel, firstDateLabel, secondDateLabel,hBox);

        pane.getChildren().addAll(vBox);

        OKButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popOver.hide();
            }
        });

        return pane;
    }
}
