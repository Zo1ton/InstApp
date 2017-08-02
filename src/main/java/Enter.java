import javax.swing.JOptionPane;

/**
 * Created by Andrey on 30.06.2017.
 */
public class Enter {
    public static void main(String[] args) {
        String login = JOptionPane.showInputDialog(null, "Введите логин: ", "Ввод логина", JOptionPane.PLAIN_MESSAGE);

        JsonString person = new JsonString();

        person.getJson(login);
        PersonJson personJson = new PersonJson();
        personJson.setJson(person.getInstJson());
        personJson.gettingInfo();

        JOptionPane.showMessageDialog(null, personJson.getInfo(), "Инфо аккаунта " + login, JOptionPane.PLAIN_MESSAGE);
    }
}
