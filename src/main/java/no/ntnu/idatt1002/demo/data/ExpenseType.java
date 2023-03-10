package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.Collections;

public enum ExpenseType {
    /**
     * Floor laminate expense.
     */
    TOILET_PAPER(1, "Toilet paper"),
    TYPE2(2, "Toilet paper"),
    TYPE3(3, "Toilet paper"),
    TYPE4(4, "Toilet paper"),
    TYPE5(5, "Toilet paper"),
    TYPE6(6, "Toilet paper"),
    OTHER(7, "OTHER"),
    ;

    /**
     * Defining object variables.
     * No variables should be changed, and won't have any mutator methods for that reason.
     */
    private final int expenseNumber;
    private final String expenseName;

    /**
     * Instantiates a new ExpenseType.
     * Contains constructor for the class, with the information about the category.
     *
     * @param expenseNumber The given number for the expense
     * @param expenseName The expense name
     */
    ExpenseType(int expenseNumber, String expenseName)
    {
        this.expenseNumber = expenseNumber;
        this.expenseName = expenseName;
    }

    /**
     * Gets expense number.
     *
     * @return the expense number
     */
    public int getCategoryNumber()
    {
        return expenseNumber;
    }

    /**
     * Gets expense name.
     *
     * @return the expense name
     */
    public String getCategoryName()
    {
        return expenseName;
    }

    /**
     * A method for getting the expense enum.
     * Iterates through all the expense enum values.
     * Checks each iteration if the expenseNumber from the parameter
     *  is equal to the expense number in the enum.
     * If it's equals the expense enum will be returned.
     * If no expense has the specific expense number,
     *  the program will throw a IllegalArgumentException to the user.
     *
     * @param categoryNumber the category number
     * @return the expense name
     */
    public static ExpenseType getCategoryByCategoryNumber(int categoryNumber)
    {
        for (ExpenseType category : ExpenseType.values())
        {
            if (category.getCategoryNumber() == categoryNumber)
            {
                return category;
            }
        }
        throw new IllegalArgumentException("This expense does not exist");
    }

    /**
     * A method to get all the expense types in an ArrayList.
     * Creates a new ArrayList to contain all the expense types.
     * Using the addAll method to add all the elements from ExpenseType
     *  to the specified collection (expenseTypeList).
     *
     * @return A ArrayList with all the expenseType
     */
    public static ArrayList<ExpenseType> listOfCategoriesAndTheirValue()
    {
        ArrayList<ExpenseType> expenseTypeList = new ArrayList<>();
        Collections.addAll(expenseTypeList, ExpenseType.values());
        return expenseTypeList;
    }
}
