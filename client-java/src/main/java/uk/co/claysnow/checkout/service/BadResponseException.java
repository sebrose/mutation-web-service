package uk.co.claysnow.checkout.service;

public final class BadResponseException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  public BadResponseException(String message) {
    super(message);
  }

}
