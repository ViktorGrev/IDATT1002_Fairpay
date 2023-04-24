package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        primaryStage.getIcons().add(new Image(FairPay.class.getResourceAsStream("/image/FairPayLogo.png")));
        primaryStage.show();
    }
}
