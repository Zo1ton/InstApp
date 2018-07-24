package objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import logic.PageParser;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by SBT-Vdovin-AI on 10.07.2017.
 */
public class Person implements Serializable {

    private static final transient Logger LOG = Logger.getLogger(Person.class);

    private final Date CREATING_DATE = new Date();
    private transient String json;
    private int followedBy;     // Подписчики (сколько на аккаунт людей подписано)
    private List<String> listFollowedBy = new ArrayList<>();
    private int follows;        // Подписки (на скольких подписан)
    private int posts;
    private long id;
    private String userName;
    private String fullName;
    private String biography;
    private boolean isPrivate;
    private boolean isVerified;
    private boolean isExist;

    public Date getCREATING_DATE() {
        return CREATING_DATE;
    }

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }

    public int getFollowedBy() {
        return followedBy;
    }

    public int getFollows() {
        return follows;
    }

    public int getPosts() {
        return posts;
    }

    public boolean isExist() {
        return isExist;
    }

    public List<String> getListFollowedBy() {
        return listFollowedBy;
    }

    public String getJson() {
        return json;
    }

    /**
     * По логину получаем json из html кода страницы, затем парсим json и получаем информацию о странице
     *
     * @param login логин инстаграм
     */
    public Person(String login) {

        PageParser pageParser = new PageParser();
        this.json = pageParser.getPageJsonInfoAsStringByLogin(login);

        if (this.json != null && !this.json.equals("")) {
            this.isExist = true;

            getInfoFromJson();

        } else {
            this.isExist = false;
        }
    }

    private void getInfoFromJson() {
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(json);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject pages = rootObject.getAsJsonObject("entry_data");
        JsonArray array = pages.getAsJsonArray("ProfilePage");
        rootElement = array.get(0);
        rootObject = rootElement.getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("user");

        pages = rootObject.getAsJsonObject("edge_followed_by");
        this.followedBy = pages.get("count").getAsInt();

        pages = rootObject.getAsJsonObject("edge_follow");
        this.follows = pages.get("count").getAsInt();

        pages = rootObject.getAsJsonObject("edge_owner_to_timeline_media");
        this.posts = pages.get("count").getAsInt();

        pages = rootObject;

        this.biography = (pages.get("biography").isJsonNull() ? "null" : pages.get("biography").getAsString());
        this.id = pages.get("id").getAsLong();
        this.userName = pages.get("username").getAsString();
        this.fullName = (pages.get("full_name").isJsonNull() ? "null" : pages.get("full_name").getAsString());
        this.isPrivate = pages.get("is_private").getAsBoolean();
        this.isVerified = pages.get("is_verified").getAsBoolean();
    }

    public String getInfoAsString() {

        if (this.json == null) {
            return String.format("%s", "Не найден логин");
        } else

            return String.format("Подписчики - %,d\nПодписки - %d\nПосты - %d\nЛогин - %s\nИмя - %s\nБиография - %s\nid - %d\n" +
                            (this.isPrivate ? "Закрытая страница" : "Открытая страница") + "\n" +
                            (this.isVerified ? "Верифицированно" : "Не верифицированно") + "\n" +
                            "Дата создания - %s",
                    this.followedBy, this.follows, this.posts, this.userName, this.fullName, this.biography, this.id, this.CREATING_DATE);
    }

}