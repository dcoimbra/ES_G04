package pt.ulisboa.tecnico.softeng.tax.exception;

public class IRSException extends RuntimeException {
  public IRSException() {
    super();
  }

  public IRSException(String message) {
    super(message);
  }
}