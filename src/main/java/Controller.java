import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    List<Person> list = new ArrayList<>();

    @FXML private TextField textField;
    @FXML private Label label;
    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person, Long> idCol;
    @FXML private TableColumn<Person, String> loginCol;
    @FXML private TableColumn<Person, Long> postsCol;
    @FXML private TableColumn<Person, Long> followersCol;
    @FXML private TableColumn<Person, Long> followingCol;

    @FXML
    public void pressOnButton() {
        System.out.println("работает");
        String login = textField.getText();
        if (!login.isEmpty()) {
            Person person = new Person(login);          // Создаем новую запись пользователя
            if (person.isExist == true) {
                label.setText(person.getInfoAsString());
                list.add(person);
                System.out.println("В коллекцию добавленна запись " + person.getUserName() + " - id - " + person.getId());
            }
        }
        else label.setText("Введите логин!");
    }

    public void tableViewSort(SortEvent<TableView> tableViewSortEvent) {

    }
}
