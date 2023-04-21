package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.Notification;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.Income;
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
  @FXML private VBox notifBox;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = userDAO.find(User.CURRENT);
    welcomeText.setText("Welcome, " + user.getUsername() + "!");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String dateToday = dateFormat.format(new Date());
    //dateText.setText("Today's date: " + dateToday);
    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();
    //weekDayText.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
    Random rn = new Random();
    int number = rn.nextInt(10);
    inspirationalText.setText(inspirationalQuotes[number]);
    notifBox.getChildren().clear();

    List<Notification> notifications = getNotifications(User.CURRENT);
    int n = 0;
    for(Notification notification : notifications) {
      Label label = new Label(notification.getTitle());
      Text content = new Text(notification.getText());
      VBox vBox2 = new VBox();
      vBox2.getChildren().addAll(label, content);
      vBox2.setOnMouseClicked(e -> {
        System.out.println("send to X");
      });
      notifBox.getChildren().add(vBox2);
      if(n++ > 1) break;
    }
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

  private List<Notification> getNotifications(long userId) {
    List<Notification> notifications = new ArrayList<>();
    Group group = groupDAO.findByUser(userId);
    if(!group.getExpenses().isEmpty()) {
      List<Expense> expenses = expenseDAO.find(group.getExpenses());
      for(Expense expense : expenses) {
        String name = expense.hasName() ? expense.getName() : expense.getType().getCategoryName();
        notifications.add(new Notification("User " + expense.getUserId() + " X minutes ago...",
                "Added new expense " + name, expense.getDate()));
      }
    }
    if(!group.getIncome().isEmpty()) {
      List<Income> incomes = incomeDAO.find(group.getIncome());
      for(Income income : incomes) {
        notifications.add(new Notification("User " + income.getUserId() + " X minutes ago...",
                "Added a new income " + income.getName(), income.getDate()));
      }
    }
    /*List<Settlement> settlements = settlementDAO.find(Group.CURRENT);
    if(!group.().isEmpty()) {
      List<Income> incomes = incomeDAO.find(group.getIncome());
      for(Income income : incomes) {
        notifications.add(new Notification("New income", "(name) added a new income", income.getDate()));
      }
    }*/

    notifications.sort(Comparator.comparing(Notification::getDate));
    return notifications;
  }
}

