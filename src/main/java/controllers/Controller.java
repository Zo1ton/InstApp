package controllers;

import start.Person;
import start.PersonObsList;
import start.Tunes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.*;

public class Controller {

    private static Map<Long, List<Person>> map = new HashMap<>();
    private List<Person> list = new ArrayList<>();
    private ObservableList<PersonObsList> obsList = FXCollections.observableArrayList();

    @FXML private TextField textField;
    @FXML private Label label;
    @FXML private TableView<PersonObsList> table;
    @FXML private TableColumn<PersonObsList, Long> idCol;
    @FXML private TableColumn<PersonObsList, String> loginCol;

    @FXML
    public void initialize() {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        // заполняем таблицу данными
        table.setItems(obsList);
    }

    @FXML
    public void pressOnButton() {
        System.out.println("Нажали на кнопку");
        String login = textField.getText();
        if (!login.isEmpty()) {
            Person person = new Person(login);          // Создаем новую запись пользователя
            if (person.isExist()) {
                label.setText(person.getInfoAsString());
                if (map.containsKey(person.getId())) {
                    list = map.get(person.getId());
                    list.add(person);
                    map.put(person.getId(), list);
                    System.out.println("Обновлена запись " + person.getUserName() + " - id - " + person.getId());
                } else {
                    list = new ArrayList<>();
                    list.add(person);
                    map.put(person.getId(), list);
                    System.out.println("Добавлена запись " + person.getUserName() + " - id - " + person.getId());
                }
            } else label.setText("Такого пользователя не существует!");
        } else label.setText("Введите логин!");
        initData();
    }

    private void initData() {
        Set<Map.Entry<Long, List<Person>>> set = map.entrySet();
        for (Map.Entry<Long, List<Person>> me : set){
            obsList.add(new PersonObsList(me.getValue().get(0).getUserName(), me.getKey()));
        }
    }

    public static void startMain(){
        // Если файл есть, пытаемся его десюрилизовать, если нет, создаем новую коллекцию.
        File file = new File(Tunes.dbFile.getTune());
        if (file.exists()){
            try (FileInputStream fis = new FileInputStream(Tunes.dbFile.getTune());
                 ObjectInputStream in = new ObjectInputStream(fis))
            {
                map = (HashMap<Long, List<Person>>) in.readObject();
            } catch (IOException io){
                System.err.println("IOException\n" + io);
                map = new HashMap<>();
            } catch (ClassNotFoundException cnfe){
                System.err.println("ClassNotFoundException\n" + cnfe);
                map = new HashMap<>();
            }
        } else {
            map = new HashMap<>();
        }
    }

    public static void endMain(){
        try (FileOutputStream fos = new FileOutputStream(Tunes.dbFile.getTune());
             ObjectOutputStream out = new ObjectOutputStream(fos))
        {
            out.writeObject(map);
        } catch (IOException io){
            System.err.println("IOException при попытке прочитать\\найти " + Tunes.dbFile.getTune() + " файл");
            io.printStackTrace();
        }
    }
}
