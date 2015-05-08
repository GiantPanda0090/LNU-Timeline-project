package GUI;

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
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

/**
 * Created by nils on 2015-04-21.
 */
public class UserInfoStage {

    public Stage userInfoStage(final SessionHandler sessionHandler){

        // FX stage
        final Stage stage = new Stage();

        // FX root
        Group root = new Group();

        // FX scene
        Scene scene = new Scene(root);

        // Title
        stage.setTitle("User Info");

        // Gridpane and settings for gridpane
        GridPane gridPane = new GridPane();
        gridPane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());
        gridPane.setPrefSize(350, 250);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        final Text headerText = new Text("User Info");
        final Text usernameText = new Text("Username:");
        final Text firstNameText = new Text("First name:");
        final Text lastNameText = new Text("Last name:");
        final Text emailText = new Text("Email:");
        final Text joinedText = new Text("Member since:");


        final Text usernameTextField = new Text(sessionHandler.getActiveUser().getUsername());
        final TextField firstNameTextField = new TextField(sessionHandler.getActiveUser().getFirst_name());
        final TextField lastNameTextField = new TextField(sessionHandler.getActiveUser().getLast_name());
        final Text emailTextField = new Text(sessionHandler.getActiveUser().getEmail());
        final Text joinedTextField = new Text(sessionHandler.getActiveUser().getDate_joined().toString());


        final Button saveButton = new Button("Save");
        final Button cancelButton = new Button("Cancel");


        gridPane.add(headerText, 1, 0);
        gridPane.add(usernameTextField, 1, 1);
        gridPane.add(usernameText, 0, 1);
        gridPane.add(firstNameTextField, 1, 2);
        gridPane.add(firstNameText, 0, 2);
        gridPane.add(lastNameText, 0, 3);
        gridPane.add(lastNameTextField, 1, 3);
        gridPane.add(emailText, 0, 4);
        gridPane.add(emailTextField, 1, 4);
        gridPane.add(joinedText, 0, 5);
        gridPane.add(joinedTextField, 1, 5);
        gridPane.add(saveButton, 0, 8);
        gridPane.add(cancelButton, 1, 8);



        /*
        Horizontal view for buttons.
         */

        HBox hbox = new HBox();
        hbox.setSpacing(25);
        //hbox.getChildren().addAll(saveButton, cancelButton);
        gridPane.add(hbox, 0, 6);


        /*
         * Close the config stage.
         */

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.close();
            }
        });


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                sessionHandler.updateUser(firstNameTextField.getText(), lastNameTextField.getText());
                //sessionHandler.getUser(sessionHandler.getUser().getUsername());
                headerText.setText("User info updated");
                headerText.setFill(Color.GREEN);
                headerText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

            }
        });


        root.getChildren().add(gridPane);
        stage.setScene(scene);
        return stage;
    }

/*
    public Stage userInfoStage(final SessionHandler sessionHandler){

        // FX stage
        final Stage stage = new Stage();

        BorderPane pane = new BorderPane();
        ImageView img = new ImageView("http://www.bth.se/com/com.nsf/bilder/Johan%20p%C3%A5%20sydnytt_JPG/$file/Johan%20p%C3%A5%20sydnytt.JPG");

        img.fitWidthProperty().bind(stage.widthProperty());
        img.fitHeightProperty().bind(stage.heightProperty());

        pane.setCenter(img);

        Scene scene = new Scene(pane);
        stage.setScene(scene);


        return stage;

    }
   */
}
