import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by SBT-Vdovin-AI on 10.07.2017.
 */
public class Person implements Serializable{
    private final Date CREATING_DATE = new Date();
    private transient String json;
    private int followedBy;
    private int follows;
    private int posts;
    private long id;
    private String userName;
    private String fullName;
    private String biography;
    private boolean isPrivate;
    private boolean isVerified;
    private boolean isExist;

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }

    public boolean isExist() {
        return isExist;
    }

    public Person(String login) {       //По логину вытягиваем json из html страницы

        try {
            URL url = new URL(" https://www.instagram.com/" + login); //Открываем страницу инстаграмма
            BufferedReader br = new BufferedReader(         //Создаём новый буффер
                    new InputStreamReader(url.openStream()));  //Читаем страницу
            String line;                                    //Создаём строковое значение
            StringBuilder instpage = new StringBuilder();
            while ((line = br.readLine()) != null)          //повторяем много раз, если есть ещё строка, тогда записываем её...
                instpage.append(line);
            br.close();                                     //...и закрываем буффер

            int x = instpage.indexOf(Tunes.startjson.getTune()) + Tunes.startjson.getTune().length();
            int y = instpage.indexOf(Tunes.endjson.getTune(), x);
            this.json = instpage.substring(x, y);

        } catch (MalformedURLException me) {                //Если же такого хоста, сайта, не существует
            System.err.println("Unknown host: " + me);      //Пишем ошибку
            System.exit(0);                              //И выходим

        } catch (IOException ioe) {                         //Если невозможно присоедениться к хосту
            System.err.println("Input error: " + ioe);      //и пишем ошибку
            System.out.println("Не найден логин " + login);
        }
        if (this.json != null) {
            this.isExist = true;
            getInfoFromJson();
        }
        else this.isExist = false;
    }

    public void getInfoFromJson(){
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

        this.biography = (pages.get("biography").isJsonNull() ? "null" : pages.get("biography").getAsString());
        this.id = pages.get("id").getAsLong();
        this.userName = pages.get("username").getAsString();
        this.fullName = ( pages.get("full_name").isJsonNull() ? "null" : pages.get("full_name").getAsString());
        this.isPrivate = pages.get("is_private").getAsBoolean();
        this.isVerified = pages.get("is_verified").getAsBoolean();
    }

    public String getInfoAsString(){

        if (this.json == null) {
            return new String("Не найден логин");
        }

        else

    return String.format("Подписчики - %,d\nПодписки - %d\nПосты - %d\nЛогин - %s\nИмя - %s\nБиография - %s\nid - %d\n" +
                        (this.isPrivate ? "Закрытая страница" : "Открытая страница") + "\n" +
                        (this.isVerified ? "Верифицированно" : "Не верифицированно") + "\n" +
                        "Дата создания - %s",
                this.followedBy, this.follows, this.posts, this.userName, this.fullName, this.biography, this.id, this.CREATING_DATE);
    }
}