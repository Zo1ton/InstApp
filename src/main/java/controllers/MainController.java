package controllers;

import interfaces.impl.CollectionInstagramAccounts;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import objects.Person;
import start.Tunes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.*;

public class MainController {

    private static Map<Long, List<Person>> map = new HashMap<>();
    private List<Person> list = new ArrayList<>();
    private CollectionInstagramAccounts collectionInstagramAccounts = new CollectionInstagramAccounts();

    @FXML private TextField textField;
    @FXML private Label labelInfo;
    @FXML private Label labelCount;
    @FXML private TableView<Person> table;
    @FXML private TableColumn<Person, Long> idCol;
    @FXML private TableColumn<Person, String> loginCol;
    @FXML private TableColumn<Person, Integer> postsCol;
    @FXML private TableColumn<Person, Integer> followersCol;
    @FXML private TableColumn<Person, Integer> followingCol;

    @FXML
    public void initialize() {
        initData();
        updateLabelCount();
        // устанавливаем тип и значения которые должны хранится в колонках
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        postsCol.setCellValueFactory(new PropertyValueFactory<>("posts"));
        followingCol.setCellValueFactory(new PropertyValueFactory<>("followedBy"));
        followersCol.setCellValueFactory(new PropertyValueFactory<>("follows"));

        collectionInstagramAccounts.getInstagramList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateLabelCount();
            }
        });

        // заполняем таблицу данными
        table.setItems(collectionInstagramAccounts.getInstagramList());
    }

    @FXML
    public void pressOnButton() {
        System.out.println("Нажали на кнопку");
        String login = textField.getText();
        if (!login.isEmpty()) {
            Person person = new Person(login);          // Создаем новую запись пользователя
            if (person.isExist()) {
                labelInfo.setText(person.getInfoAsString());
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
            } else labelInfo.setText("Такого пользователя не существует!");
        } else labelInfo.setText("Введите логин!");
        initData();
    }

    private void initData() {
        for (Map.Entry<Long, List<Person>> entry : map.entrySet())
            if (!collectionInstagramAccounts.getInstagramMap().containsKey(entry.getKey())) {
                collectionInstagramAccounts.add(entry.getValue().get(entry.getValue().size() - 1));
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

    private void updateLabelCount(){
        labelCount.setText("Всего записей: " + collectionInstagramAccounts.getInstagramMap().size());
    }
}