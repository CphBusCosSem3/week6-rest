package exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Thomas Hartmann - tha@cphbusiness.dk created on Sep 27, 2016 
 */
public class ErrorMessage {
    private int code;
    private String message;
    private String description;
    private String stackTrace;

  public ErrorMessage(Throwable ex, int code,boolean debug) {
    this.code = code;
    this.message = ex.getMessage();
    this.description = ex.getMessage();
    if(debug){
      StringWriter sw = new StringWriter();
      ex.printStackTrace(new PrintWriter(sw));
      this.stackTrace = sw.toString();
    }
  }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

}
