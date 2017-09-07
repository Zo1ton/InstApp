import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        id.setCellValueFactory(cellData -> cellData.getValue());
//        login.setCellValueFactory(cellData -> cellData.getValue().getUserName());
    }

    List<Person> list = new ArrayList<Person>();
    ObservableList<Person> observableList = FXCollections.observableList(list);

    @FXML private TextField textField;
    @FXML private Label label;
    @FXML private TableView<Person> personTable;
    @FXML private TableColumn<Person, Long> id;
    @FXML private TableColumn<Person, String> login;
    @FXML private TableColumn<Person, Long> posts;
    @FXML private TableColumn<Person, Long> followers;
    @FXML private TableColumn<Person, Long> following;

    private Main main;

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
        observableList.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                System.out.println("ttttt");
            }
        });


    }
}
