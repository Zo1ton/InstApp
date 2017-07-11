import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

/**
 * Created by SBT-Vdovin-AI on 10.07.2017.
 */
public class PersonJson {
    private String json;
    private int followedBy;

    public void setJson(String json) {
        this.json = json;
    }

    public void gettingInfo(){
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(json);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject pages = rootObject.getAsJsonObject("entry_data");
        JsonArray array = pages.getAsJsonArray("ProfilePage");
        rootElement = array.get(0);
        rootObject = rootElement.getAsJsonObject();
        pages = rootObject.getAsJsonObject("user").getAsJsonObject("followed_by");
        rootElement = pages.get("count");
        this.followedBy = rootElement.getAsInt();

//        for (Map.Entry<String,JsonElement> entry : pages.entrySet()){
//            JsonObject entryObject = entry.getValue().getAsJsonObject();
//            array = entryObject.getAsJsonArray("ProfilePage");
//        }
    }
}
