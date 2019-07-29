package controllers;

import controllers.ext.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

public class DownloadFollowersFromListController extends BaseController {

    private static final Logger LOG = Logger.getLogger(DownloadFollowersFromListController.class);

    static long userId;

    @FXML private TextArea textArea;
    @FXML private Button btnDownload;

    @FXML private void download() {
        String userList = textArea.getText();
        LOG.info("userList:" + userList);
        LOG.info("user:" + userId);
        db.getMap().get(userId).get(db.getMap().get(userId).size() - 1).getMapFollowedBy();
    }
}
