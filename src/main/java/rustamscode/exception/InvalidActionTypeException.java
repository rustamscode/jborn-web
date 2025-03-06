package rustamscode.exception;

public class InvalidActionTypeException extends RuntimeException {
  public InvalidActionTypeException(String message) {
    super(message + " is not a valid action type");
  }
}
