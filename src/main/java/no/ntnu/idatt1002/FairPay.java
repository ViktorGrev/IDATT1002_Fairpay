package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public class FairPay extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = SceneSwitcher.getLoader("frontpage");
        SceneSwitcher.setScene(new Scene(loader.load()));
        primaryStage.setScene(SceneSwitcher.getScene());
        primaryStage.setTitle("FairPay");
        primaryStage.show();
    }
}
