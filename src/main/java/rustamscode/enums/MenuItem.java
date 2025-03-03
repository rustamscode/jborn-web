package rustamscode.enums;

import rustamscode.exception.InvalidMenuItemException;

public enum MenuItem {
  LIST_ACCOUNTS, EXIT;

  public static MenuItem of(String menuItem) {
    if (menuItem.equalsIgnoreCase("1") || menuItem.equalsIgnoreCase("Exit")) {
      return EXIT;
    } else if (menuItem.equalsIgnoreCase("2") || menuItem.equalsIgnoreCase("List Accounts")) {
      return LIST_ACCOUNTS;
    }

    throw new InvalidMenuItemException(menuItem);
  }
}
