import com.google.gson.JsonArray;

import javax.swing.JOptionPane;

/**
 * Created by Andrey on 30.06.2017.
 */
public class Enter {
    public static void main(String[] args) {
        String login = JOptionPane.showInputDialog(null, "Введите логин: ", "Ввод логина", JOptionPane.PLAIN_MESSAGE);

        Person person = new Person();
//        person.getJson(login);
//        PersonJson personJson = new PersonJson();
//        personJson.setJson(person.getInstgson());
//        personJson.gettingInfo();
//        System.out.println(personJson.getJsonArray());

        JOptionPane.showMessageDialog(null, person.getInfo(login), "Инфо аккаунта " + login, JOptionPane.PLAIN_MESSAGE);
    }
}
