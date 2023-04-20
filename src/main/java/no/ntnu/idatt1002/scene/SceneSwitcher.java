package no.ntnu.idatt1002.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import no.ntnu.idatt1002.FairPay;

import java.io.IOException;

public class SceneSwitcher {

  private static Scene scene;

  public static void setView(String viewFxml) {
    FXMLLoader loader = getLoader(viewFxml);
    try {
      scene.setRoot(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static FXMLLoader getLoader(String filename) {
    String path = String.format("%s.fxml", filename);
    return new FXMLLoader(FairPay.class.getClassLoader().getResource(path));
  }

  public static void setScene(Scene scene) {
    SceneSwitcher.scene = scene;
  }

  public static Scene getScene() {
    return SceneSwitcher.scene;
  }
}
