import backend.UserRegistration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

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
        final TextField newUserName = new TextField("Enter username");
        final TextField firstPassword = new TextField("Enter password");
        final TextField secondPassword = new TextField("Repeat password");
        final TextField userEmail = new TextField("Email");

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
                UserRegistration newUser = new UserRegistration();
                newUser.register(newUserName.getText().toString(), firstPassword.getText().toString(), secondPassword.getText().toString(), userEmail.getText().toString());
                regPop.hide();
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
