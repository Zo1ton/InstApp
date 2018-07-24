package logic;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.File;

public class InstAppFileChooser {

    public String getPathToFile(ActionEvent actionEvent, String title, String description, String extensions) {
        FileChooser fileChooser = new FileChooser();    //Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle(title);          //Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(description, extensions);//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node)actionEvent.getSource()).getScene().getWindow());//Указываем текущую сцену CodeNote.mainStage
        return file.getAbsolutePath();
    }
}
