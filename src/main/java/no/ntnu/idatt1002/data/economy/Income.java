package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.Date;

public class Income {
  private final BigDecimal Amount;
  private final String type;
  private final long userId;
  private final Date date;

  public Income(BigDecimal amount, String type, long userId, Date date) {
    if (userId < 0){
      throw new IllegalArgumentException("Incorrect user-id");
    }
    if (type == null){
      throw new IllegalArgumentException("The income type can't be null");
    }
    if (date == null){
      throw new IllegalArgumentException("The date can't be null");
    }
    if (amount.compareTo(BigDecimal.ZERO) < 0){
      throw new IllegalArgumentException("The amount cannot be a negative integer");
    }
    this.Amount = amount;
    this.type = type;
    this.userId = userId;
    this.date = date;
  }

  public BigDecimal getAmount() {
    return Amount;
  }
  public String getIncomeType(){
    return type;
  }

  public Long getUserId() {
    return userId;
  }

  public Date getDate() {
    return date;
  }
}
