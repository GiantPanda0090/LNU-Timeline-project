package backend;

import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

/**
 * Created by Alston on 2015-04-25.
 */
public class LogBackend {
    //field
    public static String classname;
    public static Logger logging;
    public TextArea console ;


    //constructor
    public LogBackend(Logger log){logging = log;};
    public LogBackend(TextArea c,Logger log){console = c;logging = log;};

    /*method*/
    public void addlog(){

    }
}
