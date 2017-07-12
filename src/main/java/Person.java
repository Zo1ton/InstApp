import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Andrey on 07.06.2017.
 */
public class Person {

    private String instgson;    // Информация о странице в формате JSON

    public String getInstgson() {
        return instgson;
    }

    public void getJson(String login){
        String instname = login;                    // Имя аккаунта инстаграмм
        String instaccjson;
        try(FileWriter writer = new FileWriter(Tunes.savedir.getTune() + instname + ".txt", false))
        {

            try{ //Не знаю как объяснить, но команда важная
                URL site = new URL(" https://www.instagram.com/" + instname); //Открываем страницу инстаграмма
                BufferedReader br = new BufferedReader( //Создаём новый буффер
                        new InputStreamReader(site.openStream()));//Читаем страницу
                String line;//Создаём строковое значение
                String instpage = null;
                while ((line = br.readLine()) != null)//повторяем много раз, если есть ещё строка, тогда записываем её...
                    instpage = instpage + line;
                writer.write(instpage);
                br.close(); //...и закрываем буффер
                int x, y;
                x = instpage.indexOf(Tunes.startjson.getTune()) + Tunes.startjson.getTune().length();
                y = instpage.indexOf(Tunes.endjson.getTune(), x);
                instaccjson = instpage.substring(x, y);
                FileWriter writerjson = new FileWriter(Tunes.savedir.getTune() + instname + "_JSON.txt", false);
                writerjson.write(instaccjson);
                this.instgson = instaccjson;
                //System.out.println(instaccjson);


            }
            catch(MalformedURLException me){ //Если же такого хоста, сайта, не существует
                System.err.println("Unknown host: " + me); //Пишем ошибку
                System.exit(0); //И выходим
            }catch(IOException ioe){//Если невозможно присоедениться к хосту
                System.err.println("Input error: " + ioe); //и пишем ошибку
            }

        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    /**
     * На вход метода передается логин instagram
     * На выходе получаем строку с информацией об аккаунте в формате json
     */
    public static String getPage(String login) {
        String linef = null;
        try (FileWriter writer = new FileWriter(Tunes.savedir.getTune() + "instagramGetJSON.txt", false)) {
            try {
                URL url = new URL("https://www.instagram.com/" + login + "/");
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String line;
                while ((line = br.readLine()) != null)
                    linef += line;   // страница сохранена в строку
                writer.write(linef);    // здесь происходит запись в файл
                br.close();
            } catch (MalformedURLException me) {
                System.err.println("Unknown host: " + me);
                System.exit(0);
            } catch (IOException ioe) {
                System.err.println("Input error: " + ioe);
                System.out.println("Не найден логин " + login);
            }

        } catch (IOException ex) {
            System.out.println("ex.getMessage()" + ex);
        }
        return linef;
    }
}