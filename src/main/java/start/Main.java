package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objects.DataBase;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private static final Logger LOG = Logger.getLogger(Main.class);
    private DataBase db = new DataBase();

    @Override
    public void init() throws Exception {
        super.init();
        startMain();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        primaryStage.setTitle("Inst App");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);
        Image ico = new Image("images/InstICO_180_180.png");
        primaryStage.getIcons().add(ico);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        endMain();
    }

    private void startMain() {
        // Если файл есть, пытаемся его десюрилизовать, если нет, создаем новую коллекцию.
        File file = new File(Tunes.dbFile.getTune());
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(Tunes.dbFile.getTune());
                 ObjectInputStream in = new ObjectInputStream(fis)) {
                db.setMap((Map) in.readObject());
            } catch (IOException io) {
                io.printStackTrace();
                db.setMap(new HashMap<>());
            } catch (ClassNotFoundException cnfe) {
                LOG.error("ClassNotFoundException\n" + cnfe);
                db.setMap(new HashMap<>());
            }
        } else {
            db.setMap(new HashMap<>());
        }
    }

    private void endMain() {
        try (FileOutputStream fos = new FileOutputStream(Tunes.dbFile.getTune());
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(db.getMap());
        } catch (IOException io) {
            LOG.error("IOException при попытке прочитать\\найти " + Tunes.dbFile.getTune() + " файл");
            io.printStackTrace();
        }
    }
}
