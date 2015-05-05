import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;


/**
 * Created by Alston on 2015-05-04.
 */
public class LogFX {
    //field
    private static String classname;
    //private TextArea console;
    private static Logger consoleLog;
    private static LocalDateTime time = LocalDateTime.now();
    //constructor
    public LogFX(String n){classname =n; consoleLog = Logger.getLogger(classname);}

    /*method*/
    public static void loginfo(String info){
        consoleLog.info(info);
        CreateStage.settext(time+"INFO"+classname +info);
    }
    public static void logerror(Exception e){
        consoleLog.error(e);
        CreateStage.settext(time+"INFO"+classname +e.getMessage());
    }
}
