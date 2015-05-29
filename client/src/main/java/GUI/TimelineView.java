package GUI;/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.controlsfx.control.PopOver;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TimelineView {

    /**
     * Fields
     */

    SessionHandler sessionHandler;
    GridPane dayPane;
    GridPane yearPane;
    GridPane monthPane;
    ArrayList<Button> nodeEventsArrayList = new ArrayList<Button>();
    ScrollPane scrollPaneWithAllGrids;
    /**
     * Constructor
     */

    public TimelineView(SessionHandler sessionHandlerIn, ScrollPane scrollPaneIn){
        sessionHandler = sessionHandlerIn;
        sessionHandler.getEvents();
        scrollPaneWithAllGrids = scrollPaneIn;
    }

    /**
     * Methods
     */

    public int amountOfDays(){
        LocalDateTime first = sessionHandler.getActiveTimeline().getTimeline_start_datetime();
        LocalDateTime second = sessionHandler.getActiveTimeline().getTimeline_stop_datetime();
        int startDateLong = first.getDayOfYear();
        int endDateLong = second.getDayOfYear();

        int quickFixAccountForYears = 0;
        while(first.getYear() < second.getYear()){
            quickFixAccountForYears += 365;
            first = first.plusYears(1);
        }

        return Math.abs(endDateLong-startDateLong + quickFixAccountForYears);

    }

    public int amountOfMonths(){
        LocalDateTime first = sessionHandler.getActiveTimeline().getTimeline_start_datetime();
        LocalDateTime second = sessionHandler.getActiveTimeline().getTimeline_stop_datetime();
        int startMonthInt = first.getMonthValue();
        int endMonthInt = second.getMonthValue();

        int quickFixAccountForYears = 0;
        while(first.getYear() < second.getYear()){
            quickFixAccountForYears += 12;
            first = first.plusYears(1);
        }
        return Math.abs(endMonthInt - startMonthInt + quickFixAccountForYears);
    }

    public int amountOfYears(){
        int startYearLong = sessionHandler.getActiveTimeline().getTimeline_start_datetime().getYear();
        int endYearLong = sessionHandler.getActiveTimeline().getTimeline_stop_datetime().getYear();
        return endYearLong - startYearLong;
    }

    public void drawYears(){
        VBox vboxMainBoxTimeline = new VBox();
        vboxMainBoxTimeline.getStylesheets().add("TimelineCSS.css");
        GridPane yearViewPane = new GridPane();
        yearViewPane.setId("dayViewPane");
        sessionHandler.getEvents();
        yearPane = new GridPane();
        yearPane.setId("dayPane");
        yearPane.getStylesheets().add("css.css");
        long columnsInt = amountOfYears();
        if(columnsInt == 0)
            columnsInt++;
        long rowsInt = 10;

        for (int i = 0; i < columnsInt; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(50);
            columnConstraints.setPercentWidth(100.0);
            yearPane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rowsInt; i++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0);
            yearPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < columnsInt; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(50);
            columnConstraints.setPercentWidth(100.0);
            yearViewPane.getColumnConstraints().add(columnConstraints);
        }
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        rowConstraints.setMinHeight(100);
        yearViewPane.getRowConstraints().add(rowConstraints);

        LocalDateTime years = sessionHandler.getActiveTimeline().getTimeline_start_datetime();

        for (int i = 0; i < columnsInt; i++){
            years.plusYears(i);
            Label dayLabel = new Label(((Integer) years.toLocalDate().plusYears(i).getYear()).toString());
            dayLabel.setId("dayLabel");
            dayLabel.setPadding(new Insets(20, 20, 20, 20));
            yearViewPane.add(dayLabel, i, 0);
        }

        vboxMainBoxTimeline.getChildren().addAll(yearViewPane, yearPane);
        scrollPaneWithAllGrids.setContent(vboxMainBoxTimeline);
    }

    public void drawMonths(){
        VBox vboxMainBoxTimeline = new VBox();
        vboxMainBoxTimeline.getStylesheets().add("TimelineCSS.css");
        GridPane monthViewPane = new GridPane();
        monthPane = new GridPane();
        monthViewPane.setId("dayViewPane");
        sessionHandler.getEvents();
        monthPane.setId("dayPane");
        monthPane.getStylesheets().add("css.css");
        int columnsInt = amountOfMonths();
        if(columnsInt == 0)
            columnsInt++;
        long rowsInt = 10;

        for (int i = 0; i < columnsInt; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(50);
            columnConstraints.setPercentWidth(100.0);
            monthPane.getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < rowsInt; i++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0);
            monthPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < columnsInt; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMinWidth(50);
            columnConstraints.setPercentWidth(100.0);
            monthViewPane.getColumnConstraints().add(columnConstraints);
        }
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        rowConstraints.setMinHeight(100);
        monthViewPane.getRowConstraints().add(rowConstraints);

        LocalDateTime months = sessionHandler.getActiveTimeline().getTimeline_start_datetime();

        for (int i = 0; i < columnsInt; i++){
            months.plusMonths(i);
            System.out.println(months.getMonth().toString());
            Label dayLabel = new Label((months.toLocalDate().plusMonths(i).getMonth().toString()));
            dayLabel.setId("dayLabel");
            dayLabel.setPadding(new Insets(20, 20, 20, 20));
            monthViewPane.add(dayLabel, i, 0);
        }

        vboxMainBoxTimeline.getChildren().addAll(monthViewPane, monthPane);
        scrollPaneWithAllGrids.setContent(vboxMainBoxTimeline);
    }

    /**
     * Returns a VBox containing two gridpanes.
     * Length of gridpanes are decided in the for loops by adding column and rowconstraints.
     */

    public void drawDays(){
        VBox vboxMainBoxTimeline = new VBox();
        vboxMainBoxTimeline.getStylesheets().add("TimelineCSS.css");
        GridPane dayViewPane = new GridPane();
        dayViewPane.setId("dayViewPane");
        sessionHandler.getEvents();
        dayPane = new GridPane();
        dayPane.setId("dayPane");
        dayPane.getStylesheets().add("css.css");
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

        vboxMainBoxTimeline.getChildren().addAll(dayViewPane, dayPane);
        scrollPaneWithAllGrids.setContent(vboxMainBoxTimeline);
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
                    sessionHandler.setEvent_id(Integer.parseInt(rect.getId()));
                    PopOver popOver = new PopOver();
                    EventInfoPane eventInfoPane = new EventInfoPane();
                    Pane pane = eventInfoPane.eventInfoPane(sessionHandler, scrollPaneWithAllGrids, popOver);
                    popOver.setContentNode(pane);
                    popOver.show(rect);
                }
            });

            nodeEventsArrayList.add(rect);

            int eventColumnSpanSize = event.getEvent_stop_datetime().getDayOfYear() - event.getEvent_start_datetime().getDayOfYear() + 1;
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

    public void addEventsYear(){
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
                    sessionHandler.setEvent_id(Integer.parseInt(rect.getId()));
                    PopOver popOver = new PopOver();
                    EventInfoPane eventInfoPane = new EventInfoPane();
                    Pane pane = eventInfoPane.eventInfoPane(sessionHandler, scrollPaneWithAllGrids, popOver);
                    popOver.setContentNode(pane);
                    popOver.show(rect);
                }
            });

            nodeEventsArrayList.add(rect);

            int eventColumnSpanSize = event.getEvent_stop_datetime().getYear() - event.getEvent_start_datetime().getYear() + 1;
            System.out.println("Event Column Size: "+eventColumnSpanSize);
            if (eventColumnSpanSize == 0){
                eventColumnSpanSize = 1;
            }
            yearPane.setColumnSpan(rect, eventColumnSpanSize);
            yearPane.setFillWidth(rect, true);
            yearPane.add(rect, event.getEvent_start_datetime().getYear() - sessionHandler.getActiveTimeline().getTimeline_start_datetime().getYear(), rowInt);

            // Messy if statements, better solution would be appriciated.

            for(int j = i + 1; j < sessionHandler.eventArrayList.size(); j++){
                if(event.getEvent_start_datetime().getYear() == sessionHandler.eventArrayList.get(j).getEvent_start_datetime().getYear()){
                    rowInt++;
                }
                else if(event.getEvent_stop_datetime().getYear() > sessionHandler.eventArrayList.get(j).getEvent_start_datetime().getYear() && event.getEvent_start_datetime().getYear() < sessionHandler.eventArrayList.get(j).getEvent_stop_datetime().getYear()){
                    rowInt++;
                }
                else if(event.getEvent_start_datetime().getYear() == sessionHandler.eventArrayList.get(j).getEvent_stop_datetime().getYear()){
                    rowInt++;
                }
            }
        }
    }

    public void addEventsMonth(){
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
                    sessionHandler.setEvent_id(Integer.parseInt(rect.getId()));
                    PopOver popOver = new PopOver();
                    EventInfoPane eventInfoPane = new EventInfoPane();
                    Pane pane = eventInfoPane.eventInfoPane(sessionHandler, scrollPaneWithAllGrids, popOver);
                    popOver.setContentNode(pane);
                    popOver.show(rect);
                }
            });

            nodeEventsArrayList.add(rect);

            int eventColumnSpanSize = event.getEvent_stop_datetime().getMonthValue() - event.getEvent_start_datetime().getMonthValue() + 1;
            if (eventColumnSpanSize == 0){
                eventColumnSpanSize = 1;
            }
            monthPane.setColumnSpan(rect, eventColumnSpanSize);
            monthPane.setFillWidth(rect, true);
            monthPane.add(rect, event.getEvent_start_datetime().getMonthValue() - sessionHandler.getActiveTimeline().getTimeline_start_datetime().getMonthValue(), rowInt);

            // Messy if statements, better solution would be appriciated.

            for(int j = i + 1; j < sessionHandler.eventArrayList.size(); j++){
                if(event.getEvent_start_datetime().getMonthValue() == sessionHandler.eventArrayList.get(j).getEvent_start_datetime().getMonthValue()){
                    rowInt++;
                }
                else if(event.getEvent_stop_datetime().getMonthValue() > sessionHandler.eventArrayList.get(j).getEvent_start_datetime().getMonthValue() && event.getEvent_start_datetime().getMonthValue() < sessionHandler.eventArrayList.get(j).getEvent_stop_datetime().getMonthValue()){
                    rowInt++;
                }
                else if(event.getEvent_start_datetime().getMonthValue() == sessionHandler.eventArrayList.get(j).getEvent_stop_datetime().getMonthValue()){
                    rowInt++;
                }
            }
        }
    }
}
