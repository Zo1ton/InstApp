import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class Controller2 {

    List<Person> list = new ArrayList<>();

    ObservableList<PersonObsList> obsList = FXCollections.observableArrayList();

    @FXML private TextField textField;
    @FXML private Label label;
    @FXML TableView<PersonObsList> table;
    @FXML TableColumn<PersonObsList, Long> idCol;
    @FXML TableColumn<PersonObsList, String> loginCol;

    @FXML
    public void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        idCol.setCellValueFactory(new PropertyValueFactory<PersonObsList, Long>("id"));
        loginCol.setCellValueFactory(new PropertyValueFactory<PersonObsList, String>("login"));

        // заполняем таблицу данными
        table.setItems(obsList);
    }

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

    private void initData() {
        obsList.add(new PersonObsList("ngageman61", 123456L));
        obsList.add(new PersonObsList("emms_s__dresses", 654321L));
    }
}
