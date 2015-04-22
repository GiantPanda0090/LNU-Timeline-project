/**
 * Created by Johan on 2015-04-21.
 */

import backend.Event;
import backend.SessionHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

public class TimelineView {

    SessionHandler sessionHandler;
    GridPane dayPane;
    StackPane stackPane;

    public TimelineView(SessionHandler sessionHandlerIn){
        sessionHandler = sessionHandlerIn;
        sessionHandler.setTimeline_id(9);
    }

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

    public GridPane drawDays(){
        sessionHandler.getEvents();
        dayPane = new GridPane();
        dayPane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());
        dayPane.setGridLinesVisible(true);
        long columnsInt = amountOfDays();
        System.out.println("Amount of days "+amountOfDays());
        long rowsInt = 10;

        for(int i = 0; i < columnsInt; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0);
            dayPane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rowsInt; i++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0);
            dayPane.getRowConstraints().add(rowConstraints);
        }
        return dayPane;
    }

    public void addEventsDay(){
        System.out.println("Amount of events "+sessionHandler.eventArrayList.size());
        int rowInt = 1;
        for(int i = 0; i < sessionHandler.eventArrayList.size(); i++){
            Event event = sessionHandler.eventArrayList.get(i);
            Button rect = new Button(event.getEvent_title());
            for(int j = i+1; j > 0; j--){
                if(event.getEvent_start_datetime().getDayOfYear() == sessionHandler.eventArrayList.get(i).getEvent_start_datetime().getDayOfYear()){
                    rowInt++;
                }
            }
          dayPane.add(rect, 1, rowInt);
        }
    }
}
