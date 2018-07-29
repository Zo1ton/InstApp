package objects;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {

    private static final Logger LOG = Logger.getLogger(DataBase.class);

    private static Map<Long, List<Person>> map = new HashMap<>();

    public Map<Long, List<Person>> getMap() {
        return map;
    }

    public void setMap(Map<Long, List<Person>> map) {
        DataBase.map = map;
    }

    /**
     * Если уже есть мапа с такис пользователем, тогда добавляем пользователя в массив,
     * если нет тогда создаем новый массив
     * @param person - пользователь инстаграма
     */
    public void putPersonToMap(Person person) {
        List<Person> list;
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
    }
}
