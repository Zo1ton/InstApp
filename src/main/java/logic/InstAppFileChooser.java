package logic;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;

import java.io.File;

public class InstAppFileChooser {

    private static final Logger LOG = Logger.getLogger(InstAppFileChooser.class);

    /**
     * Метод создает окно выбора файла.
     *
     * @param actionEvent - actionEvent
     * @param title       - Заголовок окна
     * @param description - Описаное расширений
     * @param extensions  - Возможные к выбору расширения файлов
     * @return Полный путь к файлу
     */
    public String getPathToFile(ActionEvent actionEvent, String title, String description, String extensions) {
        FileChooser fileChooser = new FileChooser();    //Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle(title);                    //Заголовок диалога
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extensions);   //Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        try {
            File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());     //Указываем текущую сцену CodeNote.mainStage
            return file.getAbsolutePath();
        } catch (NullPointerException e) {
            LOG.info("Закрыли окно выбора файла");
        }
        return null;
    }
}
