import javax.swing.JOptionPane;

/**
 * Created by Andrey on 30.06.2017.
 */
public class Enter {
    public static void main(String[] args) {
        String login = JOptionPane.showInputDialog(null, "Введите логин: ", "Ввод логина", JOptionPane.PLAIN_MESSAGE);

        Person person = new Person();   // Создаем новую запись пользователя
        person.getJson(login);          // По логину получае json
        person.getInfoFromJson();       // Из json`а получаем инфо о странице

        JOptionPane.showMessageDialog(null, person.getInfo(), "Инфо аккаунта " + login, JOptionPane.PLAIN_MESSAGE);
    }
}