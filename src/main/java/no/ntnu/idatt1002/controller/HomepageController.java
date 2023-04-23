package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.Notification;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.Income;
import no.ntnu.idatt1002.data.economy.Settlement;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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

    private void displayActivity() {
        ScrollPane scrollPane = new ScrollPane();
        Label notificationLabel = new Label("Recent Activity");
        notificationLabel.setStyle("-fx-font-size: 17; -fx-padding: 3 0 0 10");
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
                vBox2.setStyle("-fx-padding: 10 0 5 10;-fx-border-style: hidden; -fx-background-color: white");
            }
            vBox2.getChildren().addAll(label, content);
            vBox2.setOnMouseClicked(e -> notification.runAction());
            noti.getChildren().add(vBox2);
        }
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(noti);
    }

    private void displayName() {
        User user = userDAO.find(User.CURRENT);
        welcomeText.setText("Welcome, " + user.getUsername() + "!");
    }

    private void displayQuote() {
        int number = random.nextInt(10);
        inspirationalText.setText(inspirationalQuotes[number]);
    }

    @FXML
    private void helpClick() {
        SceneSwitcher.setView("help");
    }

    @FXML
    private void addExpenseClick() {
        SceneSwitcher.setView("addExpense");
    }

    @FXML
    private void addIncomeClick() {
        SceneSwitcher.setView("newIncome");
    }

    private List<Notification> getNotifications(long userId) {
        List<Notification> notifications = new ArrayList<>();
        Group group = groupDAO.findByUser(userId);
        if(!group.getExpenses().isEmpty()) {
            List<Expense> expenses = expenseDAO.find(group.getExpenses());
            getUsers(expenses.stream().map(Expense::getUserId).collect(Collectors.toList()));
            for(Expense expense : expenses) {
                String name = expense.hasName() ? expense.getName() : expense.getType().getCategoryName();
                String date = DateUtil.getElapsedTime(expense.getAddDate().getTime());
                notifications.add(new Notification(getUser(expense.getUserId()).getUsername()
                        + " - " + date, "Added new expense " + name,
                        expense.getAddDate(), () -> SceneSwitcher.setView("expense")));
            }
        }
        if(!group.getIncome().isEmpty()) {
            List<Income> incomes = incomeDAO.find(group.getIncome());
            getUsers(incomes.stream().map(Income::getUserId).toList());
            for(Income income : incomes) {
                String date = DateUtil.getElapsedTime(income.getAddDate().getTime());
                notifications.add(new Notification(getUser(income.getUserId()).getUsername()
                        + " - " + date, "Added new income " + income.getName(), income.getAddDate(),
                        () -> SceneSwitcher.setView("income")));
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
                SceneSwitcher.setView("editSettlement");
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
