package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Alston on 2015-05-13.
 */
public class Tips {

    public Stage start() throws Exception {
        LogFX LOG = new LogFX("Tips.class");
        Stage tipstage = new Stage();
        final BorderPane bpane = new BorderPane();
        bpane.setPadding(new Insets(40, 40, 40, 40));
        tipstage.setMinWidth(300);
        tipstage.setMinHeight(200);
        Scene tipsscene = new Scene(bpane, 300, 200);
        tipstage.setScene(tipsscene);
        try {
            //contant intialize
            HBox top = new HBox();

            Label label1 = new Label("Tips");
            Image image = new Image("tips.png");
            label1.setGraphic(new ImageView(image));
            label1.setTextFill(Color.web("#0076a3"));

            top.getChildren().add(label1);


            bpane.setTop(top);

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
