import backend.SessionHandler;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN MARKUS WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

/**
 * Created by nils on 2015-04-20.
 */
public class CreateTimelinePane {

    public GridPane createTimelinePane(
            PopOver popOver,
          final  SessionHandler sessionHandlerIn,
            ObservableList<Label> timelineObservableListIn,
            ListView<Label> timelineListViewIn
            ){

        final ObservableList<Label> timelineObservableList = timelineObservableListIn;
        final ListView<Label> timelineListView = timelineListViewIn;

        final SessionHandler sessionHandler = sessionHandlerIn;

        final PopOver newTimeorEvent = popOver;

        final GridPane popPane = new GridPane();
        popPane.setMinSize(300, 200);
        popPane.getStylesheets().add(this.getClass().getResource("popover.css").toExternalForm());

        // Name Label
        final TextField name = new TextField("Name your timeline");
        final TextField  descTextField = new TextField("Timeline description...");

        name.setFont(new Font("System", 20));
        name.setId("nametime");



        // first datepicker
        final DatePicker firstDate = new DatePicker();
        // second datePicker
        final DatePicker secondDate = new DatePicker();

        // first label
        final Label firstLbl = new Label("Start date");
        firstLbl.setId("timelineLabel");

        // second label
        final Label secondLbl = new Label("End date");
        secondLbl.setId("timelineLabel");


        Button okButton = new Button("Create");
        Button cancelButton = new Button("Cancel");


        HBox hBox = new HBox();

        hBox.setSpacing(20);
        hBox.getChildren().addAll(okButton,cancelButton);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(20);
        vBox.getChildren().addAll(name, descTextField,firstLbl, firstDate,secondLbl, secondDate,hBox);

        popPane.getChildren().addAll(vBox);

        /*
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
*/


        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                newTimeorEvent.hide();
                Label timelineLabel = new Label(name.getText().toString());
                timelineLabel.setId("timelineLabel");
                //timelineList.getChildren().addAll(timelineLabel);
                sessionHandler.createTimeline(name.getText().toString(), descTextField.getText(), firstDate.getValue().atStartOfDay(), secondDate.getValue().atTime(23, 59));
              //  sessionHandler.createTimeline(name.getText().toString(), "description", firstDate.getValue().atStartOfDay(), secondDate.getValue().atTime(23, 59));
                GenerateTimelineList generateTimelineList = new GenerateTimelineList();
                generateTimelineList.generateTimelineList(sessionHandler, timelineObservableList, timelineListView);
                name.setText("Name your Timeline");
                firstDate.setValue(null);
                secondDate.setValue(null);

            }
        });

        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                name.setText("Name your timeline");
                descTextField.setText("timeline description...");
                newTimeorEvent.hide();
            }
        });

        return popPane;
    }


}
