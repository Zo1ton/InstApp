import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private TextField textField;

    @FXML
    private Label label;

    @FXML
    public void pressOnButton() {
        System.out.println("работает");
        String login = textField.getText();
        if (!login.isEmpty()) {
            Person person = new Person(login);          // Создаем новую запись пользователя
            if (person.isExist == true) {
                label.setText(person.getInfoAsString());
//                list.add(person);
//                System.out.println("В коллекцию добавленна запись " + person.getUserName() + " - id - " + person.getId());
            }
        }
        else label.setText("Введите логин!");
    }
}
