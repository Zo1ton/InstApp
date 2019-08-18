package logic;

import controllers.ext.BaseController;
import objects.Person;
import org.apache.log4j.Logger;

import java.util.*;

public class PersonMatcher extends BaseController {

    static long userId;

    private static final Logger LOG = Logger.getLogger(PersonMatcher.class);

    /**
     * @param before - пользователи до
     * @param after  - пользователи после
     */
    private void matchLists(List<String> before, List<String> after) {
        for (String name : before) {
            if (after.contains(name)) {
                after.remove(name);
                before.remove(name);
            }
        }
        LOG.info("Отписались пользователи - " + before.toString());
        LOG.info("Подписались пользователи - " + after.toString());
    }

    public void matchPerson(long userId) {
        List<String> after = getLists(userId, 1);
        List<String> before = getLists(userId, 2);
        matchLists(before, after);
    }

    private List<String> getLists(long userId, long counter) {
        LOG.info("Запустили метод getLists");
        long d = 1;
        Person person = db.getActualPersonById(userId);
        List<String> values = new ArrayList<>();
        Map<Date, Map<Long, String>> mapFollows = person.getMapFollows();
        if (mapFollows.isEmpty() || mapFollows.size() < 2) {
            LOG.info("Меньше двух списков");
        } else {
            long mapSize = mapFollows.size();
            for (Map.Entry<Date, Map<Long, String>> entry : mapFollows.entrySet()) {
                for (Map.Entry<Long, String> map : entry.getValue().entrySet()) {
                    if (d == counter) {
                        values.add(map.getValue());
                    }
                }
                d++;
            }
        }
        return values;
    }
}
