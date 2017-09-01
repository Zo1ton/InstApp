import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Controller {
    public TextArea info = new TextArea();
    public Button getData = new Button();
    public TextField login = new TextField();
    public Pane mainPane;



    public void getInfoFromJson(ActionEvent actionEvent) {

        getData.setOnAction(e -> {
            String login1 =  login.getText();
            if (!login1.isEmpty()) {
                Person person = new Person(login1);          // Создаем новую запись пользователя
                login.setText(person.getInfoAsString());
            }
            else login.setText("Введите логин!");
        });

    }
}
