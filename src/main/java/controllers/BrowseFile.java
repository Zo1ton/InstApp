package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BrowseFile {
    @FXML private Button btnBrowse;
    @FXML private Button btnDownload;
    @FXML private TextField textInputField;

    @FXML private void browseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();    //Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");          //Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(((Node)actionEvent.getSource()).getScene().getWindow());//Указываем текущую сцену CodeNote.mainStage
        textInputField.setText(file.getAbsolutePath());
    }

    @FXML private void downloadFile() {
        String pathToFile = textInputField.getText();
        if (pathToFile == null || pathToFile.equals("")) {
            System.err.println("Укажите файл");
        } else {
            File file = new File(pathToFile);
            if (!file.exists() /*|| file.canRead()*/) {
                System.err.println("Файл не существует");
            } else {
                try {
                    Files.lines(Paths.get(pathToFile), StandardCharsets.UTF_8).forEach(System.out::println);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
