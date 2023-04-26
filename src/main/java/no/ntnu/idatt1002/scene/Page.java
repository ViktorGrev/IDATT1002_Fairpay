package no.ntnu.idatt1002.scene;

/**
 * Defines the pages and their file name.
 */
public enum Page {

  BUDGET("budget"),
  BUDGET_HELP("budgetHelp"),
  EXPENSE("expense"),
  EXPENSE_ADD("expenseAdd"),
  EXPENSE_HELP("expenseHelp"),
  GROUP("group"),
  GROUP_HELP("groupHelp"),
  GROUP_INVITES("groupInvites"),
  GROUP_SETTINGS("groupSettings"),
  HELP("help"),
  HOMEPAGE("homepage"),
  INCOME("income"),
  INCOME_ADD("incomeAdd"),
  INCOME_HELP("incomeHelp"),
  JOIN_CREATE("joinCreate"),
  LOGIN("login"),
  PROFILE("profile"),
  PROFILE_HELP("profileHelp"),
  PROFILE_SETTINGS("profileSettings"),
  SETTLEMENT("settlement"),
  SETTLEMENT_ADD_EXPENSE("settlementAddExpense"),
  SETTLEMENT_EDIT("settlementEdit"),
  SIGNUP("signup"),
  STATUS("status"),
  STATUS_HELP("statusHelp");

  private final String name;

  /**
   * Constructs a new page with the specified file name.
   *
   * @param   name the file name
   */
  Page(String name) {
    this.name = name;
  }

  /**
   * Returns the file name of this page.
   *
   * @return  the file name of this page
   */
  public String getName() {
    return name;
  }
}
