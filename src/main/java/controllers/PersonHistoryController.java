package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import objects.Person;

public class PersonHistoryController {
    @FXML private TableView<Person> tablePersonHistory;
    @FXML private TableColumn<Person, String> colData;
}
