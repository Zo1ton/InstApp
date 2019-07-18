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
     * Если уже есть мапа с таким пользователем, тогда добавляем пользователя в массив,
     * если нет тогда создаем новый массив
     * @param person - пользователь инстаграма
     */
    public void putPersonToMap(Person person) {
        long personId = person.getId();
        List<Person> list;
        if (map.containsKey(personId)) {
            list = map.get(personId);
            list.add(person);
            map.put(personId, list);
            LOG.info("Обновлена запись " + person.getUserName() + " - id - " + personId);
        } else {
            list = new ArrayList<>();
            list.add(person);
            map.put(personId, list);
            LOG.info("Добавлена запись " + person.getUserName() + " - id - " + personId);
        }
    }

    public void removePersonFromMapById (long personId) {
        String userName = map.get(personId).get(0).getUserName();
        map.remove(personId);
        LOG.info("Удален пользователь " + userName + " - id - " + personId);
    }
}
