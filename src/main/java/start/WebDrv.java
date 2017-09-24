package start;

import objects.Person;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebDrv {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
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
        Thread.sleep(5000);

        driver.navigate().to("https://www.instagram.com/ngageman61/followers/");

        WebElement followers = driver.findElement(By.xpath("//*[@id='react-root']/section/main/article/header/div[2]/ul/li[2]/a"));
        followers.click();

        // Ждем 3 сек пока загрузятся подписчики
        Thread.sleep(3000);

        WebElement we = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/div/div[2]/div/div[2]/ul/li[1]/div/div[1]/div/div[2]"));
        we.click();

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <50 ; i++) {
            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            Thread.sleep(300);
        }


//        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 5000)", "");

//        List<WebElement> elementsList = driver.findElements(By.className("_2g7d5 notranslate _o5iw8"));
        List<WebElement> elementsList = driver.findElements(By.className("_2nunc"));
//        List<WebElement> elementsList = driver.findElements(By.tagName("a"));

        List<String> loginList = new ArrayList<>();
        for (WebElement element : elementsList) {
            loginList.add(element.getText());
//            System.out.println(element.getText());
        }

        // Закрываем браузер
        driver.quit();

        Map<Long, String> userMap = new HashMap<>();
        int x = 1;
        for (String login : loginList) {
            Person person = new Person(login, false);
            userMap.put(person.getId(), person.getUserName());
            System.out.println("Обработан " + x++ + " из " + loginList.size());
        }

        // Отправляем форму, при этом дравер сам определит как отправить форму по элементу
        //        element.submit();

        // Ожидаем увидеть: "Selenium - Google Search"
//        System.out.println("Page title is: " + driver.getTitle());
    }

}