package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import logic.AccountParser;
import objects.Person;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        List<String> personsList = new ArrayList<String>();
        if (pathToFile == null || pathToFile.equals("")) {
            System.err.println("Укажите файл");
        } else {
            File file = new File(pathToFile);
            if (!file.exists()) {
                System.err.println("Файл не существует");
            } else {
                try {
                    Files.lines(Paths.get(pathToFile), StandardCharsets.UTF_8).forEach(personsList::add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        new AccountParser().personsParser(personsList);
    }
}
