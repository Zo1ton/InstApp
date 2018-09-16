package controllers;

import controllers.ext.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.DataBase;
import objects.Person;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PersonHistoryController extends BaseController implements Initializable {

    @FXML private TableView<Person> tablePersonHistory;
    @FXML private TableColumn<Person, Date> dateColumn;
    @FXML private TableColumn<Person, Long> followersColumn;
    @FXML private TableColumn<Person, Boolean> isHistoryExistsColumn;

    private DataBase db = new DataBase();
    private ActionEvent actionEvent;
    private long personId;

    public PersonHistoryController(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        List<Person> person = db.getMap().get(personId);
//        dateColumn.setText("888");
    }

    public void showUserHistory(Long userId) {
//        this.personId = userId;
        createModalWindow(actionEvent, "Просмотр истории пользователя", "../fxml/personHistory.fxml");
    }

}
