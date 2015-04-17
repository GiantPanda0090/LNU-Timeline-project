/**
 * Created by Johan on 2015-04-08.
 * AS OF RIGHT NOW EVERYTHING WORKS
*/

import backend.*;
import gui.Config;
import javafx.application.Application;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
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
    ObservableList<String> entries = FXCollections.observableArrayList();
    ListView<String> list = new ListView<String>();
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
        HBox register = new HBox();
        register.setPadding(new Insets(0, 0, 30, 30));
        register.setSpacing(20);


        HBox fillLeft = new HBox();
        fillLeft.setMinWidth(100);
        fillLeft.setMinHeight(200);
        HBox fillRight = new HBox();
        HBox fillTop = new HBox();
        fillTop.setMinHeight(50);

        login.setSpacing(20);

        Label logo = new Label("MiTime");
        logo.setId("labelLogo");

        final TextField username = new TextField("austin");
        username.setFont(new Font("System", 18));
        username.setMaxWidth(200);

        final PasswordField password = new PasswordField();
        password.setText("password");
        password.setMaxWidth(200);

        Button button = new Button("Log in");
        button.setMinWidth(200);

        final Label regBtn = new Label("Sign up");
        regBtn.setId("regBtn");

        // Config Icon
        final SVGPath config = new SVGPath();
        config.setContent("M31.229,17.736c0.064-0.571,0.104-1.148,0.104-1.736s-0.04-1.166-0.104-1.737l-4.377-1.557c-0.218-0.716-0.504-1.401-0.851-2.05l1.993-4.192c-0.725-0.91-1.549-1.734-2.458-2.459l-4.193,1.994c-0.647-0.347-1.334-0.632-2.049-0.849l-1.558-4.378C17.165,0.708,16.588,0.667,16,0.667s-1.166,0.041-1.737,0.105L12.707,5.15c-0.716,0.217-1.401,0.502-2.05,0.849L6.464,4.005C5.554,4.73,4.73,5.554,4.005,6.464l1.994,4.192c-0.347,0.648-0.632,1.334-0.849,2.05l-4.378,1.557C0.708,14.834,0.667,15.412,0.667,16s0.041,1.165,0.105,1.736l4.378,1.558c0.217,0.715,0.502,1.401,0.849,2.049l-1.994,4.193c0.725,0.909,1.549,1.733,2.459,2.458l4.192-1.993c0.648,0.347,1.334,0.633,2.05,0.851l1.557,4.377c0.571,0.064,1.148,0.104,1.737,0.104c0.588,0,1.165-0.04,1.736-0.104l1.558-4.377c0.715-0.218,1.399-0.504,2.049-0.851l4.193,1.993c0.909-0.725,1.733-1.549,2.458-2.458l-1.993-4.193c0.347-0.647,0.633-1.334,0.851-2.049L31.229,17.736zM16,20.871c-2.69,0-4.872-2.182-4.872-4.871c0-2.69,2.182-4.872,4.872-4.872c2.689,0,4.871,2.182,4.871,4.872C20.871,18.689,18.689,20.871,16,20.871z");
        config.setFill(Paint.valueOf("19b225"));
        config.setScaleX(0.7);
        config.setScaleY(0.7);

        final Button configButton = new Button("API config");

        login.getChildren().addAll(logo, username, password, button);
        register.getChildren().addAll(regBtn, config, configButton);

        pane.setTop(fillTop);
        pane.setCenter(login);
        pane.setRight(fillRight);
        pane.setLeft(fillLeft);
        pane.setBottom(register);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaxHeight(600.0);
        primaryStage.setMaxWidth(400.0);
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
                    plus.setMinWidth(100);

                    // Parent for timeline view
                    ScrollPane timelinePane = new ScrollPane();
                    timelinePane.setPrefSize(1000, 800);

                    // Config button (just a button right now)
                    final Button config = new Button("Config");
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
                    timelineList.setVgrow(plus, Priority.ALWAYS);
                    timelineList.setVgrow(search, Priority.ALWAYS);

                    VBox timelineView = new VBox();
                    timelineView.setPadding(new Insets(40, 40, 100, 40));

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
                    ListView<Label> labelView = new ListView<Label>();

                    // Get user Timelines
                    sessionHandler.getTimelines();
                    int size = sessionHandler.timelineArrayList.size();

                    ObservableList<Label> items = FXCollections.observableArrayList();

                    for (int i = 0; i < size; i++) {
                        Label timelineLabel = new Label(sessionHandler.timelineArrayList.get(i).getTimeline_title());
                        items.add(i, timelineLabel);
                        timelineLabel.setId("timelineLabel");
                        top += 100;
                    }

                    labelView.setItems(items);

                    // Search field
                    TextField Search = new TextField();
                    Search.setPromptText("Search your timeline");;
                    // search.textProperty().addListener(new ChangeListener<Object>() {
                    //     public void changed(ObservableValue<?> observable, Object oldVal,
                    //                        Object newVal) {
                    //       search((String) oldVal, (String) newVal);
                    //   }
                    //});

                    list.setMaxHeight(180);
                    for (int i = 0; i < 100; i++) {
                        entries.add("Item " + i);
                    }
                    entries.add("A");
                    entries.add("B");
                    entries.add("C");
                    entries.add("D");
                    list.setItems(entries);
                    HBox root = new HBox();
                    root.setPadding(new Insets(10, 10, 10, 10));
                    root.setSpacing(2);
                    root.getChildren().addAll(search, list);

                    search.setFont(new Font("System", 12));
                    search.setMaxWidth(200);
                    search.setMaxHeight(30);

                    bannerView.getChildren().addAll(logo);
                    timelineList.getChildren().addAll(configbox, Search, labelView);
                    timelineView.getChildren().addAll(timelinePane);

                    bpane.setTop(bannerView);
                    bpane.setLeft(timelineList);
                    bpane.setCenter(timelineView);

                    stage.setScene(new Scene(bpane, 1280, 600));
                    stage.setFullScreen(true);
stage.setResizable(false);


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

    public void searchTimeline(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            list.setItems(entries);
        }
        String value = newVal.toUpperCase();
        ObservableList<String> subentries = FXCollections.observableArrayList();
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
    public static void main(String[] args){
        launch(args);
    }
}
