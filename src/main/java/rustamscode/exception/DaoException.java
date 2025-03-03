package rustamscode.exception;

public class DaoException extends RuntimeException {
  public DaoException(Throwable cause) {
    super(cause);
  }

  public DaoException(String message) {
    super(message);
  }
}
