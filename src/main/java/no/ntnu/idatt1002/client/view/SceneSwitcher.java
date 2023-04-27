package no.ntnu.idatt1002.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import no.ntnu.idatt1002.FairPay;

import java.io.IOException;

/**
 * A utility class for switching between JavaFX scenes.
 */
public class SceneSwitcher {

  private static Scene scene; // The current scene

  /**
   * Set the current scene.
   *
   * @param   page the page to show
   */
  public static void setView(Page page) {
    FXMLLoader loader = getLoader(page.getName());
    try {
      scene.setRoot(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the FXMLLoader for an FXML file.
   * @param   filename the file name
   * @return  the FXMLLoader for an FXML file
   */
  public static FXMLLoader getLoader(String filename) {
    String path = String.format("%s.fxml", filename);
    return new FXMLLoader(FairPay.class.getClassLoader().getResource(path));
  }

  /**
   * Set the current scene.
   *
   * @param   scene the scene
   */
  public static void setScene(Scene scene) {
    SceneSwitcher.scene = scene;
  }

  /**
   * Return the current scene.
   *
   * @return  the current scene
   */
  public static Scene getScene() {
    return SceneSwitcher.scene;
  }
}
