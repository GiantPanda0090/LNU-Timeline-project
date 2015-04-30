import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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
        ToolBar tb = new ToolBar();


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


/*
                final ColorPicker colorPicker = new ColorPicker();
        tb.getItems().addAll( colorPicker);

                final SVGPath svg = new SVGPath();
                svg.setContent("M70,50 L90,50 L120,90 L150,50 L170,50"
                        + "L210,90 L180,120 L170,110 L170,200 L70,200 L70,110 L60,120 L30,90"
                        + "L70,50");
        svg.setStroke(Color.DARKGREY);
        svg.setStrokeWidth(2);
        svg.setEffect(new DropShadow());
        svg.setFill(colorPicker.getValue());


                colorPicker.setOnAction(new EventHandler() {
                    public void handle(Event t) {
                        svg.setFill(colorPicker.getValue());
                    }
                });

*/
        return pane;

    }


}
