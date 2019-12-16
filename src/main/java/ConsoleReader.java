import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleReader {

  Scanner in = new Scanner(System.in);

  private String userName;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private String wordsContain;

  private boolean hasReadUserName(){
    System.out.println("Choose filters:");
    String filterUserName = "";
    while ( !(filterUserName.toUpperCase().equals("Y") || filterUserName.toUpperCase().equals("N"))) {
      System.out.println("1. By user name? (Y/N):");
      filterUserName = in.nextLine();
    }
    if (filterUserName.toUpperCase().equals("Y")) {
      System.out.println("1.1. User name:");
      userName = in.nextLine();
    }

    return filterUserName.toUpperCase().equals("Y");
  }

  private static boolean isValidDateTime(String str){
    try {
      LocalDateTime.parse(str);
    } catch (DateTimeParseException e) {
      System.out.println("Incorrect date format. Try again!");
      return false;
    }
    return true;
  }

  private boolean hasReadStartDateTime(){
    String filterStartTimePeriod = "";
    while ( !(filterStartTimePeriod.toUpperCase().equals("Y") || filterStartTimePeriod.toUpperCase().equals("N"))) {
      System.out.println("2.1. By start time period? (Y/N):");
      filterStartTimePeriod = in.nextLine();
    }
    if (filterStartTimePeriod.toUpperCase().equals("Y")) {
      String startTime;
      do  {
        System.out.println("2.1. Start dateTimePredicate and time (yyyy-mm-ddThh:mm:ss):");
        startTime = in.nextLine();
      } while (!isValidDateTime(startTime));

      startDateTime = LocalDateTime.parse(startTime);
    }

    return filterStartTimePeriod.toUpperCase().equals("Y");
  }
  private boolean hasReadEndDateTime(){
    String filterEndTimePeriod = "";
    while ( !(filterEndTimePeriod.toUpperCase().equals("Y") || filterEndTimePeriod.toUpperCase().equals("N"))) {
      System.out.println("2.2. By end time period? (Y/N):");
      filterEndTimePeriod = in.nextLine();
    }
    if (filterEndTimePeriod.toUpperCase().equals("Y")) {

      String endTime;
      do  {
        System.out.println("2.2. End dateTimePredicate and time (yyyy-mm-ddThh:mm:ss):");
        endTime = in.nextLine();
      } while (!isValidDateTime(endTime));

      endDateTime = LocalDateTime.parse(endTime);
    }

    return filterEndTimePeriod.toUpperCase().equals("Y");
  }
  private boolean hasReadWordsContain(){
    String filterContainWord = "";
    while ( !(filterContainWord.toUpperCase().equals("Y") || filterContainWord.toUpperCase().equals("N"))) {
      System.out.println("3. By Consist Words in message? (Y/N):");
      filterContainWord = in.nextLine();
    }

    if (filterContainWord.toUpperCase().equals("Y")) {
      System.out.println("3.1. Words in message:");
      wordsContain = in.nextLine();
    }

    return filterContainWord.toUpperCase().equals("Y");
  }

  public void read(){
    while ( !(hasReadUserName() |
        hasReadStartDateTime() |
        hasReadEndDateTime() |
        hasReadWordsContain())
    ){
      System.out.println("Please select at least one parameter ");
    }
    System.out.println("Filter Parameters: " + userName + " " + startDateTime + " " + endDateTime + " " + wordsContain);
  }

  public String getUserName() {
    return userName;
  }

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  public String getWordsContain() {
    return wordsContain;
  }
}
