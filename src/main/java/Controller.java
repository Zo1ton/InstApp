import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    static List<Person> list = new ArrayList<>();

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
        initData();
    }

    private void initData() {
        for (Person p : list){
            obsList.add(new PersonObsList(p.getUserName(), p.getId()));
        }
    }

    public static void startMain(){
        // Если файл есть, пытаемся его десюрилизовать, если нет, создаем новую коллекцию.
        File file = new File(Tunes.dbFile.getTune());
        if (file.exists()){
            try (FileInputStream fis = new FileInputStream(Tunes.dbFile.getTune());
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
    }

    public static void end(){
        try (FileOutputStream fos = new FileOutputStream(Tunes.dbFile.getTune());
             ObjectOutputStream out = new ObjectOutputStream(fos))
        {
            out.writeObject(list);
        } catch (IOException io){
            System.err.println(io);
        }
    }
}
