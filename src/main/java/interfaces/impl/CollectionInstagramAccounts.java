package interfaces.impl;

import interfaces.ICollectionInstagramAccounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Person;

import java.util.List;
import java.util.Map;

public class CollectionInstagramAccounts implements ICollectionInstagramAccounts {

    private ObservableList<Person> instagramList = FXCollections.observableArrayList();

    public ObservableList<Person> getInstagramList() {
        return instagramList;
    }

    public void updateList(Map<Long, List<Person>> map) {
        instagramList.clear();
        map.forEach((k, v) -> instagramList.add(v.get(v.size() - 1)));
    }

    @Override
    public void add(Person person) {
        instagramList.add(person);
    }

    @Override
    public void update(Person p) {
    }

    @Override
    public void delete(Person person) {
    }
}