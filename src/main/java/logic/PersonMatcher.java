package logic;

import controllers.ext.BaseController;
import objects.Person;
import org.apache.log4j.Logger;

import java.util.*;

public class PersonMatcher extends BaseController {

    private static final Logger LOG = Logger.getLogger(PersonMatcher.class);

    /**
     * @param before - пользователи до
     * @param after  - пользователи после
     */
    private void matchLists(List<String> before, List<String> after) {
        List<String> added = new ArrayList<>();
        List<String> deleted = new ArrayList<>();
        for (String name : before) {
            if (!after.contains(name)) {
                deleted.add(name);
            }
        }
        for (String name : after) {
            if (!before.contains(name)) {
                added.add(name);
            }
        }
        LOG.info("Отписались пользователи - " + deleted.toString());
        LOG.info("Подписались пользователи - " + added.toString());
    }

    public void matchPerson(long userId) {
        List<String> after = getLists(userId, "Thu Oct 17 19:37:50 MSK 2019");
        List<String> before = getLists(userId, "Wed Jul 31 18:39:59 MSK 2019");
        matchLists(before, after);
    }

    private List<String> getLists(long userId, String date) {
        LOG.info("Запустили метод getLists2");
        List<String> list = new ArrayList<>();
        Map<String, List<String>> allFollowersLists = getAllFollowersLists(userId);
        if (allFollowersLists.isEmpty() || allFollowersLists.size() < 2) {
            LOG.info("Меньше двух списков");
        } else {
            try {
                list = allFollowersLists.get(date);
            } catch (IndexOutOfBoundsException e) {
                LOG.info("Нет списка с таким номером");
                LOG.trace(e);
            }
        }
        return list;
    }

    private Map<String, List<String>> getAllFollowersLists(long userId) {
        List<Map<Date, Map<Long, String>>> list = getAllMapFollowsThisPerson(userId);
        Map<String, List<String>> map = getMap(list);
        map.forEach((date, listPerson) -> {
            LOG.info(date + " " + listPerson.size());
        });
        return getMap(list);
    }

    /**
     * На вход подаем id пользователя
     * На выходе получаем коллекцию пользователей
     */
    private List<Map<Date, Map<Long, String>>> getAllMapFollowsThisPerson(long userId) {
        List<Map<Date, Map<Long, String>>> list = new ArrayList<>();
        for (Person person : db.getMap().get(userId)) {
            list.add(person.getMapFollows());
        }
        return list;
    }

    private Map<String, List<String>> getMap(List<Map<Date, Map<Long, String>>> list) {
        Map<String, List<String>> map = new HashMap<>();
        for (Map<Date, Map<Long, String>> mapMap : list) {
            if (mapMap != null) {
                for (Map.Entry<Date, Map<Long, String>> entry : mapMap.entrySet()) {
                    map.put(entry.getKey().toString(), getListFollowers(entry.getValue()));
                }
            }
        }
        return map;
    }

    private List<String> getListFollowers(Map<Long, String> longStringMap) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<Long, String> map : longStringMap.entrySet()) {
            list.add(map.getValue());
        }
        return list;
    }


}
