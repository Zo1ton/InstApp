package objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import logic.PageParser;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by SBT-Vdovin-AI on 10.07.2017.
 */
public class Person implements Serializable {

    private static final Logger LOG = Logger.getLogger(Person.class);
    PageParser pageParser = new PageParser();

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

        this.json = pageParser.getPageJsonInfoAsStringByLogin(login);

        if (this.json != null && !this.json.equals("")) {
            this.isExist = true;

            getInfoFromJson();

        } else {
            this.isExist = false;
        }
    }

    public Person(String login, Boolean isGetFollowers) {
        this(login);
        if (isGetFollowers && this.followedBy < 10000) {
            this.listFollowedBy = createListfollowedBy();
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

    private List<String> createListfollowedBy() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        // Открываем гугл, используя драйвер
        driver.get("https://www.instagram.com/accounts/login/");
        // По-другому это можно сделать так:
        // driver.navigate().to("http://www.google.com");

        // Находим элемент по атрибуту name
        WebElement userName = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement btnLogin = driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/span/button"));

        // Вводим текст
        userName.sendKeys("ngageman61");
        password.sendKeys("57055705b");
        btnLogin.click();
//        signIn.submit();

        // Ждем 5 сек, пока загрузится страница
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.navigate().to("https://www.instagram.com/" + this.userName + "/followers/");

        WebElement followers = driver.findElement(By.xpath("//*[@id='react-root']/section/main/article/header/section/ul/li[2]/a"));
        followers.click();

        // Ждем 3 сек пока загрузятся подписчики
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement we = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div[2]/ul/div/li[1]"));
        we.click();

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < (this.followedBy / 6); i++) {
            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<WebElement> elementsList = driver.findElements(By.className("_2nunc"));

        List<String> loginList = new ArrayList<>();
        for (WebElement element : elementsList) {
            loginList.add(element.getText());
            LOG.debug(element.getText());
        }

        // Закрываем браузер
        driver.quit();

        return loginList;
    }
}