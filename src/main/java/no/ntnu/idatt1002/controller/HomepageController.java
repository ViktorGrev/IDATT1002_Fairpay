package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.User;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public final class HomepageController extends MenuController implements Initializable {

  @FXML private Text welcomeText;
  @FXML private Text dateText;
  @FXML private Text weekDayText;

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
