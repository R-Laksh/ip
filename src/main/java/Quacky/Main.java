package quacky;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class Main extends Application {
    private Quacky quacky = new Quacky();

    @Override
    public void start(Stage stage) {
        try {
            assert Main.class.getResource("/view/MainWindow.fxml") != null : "FXML resource should be found.";
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Quacky");
            fxmlLoader.<MainWindow>getController().setQuacky(quacky);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

