import static java.time.temporal.ChronoUnit.DAYS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    final String DELIMETER = " --- ";


    Path pathes = Paths.get("logsDir/");
    Stream<String> lineStream1 = null;
    List<String> a = new ArrayList<>();
    List<CustomLog> test = new ArrayList<>();

    try (Stream<Path> paths = Files.walk(pathes)){
      test = paths
          .filter(Files::isRegularFile)
          .flatMap(p -> {
            try {
              return Files.lines(p);
            } catch (IOException e) {
              e.printStackTrace();
            }
            return null;
          }).map(line -> new CustomLog(line.split(DELIMETER)[0], line.split(DELIMETER)[1], line.split(DELIMETER)[2]))
          .filter(new CustomPredicate("Dima")).collect(Collectors.toList());
//          .forEach(System.out::println);
    } catch (IOException e) {
      e.printStackTrace();
    }
      test.size();


    //TODO Бюлдера сделать и интерфейс прописать. И в зависимости от фильтра нужное бюлдить

    Path path = Paths.get("file.txt");

    LocalDateTime localDateTime1 = LocalDateTime.parse("2019-12-12T10:39:23");
    LocalDateTime localDateTime2 = LocalDateTime.parse("2019-12-12T20:39:23");

    Predicate<CustomLog> nameDima = p -> p.getUserName().equals("Dima");

    String str = "Dima";

    try (Stream<String> lineStream = Files.lines(path)) {
      List<CustomLog> listLog = lineStream
          .map(line -> new CustomLog(line.split(DELIMETER)[0], line.split(DELIMETER)[1], line.split(DELIMETER)[2]))
          .filter(new CustomPredicate(str))
//          .filter(log -> log.getDateTime().isAfter(localDateTime1)&&log.getDateTime().isBefore(localDateTime2))
//          .filter(log -> log.getMessage().contains("hello"))
          .collect(Collectors.toList());

      Stream<CustomLog> logStream = listLog.stream();
// Группировка по ЮзерНэйм
//      Map<String, Long> logByUserName = logStream.collect(
//          Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));
//
//      for(Map.Entry<String, Long> item : logByUserName.entrySet()){
//
//        System.out.println(item.getKey() + " - " + item.getValue());
//      }
//      Группировка по дате
//      Map<LocalDateTime, List<CustomLog>> logByDate = listLog.stream()
//          .collect(Collectors.groupingBy(e ->
//          LocalDateTime.from(e.getDateTime().truncatedTo(ChronoUnit.DAYS)))
//      );
//
//      for(Map.Entry<LocalDateTime, List<CustomLog>> item : logByDate.entrySet()){
//
//        System.out.println(item.getKey() + " - " + item.getValue().size());
//      }
//      Группировка по юзернэйм и дате

      Map<LocalDateTime, List<CustomLog>> logByDate = listLog.stream()
          .collect(Collectors.groupingBy(e ->
              LocalDateTime.from(e.getDateTime().truncatedTo(ChronoUnit.DAYS)))
          );

      for(Map.Entry<LocalDateTime, List<CustomLog>> item : logByDate.entrySet()){

        System.out.println(item.getKey() + " - ");
        Map<String, Long> logByUserName = item.getValue().stream().collect(
            Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));

        for(Map.Entry<String, Long> item1 : logByUserName.entrySet()){

          System.out.println(item1.getKey() + " - " + item1.getValue());
        }
      }


    } catch (IOException ignored) {
    }

    System.out.println("Choose filters:");
    System.out.println("1. User name:");
    System.out.println("2. Start date and time (format - ):");
    System.out.println("3. End date and time (format - ):");
    System.out.println("4. Consist Words in message (format - ):");

    System.out.println("5. Grouping by userName? (Y/N)");
    System.out.println("6. Grouping by time unit? (N/SECOND/MINUTE/HOUR/DAY/MONTH/YEAR)");

    System.out.println("7. Path or filename to output file: ");


  }

}

//4.2. groupingBy(classifier, collectingAndThen(maxBy(comparator), Optional::get))
//https://habr.com/ru/post/337350/

