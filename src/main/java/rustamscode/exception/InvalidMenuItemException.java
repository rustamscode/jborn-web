package rustamscode.exception;

public class InvalidMenuItemException extends RuntimeException {
  public InvalidMenuItemException(String message) {
    super(message + "is not a valid menu item");
  }
}
