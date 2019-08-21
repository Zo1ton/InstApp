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
        List<String> after = getLists(userId, 3);
        List<String> before = getLists(userId, 0);
        matchLists(before, after);
    }

    private List<String> getLists(long userId, int listNumber) {
        LOG.info("Запустили метод getLists2");
        List<String> list = new ArrayList<>();
        List<List<String>> allFollowersLists = getAllFollowersLists(userId);
        if (allFollowersLists.isEmpty() || allFollowersLists.size() < 2) {
            LOG.info("Меньше двух списков");
        } else {
            try {
                list = allFollowersLists.get(listNumber);
            } catch (IndexOutOfBoundsException e) {
                LOG.info("Нет списка с таким номером");
                LOG.trace(e);
            }
        }
        return list;
    }

    private List<List<String>> getAllFollowersLists(long userId) {
        List<Person> personList = db.getMap().get(userId);
        List<List<String>> allFollowersLists = new ArrayList<>();
        for (Person person : personList) {
            Map<Date, Map<Long, String>> mapFollows = person.getMapFollows();
            if (mapFollows != null) {
                for (Map.Entry<Date, Map<Long, String>> entry : mapFollows.entrySet()) {
                    List<String> followersLists = new ArrayList<>();
                    for (Map.Entry<Long, String> map : entry.getValue().entrySet()) {
                        followersLists.add(map.getValue());
                    }
                    allFollowersLists.add(followersLists);
                }
            }
        }
        LOG.info("allFollowersLists.size()=" + allFollowersLists.size());
        return allFollowersLists;
    }
}
