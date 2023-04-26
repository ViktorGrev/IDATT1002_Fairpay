package no.ntnu.idatt1002.model.economy;

/**
 * Defines the categories for expenses.
 */
public enum ExpenseType {

  TOILETRIES(1, "Toiletries"),
  KITCHEN(2, "Kitchen"),
  DETERGENTS(3, "Detergents"),
  FOOD(4, "Food"),
  CAR(5, "Car"),
  RENT(6, "Rent"),
  FURNITURE(7, "Furniture"),
  ELECTRICITY(8, "Electricity"),
  INTERNET(9, "Internet"),
  SERVICES(10, "Services"),
  OTHER(11, "Other");

  private final int number;
  private final String name;

  /**
   * Constructs a new expense type with the specified values.
   *
   * @param   number The expense number
   * @param   name The expense name
   */
  ExpenseType(int number, String name) {
    this.number = number;
    this.name = name;
  }

  /**
   * Returns the expense number.
   *
   * @return  the expense number
   */
  public int getNumber() {
    return number;
  }

  /**
   * Returns the expense name.
   *
   * @return  the expense name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns an expense type with the specified name, or
   * {@code null} if no expense types has the name.
   *
   * @param   name the expense type name
   * @return  an expense type with the specified name
   */
  public static ExpenseType fromName(String name) {
    for (ExpenseType type : values()) {
      if (type.name.equalsIgnoreCase(name))
        return type;
    }
    return null;
  }

  /**
   * Returns an expense type with the specified number, or
   * {@code null} if no expense types has the number.
   *
   * @param   categoryNumber the category number
   * @return  an expense type with the specified number
   */
  public static ExpenseType fromNumber(int categoryNumber) {
    for (ExpenseType type : values()) {
      if (type.number == categoryNumber) {
        return type;
      }
    }
    return null;
  }
}
