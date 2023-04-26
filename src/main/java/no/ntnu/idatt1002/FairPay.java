package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.ntnu.idatt1002.client.view.SceneSwitcher;

import java.io.IOException;
import java.io.InputStream;

public class FairPay extends Application {

  public static boolean test; // Used for tests

  /**
   * Launch the JavaFX application.
   *
   * @param   args the Java program arguments
   */
  public static void startApplication(String[] args) {
    launch(args);
  }

  /**
   * Starts the application by loading the front page and setting
   * title, size and icon for the application window.
   *
   * @param   primaryStage the primary stage provided by JavaFX
   * @throws  Exception if an error during startup occurs
   */
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = SceneSwitcher.getLoader("frontpage");
    SceneSwitcher.setScene(new Scene(loader.load()));
    stage.setScene(SceneSwitcher.getScene());
    stage.setTitle("FairPay");
    stage.setWidth(900);
    stage.setHeight(600);
    setLogo(stage);
    stage.show();
  }

  /**
   * Set the application icon to the logo.
   *
   * @param   stage the current stage.
   */
  private void setLogo(Stage stage) {
    try (InputStream stream = FairPay.class.getResourceAsStream("/image/FairPayLogo.png")) {
      if (stream != null)
        stage.getIcons().add(new Image(stream));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
