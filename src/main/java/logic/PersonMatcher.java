package logic;

import controllers.ext.BaseController;
import objects.Person;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

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
        List<String> after = getLists(userId, 1578229931148L);  //после
        List<String> before = getLists(userId, 1578491404442L); //до
        matchLists(before, after);
    }

    private List<String> getLists(long userId, Long date) {
        LOG.info("Запустили метод getLists");
        List<String> list = new ArrayList<>();
        Map<Long, List<String>> allFollowersLists = getAllFollowersLists(userId);
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

    private Map<Long, List<String>> getAllFollowersLists(long userId) {
        List<Map<Date, Map<Long, String>>> list = getAllMapFollowsThisPerson(userId);
        Map<Long, List<String>> map = getMap(list);
        TreeMap<Long, List<String>> sorted = new TreeMap(map);
        sorted.forEach((date, listPerson) -> {
            LOG.info(date + " " + listPerson.size() + " -- " + new Date(date));
        });
        return getMap(list);
    }

    /**
     * На вход подаем id пользователя
     * На выходе получаем коллекцию пользователей, это история состояний этого пользователя.
     */
    private List<Map<Date, Map<Long, String>>> getAllMapFollowsThisPerson(long userId) {
        db.getMap().get(userId);
        return db.getMap().get(userId).stream()
                .map(Person::getMapFollows)
                .collect(Collectors.toList());
    }

    private Map<Long, List<String>> getMap(List<Map<Date, Map<Long, String>>> list) {
        Map<Long, List<String>> map = new HashMap<>();
        for (Map<Date, Map<Long, String>> mapMap : list) {
            if (mapMap != null) {
                for (Map.Entry<Date, Map<Long, String>> entry : mapMap.entrySet()) {
                    map.put(entry.getKey().getTime(), getListFollowers(entry.getValue()));
                }
            }
        }
        return map;
    }

    /**
     * Из мапы возвращаем только values в виде ArrayList
     */
    private List<String> getListFollowers(Map<Long, String> map) {
        return new ArrayList<>(map.values());
    }
}
