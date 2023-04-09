package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.io.IOException;

public class FairPay extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = SceneSwitcher.getLoader("frontpage");
            SceneSwitcher.setScene(new Scene(loader.load()));
            SceneSwitcher.setCurrentScene("frontpage");
            primaryStage.setScene(SceneSwitcher.getScene());
            primaryStage.setTitle("FairPay");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            String username = "username";
            String password = "1234";

            // Insert a user:
            //String pswd = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            //User user = new User(-1, username, pswd, new Date(), 1);
            //Database.getUserDAO().insert(user);

            //UserDAO userDAO = Database.getUserDAO();
            //GroupDAO groupDAO = Database.getGroupDAO();

            //groupDAO.addInvite(3, 4, 3);

            //List<Invite> invites = groupDAO.getInvites(3);

            //List<User> users = userDAO.find(invites.stream().map(Invite::getTargetId).collect(Collectors.toList()));

            /*for(Invite invite : groupDAO.getInvites(3)) {
                System.out.println("inv: " + invite.getGroupId() + " " + invite.getTargetId());
            }

            groupDAO.removeInvite(3, 3);

            for(Invite invite : groupDAO.getInvites(3)) {
                System.out.println("inv: " + invite.getGroupId() + " " + invite.getTargetId());
            }*/

            //userDAO.find(Long.valueOf(0));


            /*for(User user : userDAO.find(Arrays.asList(3L, 3L, null))) {
                System.out.println("user found: " + user.getUsername());
            }*/

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}



