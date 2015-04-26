import backend.API;
import backend.APIConfigReader;
import backend.SessionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
 * Created by nils on 2015-04-16.
 */
public class ConfigStage {

    /*
     * sessionHandler is passed to this function.
     * The reason for that is that when saved is pressed
     * sessionHandler needs to reload API settings from config file.
     */

    public  GridPane config(final SessionHandler sessionHandler,PopOver popOver){

        // New API config reader, use to create new instance of API and to write to config file
        APIConfigReader apiConfigReader = new APIConfigReader();

        // Used to read from config file
        API  api = apiConfigReader.read();

        // FX stage
        final PopOver stage = popOver;

        // FX root
        Group root = new Group();

        // FX scene
        Scene scene = new Scene(root);

        // Title
        //popover do not have a title.
        //stage.setTitle("Config");

        // Gridpane and settings for gridpane
        GridPane gridPane = new GridPane();
        gridPane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());
        gridPane.setPrefSize(350, 250);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        final Text headerText = new Text("Config API settings");
        final Text hostText = new Text("Host:");
        final Text portText = new Text("Port:");

        final TextField hostTextField = new TextField(api.getHost());
        hostTextField.setPrefSize(10, 10);

        final TextField portTextField = new TextField(api.getPort());
        final Button saveButton = new Button("Save");

        saveButton.setPrefSize(100,10);

        final Button cancelButton = new Button("Cancel");

        cancelButton.setPrefSize(100,10);
        gridPane.add(headerText, 1, 0);
        gridPane.add(hostTextField, 1, 1);
        gridPane.add(hostText, 0, 1);
        gridPane.add(portTextField, 1, 2);
        gridPane.add(portText, 0, 2);

        /*
        Horizontal view for buttons.
         */
        HBox hbox = new HBox();
        hbox.setSpacing(25);
        hbox.getChildren().addAll(saveButton, cancelButton);
        gridPane.add(hbox, 1, 4);


        /*
         * Close the config stage.
         */
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                stage.hide();
            }
        });


        /*
         * Save button
         * Take the text from hostTextField and portTextField and saves the values to API config file.
         * After saving to config file. sessionHandler is reloaded with new API settings.
         * It sessionHandler wouldnt be reloaded, the old API settings would still be there.
         */
        saveButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                APIConfigReader apiConfigReader = new APIConfigReader();
                apiConfigReader.write(hostTextField.getText(), portTextField.getText());
                headerText.setText("Host and port saved!");
                headerText.setFill(Color.GREEN);
                headerText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                sessionHandler.updateAPIconfig();
            }
        });

        root.getChildren().add(gridPane);
        return gridPane;
    }


}
