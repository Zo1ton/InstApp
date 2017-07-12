import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by SBT-Vdovin-AI on 10.07.2017.
 */
public class PersonJson {
    private String json;
    private int followedBy;
    private int follows;
    private int posts;
    private long id;
    private String userName;
    private String fullName;
    private String biography;
    private boolean isPrivate;

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
        this.followedBy = pages.get("count").getAsInt();

        pages = rootObject.getAsJsonObject("user").getAsJsonObject("follows");
        this.follows = pages.get("count").getAsInt();

        pages = rootObject.getAsJsonObject("user").getAsJsonObject("media");
        this.posts = pages.get("count").getAsInt();

        pages = rootObject.getAsJsonObject("user");
        this.biography = pages.get("biography").getAsString();
        this.id = pages.get("id").getAsLong();
        this.userName = pages.get("username").getAsString();
        this.fullName = pages.get("full_name").getAsString();
        this.isPrivate = pages.get("is_private").getAsBoolean();

//        for (Map.Entry<String,JsonElement> entry : pages.entrySet()){
//            JsonObject entryObject = entry.getValue().getAsJsonObject();
//            array = entryObject.getAsJsonArray("ProfilePage");
//        }
    }

    public String getInfo(){
        return String.format("Подписчики - %d\nПодписки - %d\nПосты - %d\nЛогин - %s\nИмя - %s\nБиография - %s\nid - %d\n" +
                     (this.isPrivate == true ? "Закрытая страница" : "Открытая страница"),
                      this.followedBy, this.follows, this.posts, this.userName, this.fullName, this.biography, this.id);
    }
}
