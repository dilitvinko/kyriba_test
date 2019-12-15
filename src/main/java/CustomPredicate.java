import java.util.function.Predicate;

public class CustomPredicate implements Predicate<CustomLog> {

  private String userName;

  public CustomPredicate(String userName) {
    this.userName = userName;
  }

  @Override
  public boolean test(CustomLog customLog) {
    return customLog.getUserName().equals(userName);
  }
}
