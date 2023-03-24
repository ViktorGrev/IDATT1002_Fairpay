package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.SceneSwitcher;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.UserDAO;
import no.ntnu.idatt1002.dao.exception.AuthException;
import no.ntnu.idatt1002.data.User;

public class SignupController {

    @FXML private TextField usernameField, phoneField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private Text signupFeedback;

    @FXML
    private void createAccountButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        long phoneNumber = Long.parseLong(phoneField.getText());
        UserDAO userDAO = Database.getUserDAO();
        try {
            User user = userDAO.create(username, password, phoneNumber);
            SceneSwitcher.setView("settlement");
        } catch (AuthException e) {
            signupFeedback.setText(e.getLocalizedMessage());
        }
    }

    @FXML
    private void loginButtonClick() {
        SceneSwitcher.setView("login");
    }
}
