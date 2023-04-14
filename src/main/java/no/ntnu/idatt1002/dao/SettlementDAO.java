package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.economy.Settlement;

import java.util.List;

public interface SettlementDAO extends DAO<Settlement, Long> {

    Settlement create(String name, long userId);

    void delete(long settlementId);

    List<Settlement> findByUser(long userId);

    void addMember(long settlementId, long userId);

    void removeMember(long settlementId, long userId);

    void addExpense(long settlementId, long expenseId);
}
