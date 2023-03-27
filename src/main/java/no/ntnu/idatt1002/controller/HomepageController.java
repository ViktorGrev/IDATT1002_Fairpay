package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;
import no.ntnu.idatt1002.data.User;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public final class HomepageController extends Controller implements Initializable {

  @FXML private Text welcomeText;
  @FXML private Text dateText;
  @FXML private Text weekDayText;

  /**
   * Send the user to the expense page.
   */
  @FXML
  private void expenseButtonClick() {
    SceneSwitcher.setView("expense");
  }

  /**
   * Send the user to the settlement page.
   */
  @FXML
  private void settlementButtonClick() {
    SceneSwitcher.setView("settlement");
  }

  /**
   * Send the user to the status page.
   */
  @FXML
  private void statusButtonClick() {
    SceneSwitcher.setView("status");
  }

  /**
   * Send the user to the budget page.
   */
  @FXML
  private void budgetClick() {
    SceneSwitcher.setView("budget");
  }

  /**
   * Send the user to the dorm group page.
   */
  @FXML
  private void dormGroupClick() {
    SceneSwitcher.setView("dormGroup");
  }

  /**
   * Send the user to the income page.
   */
  @FXML
  private void incomeClick() {
    SceneSwitcher.setView("income");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    welcomeText.setText("Welcome, " + User.CURRENT.getUsername() + "!");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String dateToday = dateFormat.format(new Date());
    dateText.setText("Today's date: " + dateToday);
    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();
    weekDayText.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
  }
}
