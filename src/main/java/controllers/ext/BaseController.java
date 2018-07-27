package controllers.ext;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.DataBase;
import org.apache.log4j.Logger;

import java.io.IOException;

public class BaseController {

    private static final Logger LOG = Logger.getLogger(BaseController.class);
    protected DataBase db = new DataBase();

    protected void createModalWindow (ActionEvent actionEvent, String title, String controller) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(controller));
            stage.setTitle(title);
            Image ico = new Image("images/InstICO_180_180.png");
            stage.getIcons().add(ico);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((MenuItem)actionEvent.getSource()).getParentPopup().getOwnerWindow().getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
