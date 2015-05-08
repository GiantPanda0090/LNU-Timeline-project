package GUI;

import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;


/**
 * Created by Alston on 2015-05-04.
 */
public class LogFX {
    //field
    private static String classname;
    private static Logger consoleLog;
    private static LocalDateTime time = LocalDateTime.now();


    //constructor
    public LogFX(String n){classname =n; consoleLog = Logger.getLogger(classname);}

    /* Method */
    public static void loginfo(String info){

        consoleLog.info(info);
        String text = time + "INFO" + classname + info;
        CreateStage.settext(text);

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Information");
        a.setHeaderText(info);
        a.setResizable(true);
        String version = System.getProperty("java.version");
        String content = String.format(text);
        a.setContentText(content);
        a.showAndWait();

    }

    public static void logerror(Exception e){
        consoleLog.error(e);

        String text = time + "ERROR" + classname +e.getMessage();
        CreateStage.settext(text);

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error occured");
        a.setHeaderText(e.getMessage());
        a.setResizable(true);
        String version = System.getProperty("java.version");
        String content = String.format(text);
        a.setContentText(content);
        a.showAndWait();


    }
}
