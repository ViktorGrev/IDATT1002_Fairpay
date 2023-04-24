package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.economy.Settlement;

import java.util.List;

/**
 * This interface defines methods to interact with settlements and settlement related tasks.
 */
public interface SettlementDAO extends DAO<Settlement, Long> {

    /**
     * Creates a new settlement with the specified name and creater.
     * @param   name the settlement name
     * @param   userId the ID of the user who creates the settlement
     * @return  a new settlement
     */
    Settlement create(String name, long userId);

    /**
     * Deletes a settlement specified by the ID.
     * @param   settlementId the settlement ID
     */
    void delete(long settlementId);

    /**
     * Finds all settlements that a user is part of.
     * @param   userId the user ID
     * @return  a list of settlements
     */
    List<Settlement> findByUser(long userId);

    /**
     * Add a member to a settlement
     * @param   settlementId the settlement ID
     * @param   userId the user ID
     */
    void addMember(long settlementId, long userId);

    /**
     * Remove a member from a settlement
     * @param   settlementId the settlement ID
     * @param   userId the user ID
     */
    void removeMember(long settlementId, long userId);

    /**
     * Add an expense to a settlement
     * @param   settlementId the settlement ID
     * @param   expenseId the expense ID
     */
    void addExpense(long settlementId, long expenseId);

    /**
     * Set the end status for a settlement
     * @param   settlementId the settlement ID
     * @param   ended whether the settlement is ended
     */
    void setEnded(long settlementId, boolean ended);
}
