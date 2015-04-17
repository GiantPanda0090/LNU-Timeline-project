/**
 * Created by Johan on 2015-04-08.
 * AS OF RIGHT NOW EVERYTHING WORKS
*/

import backend.SessionHandler;
import backend.Timeline;
import backend.UserRegistration;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;


public class login extends Application {

    PopOver newTimeorEvent;
    PopOver ConfigPop;
    PopOver regPop;
    double top = 0;
    ArrayList<Timeline> labelList = new ArrayList<Timeline>();
    ListView<Label> list = new ListView<Label>();
    ObservableList<Label> items = FXCollections.observableArrayList();
    UserRegistration newUser;

    Insets labelInsets;


    @Override
    public void start(final Stage primaryStage) throws Exception {

        final SessionHandler sessionHandler = new SessionHandler();

        BorderPane pane = new BorderPane();
        pane.setId("gpane");
        Scene scene = new Scene(pane, 400, 400);
        pane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());

        VBox login = new VBox();
        login.setPadding(new Insets(20, 20, 20, 80));
        login.setId("loginBox");
        HBox insideLogin = new HBox();
        insideLogin.setPadding(new Insets(0, 0, 0, 30));
        insideLogin.setSpacing(12);

        HBox register = new HBox();
        register.setPadding(new Insets(0, 0, 30, 30));
        register.setSpacing(20);
        register.setMinHeight(25);


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

        Button button = new Button("Log in");
        button.setMinWidth(200);

        final Label regBtn = new Label("Sign up");
        regBtn.setId("regBtn");

        final Button configButton = new Button("API");

        insideLogin.getChildren().addAll(regBtn, configButton);
        login.getChildren().addAll(logo, username, password, button, insideLogin);

        pane.setTop(fillTop);
        pane.setCenter(login);
        pane.setRight(fillRight);
        pane.setLeft(fillLeft);
        pane.setBottom(register);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaxHeight(550);
       // primaryStage.setMinHeight(500.0);
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

        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if (sessionHandler.loginUser(username.getText(), password.getText())) {
                    Stage stage = new Stage();
                    final BorderPane bpane = new BorderPane();
                    bpane.setPadding(new Insets(40, 40, 40, 40));
                    stage.setMinWidth(800);
                    stage.setMinHeight(100);

                    bpane.setId("gpanemain");
                    bpane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());

                    final Label logo = new Label("MiTime");
                    logo.setId("labelLogo");

                    // Label
                    Label label = new Label("MiTime");
                    label.setId("createlabelLogo");

                    // Plus icon (as of right now, just a button)
                    final Button plus = new Button("New");
                    plus.setId("newTimelineButton");
                    plus.setMinWidth(100);

                    // Parent for timeline view
                    ScrollPane timelinePane = new ScrollPane();
                    timelinePane.setId("scrollpane");
                    timelinePane.setPrefSize(1000, 800);

                    // Config button (just a button right now)
                    final Button config = new Button("Config");
                    config.setId("configButton");
                    config.setMinWidth(100);

                    // Listview for timelines
                    ListView<String> labelList = new ListView<String>();
                    ObservableList<String> listtest = FXCollections.observableArrayList("test", "funkar");
                    labelList.setItems(listtest);

                    // Search field
                    TextField search = new TextField();
                    search.setPromptText("Search your timeline");

                    search.setFont(new Font("System", 12));
                    search.setMaxWidth(200);
                    search.setMaxHeight(30);

                    final VBox timelineList = new VBox();
                    timelineList.setSpacing(12);
                    timelineList.setId("listBox");
                    timelineList.setPadding(new Insets(30, 30, 30, 30));
                    timelineList.setVgrow(plus, Priority.ALWAYS);
                    timelineList.setVgrow(search, Priority.ALWAYS);

                    VBox timelineView = new VBox();
                    timelineView.setPadding(new Insets(0, 40, 100, 40));

                    HBox bannerView = new HBox();
                    bannerView.setMinWidth(200);

                    HBox rightFill = new HBox();
                    rightFill.setMinWidth(40);

                    HBox bottomFill = new HBox();
                    bottomFill.setMinHeight(200);
                    bottomFill.setMaxHeight(10);
                    HBox configbox = new HBox();
                    configbox.setPadding(new Insets(10, 10, 10, 10));
                    configbox.setSpacing(5.0);
                    configbox.getChildren().addAll(plus, config);

                    // Listview for timelines


                    // Get user Timelines
                    sessionHandler.getTimelines();
                    int size = sessionHandler.timelineArrayList.size();



                    for (int i = 0; i < size; i++) {
                        Label timelineLabel = new Label(sessionHandler.timelineArrayList.get(i).getTimeline_title());
                        items.add(i, timelineLabel);
                        timelineLabel.setId("timelineLabel");
                        top += 100;
                    }

                    list.setItems(items);

                    // Search field
                    final TextField Search = new TextField();
                    Search.setPromptText("Search your timeline");;
                     search.textProperty().addListener(new ChangeListener<Object>() {
                        public void changed(ObservableValue<?> observable, Object oldVal,
                                            Object newVal) {
                           search((String) oldVal, (String) newVal);
                       }
                    });

