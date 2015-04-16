package gui;

import backend.API;
import backend.APIConfigReader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by nils on 2015-04-16.
 */
public class Config {

    public Stage config(){

        API api = APIConfigReader.read();

        final Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);

        stage.setTitle("Config");

        GridPane gridPane = new GridPane();

        final Text headerText = new Text("Config API settings");

        final TextField hostTextField = new TextField(api.getHost());
        final TextField portTextField = new TextField(api.getPort());

        gridPane.add(headerText, 0, 0);
        gridPane.add(hostTextField, 1, 1);
        gridPane.add(portTextField, 1, 2);


        root.getChildren().add(gridPane);
        stage.setScene(scene);
        return stage;
    }

}
