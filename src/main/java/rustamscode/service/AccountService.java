package rustamscode.service;

import rustamscode.dao.AccountDao;
import rustamscode.dao.impl.AccountDaoImpl;
import rustamscode.model.AccountEntity;

import java.util.List;

public class AccountService {

  private final AccountDao accountDao = new AccountDaoImpl();

  public void listAccounts(Long userId) {
    List<AccountEntity> accounts = accountDao.getAccountsByUserId(userId);

    System.out.println("Accounts: ");
    accounts.forEach(System.out::println);
  }
}
