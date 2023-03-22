package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MyApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("JavaFX App");

        /*AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("test.fxml"));
        Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("test.fxml")));

        primaryStage.setScene(scene);
        primaryStage.show();*/

        try {
            FXMLLoader loader = SceneSwitcher.getLoader("frontpage");
            SceneSwitcher.setScene(new Scene(loader.load()));
            SceneSwitcher.setCurrentScene("frontpage");
            primaryStage.setScene(SceneSwitcher.getScene());
            primaryStage.setTitle("JavaFX App");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*try {
            String username = "username";
            String password = "1234";

            // Insert a user:
            //String pswd = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            //User user = new User(-1, username, pswd, new Date(), 1);
            //Database.getUserDAO().insert(user);

            long userId = Database.getUserDAO().authenticate(username, password);
            System.out.println("authenticated: " + userId);

            Group group = Database.getGroupDAO().findByUser(userId);
            System.out.println("group: " + group);

        } catch(AuthException e) {
            e.printStackTrace();
        }*/
    }
}



