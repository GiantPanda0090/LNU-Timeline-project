import backend.UserRegistration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN MARKUS WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/

/**
 * This class is to generate a PopOver for registration
 * The registrationBorderPane function takes a PopOver object as argument and
 * add the things to is and then assigns the things to a BorderPane
 */
public class RegistraionBorderPane {

    public BorderPane registrationBorderPane(PopOver popOver){

        final PopOver regPop = popOver;

        final BorderPane regPane = new BorderPane();
        regPane.setMinSize(200, 200);
        VBox regBox = new VBox();
        regBox.setSpacing(12);
        regBox.setPadding(new Insets(20, 20, 20, 20));
        final TextField newUserName = new TextField();
        newUserName.setPromptText("Enter Username");
        final PasswordField firstPassword = new PasswordField();
        firstPassword.setPromptText("Enter Password");
        final PasswordField secondPassword = new PasswordField();
        secondPassword.setPromptText("Repeat Password");
        final TextField userEmail = new TextField();
        userEmail.setPromptText("Email");

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(12);
        buttonBox.setPadding(new Insets(20, 20, 20, 20));
        final Button signUp = new Button("Sign up!");
        final Button signCancel = new Button("Cancel");
        buttonBox.getChildren().addAll(signUp, signCancel);
        regBox.getChildren().addAll(newUserName, firstPassword, secondPassword, userEmail);
        regPane.setCenter(regBox);
        regPane.setBottom(buttonBox);

        signUp.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if (firstPassword.getText().equals(secondPassword.getText())) {
                    UserRegistration newUser = new UserRegistration();
                    newUser.register(newUserName.getText().toString(), firstPassword.getText().toString(), secondPassword.getText().toString(), userEmail.getText().toString());
                    regPop.hide();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thank you for signing up with MiTime!");
                    alert.setHeaderText(null);
                    alert.setContentText("An email has been sent!");

                    alert.showAndWait();
                }

                     else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Password Mismatch");
                    alert.setContentText("Please enter passwords of the same value");
                    alert.showAndWait();

                         }

            }
        });
        signCancel.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                regPop.hide();
            }
        });

        return regPane;
    }
}
