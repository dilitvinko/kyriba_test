import java.util.function.Predicate;

public class CustomPredicate {

  public static Predicate<CustomLog> createPredicate(ConsoleReader reader){
    Predicate<CustomLog> predicate = log -> true;
    if (reader.getUserName() != null){
      predicate = predicate.and(log -> log.getUserName().equals(reader.getUserName()));
    }
    if (reader.getStartDateTime() != null){
      predicate = predicate.and(log -> log.getDateTime().isAfter(reader.getStartDateTime()));
    }
    if (reader.getEndDateTime() != null){
      predicate = predicate.and(log -> log.getDateTime().isBefore(reader.getEndDateTime()));
    }
    if (reader.getWordsContain() != null){
      predicate = predicate.and(log -> log.getMessage().contains(reader.getWordsContain()));
    }

    return predicate;
  }

}
