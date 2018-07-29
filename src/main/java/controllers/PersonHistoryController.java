package controllers;

import controllers.ext.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.DataBase;
import objects.Person;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class PersonHistoryController extends BaseController implements Initializable {

    private DataBase db = new DataBase();

    @FXML private TableView<Person> tablePersonHistory;
    @FXML private TableColumn<Person, String> colData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    private Person getPerson() {
//        String name = selectedPerson;
//        Map map = db.getMap();
//        map.get()
//        return
//    }
}
