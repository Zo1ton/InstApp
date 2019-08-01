package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import objects.DataBase;
import org.apache.log4j.Logger;

public class DataBaseToJson {

    private static final Logger LOG = Logger.getLogger(DataBaseToJson.class);
    private DataBase db = new DataBase();

    public void exportDBtoJson() {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(db.getMap());
        LOG.info(json);
    }
}
