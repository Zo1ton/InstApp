package controllers;

import interfaces.impl.PersonHistory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.DataBase;
import objects.Person;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class PersonHistoryController implements Initializable {

    private static final Logger LOG = Logger.getLogger(PersonHistoryController.class);

    private PersonHistory tableList = new PersonHistory();

    @FXML private TableView<Person> tablePersonHistory;
    @FXML private TableColumn<Person, CheckBox> select;
    @FXML private TableColumn<Person, Date> dateColumn;
    @FXML private TableColumn<Person, Long> followersColumn;
    @FXML private TableColumn<Person, Boolean> isHistoryExistsColumn;

    private DataBase db = new DataBase();
    static long personId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Выбран пользователь Id - " + personId);
        String userName = db.getActualPersonById(personId).getUserName();
        LOG.info("Выбран пользователь userName - " + userName);

        tableList.updateList(db.getMap().get(personId));

        select.setCellValueFactory(new PropertyValueFactory<>("select"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("CREATING_DATE"));
        followersColumn.setCellValueFactory(new PropertyValueFactory<>("followedBy"));
        isHistoryExistsColumn.setCellValueFactory(new PropertyValueFactory<>("isHistoryExistsColumn"));

        tableList.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
//                tablePersonHistory();
            }
        });

        // заполняем таблицу данными
        tablePersonHistory.setItems(tableList.getPersonList());
    }
}
