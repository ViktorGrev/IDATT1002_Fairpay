package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public final class HomepageController extends MenuController implements Initializable {

  @FXML private Text welcomeText;
  @FXML private Text dateText;
  @FXML private Text weekDayText;
  @FXML private String[] inspirationalQuotes = {
          "When you have a dream, you've got to grab it and never let go.",
          "There is nothing impossible to they who will try.",
          "Keep your face always toward the sunshine, and shadows will fall behind you.",
          "The bad news is time flies. The good news is you're the pilot.",
          "Life has got all those twists and turns. You've got to hold on tight and off you go.",
          "Keep your face always toward the sunshine, and shadows will fall behind you.",
          "Success is not final, failure is not fatal: it is the courage to continue that counts.",
          "You define your own life. Don't let other people write your script.",
          "You are never too old to set another goal or to dream a new dream.",
          "Spread love everywhere you go."
  };
  @FXML private Text inspirationalText;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    welcomeText.setText("Welcome, " + User.CURRENT.getUsername() + "!");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String dateToday = dateFormat.format(new Date());
    //dateText.setText("Today's date: " + dateToday);
    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();
    //weekDayText.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
    Random rn = new Random();
    int number = rn.nextInt(10);
    inspirationalText.setText(inspirationalQuotes[number]);
  }
  @FXML
  private void helpClick() {
    SceneSwitcher.setView("help");
  }

  public void addExpenseClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("addExpense");
  }

  public void addIncomeClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("newIncome");
  }
}

