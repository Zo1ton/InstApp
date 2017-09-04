import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Controller {

//    public Pane mainPane;

    public void getInfoFromJson(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        //Если нажата не кнопка выходим
        if (!(source instanceof Button)){
            return;
        }

        TextArea info = new TextArea();
        Button getData = new Button();
        TextField login = new TextField();

        ((Button) source).setOnAction(e-> {

            String login1 = login.getText();

            if (!login1.isEmpty()) {
                Person person = new Person(login1);          // Создаем новую запись пользователя
                login.setText(person.getInfoAsString());
            } else login.setText("Введите логин!");
        });

    }
}
