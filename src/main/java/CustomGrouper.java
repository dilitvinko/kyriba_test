import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomGrouper {

  public static List<String> groupBy(ConsoleReader reader){
    List<String> strings = new ArrayList<>();

    return strings;
  }

  private List<String> groupByUserName(List<CustomLog> listLogs) {
    List<String> lines = new ArrayList<>();
    Map<String, Long> logByUserName = listLogs.stream().collect(
        Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));

    for (Map.Entry<String, Long> item : logByUserName.entrySet()) {
      lines.add(item.getKey() + " - " + item.getValue());
    }
    return lines;
  }

  private List<String> groupByTimeUnit(List<CustomLog> listLogs, ChronoUnit timeUnit) {
    List<String> lines = new ArrayList<>();
    Map<LocalDateTime, Long> logByDate = listLogs.stream()
        .collect(Collectors.groupingBy(e ->
            LocalDateTime.from(e.getDateTime().truncatedTo(timeUnit)), Collectors.counting())
        );

    for(Map.Entry<LocalDateTime, Long> item : logByDate.entrySet()){
      lines.add(item.getKey() + " - " + item.getValue());
    }
    return lines;
  }

  public static List<String> groupByTimeUnitAndUserName(List<CustomLog> listLogs, ChronoUnit timeUnit) {
    List<String> lines = new ArrayList<>();
    Map<LocalDateTime, List<CustomLog>> logByDateAndUser = listLogs.stream()
        .collect(Collectors.groupingBy(e ->
            LocalDateTime.from(e.getDateTime().truncatedTo(timeUnit)))
        );

    for(Map.Entry<LocalDateTime, List<CustomLog>> item : logByDateAndUser.entrySet()){

      lines.add(item.getKey().toString());
      Map<String, Long> logByUserName = item.getValue().stream().collect(
          Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));

      for(Map.Entry<String, Long> item1 : logByUserName.entrySet()){
        lines.add(item1.getKey() + " - " + item1.getValue());
      }
      lines.add("\n");
    }
    return lines;
  }

}
