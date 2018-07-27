package controllers;

import controllers.ext.BaseController;
import interfaces.impl.CollectionInstagramAccounts;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import objects.Person;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class MainController extends BaseController {

    private static final Logger LOG = Logger.getLogger(MainController.class);

    private CollectionInstagramAccounts tableList = new CollectionInstagramAccounts();

    @FXML private TextField textField;
    @FXML private Label labelInfo;
    @FXML private Label labelCount;
    @FXML private TableView<Person> table;
    @FXML private TableColumn<Person, Long> idColumn;
    @FXML private TableColumn<Person, String> loginColumn;
    @FXML private TableColumn<Person, Integer> postsColumn;
    @FXML private TableColumn<Person, Integer> followersColumn;
    @FXML private TableColumn<Person, Integer> followingColumn;

    @FXML
    public void initialize() {
        tableList.updateList(db.getMap());
        updateLabelCount();
        // устанавливаем тип и значения которые должны хранится в колонках
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        postsColumn.setCellValueFactory(new PropertyValueFactory<>("posts"));
        followingColumn.setCellValueFactory(new PropertyValueFactory<>("followedBy"));
        followersColumn.setCellValueFactory(new PropertyValueFactory<>("follows"));

        tableList.getInstagramList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateLabelCount();
            }
        });

        // заполняем таблицу данными
        table.setItems(tableList.getInstagramList());
    }

    @FXML
    public void pressRequestButton() {
        LOG.info("Нажали на кнопку Запросить");
        String login = textField.getText().isEmpty() ? table.getSelectionModel().getSelectedItem().getUserName() : textField.getText();
        if (!login.isEmpty()) {
            Person person = new Person(login);          // Создаем новую запись пользователя
            if (person.isExist()) {
                labelInfo.setText(person.getInfoAsString());
                putPersonToMap(person);
            } else {
                labelInfo.setText("Такого пользователя не существует!");
            }
        } else {
            labelInfo.setText("Введите логин!");
        }
        tableList.updateList(db.getMap());
    }

    /**
     * Если уже есть мапа с такис пользователем, тогда добавляем пользователя в массив,
     * если нет тогда создаем новый массив
     * @param person - пользователь инстаграма
     */
    private void putPersonToMap(Person person) {
        List<Person> list;
        if (db.getMap().containsKey(person.getId())) {
            list = db.getMap().get(person.getId());
            list.add(person);
            db.getMap().put(person.getId(), list);
            LOG.info("Обновлена запись " + person.getUserName() + " - id - " + person.getId());
        } else {
            list = new ArrayList<>();
            list.add(person);
            db.getMap().put(person.getId(), list);
            LOG.info("Добавлена запись " + person.getUserName() + " - id - " + person.getId());
        }
    }

    private void updateLabelCount() {
        labelCount.setText("Всего записей: " + tableList.getInstagramList().size());
    }

    @FXML
    public void getHistory(ActionEvent actionEvent) {
        try {
            Person selectedPerson = table.getSelectionModel().getSelectedItem();
            labelInfo.setText(getPersonHistory(selectedPerson.getId()));
        } catch (Exception e) {
            labelInfo.setText("Запись не выбрана!");
            LOG.info("Запись не выбрана!", e);
        }
    }

    /**
     * По id аккаунта инстаграм, показывает последнии сохраненные данные, т.е. историю
     * Возвращается истария с последней записи
     * @param id - id пользователя инстаграм
     * @return строка с историей
     */
    private String getPersonHistory(Long id) {
        StringBuilder str = new StringBuilder("");
        for (Map.Entry<Long, List<Person>> entry : db.getMap().entrySet()) {
            if (entry.getKey().equals(id)) {
                for (Person p : entry.getValue()) {
                    str.insert(0, String.format("Date - %s UserName - %s Followers - %,d\n",
                            p.getCREATING_DATE(),
                            p.getUserName(),
                            p.getFollowedBy()));
                }
            }
        }
        return str.toString();
    }

    public void merge(ActionEvent actionEvent) {

    }

    public void showPersonHistory(MouseEvent mouseEvent) {

    }

    @FXML
    public void browseFile(ActionEvent actionEvent) {
        LOG.info("Нажали кнопку 'Обработать подписчиков'");
        createModalWindow(actionEvent, "Загрузка данных из файла", "../fxml/browseFile.fxml");
    }

    @FXML
    public void openAboutForm(ActionEvent actionEvent) {
        createModalWindow(actionEvent, "О программе", "../fxml/aboutForm.fxml");
    }

    @FXML
    public void downloadFollowersFromXML(ActionEvent actionEvent) {
        createModalWindow(actionEvent, "Загрузка подписчиков из XML-файла", "../fxml/downloadFollowersFromXML.fxml");
    }

}