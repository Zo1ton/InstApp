package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.AccountParser;
import logic.InstAppFileChooser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrowseFileController {

    private static final Logger LOG = Logger.getLogger(BrowseFileController.class);
    private AccountParser accountParser = new AccountParser();
    private InstAppFileChooser chooser = new InstAppFileChooser();

    @FXML private Button btnBrowse;
    @FXML private Button btnDownload;
    @FXML private TextField textInputField;
    @FXML private TextField tagsField;

    @FXML private void browseFile(ActionEvent actionEvent) {
        String pathToFile = chooser.getPathToFile(actionEvent, "Open Document", "TXT files (*.txt)", "*.txt");
        textInputField.setText(pathToFile);
    }

    @FXML private void downloadFile() {
        LOG.info("Нажали кнопку 'Загрузить и искать'");
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
        String tags = tagsField.getText().toUpperCase();
        accountParser.findUsersWithTagsInComments(personsList, getTags(tags));
        LOG.debug("END");
    }

    /**
     * Принимает строку с хэштегами и разбивает её на отдельные слова
     *
     * @return Возвращает коллекцию строк
     */
    private List<String> getTags(String tagsString) {
        tagsString = tagsString.replaceAll("[^А-Яа-яA-Za-z0-9_]", " ").replaceAll("[\\s]{2,}", "").trim();
        String[] s = tagsString.split(" ");
        System.out.println("Arrays.asList(s) - " + Arrays.asList(s));
        return Arrays.asList(s);
    }
}
