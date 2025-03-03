package rustamscode.model;

public class TransactionEntity {
  private Long id;
  private Long transactionId;
  private Long accountId;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getId() {
    return id;
  }

  public Long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
  }
}
