package start;

import controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        Controller.startMain();
    }

    //http://o7planning.org/ru/11079/javafx-tableview-tutorial
    //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
    //http://devcolibri.com/3160

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/sample.fxml"));
        primaryStage.setTitle("Inst App");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);
        Image ico = new Image("images/iconLogo.png");
        primaryStage.getIcons().add(ico);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Controller.endMain();
    }
}
