package controllers;

import controllers.ext.BaseController;
import excel.ExcelParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.InstAppFileChooser;
import objects.Person;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DownloadFollowersFromXMLController extends BaseController {

    @FXML private TextField textAreaPathToFile;
    @FXML private Button btnChooseFile;
    @FXML private Button btnDownload;

    private ExcelParser excelParser = new ExcelParser();
    private InstAppFileChooser chooser = new InstAppFileChooser();

    @FXML private void browseFile(ActionEvent actionEvent) {
        String pathToFile = chooser.getPathToFile(actionEvent, "Open Document", "XLSX files (*.xlsx)", "*.xlsx");
        textAreaPathToFile.setText(pathToFile);
    }

    @FXML private void downloadXMLFile () {
        Date date = new Date();
        Person person = new Person(selectedPerson);
        Map<Date, Map<Long, String>> map = new HashMap<>();
        map.put(date, getMapFromExcel());
        person.setMapFollowedBy(map);
        db.putPersonToMap(person);
    }

    private Map<Long, String> getMapFromExcel() {
        Map<Long, String> map = excelParser.getExcelSheet(textAreaPathToFile.getText());
        map.forEach((k,v)->System.out.println("K-" + k + " V-" + v));
        return map;
    }
}
