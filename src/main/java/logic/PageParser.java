package logic;

import objects.Person;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class PageParser {

    private static final Logger LOG = Logger.getLogger(Person.class);
    JsonGetter jsonGetter = new JsonGetter();

    public String getPageJsonInfoAsStringByLogin(String login) {

        String jsonString = "";

        try {
            URL url = new URL(" https://www.instagram.com/" + login); //Открываем страницу инстаграмма
            BufferedReader br = new BufferedReader(         //Создаём новый буффер
                    new InputStreamReader(url.openStream()));  //Читаем страницу
            String line;                                    //Создаём строковое значение
            StringBuilder instpage = new StringBuilder();
            while ((line = br.readLine()) != null)          //повторяем много раз, если есть ещё строка, тогда записываем её...
                instpage.append(line);
            br.close();                                     //...и закрываем буффер

            jsonString = jsonGetter.getJsonFromAccountPage(instpage.toString());

        } catch (MalformedURLException me) {                //Если же такого хоста, сайта, не существует
            LOG.error("Unknown host: " + me);               //Пишем ошибку
            System.exit(0);                          //И выходим

        } catch (IOException ioe) {                         //Если невозможно присоедениться к хосту
            LOG.error("IOException: " + ioe);               //и пишем ошибку
        }
        return jsonString;
    }
}
