package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.Date;

public class Income {
  private final BigDecimal Amount;
  private final String type;
  private final long userId;
  private final Date date;

  public Income(BigDecimal amount, String type, long userId, Date date) {
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