                    Search.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Label l = items.get(johanSearch(Search.getText()));
                            list.scrollTo(l);
                        }
                    });


                    //HBox root = new HBox();
                    //root.setPadding(new Insets(10, 10, 10, 10));
                    //root.setSpacing(2);
                    //root.getChildren().addAll(search, list);

                    search.setFont(new Font("System", 12));
                    search.setMaxWidth(200);
                    search.setMaxHeight(30);

                    bannerView.getChildren().addAll(logo);
                    timelineList.getChildren().addAll(configbox, Search, list);
                    timelineView.getChildren().addAll(timelinePane);

                    bpane.setTop(bannerView);
                    bpane.setLeft(timelineList);
                    bpane.setCenter(timelineView);

                    stage.setScene(new Scene(bpane, 1280, 600));
                    stage.setMinWidth(900);
                    stage.setMinHeight(400);


                    primaryStage.close();
                    stage.show();

                   /*
                   ...
                   ...
                   DATA FOR PLUS SIGN ICON, IE MAKING A NEW TIMELINE
                   ...
                   ...
                    */

                    final GridPane popPane = new GridPane();
                    popPane.setMinSize(300, 400);
                    popPane.getStylesheets().add(this.getClass().getResource("popover.css").toExternalForm());

                    // Name Label
                    final TextField name = new TextField("Name your timeline");
                    name.setMaxWidth(200);
                    name.setMinHeight(50);
                    name.setFont(new Font("System", 20));
                    name.setId("nametime");
                    popPane.setRowIndex(name, 0);
                    popPane.setColumnIndex(name, 0);
                    popPane.setMargin(name, new Insets(-300, 50, 0, 50));

                    // first label
                    Label firstLbl = new Label("Start date");
                    firstLbl.setId("timelineLabel");
                    firstLbl.setMinWidth(100);
                    popPane.setRowIndex(firstLbl, 0);
                    popPane.setColumnIndex(firstLbl, 0);
                    popPane.setMargin(firstLbl, new Insets(-150, 0, 0, 50));

                    // first datepicker
                    DatePicker firstDate = new DatePicker();
                    firstDate.setMaxWidth(150);
                    popPane.setRowIndex(firstDate, 0);
                    popPane.setColumnIndex(firstDate, 0);
                    popPane.setMargin(firstDate, new Insets(-70, 0, 0, 50));

                    // second label
                    Label secondLbl = new Label("End date");
                    secondLbl.setMinWidth(100);
                    popPane.setRowIndex(secondLbl, 0);
                    popPane.setColumnIndex(secondLbl, 0);
                    popPane.setMargin(secondLbl, new Insets(30, 0, 0, 50));

                    // second datePicker
                    DatePicker secondDate = new DatePicker();
                    secondDate.setMaxWidth(150);
                    popPane.setRowIndex(secondDate, 0);
                    popPane.setColumnIndex(secondDate, 0);
                    popPane.setMargin(secondDate, new Insets(120, 0, 0, 50));

                    // test OK rectangle
                    final Rectangle okRect = new Rectangle(165, 100, Color.WHITE);
                    popPane.setMargin(okRect, new Insets(300, 0, 0, 0));
                    okRect.setStroke(Color.valueOf("C4C4C4"));
                    okRect.setId("okrect");
                    Text okText = new Text("Create");
                    popPane.setMargin(okText, new Insets(300, 0, 0, 50));
                    okText.setId("oktext");

                    // test Cancel rectangle
                    final Rectangle cancelRect = new Rectangle(160, 100, Color.WHITE);
                    popPane.setMargin(cancelRect, new Insets(300, 0, 0, 165));
                    cancelRect.setStroke(Color.valueOf("C4C4C4"));
                    cancelRect.setId("cancelrect");
                    Text cancelText = new Text("Cancel");
                    popPane.setMargin(cancelText, new Insets(300, 0, 0, 220));
                    cancelText.setId("canceltext");


                    popPane.getChildren().addAll(firstDate, secondDate, firstLbl, secondLbl, name, okRect, cancelRect, okText, cancelText);



                    /*
                    ...
                    EVENTS FOR PLUS SIGN
                    ...
                     */

                    plus.setOnAction(new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent event) {
                            newTimeorEvent = new PopOver();
                            newTimeorEvent.setOpacity(0.99);

                            newTimeorEvent.setContentNode(popPane);

                            newTimeorEvent.show(plus);

                            okRect.setOnMouseClicked(new EventHandler<MouseEvent>() {

                                public void handle(MouseEvent event) {
                                    newTimeorEvent.hide();
                                    Label timelineLabel = new Label(name.getText().toString());
                                    timelineLabel.setId("timelineLabel");
                                    timelineList.getChildren().addAll(timelineLabel);
                                    top += 100;
                                    sessionHandler.createTimeline(name.getText().toString(), "description");
                                }
                            });
                        }
                    });

                    cancelRect.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        public void handle(MouseEvent event) {
                            newTimeorEvent.hide();
                        }
                    });

                } else {
                    username.setText("false");
                }
            }
        });
    }

    public void search(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            list.setItems(items);
        }
        String value = newVal.toUpperCase();
        ObservableList subentries = FXCollections.observableArrayList();
        for (Object entry : list.getItems()) {
            boolean match = true;
            String entryText = (String) entry;
            if (!entryText.toUpperCase().contains(value)) {
                match = false;
                break;
            }
            if (match == true) {

                subentries.add(entryText);
            }
        }
        list.setItems(subentries);
    }

    // Alternate method for searching through the listview
    public int johanSearch(String str){
        int index = 0;
        for(int i = 0; i<items.size(); i++){
            if(items.get(i).getText().equals(str)){
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args){
        launch(args);
    }
}
