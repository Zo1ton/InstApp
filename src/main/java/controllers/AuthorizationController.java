package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AuthorizationController extends Application {

    private static final Logger LOG = Logger.getLogger(AuthorizationController.class);

    public void run() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            LOG.info("Class.forName(URL) is OK");
        } catch (ClassNotFoundException e) {
            LOG.error("ClassNotFoundException");
            LOG.error(e.getMessage());
        }

        Stage stage = new Stage();
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/authorization.fxml"));
        primaryStage.setTitle("Inst App - Authorization");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);
        Image ico = new Image("images/InstICO_180_180.png");
        primaryStage.getIcons().add(ico);
        primaryStage.show();
    }

    @FXML TextField textFieldLogin;
    @FXML TextField passwordField;
    @FXML TextField textFieldURL;
    @FXML Button btnEnter;
    @FXML Label labelInfo;

    @FXML
    public void authorization() {
        String login = textFieldLogin.getText();
        String password = passwordField.getText();
        String url = textFieldURL.getText();

        if (login == null || password == null || url == null) {
            labelInfo.setText("Все поля должны быть заполнены");
        } else if (login.equals("") || password.equals("") || url.equals("")) {
            labelInfo.setText("Все поля должны быть заполнены");
        } else {

            try (Connection connection = DriverManager.getConnection(url, login, password)) {
                LOG.info("Connection successful");
                labelInfo.setText("Connection successful");
                return;
            } catch (SQLException sql) {
                labelInfo.setText("SQLException - " + sql.getMessage());
                LOG.error("SQLException");
                LOG.error(sql.getMessage());
                LOG.error(sql.getSQLState());
            }
        }
    }
}
