import javafx.application.Application;
import javafx.geometry.Pos;
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
    List<Person> list;

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

        // Если файл есть, пытаемся его десюрилизовать, если нет, создаем новую коллекцию.
        File file = new File("temp.out");
        if (file.exists()){
            try (FileInputStream fis = new FileInputStream("temp.out");
                 ObjectInputStream in = new ObjectInputStream(fis))
            {
                list = (List<Person>) in.readObject();
            } catch (IOException io){
                System.err.println(io);
                list = new ArrayList();
            } catch (ClassNotFoundException cnfe){
                System.err.println(cnfe);
                list = new ArrayList();
            }
        } else {
            list = new ArrayList<>();
        }

        button.setOnAction(e -> {
            String login = textField.getText();
            if (!login.isEmpty()) {
                Person person = new Person(login);          // Создаем новую запись пользователя
                if (person.isExist == true) {
                    label.setText(person.getInfoAsString());
                    list.add(person);
                    System.out.println("В коллекцию List добавленна запись " + person.getUserName() + " - id - " + person.getId());
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
        try (FileOutputStream fos = new FileOutputStream("temp.out");
             ObjectOutputStream out = new ObjectOutputStream(fos))
        {
            out.writeObject(list);
        } catch (IOException io){
            System.err.println(io);
        }
}
}