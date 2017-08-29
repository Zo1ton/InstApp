import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Created by Andrey on 30.06.2017.
 */
//public class Enter {
//    public static void main(String[] args) {
//        String login = JOptionPane.showInputDialog(null, "Введите логин: ", "Ввод логина", JOptionPane.PLAIN_MESSAGE);
//
//        Person person = new Person(login);   // Создаем новую запись пользователя
//
//        JOptionPane.showMessageDialog(null, person.getInfoAsString(), "Инфо аккаунта " + login, JOptionPane.PLAIN_MESSAGE);
//    }
//}
public class Enter extends Application {

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
        Stage mainStage = new Stage();
        mainStage.setTitle("InstApp");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.setAlignment(Pos.CENTER);

        Scene mainScene = new Scene(flowPane, 500, 500);

        TextField textField = new TextField();
        Button button = new Button("Получить инфо");
        label = new Label("Info about Instagramm account");

        button.setOnAction(e -> {
            String login = textField.getText();
            if (!login.isEmpty()) {
                Person person = new Person(login);
                label.setText(person.getInfoAsString());
            }
            else label.setText("Введите логин!");
        });

        flowPane.getChildren().addAll(textField, button, label);

        mainStage.setScene(mainScene);
        mainStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

}