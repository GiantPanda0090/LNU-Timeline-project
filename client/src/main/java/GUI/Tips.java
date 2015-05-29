package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Created by Alston on 2015-05-13.
 */
public class Tips {

    public Stage start() throws Exception {
        LogFX LOG = new LogFX("Tips.class");
        Stage tipstage = new Stage();
        final BorderPane bpane = new BorderPane();
        tipstage.setMinWidth(460);
        tipstage.setMinHeight(250);
        Scene tipsscene = new Scene(bpane, 300, 200);
        tipstage.setScene(tipsscene);
        tipstage.setResizable(false);
        //bpane.setId("tips");
        //bpane.getStylesheets().add("css.css");

        try {
            //contant intialize
            VBox topframe = new VBox();
            topframe.setPadding(new Insets(10,10,10,10));
            topframe.setSpacing(10.0);

            HBox top = new HBox();
            top.setPadding(new Insets(10, 10, 10, 10));
            top.setSpacing(5.0);
            //top.setStyle("-fx-background-color: #336699;");

           StackPane stacktop = new StackPane();

            InnerShadow is = new InnerShadow();
            is.setOffsetX(4.0f);
            is.setOffsetY(4.0f);
            /*
            Text text = new Text();
            text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
            text.setEffect(is);
            text.setX(20);
            text.setY(100);
            text.setText("Tips");
            text.setFill(Color.web("0099ff"));


            text.setTranslateX(300);
            text.setTranslateY(300);
            */
            Label label1 = new Label("Tips");
            Image image = new Image("tips.png");
            label1.setGraphic(new ImageView(image));
            label1.setTextFill(Color.web("0099ff"));
          label1.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
            label1.setTextAlignment(TextAlignment.LEFT);

            //label1.setId("labeltips");
            Line line = new Line();
            line.setStartX(0);
            line.setEndX(400);
            line.setStroke(Color.web("#999999"));
            stacktop.getChildren().addAll(label1);
            top.getChildren().addAll(stacktop);
            topframe.getChildren().addAll(label1, line);

            HBox centre = new HBox();
            StackPane stackcentre = new StackPane();
            centre.setPadding(new Insets(0,10,10,10));
            centre.setSpacing(5.0);

            Label label2 = new Label("Do you know that you can use MiTime to record life track of a famous history person?" +
                    "Like Thomas Edison, Albert Einstein or even yourself XD.");
            label2.setWrapText(true);
            label2.setFont(new Font("Cambria", 15));
            stackcentre.getChildren().add(label2);
            centre.getChildren().add(stackcentre);

            //label2.setStyle("-fx-background-color: #feffff;");
            //centre.setAlignment(Pos.CENTER);

            bpane.setTop(topframe);
            bpane.setCenter(centre);

        }
        catch(Exception e){
            LOG.error(e);
        }
        finally {

            //end of the class
            return tipstage;
        }

    }
//debug
    /*
    public static void main(String[] args) {
    Tips tips = new Tips();
        tips.start().show();
    }
    */
}
