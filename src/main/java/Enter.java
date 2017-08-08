import javax.swing.JOptionPane;

/**
 * Created by Andrey on 30.06.2017.
 */
public class Enter {
    public static void main(String[] args) {
        String login = JOptionPane.showInputDialog(null, "Введите логин: ", "Ввод логина", JOptionPane.PLAIN_MESSAGE);

        Person person = new Person(login);   // Создаем новую запись пользователя

        JOptionPane.showMessageDialog(null, person.getInfoAsString(), "Инфо аккаунта " + login, JOptionPane.PLAIN_MESSAGE);
    }
}