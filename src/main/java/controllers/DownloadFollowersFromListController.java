package controllers;

import controllers.ext.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DownloadFollowersFromListController extends BaseController {

    private static final Logger LOG = Logger.getLogger(DownloadFollowersFromListController.class);

    static long userId;

    @FXML private TextArea textArea;
    @FXML private Button btnDownload;

    @FXML private void download() {
        List<String> users = getListOfUsers(textArea.getText());
        LOG.info("userList:" + users);
        LOG.info("user:" + userId);
        db.getMap().get(userId).get(db.getMap().get(userId).size() - 1).getMapFollowedBy();
    }

    private List<String> getListOfUsers(String textArea) {
        List<String> list = new ArrayList<>();
        String[] strings = textArea.split("\n");
        for (String name : strings) {
            if (name.length() > 0) {
                list.add(name);
            }
        }
        return list;
    }
}
