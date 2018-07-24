package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutFormController implements Initializable {

    @FXML private Label labelVersion;
    @FXML private Button btnOk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.labelVersion.setText("0.0.0.0071");
    }

    @FXML
    public void pressOkButton () {
        Stage stage = (Stage) this.btnOk.getScene().getWindow();
        stage.close();
    }

}
