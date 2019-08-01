package controllers;

import controllers.ext.BaseController;
import interfaces.impl.CollectionInstagramAccounts;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import objects.Person;
import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
                db.putPersonToMap(person);
            } else {
                labelInfo.setText("Такого пользователя не существует!");
            }
        } else {
            labelInfo.setText("Введите логин!");
        }
        tableList.updateList(db.getMap());
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
     *
     * @param id - id пользователя инстаграм
     * @return строка с историей
     */
    private String getPersonHistory(Long id) {
        StringBuilder str = new StringBuilder();
        db.getMap().forEach((k, v) -> {
            if (k.equals(id)){
                v.forEach((p) ->
                        str.insert(0, String.format("Date - %s %s Post - %,d Followers - %,d Following - %,d\n",
                                p.getCREATING_DATE(),
                                p.getUserName(),
                                p.getPosts(),
                                p.getFollowedBy(),
                                p.getFollows()))
                );
            }
        });
        return str.toString();
    }

    public void merge(ActionEvent actionEvent) {
    }

    public void showPersonHistory(ActionEvent actionEvent) {
        LOG.info("Запустили метод " + new Object(){}.getClass().getEnclosingMethod().getName());
        PersonHistoryController.personId = table.getSelectionModel().getSelectedItem().getId();
        createModalWindow(actionEvent, "Просмотр истории пользователя", "../fxml/personHistory.fxml");
    }

    public void deleteUserFromDB() {
        long userId = table.getSelectionModel().getSelectedItem().getId();
        db.removePersonFromMapById(userId);
        tableList.updateList(db.getMap());
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
        selectedPerson = table.getSelectionModel().getSelectedItem().getUserName();
        createModalWindow(actionEvent, "Загрузка подписчиков из XML-файла", "../fxml/downloadFollowersFromXML.fxml");
    }

    @FXML
    public void downloadFollowersFromList(ActionEvent actionEvent) {
        DownloadFollowersFromListController.userId = table.getSelectionModel().getSelectedItem().getId();
        createModalWindow(actionEvent, "Загрузка списка подписчиков", "../fxml/downloadFollowersFromList.fxml");
    }

}