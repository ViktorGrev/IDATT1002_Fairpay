package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.data.User;

import java.net.URL;
import java.util.ResourceBundle;

public final class SignupController extends Controller implements Initializable {

    @FXML private TextField usernameField, phoneField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private Text signupFeedback;

    /**
     * Attempts to create a new user with the given credentials.
     */
    @FXML
    private void createAccountButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        try {
            if(!password.equals(confirmPassword))
                throw new IllegalArgumentException("Password does not match");
            if(phoneField.getText().isBlank())
                throw new IllegalArgumentException("Phone number is required");
            long phoneNumber = Long.parseLong(phoneField.getText());
            User user = userDAO.create(username, password, phoneNumber);
            User.setCurrent(user);
            SceneSwitcher.setView("joincreatepage");
        } catch (Exception e) {
            signupFeedback.setText(e.getLocalizedMessage());
        }
    }

    /**
     * Send the user to the login page.
     */
    @FXML
    private void loginButtonClick() {
        SceneSwitcher.setView("login");
    }

    // Ensures that numbers is the only accepted input for the phone number field.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
