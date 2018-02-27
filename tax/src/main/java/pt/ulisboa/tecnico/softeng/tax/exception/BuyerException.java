package pt.ulisboa.tecnico.softeng.tax.exception;

public class BuyerException extends RuntimeException {
  public BuyerException() {
    super();
  }

  public BuyerException(String message) {
    super(message);
  }
}