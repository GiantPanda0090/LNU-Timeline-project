/**
 * Created by Johan on 2015-04-08.
 * AS OF RIGHT NOW EVERYTHING WORKS
*/
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;


public class login extends Application {

    PopOver newTimeorEvent;
    double top = 0;
    ArrayList<Label> labelList = new ArrayList<Label>();

    Insets labelInsets;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final GridPane gpane = new GridPane();
        gpane.setId("gpane");
        Scene scene = new Scene(gpane, 400, 400);
        gpane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());
        gpane.setCenterShape(true);

        Label logo = new Label("MiTime");
        logo.setId("labelLogo");
        gpane.setRowIndex(logo, 2);
        logo.setAlignment(Pos.CENTER_LEFT);
        gpane.setColumnIndex(logo, 0);
        gpane.setMargin(logo, new Insets(0, 20, 150, 140));

        TextField username = new TextField("username");
        username.setFont(new Font("System", 18));
        username.setMaxWidth(200);
        gpane.setRowIndex(username, 2);
        gpane.setColumnIndex(username, 0);
        gpane.setMargin(username, new Insets(20, 20, 70, 110));

        PasswordField password = new PasswordField();
        password.setText("password");
        gpane.setRowIndex(password, 2);
        gpane.setColumnIndex(password, 0);
        password.setMaxWidth(200);
        gpane.setMargin(password, new Insets(120, 20, 70, 110));

        Button button = new Button("Sign in");
        button.setMinWidth(200);
        gpane.setRowIndex(button, 2);
        gpane.setColumnIndex(button, 0);
        gpane.setMargin(button, new Insets(230, 20, 70, 110));



        gpane.getChildren().addAll(logo, username, password, button);
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                // Gridpane
                final GridPane gpane = new GridPane();
                gpane.setId("gpane");
                gpane.getStylesheets().add(this.getClass().getResource("css.css").toExternalForm());
                gpane.setCenterShape(true);

                // Line 1
                Line line1 = new Line();
                line1.setStartX(500);
                line1.setStartY(-70);
                line1.setEndX(500);
                line1.setEndY(820);
                line1.setStrokeWidth(3);
                line1.setStroke(Paint.valueOf("14cc23"));
                gpane.setMargin(line1, new Insets(50, 0, 50, 400));


                // Line 2
                Line line2 = new Line();
                line2.setStartX(-100);
                line2.setStartY(0);
                line2.setEndX(1185);
                line2.setEndY(0);
                line2.setStrokeWidth(3);
                line2.setStroke(Paint.valueOf("14cc23"));
                gpane.setMargin(line2, new Insets(-600, 0, 200, 350));


                // Rectangle
                Rectangle rect = new Rectangle();
                rect.setArcWidth(5);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                rect.setWidth(1219);
                rect.setHeight(820);
                gpane.setMargin(rect, new Insets(50, 0, 0, 420));

                // Label
                Label label = new Label("MiTime");
                label.setId("createlabelLogo");
                gpane.setMargin(label, new Insets(0, 0, 900, 60));

                // Plus icon
                final SVGPath plus = new SVGPath();
                plus.setContent("M992 384h-352v-352c0-17.672-14.328-32-32-32h-192c-17.672 0-32 14.328-32 32v352h-352c-17.672 0-32 14.328-32 32v192c0 17.672 14.328 32 32 32h352v352c0 17.672 14.328 32 32 32h192c17.672 0 32-14.328 32-32v-352h352c17.672 0 32-14.328 32-32v-192c0-17.672-14.328-32-32-32z");
                plus.setFill(Paint.valueOf("19b225"));
                plus.setScaleX(0.025);
                plus.setScaleY(0.025);
                plus.setScaleZ(1);
                gpane.setMargin(plus, new Insets(-350, 0, 450, -430));

                // Config Icon
                final SVGPath config = new SVGPath();
                config.setContent("M31.229,17.736c0.064-0.571,0.104-1.148,0.104-1.736s-0.04-1.166-0.104-1.737l-4.377-1.557c-0.218-0.716-0.504-1.401-0.851-2.05l1.993-4.192c-0.725-0.91-1.549-1.734-2.458-2.459l-4.193,1.994c-0.647-0.347-1.334-0.632-2.049-0.849l-1.558-4.378C17.165,0.708,16.588,0.667,16,0.667s-1.166,0.041-1.737,0.105L12.707,5.15c-0.716,0.217-1.401,0.502-2.05,0.849L6.464,4.005C5.554,4.73,4.73,5.554,4.005,6.464l1.994,4.192c-0.347,0.648-0.632,1.334-0.849,2.05l-4.378,1.557C0.708,14.834,0.667,15.412,0.667,16s0.041,1.165,0.105,1.736l4.378,1.558c0.217,0.715,0.502,1.401,0.849,2.049l-1.994,4.193c0.725,0.909,1.549,1.733,2.459,2.458l4.192-1.993c0.648,0.347,1.334,0.633,2.05,0.851l1.557,4.377c0.571,0.064,1.148,0.104,1.737,0.104c0.588,0,1.165-0.04,1.736-0.104l1.558-4.377c0.715-0.218,1.399-0.504,2.049-0.851l4.193,1.993c0.909-0.725,1.733-1.549,2.458-2.458l-1.993-4.193c0.347-0.647,0.633-1.334,0.851-2.049L31.229,17.736zM16,20.871c-2.69,0-4.872-2.182-4.872-4.871c0-2.69,2.182-4.872,4.872-4.872c2.689,0,4.871,2.182,4.871,4.872C20.871,18.689,18.689,20.871,16,20.871z");
                config.setFill(Paint.valueOf("19b225"));
                gpane.setMargin(config, new Insets(0, 0, 880, 1570));

                // Search field
                final TextField search = new TextField("Search your timelines");
                search.setFont(new Font("System", 12));
                search.setMaxWidth(200);
                search.setMaxHeight(30);
                gpane.setMargin(search, new Insets(-610, 0, 100, 60));


                gpane.getChildren().addAll(line1, line2, rect, label, plus, config, search);
                stage.setScene(new Scene(gpane, 1657, 951));
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
                popPane.setRowIndex(firstLbl,0);
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
                *Data for Config Button(The one to the Right)
                 */

                final GridPane ConfPane = new GridPane();
                popPane.setMinSize(300, 400);

                Button butt = new Button();
                butt.setMinWidth(50);


                ConfPane.getChildren().add(butt);

                config.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        PopOver ConfigPop = new PopOver();

                        ConfigPop.setContentNode(ConfPane);
                        ConfigPop.show(config);
                    }
                });

                /*
                ...
                EVENTS FOR PLUS SIGN
                ...
                 */

                plus.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        plus.setFill(Paint.valueOf("ffffff"));
                        newTimeorEvent = new PopOver();
                        newTimeorEvent.setOpacity(0.99);

                        newTimeorEvent.setContentNode(popPane);

                        newTimeorEvent.show(plus);

                        okRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                newTimeorEvent.hide();
                                Label timelineLabel = new Label(name.getText().toString());
                                timelineLabel.setId("timelineLabel");
                                gpane.setMargin(timelineLabel, new Insets(top, 0, 500, 60));
                                gpane.setColumnIndex(timelineLabel, 0);
                                gpane.setRowIndex(timelineLabel, 0);
                                labelList.add(timelineLabel);
                                gpane.getChildren().addAll(timelineLabel);
                                top += 100;
                                plus.setFill(Paint.valueOf("19b225"));
                            }
                        });
                    }
                });

                    cancelRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            newTimeorEvent.hide();
                            plus.setFill(Paint.valueOf("19b225"));
                        }
                    });

                 gpane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         newTimeorEvent.hide();
                         plus.setFill(Paint.valueOf("19b225"));
                     }
                 });




            }
        });
    }
    public static void main(String[] args){
        launch(args);
    }
}
