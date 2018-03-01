package pt.ulisboa.tecnico.softeng.tax.exception;

public class InvoiceException extends RuntimeException {
  public InvoiceException() {
    super();
  }

  public InvoiceException(String message) {
    super(message);
  }
}