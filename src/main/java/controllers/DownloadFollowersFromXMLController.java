package controllers;

import excel.ExcelParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.InstAppFileChooser;

import java.util.Map;

public class DownloadFollowersFromXMLController {

    @FXML private TextField textAreaPathToFile;
    @FXML private Button btnChooseFile;
    @FXML private Button btnDownload;

    ExcelParser excelParser = new ExcelParser();

    InstAppFileChooser chooser = new InstAppFileChooser();;

    @FXML private void browseFile(ActionEvent actionEvent) {
        String pathToFile = chooser.getPathToFile(actionEvent, "Open Document", "XLSX files (*.xlsx)", "*.xlsx");
        textAreaPathToFile.setText(pathToFile);
    }

    @FXML private void downloadXMLFile() {
        Map<Long, String> map = excelParser.getExcelSheet(textAreaPathToFile.getText());
        map.forEach((k,v)->System.out.println("K-" + k + " V-" + v));
    }
}
