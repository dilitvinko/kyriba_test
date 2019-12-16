import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class ConsoleReader {

  Scanner in = new Scanner(System.in);

  private String userName;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private String wordsContain;
  private String groupByUserName;
  private ChronoUnit groupByTimeUnit;

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

  private static boolean isValidDateTime(String dateTime){
    try {
      LocalDateTime.parse(dateTime);
    } catch (DateTimeParseException e) {
      System.out.println("Incorrect date format. Try again!");
      return false;
    }
    return true;
  }

  private static boolean isValidTimeUnit(String timeUnit){
    try {
      ChronoUnit.valueOf(timeUnit.toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println("Incorrect unit time format. Try again!");
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

  private boolean hasReadGroupingByUserName(){
    String groupingByUserName = "";
    while ( !(groupingByUserName.toUpperCase().equals("Y") || groupingByUserName.toUpperCase().equals("N"))) {
      System.out.println("4. Group by userName? (Y/N)");
      groupingByUserName = in.nextLine();
    }

    groupByUserName = groupingByUserName;

    return groupingByUserName.toUpperCase().equals("Y");
  }

  private boolean hasReadGroupingByTimeUnit(){
    String groupingByTimeUnit = "";
    while ( !(groupingByTimeUnit.toUpperCase().equals("Y") || groupingByTimeUnit.toUpperCase().equals("N"))) {
      System.out.println("5. Grouping by time unit? (Y/N):");
      groupingByTimeUnit = in.nextLine();
    }

    if (groupingByTimeUnit.toUpperCase().equals("Y")) {
      String timeUnit;
      do {
        System.out.println("5.1. Time Unit (SECONDS/MINUTES/HOURS/DAYS/MONTHS/YEARS ... and so on):");
        timeUnit = in.nextLine();
      } while (!isValidTimeUnit(timeUnit));

      groupByTimeUnit = ChronoUnit.valueOf(timeUnit.toUpperCase());
    }

    return groupingByTimeUnit.toUpperCase().equals("Y");
  }

  public void readForPredicate(){
    while ( !(hasReadUserName() |
        hasReadStartDateTime() |
        hasReadEndDateTime() |
        hasReadWordsContain())
    ){
      System.out.println("Please select at least one parameter ");
    }
    System.out.println("Filter Parameters: " + userName + " " + startDateTime + " " + endDateTime + " " + wordsContain);
  }

  public void readForGrouping(){
    while ( !(hasReadGroupingByUserName() |
        hasReadGroupingByTimeUnit())
    ){
      System.out.println("Please select at least one parameter ");
    }
    System.out.println("Group Parameters: " + groupByUserName + " " + groupByTimeUnit );

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
