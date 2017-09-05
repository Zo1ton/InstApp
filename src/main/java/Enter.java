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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 30.06.2017.
 */

public class Enter extends Application implements Serializable {

    Label label;
    List<Person> list = new ArrayList();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
//        super.init();
        if (!list.isEmpty()) {
            FileInputStream fis = new FileInputStream("temp.out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            List<Person> list = (List<Person>) oin.readObject();
            System.out.println("Сериализация выполнена");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

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
                Person person = new Person(login);          // Создаем новую запись пользователя
                if (person.isExist == true) {
                    label.setText(person.getInfoAsString());
                    list.add(person);
                    System.out.println("В коллекцию List добавленна запись " + person.getUserName());
                }
            }
            else label.setText("Введите логин!");
        });

        flowPane.getChildren().addAll(textField, button, label);

        TextField textField1 = new TextField();
        Button button1 = new Button("Получить инфо");
        Label label1 = new Label("Info about Instagramm account");

        button1.setOnAction(e-> {
            final String id = textField1.getText();
            for (Person source : list){
                if (String.format("%d", source.getId()).equals(id)){
                    label1.setText("Логин есть в коллекции");
                    break;
                }
                else {
                    label1.setText("Логина нет в коллекции");
                }
            }
        });

        flowPane.getChildren().addAll(textField1, button1, label1);

        mainStage.setScene(mainScene);
        mainStage.show();
    }

    @Override
    public void stop() throws Exception {
//        super.stop();
        FileOutputStream fos = new FileOutputStream("temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(list);
        oos.flush();
        oos.close();
    }

}