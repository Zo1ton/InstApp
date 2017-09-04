import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Created by Andrey on 30.06.2017.
 */

public class Enter2 extends Application {

    Label label;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

        // Даём контроллеру доступ к главному приложению.
        PersonOverviewController controller = loader.getController();
        controller.setMainApp(this);

        TextField textField = new TextField();
        label = new Label("Info about Instagramm account");

    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

}