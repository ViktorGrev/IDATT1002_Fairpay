package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.GroupDAO;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public class FairPay extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = SceneSwitcher.getLoader("frontpage");
        SceneSwitcher.setScene(new Scene(loader.load()));
        SceneSwitcher.setCurrentScene("frontpage");
        primaryStage.setScene(SceneSwitcher.getScene());
        primaryStage.setTitle("FairPay");
        primaryStage.show();

        try {
            //String pswd = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            //User user = new User(-1, username, pswd, new Date(), 1);
            //Database.getUserDAO().insert(user);

            //UserDAO userDAO = Database.getUserDAO();
            /*GroupDAO groupDAO = Database.getDAO(GroupDAO.class);

            Group group = groupDAO.find(3L);
            for(User user : group.getMembers()) {
                System.out.println(user.getId() + " " + user.getUsername());
            }*/

            //groupDAO.addInvite(3, 4, 3);

            //List<Invite> invites = groupDAO.getInvites(3);

            //List<User> users = userDAO.find(invites.stream().map(Invite::getTargetId).collect(Collectors.toList()));

            /*for(Invite invite : groupDAO.getInvites(3)) {
                System.out.println("inv: " + invite.getGroupId() + " " + invite.getTargetId());
            }*/

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
