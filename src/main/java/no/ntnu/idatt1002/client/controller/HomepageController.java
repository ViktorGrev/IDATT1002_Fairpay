package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.Notification;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.model.economy.Expense;
import no.ntnu.idatt1002.model.economy.Income;
import no.ntnu.idatt1002.model.economy.Settlement;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The controller class for the home page.
 */
public final class HomepageController extends MenuController implements Initializable {

  private static final Random random = new Random();

  @FXML private Text welcomeText;
  @FXML private Text inspirationalText;
  @FXML private VBox notifBox;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    displayName();
    displayQuote();
    displayActivity();
  }

  /**
   * Displays the recent activity in the notification box.
   */
  private void displayActivity() {
    ScrollPane scrollPane = new ScrollPane();
    Label notificationLabel = new Label("Recent Activity");
    notificationLabel.setStyle("-fx-font-size: 17; -fx-padding: 3 0 0 10; -fx-text-fill: #9c5151");
    notifBox.getChildren().clear();
    notifBox.getChildren().add(notificationLabel);
    notifBox.getChildren().add(scrollPane);
    notifBox.setAlignment(Pos.TOP_LEFT);

    List<Notification> notifications = getNotifications(User.CURRENT).stream()
            .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
            .limit(10).toList();

    VBox noti = new VBox();
    for(Notification notification : notifications) {
      Label label = new Label(notification.getTitle());
      Text content = new Text(notification.getContent());
      content.setStyle("-fx-opacity: 0.65");
      VBox vBox2 = new VBox();
      vBox2.setAlignment(Pos.BOTTOM_LEFT);
      vBox2.setStyle("-fx-padding: 10 0 5 10;" +
              "-fx-border-style: hidden hidden solid hidden;" +
              "-fx-border-color: rgba(128,128,128,0.65);" +
              "-fx-border-width: 1px;" +
              "-fx-background-color: white;" +
              "-fx-cursor: hand;");
      if(notifications.indexOf(notification) == notifications.size() - 1) {
        vBox2.setStyle("-fx-padding: 10 0 5 10;-fx-border-style: hidden; -fx-background-color: white; -fx-cursor: hand");
      }
      vBox2.getChildren().addAll(label, content);
      vBox2.setOnMouseClicked(e -> notification.runAction());
      noti.getChildren().add(vBox2);
    }
    scrollPane.setFitToWidth(true);
    scrollPane.setContent(noti);
  }

  /**
   * Display a welcome message.
   */
  private void displayName() {
    User user = userDAO.find(User.CURRENT);
    welcomeText.setText("Welcome, " + user.getUsername() + "!");
  }

  /**
   * Display a quote.
   */
  private void displayQuote() {
    int number = random.nextInt(10);
    inspirationalText.setText(inspirationalQuotes[number]);
  }

  /**
   * Send the user to the help page.
   */
  @FXML
  private void helpClick() {
    viewPage(Page.HELP);
  }

  /**
   * Send the user to the add expense page.
   */
  @FXML
  private void addExpenseClick() {
    viewPage(Page.EXPENSE_ADD);
  }

  /**
   * Send the user to the add income page.
   */
  @FXML
  private void addIncomeClick() {
    viewPage(Page.INCOME_ADD);
  }

  /**
   * Get a list of notifications for a user.
   *
   * @param   userId the user ID
   * @return  a list of notifications for a user
   */
  private List<Notification> getNotifications(long userId) {
    List<Notification> notifications = new ArrayList<>();
    Group group = groupDAO.findByUser(userId);
    if(!group.getExpenses().isEmpty()) {
      List<Expense> expenses = expenseDAO.find(group.getExpenses());
      getUsers(expenses.stream().map(Expense::getUserId).collect(Collectors.toList()));
      for(Expense expense : expenses) {
        String name = expense.hasName() ? expense.getName() : expense.getType().getName();
        String date = DateUtil.getElapsedTime(expense.getAddDate().getTime());
        notifications.add(new Notification(getUser(expense.getUserId()).getUsername()
                + " - " + date, "Added new expense " + name,
                expense.getAddDate(), () -> viewPage(Page.EXPENSE)));
      }
    }
    if(!group.getIncome().isEmpty()) {
      List<Income> incomes = incomeDAO.find(group.getIncome());
      getUsers(incomes.stream().map(Income::getUserId).toList());
      for(Income income : incomes) {
        String date = DateUtil.getElapsedTime(income.getAddDate().getTime());
        notifications.add(new Notification(getUser(income.getUserId()).getUsername()
                + " - " + date, "Added new income " + income.getName(), income.getAddDate(),
                () -> viewPage(Page.INCOME)));
      }
    }

    List<Settlement> settlements = settlementDAO.findByUser(User.CURRENT);
    getUsers(settlements.stream().map(Settlement::getUserId).toList());
    for(Settlement settlement : settlements) {
      String date = DateUtil.getElapsedTime(settlement.getDate().getTime());
      notifications.add(new Notification(getUser(settlement.getUserId()).getUsername()
              + " - " + date, "Added a new settlement " + settlement.getName(), settlement.getDate(),
              () -> {
                SettlementController.settlementId = settlement.getId();
                viewPage(Page.SETTLEMENT_EDIT);
              }));
    }

    notifications.sort(Comparator.comparing(Notification::getDate));
    return notifications;
  }

  private static final String[] inspirationalQuotes = {
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
}
