import backend.API;
import backend.APIConfigReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by nils on 2015-04-16.
 */
public class Config {

    public Stage config(){

        final API api = APIConfigReader.read();

        final Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);


        stage.setTitle("Config");



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

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                api.setHost(hostTextField.getText());
                api.setPort(portTextField.getText());
            }
        });

        root.getChildren().add(gridPane);
        stage.setScene(scene);
        return stage;
    }

}
