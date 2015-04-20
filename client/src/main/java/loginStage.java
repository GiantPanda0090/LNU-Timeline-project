import backend.SessionHandler;
import backend.UserRegistration;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 * Created by Johan on 2015-04-20.
 */
public class loginStage extends Application{

    public loginStage(){};

    public final SessionHandler sessionHandler = new SessionHandler();

    PopOver regPop;
    double top = 0;
    ListView<Label> list = new ListView<Label>();
    ObservableList<Label> items = FXCollections.observableArrayList();
    UserRegistration newUser;


    @Override
    public void start(final Stage primaryStage) throws Exception {

        BorderPane pane = new BorderPane();
        pane.setId("gpane");
        Scene scene = new Scene(pane, 400, 400);
        pane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());

        VBox login = new VBox();
        login.setPadding(new Insets(20, 20, 20, 80));
        login.setId("loginBox");
        HBox insideLogin = new HBox();
        insideLogin.setPadding(new Insets(0, 0, 0, 30));
        insideLogin.setSpacing(50);

        HBox bottomFill = new HBox();
        bottomFill.setPadding(new Insets(0, 0, 30, 30));
        bottomFill.setSpacing(20);
        bottomFill.setMinHeight(25);


        HBox fillLeft = new HBox();
        fillLeft.setMinWidth(25);
        fillLeft.setMinHeight(200);
        HBox fillRight = new HBox();
        fillRight.setMinWidth(25);
        HBox fillTop = new HBox();
        fillTop.setMinHeight(25);

        login.setSpacing(20);

        Label logo = new Label("MiTime");
        logo.setId("labelLogo");
        logo.setPadding(new Insets(0, 0, 0, 20));

        final TextField username = new TextField("user");
        username.setFont(new Font("System", 18));
        username.setMaxWidth(200);

        final PasswordField password = new PasswordField();
        password.setText("password");
        password.setMaxWidth(200);

        Button logIn = new Button("Log in");
        logIn.setMinWidth(200);

        final Label regBtn = new Label("Sign up");
        regBtn.setId("regBtn");
        regBtn.setPadding(new Insets(0, 0, 0, -25));

        final Button configButton = new Button("API");

        insideLogin.getChildren().addAll(regBtn, configButton);
        login.getChildren().addAll(logo, username, password, logIn, insideLogin);

        pane.setTop(fillTop);
        pane.setCenter(login);
        pane.setRight(fillRight);
        pane.setLeft(fillLeft);
        pane.setBottom(bottomFill);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaxHeight(550);
        primaryStage.setMaxWidth(450);
        primaryStage.setResizable(false);

        /*
          ...
          DATA FOR REGISTRATION
          ...
         */
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


        regBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                regPop = new PopOver();
                regPop.setContentNode(regPane);
                regPop.show(regBtn);

                signUp.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        newUser = new UserRegistration();
                        newUser.register(newUserName.getText().toString(), firstPassword.getText().toString(), secondPassword.getText().toString(), userEmail.getText().toString());
                        regPop.hide();
                    }
                });
                signCancel.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                        regPop.hide();
                    }
                });
            }
        });

        configButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Config configGui = new Config();
                configGui.config().show();
            }
        });
        logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sessionHandler.loginUser(username.getText(), password.getText())) {
                    createStage create = new createStage(sessionHandler);
                    try {
                        create.start().show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    primaryStage.close();
                }
                else {
                    username.setText("false");
                }
            }
        });

    }
    public static void main(String[] args){
        Application.launch(args);
    }

    public SessionHandler getSessionHandler(){
        return sessionHandler;
    }
}
