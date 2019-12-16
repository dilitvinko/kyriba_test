import java.time.LocalDateTime;

public class CustomLog {
  private LocalDateTime dateTime;
  private String userName;
  private String message;

  public CustomLog() {
  }

  public CustomLog(String stringDateTime, String userName, String message) {
    this.dateTime = LocalDateTime.parse(stringDateTime);
    this.userName = userName;
    this.message = message;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public String toString() {
    return "CustomLog{" +
        "dateTime=" + dateTime +
        ", userName='" + userName + '\'' +
        ", message='" + message + '\'' +
        '}';
  }
}
