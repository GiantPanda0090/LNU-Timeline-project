import backend.SessionHandler;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

/**
 * Created by nils on 2015-04-20.
 */
public class CreateTimelinePane {

    public GridPane createTimelinePane(PopOver popOver, VBox listIn, double topIn, SessionHandler sessionHandlerIn){

        final SessionHandler sessionHandler = sessionHandlerIn;


        final VBox timelineList = listIn;

        final PopOver newTimeorEvent = popOver;

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

        okRect.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                newTimeorEvent.hide();
                Label timelineLabel = new Label(name.getText().toString());
                timelineLabel.setId("timelineLabel");
                timelineList.getChildren().addAll(timelineLabel);
                //topIn += 100;
                sessionHandler.createTimeline(name.getText().toString(), "description");
            }
        });

        cancelRect.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                newTimeorEvent.hide();
            }
        });

        return popPane;
    }


}
