/**
 * Created by Johan on 2015-04-21.
 */

/**
 * This class holds the representation of a timeline in a graphical sense.
 * The methods that count the amounts of days, months and years are to be used in the different representations
 * of the timeline.
 * The draw methods will draw out a VBox contaning to gridpanes. One for handling dates and one for events.
 * The add events methods simply add the events starting and ending points to certain columns in the gridpane, the
 * events are then stretching (or spanning) through the columns depending on how long they are.
 */

import backend.Event;
import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TimelineView {

    /**
     * Fields
     */

    SessionHandler sessionHandler;
    GridPane dayPane;
    StackPane stackPane;
    ArrayList<Button> nodeEventsArrayList = new ArrayList<Button>();

    /**
     * Constructor
     */

    public TimelineView(SessionHandler sessionHandlerIn){
        sessionHandler = sessionHandlerIn;
        sessionHandler.getEvents();
    }

    /**
     * Methods
     */

    public int amountOfDays(){
        int startDateLong = sessionHandler.getActiveTimeline().getTimeline_start_datetime().getDayOfYear();
        int endDateLong = sessionHandler.getActiveTimeline().getTimeline_stop_datetime().getDayOfYear();
        return endDateLong - startDateLong;

    }

    public int amountOfMonths(DatePicker first, DatePicker second){
        int startMonthInt = sessionHandler.timelineArrayList.get(sessionHandler.getTimeline_id()).getTimeline_start_datetime().getMonthValue();
        int endMonthInt = sessionHandler.timelineArrayList.get(sessionHandler.getTimeline_id()).getTimeline_stop_datetime().getMonthValue();
        return endMonthInt - startMonthInt;
    }

    public int amountOfYears(DatePicker first, DatePicker second){
        int startYearInt = sessionHandler.timelineArrayList.get(sessionHandler.getTimeline_id()).getTimeline_start_datetime().getYear();
        int endYearInt = sessionHandler.timelineArrayList.get(sessionHandler.getTimeline_id()).getTimeline_stop_datetime().getYear();
        return  startYearInt - endYearInt;
    }

    //  public GridPane drawYears(){

    // }

    // public GridPane drawMonths() hej{

    //}

    /**
     * Returns a VBox containing two gridpanes.
     * Length of gridpanes are decided in the for loops by adding column and rowconstraints.
     */

    public VBox drawDays(){
        VBox vbox = new VBox();
        vbox.getStylesheets().add(this.getClass().getResource("TimelineCSS.css").toExternalForm());
        GridPane dayViewPane = new GridPane();
        dayViewPane.setId("dayViewPane");
        sessionHandler.getEvents();
        dayPane = new GridPane();
        dayPane.setId("dayPane");
        dayPane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());
        long columnsInt = amountOfDays();
        if(columnsInt == 0)
            columnsInt++;
        long rowsInt = 10;

        for (int i = 0; i < columnsInt; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(50);
            columnConstraints.setPercentWidth(100.0);
            dayPane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rowsInt; i++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0);
            dayPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < columnsInt; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(50);
            columnConstraints.setPercentWidth(100.0);
            dayViewPane.getColumnConstraints().add(columnConstraints);
        }
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        rowConstraints.setMinHeight(100);
        dayViewPane.getRowConstraints().add(rowConstraints);

        LocalDateTime dates = sessionHandler.getActiveTimeline().getTimeline_start_datetime();


        for (int i = 0; i < columnsInt; i++){
            dates.plusDays(i);
            Label dayLabel = new Label(dates.toLocalDate().plusDays(i).toString());
            dayLabel.setId("dayLabel");
            dayLabel.setPadding(new Insets(20, 20, 20, 20));
            dayViewPane.add(dayLabel, i, 0);
        }

        vbox.getChildren().addAll(dayViewPane, dayPane);
        return vbox;
    }

    public void addEventsDay(){
        int rowInt = 1;
        for(int i = 0; i < sessionHandler.eventArrayList.size(); i++){
            Event event = sessionHandler.eventArrayList.get(i);

            final Button rect = new Button(event.getEvent_title());
            rect.setId(Integer.toString(event.getId())); // sets the event id to the buttons id !
            rect.setPadding(new Insets(30, 0, 30, 0));
            rect.setMaxWidth(Double.POSITIVE_INFINITY);

            rect.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println(rect.getId());
                }
            });

            nodeEventsArrayList.add(rect);

            int eventColumnSpanSize = event.getEvent_stop_datetime().getDayOfYear() - event.getEvent_start_datetime().getDayOfYear();
            if (eventColumnSpanSize == 0){
                eventColumnSpanSize = 1;
            }
            dayPane.setColumnSpan(rect, eventColumnSpanSize);
            dayPane.setFillWidth(rect, true);
            dayPane.add(rect, event.getEvent_start_datetime().getDayOfYear() - sessionHandler.getActiveTimeline().getTimeline_start_datetime().getDayOfYear(), rowInt);

            // Messy if statements, better solution would be appriciated.

            for(int j = i + 1; j < sessionHandler.eventArrayList.size(); j++){
                if(event.getEvent_start_datetime().getDayOfYear() == sessionHandler.eventArrayList.get(j).getEvent_start_datetime().getDayOfYear()){
                    rowInt++;
                }
                else if(event.getEvent_stop_datetime().getDayOfYear() > sessionHandler.eventArrayList.get(j).getEvent_start_datetime().getDayOfYear() && event.getEvent_start_datetime().getDayOfYear() < sessionHandler.eventArrayList.get(j).getEvent_stop_datetime().getDayOfYear()){
                    rowInt++;
                }
                else if(event.getEvent_start_datetime().getDayOfYear() == sessionHandler.eventArrayList.get(j).getEvent_stop_datetime().getDayOfYear()){
                    rowInt++;
                }
            }
        }
    }
}
