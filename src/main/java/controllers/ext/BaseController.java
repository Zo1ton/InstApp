package controllers.ext;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.DataBase;
import org.apache.log4j.Logger;

import java.io.IOException;

public abstract class BaseController {

    private static final Logger LOG = Logger.getLogger(BaseController.class);
    protected String selectedPerson;
    protected DataBase db = new DataBase();

    protected void createModalWindow (ActionEvent actionEvent, String title, String fxml) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            stage.setTitle(title);
            Image ico = new Image("images/InstICO_180_180.png");
            stage.getIcons().add(ico);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((MenuItem)actionEvent.getSource()).getParentPopup().getOwnerWindow().getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            LOG.info(e.toString());
            e.printStackTrace();
        }
    }

    protected void createModalWindowMouse (MouseEvent mouseEvent, String title, String controller) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(controller));
            stage.setTitle(title);
            Image ico = new Image("images/InstICO_180_180.png");
            stage.getIcons().add(ico);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((TableView)mouseEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            LOG.info(e.toString());
        }
    }
}
