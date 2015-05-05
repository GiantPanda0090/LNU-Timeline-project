/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/
import backend.SessionHandler;
import backend.UserRegistration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import javafx.animation.Animation;

import java.awt.*;

/**
 * Created by Johan on 2015-04-20.
 */
public class LoginStage extends Application{



    public LoginStage(){};

    /**
     * This is where SessionHandler is created.
     * Dont create new instances.
     * Work with the same instance.
     * If needed in a class or function, pass the instance as an argument to the method/function
     * and operate on that.
     */
    public final SessionHandler sessionHandler = new SessionHandler();

    @Override
    public void start(final Stage primaryStage) throws Exception {

        BorderPane pane = new BorderPane();
        pane.setId("gpane");
        Scene scene = new Scene(pane, 400, 400);
        pane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());

        final VBox login = new VBox();
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

        Label logo = new Label(" MiTime");
        logo.setId("labelLogo");
        logo.setPadding(new Insets(0,-40,0, 20));

        final TextField username = new TextField("user");
        username.setFont(new Font("System", 18));
        username.setMaxWidth(200);

        final PasswordField password = new PasswordField();
        password.setText("password");
        password.setMaxWidth(200);

        final Button logIn = new Button("Log in");
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


        /**
         * DATA FOR REGISTRATION
         * Registration PopOver
         * Calls the registrationBorderPane with a PopOver as argument.
         * registrationBorderPane then add things to the PopOver and returns the popover.
         */
        final PopOver regPop = new PopOver();

        RegistraionBorderPane registraionBorderPane = new RegistraionBorderPane();
        regPop.setContentNode(registraionBorderPane.registrationBorderPane(regPop));
        regBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {


                if (!regPop.isShowing()) {
                    regPop.show(regBtn);
                       // logIn.setDisable(true);
                }


            }
        });

        /**
         * Configure for the API.
         */
        final PopOver configPop = new PopOver();
        ConfigStage configStageGui = new ConfigStage();
        configPop.setContentNode(configStageGui.config(sessionHandler, configPop));
            configButton.setOnMouseClicked(new EventHandler<MouseEvent>()

            {
                public void handle (MouseEvent event){

                // Create new API config window
                if (configButton.getLayoutBounds().contains(event.getX(), event.getY())) {
                    if (!configPop.isShowing()) {
                        configPop.show(configButton);
                    }
                }
            }
        });
        /*
        if(MouseLocation.getX()!=event.getX()&& MouseLocation.getY()!=event.getY()){
            configPop.hide();
        }
*/
               /*
        configButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // Create new API config window
                if (configButton.getLayoutBounds().contains(event.getX(), event.getY())) {
                    if (configPop.isShowing()) {
                        configPop.hide();
                    }
                }


            }

            // Show the new window
            //configStageGui.config(sessionHandler).show();


        });
        */


        logIn.setOnAction(new EventHandler<ActionEvent>() {


            int x = 0;

            public void shake(){


                Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent t) {


                            if (x == 0) {

                                primaryStage.setX(primaryStage.getX() + 10);
                                x = 1;
                            } else {
                                primaryStage.setX(primaryStage.getX() - 10);
                                x = 0;
                            }



                    }
                }));

                timelineX.setCycleCount(7);
                timelineX.setAutoReverse(false);
                timelineX.play();


            }

            public void handle(ActionEvent event) {
                if (sessionHandler.loginUser(username.getText(), password.getText())) {
                    CreateStage create = new CreateStage(sessionHandler);
                    try {
                        create.start().show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    primaryStage.close();
                } else {
                    username.setText("");
                    username.requestFocus();
                    password.setText("");
                    shake();

                }
            }
        });

    }
            /*
            public void enterK(KeyEvent e)
            {
                int key = e.getKeyKode();
                if()
            }
            */
    /**
     * JavaFX entry point?
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
