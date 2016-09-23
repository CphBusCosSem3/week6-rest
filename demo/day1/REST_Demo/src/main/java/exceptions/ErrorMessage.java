package exceptions;

public class ErrorMessage {

  public ErrorMessage(Throwable ex, int code) {
    this.code = code;
    this.message = ex.getMessage();
    
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

 
  private int code;
  private String message;
  
}