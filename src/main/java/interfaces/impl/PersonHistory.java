package interfaces.impl;

import interfaces.IPersonHistoryList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Person;

import java.util.List;

public class PersonHistory implements IPersonHistoryList {

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public void updateList(List<Person> list) {
        personList.clear();
        list.forEach(p -> personList.add(p));
    }

}
