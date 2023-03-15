package no.ntnu.idatt1002;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.exception.AuthException;
import no.ntnu.idatt1002.data.User;

public class MyApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("JavaFX App");

        MenuBar menuBar = new MenuBar();

        VBox vBox = new VBox(menuBar);

        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        /*try {
            long userId = Database.getUserDAO().authenticate("username", "password");
            System.out.println("userId: " + userId);

            String password = "1234";
            String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
// $2a$12$US00g/uMhoSBm.HiuieBjeMtoN69SN.GE25fCpldebzkryUyopws6

            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
// result.verified == true

            User user = Database.getUserDAO().find(userId);
            System.out.println("user: " + user);
        } catch(AuthException e) {
            e.printStackTrace();
        }*/
    }
}



