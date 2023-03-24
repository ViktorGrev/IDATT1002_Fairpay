package no.ntnu.idatt1002.Scenes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.MyApp;

import java.io.IOException;


public class SceneSwitcher {
  private static Scene scene;
  private static Stage stage;
  private static String lastScene = "";
  private static String currentScene = "";

  public SceneSwitcher() {
  }

  public static void setView(String viewFxml) {
    lastScene = currentScene;
    currentScene = viewFxml;
    FXMLLoader loader = getLoader(viewFxml);
    try {
      scene.setRoot(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void goToLastScene() {
    setView(lastScene);
  }

  public static FXMLLoader getLoader(String filename) {
    String path = String.format("%s.fxml", filename);
    return new FXMLLoader(MyApp.class.getClassLoader().getResource(path));
  }

  public static void setScene(Scene scene) {
    SceneSwitcher.scene = scene;
  }

  public static void setCurrentScene(String sceneName) {
    lastScene = currentScene;
    currentScene = sceneName;
  }

  public static Scene getScene() {
    return SceneSwitcher.scene;
  }

  public static Stage getStage() {
    return stage;
  }
}
