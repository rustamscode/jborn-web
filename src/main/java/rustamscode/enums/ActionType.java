package rustamscode.enums;

import rustamscode.exception.InvalidActionTypeException;

public enum ActionType {
  REGISTER, LOGIN;

  public static ActionType of(String action) {
    if (action.equalsIgnoreCase("R") || action.equalsIgnoreCase("register")) {
      return REGISTER;
    } else if (action.equalsIgnoreCase("L") || action.equalsIgnoreCase("login")) {
      return LOGIN;
    }

    throw new InvalidActionTypeException(action);
  }
}
