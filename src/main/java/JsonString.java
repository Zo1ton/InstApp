import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Andrey on 07.06.2017.
 */
public class JsonString {

    private String instJson;    // Информация о странице в формате JSON

    public String getInstJson() {
        return instJson;
    }

    public void getJson(String login) {
        String instName = login;    // Имя аккаунта инстаграмм
        String instAccJson;         // Строка json

        try { //Не знаю как объяснить, но команда важная
            URL url = new URL(" https://www.instagram.com/" + instName); //Открываем страницу инстаграмма
            BufferedReader br = new BufferedReader( //Создаём новый буффер
                    new InputStreamReader(url.openStream()));//Читаем страницу
            String line;//Создаём строковое значение
            String instpage = null;
            while ((line = br.readLine()) != null)//повторяем много раз, если есть ещё строка, тогда записываем её...
                instpage = instpage + line;
            br.close(); //...и закрываем буффер

            int x = instpage.indexOf(Tunes.startjson.getTune()) + Tunes.startjson.getTune().length();
            int y = instpage.indexOf(Tunes.endjson.getTune(), x);
            instAccJson = instpage.substring(x, y);
            this.instJson = instAccJson;

        } catch (MalformedURLException me) { //Если же такого хоста, сайта, не существует
            System.err.println("Unknown host: " + me); //Пишем ошибку
            System.exit(0); //И выходим

        } catch (IOException ioe) {//Если невозможно присоедениться к хосту
            System.err.println("Input error: " + ioe); //и пишем ошибку
            System.out.println("Не найден логин " + login);
        }
    }
}