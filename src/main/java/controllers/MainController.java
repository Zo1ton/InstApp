package controllers;

import interfaces.impl.CollectionInstagramAccounts;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Person;
import org.apache.log4j.Logger;
import start.Tunes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class MainController {

    private static final Logger LOG = Logger.getLogger(MainController.class);

    private static Map<Long, List<Person>> map = new HashMap<>();
    private List<Person> list = new ArrayList<>();
    private CollectionInstagramAccounts tableList = new CollectionInstagramAccounts();

    @FXML private TextField textField;
    @FXML private Label labelInfo;
    @FXML private Label labelCount;
    @FXML private CheckBox cbFol;
    @FXML private TableView<Person> table;
    @FXML private TableColumn<Person, Long> idCol;
    @FXML private TableColumn<Person, String> loginCol;
    @FXML private TableColumn<Person, Integer> postsCol;
    @FXML private TableColumn<Person, Integer> followersCol;
    @FXML private TableColumn<Person, Integer> followingCol;

    @FXML
    public void initialize() {
        tableList.updateList(map);
        updateLabelCount();
        // устанавливаем тип и значения которые должны хранится в колонках
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        postsCol.setCellValueFactory(new PropertyValueFactory<>("posts"));
        followingCol.setCellValueFactory(new PropertyValueFactory<>("followedBy"));
        followersCol.setCellValueFactory(new PropertyValueFactory<>("follows"));

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
    public void pressOnButton() {
        LOG.info("Нажали на кнопку Запросить");
        String login = textField.getText().isEmpty() ? table.getSelectionModel().getSelectedItem().getUserName() : textField.getText();
        if (!login.isEmpty()) {
            Person person = new Person(login);          // Создаем новую запись пользователя
            if (person.isExist()) {
                labelInfo.setText(person.getInfoAsString());
                if (map.containsKey(person.getId())) {
                    list = map.get(person.getId());
                    list.add(person);
                    map.put(person.getId(), list);
                    LOG.info("Обновлена запись " + person.getUserName() + " - id - " + person.getId());
                } else {
                    list = new ArrayList<>();
                    list.add(person);
                    map.put(person.getId(), list);
                    LOG.info("Добавлена запись " + person.getUserName() + " - id - " + person.getId());
                }
            } else {
                labelInfo.setText("Такого пользователя не существует!");
            }
        } else {
            labelInfo.setText("Введите логин!");
        }
        tableList.updateList(map);
    }

    public static void startMain() {
        // Если файл есть, пытаемся его десюрилизовать, если нет, создаем новую коллекцию.
        File file = new File(Tunes.dbFile.getTune());
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(Tunes.dbFile.getTune());
                 ObjectInputStream in = new ObjectInputStream(fis)) {
                map = (Map) in.readObject();
            } catch (IOException io) {
                io.printStackTrace();
                map = new HashMap<>();
            } catch (ClassNotFoundException cnfe) {
                LOG.error("ClassNotFoundException\n" + cnfe);
                map = new HashMap<>();
            }
        } else {
            map = new HashMap<>();
        }
    }

    public static void endMain() {
        try (FileOutputStream fos = new FileOutputStream(Tunes.dbFile.getTune());
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(map);
        } catch (IOException io) {
            LOG.error("IOException при попытке прочитать\\найти " + Tunes.dbFile.getTune() + " файл");
            io.printStackTrace();
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
        for (Map.Entry<Long, List<Person>> entry : map.entrySet()) {
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
        long id = table.getSelectionModel().getSelectedItem().getId();
        List<Person> lp = map.get(id);
        if (lp.size() < 2) {
            LOG.info("Недостаточно записей");
        } else {
            Person pOld = lp.get(lp.size() - 2);
            Person pNew = lp.get(lp.size() - 1);
            List<String> lOld = pOld.getListFollowedBy();
            List<String> lNew = pNew.getListFollowedBy();

            for (String str : lOld) {
                if (!lNew.contains(str)) {
                    LOG.info("Отписался пользователь - " + str);
                }
            }

            for (String str : lNew) {
                if (!lOld.contains(str)) {
                    LOG.info("Подписался пользователь - " + str);
                }
            }
        }
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

    private void createModalWindow (ActionEvent actionEvent, String title, String controller) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(controller));
            stage.setTitle(title);
            Image ico = new Image("images/InstICO_180_180.png");
            stage.getIcons().add(ico);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((MenuItem)actionEvent.getSource()).getParentPopup().getOwnerWindow().getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}