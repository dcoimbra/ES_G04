package pt.ulisboa.tecnico.softeng.tax.exception;

public class TaxPayerException extends RuntimeException {
  public TaxPayerException() {
    super();
  }

  public TaxPayerException(String message) {
    super(message);
  }
}