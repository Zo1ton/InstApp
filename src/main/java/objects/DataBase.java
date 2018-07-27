package objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {

    private static Map<Long, List<Person>> map = new HashMap<>();

    public Map<Long, List<Person>> getMap() {
        return map;
    }

    public void setMap(Map<Long, List<Person>> map) {
        DataBase.map = map;
    }
}
