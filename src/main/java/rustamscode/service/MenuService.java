package rustamscode.service;

import rustamscode.enums.ActionType;
import rustamscode.enums.MenuItem;
import rustamscode.exception.InvalidActionTypeException;
import rustamscode.model.UserEntity;

import java.util.Scanner;
import java.util.logging.Logger;

public class MenuService {
  private static final Logger logger = Logger.getLogger(MenuService.class.getName());

  private final Scanner scanner;
  private final AuthService authService;
  private final AccountService accountService;

  public MenuService(Scanner scanner, AuthService authService, AccountService accountService) {
    this.scanner = scanner;
    this.authService = authService;
    this.accountService = accountService;
  }

  public void start() {
    System.out.print("Register [R]" + "\n" + "Log in [L]: \nChoice: ");
    ActionType actionType = ActionType.of(scanner.nextLine());

    UserEntity user =
        switch (actionType) {
          case REGISTER -> authService.register();
          case LOGIN -> authService.login();
          default -> throw new InvalidActionTypeException(actionType.toString());
        };

    printUserGreetings(user.getId());
    printMenu();

    System.out.print("Choice: ");

    while (true) {
      MenuItem menuItem = MenuItem.of(scanner.nextLine());
      switch (menuItem) {
        case EXIT -> {return;}
        case LIST_ACCOUNTS -> accountService.listAccounts(user.getId());
      }
      System.out.print("Choice: ");
    }
  }

  private void printUserGreetings(Long userId) {
    System.out.printf("Hello user %s!", userId);
  }

  private void printMenu() {
    System.out.println("\nMenu: \n1.Exit\n2.List accounts");
  }
}
