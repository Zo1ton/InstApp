package start;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDrv {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        // Открываем гугл, используя драйвер
        driver.get("https://www.instagram.com/accounts/login/");
        // По-другому это можно сделать так:
        // driver.navigate().to("http://www.google.com");

        // Находим элемент по атрибуту name
//        WebElement element = driver.findElement(By.name("q"));
//        WebElement userName = driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[1]/div/input"));
        WebElement userName = driver.findElement(By.name("username"));
//        WebElement password = driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[2]/div/input"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement signIn = driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/span/button"));

        // Вводим текст
        userName.sendKeys("ngageman61");
        password.sendKeys("57055705b");
        signIn.click();
//        signIn.submit();

        Thread.sleep(5000);

        driver.navigate().to("https://www.instagram.com/ngageman61/");

        WebElement followers = driver.findElement(By.xpath("//*[@id='react-root']/section/main/article/header/div[2]/ul/li[2]/a"));

        followers.click();

        // Отправляем форму, при этом дравер сам определит как отправить форму по элементу
//        element.submit();

        // Проверяем тайтл страницы
        System.out.println("Page title is: " + driver.getTitle());

        // Страницы гугл динамически отрисовывается с помощью javascript
        // Ждем загрузки страницы с таймаутом в 10 секунд
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("selenium");
            }
        });

        // Ожидаем увидеть: "Selenium - Google Search"
        System.out.println("Page title is: " + driver.getTitle());

        // Закрываем браузер
        driver.quit();
    }

}
