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
        this.id = pages.get("id").getAsLong();
        this.userName = pages.get("username").getAsString();
        this.fullName = pages.get("full_name").getAsString();
        this.isPrivate = pages.get("is_private").getAsBoolean();

        System.out.println("this.followedBy = " + this.followedBy);
        System.out.println("this.follows = " + this.follows);
        System.out.println("this.posts = " + this.posts);
        System.out.println("this.id = " + this.id);
        System.out.println("this.userName = " + this.userName);
        System.out.println("this.fullName = " + this.fullName);
        System.out.println( this.isPrivate == true ? "Закрытая страница" : "Открытая страница");

//        for (Map.Entry<String,JsonElement> entry : pages.entrySet()){
//            JsonObject entryObject = entry.getValue().getAsJsonObject();
//            array = entryObject.getAsJsonArray("ProfilePage");
//        }
    }
}
