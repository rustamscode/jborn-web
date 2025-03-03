package rustamscode.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountEntity {
  private Long id;
  private String name;
  private Long userId;
  private BigDecimal balance;
  private LocalDate date;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return String.format("Account %s \n- Balance: %s \n- Owner: %s \n- Date: %s", name, balance.toString(), userId.toString(), date.toString());
  }

  public void setId(Long id) {
    this.id = id;
  }
}
