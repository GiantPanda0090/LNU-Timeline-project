/**
 * Created by Johan on 2015-04-21.
 */

import javafx.scene.control.DatePicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.joda.time.DateTime;

public class TimelineView {

    // fields
    java.time.LocalDate datePickerFirst;
    java.time.LocalDate datePickerSecond;

    public TimelineView(DatePicker first, DatePicker second){
        datePickerFirst = first.getValue();
        DateTime stupidDatePicker1 = new DateTime(datePickerFirst);
        org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(stupidDatePicker1);

        datePickerSecond = second.getValue();
        DateTime stupidDatePicker2 = new DateTime(datePickerSecond);
        org.joda.time.LocalDate localDate1 = new org.joda.time.LocalDate(stupidDatePicker2);
    }

    public long amountOfDays(){
        long firstDaysInt = this.datePickerFirst.getDayOfYear();
        long secondDaysInt = this.datePickerSecond.getDayOfYear();

        long numbOfDaysInt = secondDaysInt - firstDaysInt;
        return numbOfDaysInt;
    }

    public int amountOfMonths(DatePicker first, DatePicker second){
        int numbOfMonths = 0;
        return 1;
    }

    public int amountOfYears(DatePicker first, DatePicker second){
        int numbOfYears = 0;
        return 1;
    }

    //  public GridPane drawYears(){

    // }

    // public GridPane drawMonths(){

    //}

    public GridPane drawDays(){
        GridPane dayPane = new GridPane();
        dayPane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());
        dayPane.setGridLinesVisible(true);
        long columnsInt = amountOfDays();
        long rowsInt = 2;

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

        for (int i = 0; i < columnsInt; i++){
            VBox vbox = new VBox();
            vbox.setStyle("-fx-border-style: solid;"+
                    "-fx-border-color: black;"+
                    "-fx-border-width: 1;");
            vbox.setMinHeight(200);
            vbox.setMinWidth(200);
            dayPane.add(vbox, i, 0);
        }
        return dayPane;
    }
}
