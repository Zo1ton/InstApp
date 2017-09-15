package interfaces.impl;

import interfaces.ICollectionInstagramAccounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import objects.Person;

public class CollectionInstagramAccounts implements ICollectionInstagramAccounts {

    private ObservableList<Person> instagramList = FXCollections.observableArrayList();
    private ObservableMap<Long, Person> instagramMap = FXCollections.observableHashMap();

    public ObservableList<Person> getInstagramList() {
        return instagramList;
    }

    public ObservableMap<Long, Person> getInstagramMap() {
        return instagramMap;
    }

    @Override
    public void add(Person person) {
        instagramList.add(person);
        instagramMap.put(person.getId(), person);
    }

    @Override
    public void update(long l) {

    }

    @Override
    public void delete(Person person) {

    }
}
