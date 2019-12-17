package kyriba.task.logic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kyriba.task.entity.CustomLog;
import kyriba.task.menu.ConsoleMenu;

public class CustomGrouper {

  public static List<String> groupBy(List<CustomLog> listCustomLog, ConsoleMenu reader) {

    if (reader.getGroupByUserName().toUpperCase().equals("Y")
        && reader.getGroupByTimeUnit() != null) {
      return groupByTimeUnitAndUserName(listCustomLog, reader.getGroupByTimeUnit());
    } else if (reader.getGroupByUserName().toUpperCase().equals("Y")) {
      return groupByUserName(listCustomLog);
    } else if (reader.getGroupByTimeUnit() != null) {
      return groupByTimeUnit(listCustomLog, reader.getGroupByTimeUnit());
    }

    return null;
  }

  private static List<String> groupByUserName(List<CustomLog> listLogs) {
    List<String> lines = new ArrayList<>();
    Map<String, Long> logByUserName = listLogs.stream().collect(
        Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));

    for (Map.Entry<String, Long> item : logByUserName.entrySet()) {
      lines.add(item.getKey() + " - " + item.getValue());
    }
    return lines;
  }

  private static List<String> groupByTimeUnit(List<CustomLog> listLogs, ChronoUnit timeUnit) {
    List<String> lines = new ArrayList<>();
    Map<LocalDateTime, Long> logByDate = listLogs.stream()
        .collect(Collectors.groupingBy(e ->
            LocalDateTime.from(e.getDateTime().truncatedTo(timeUnit)), Collectors.counting())
        );

    for (Map.Entry<LocalDateTime, Long> item : logByDate.entrySet()) {
      lines.add(item.getKey() + " - " + item.getValue());
    }
    return lines;
  }

  private static List<String> groupByTimeUnitAndUserName(List<CustomLog> listLogs,
      ChronoUnit timeUnit) {
    List<String> lines = new ArrayList<>();
    Map<LocalDateTime, List<CustomLog>> logByDateAndUser = listLogs.stream()
        .collect(Collectors.groupingBy(e ->
            LocalDateTime.from(e.getDateTime().truncatedTo(timeUnit)))
        );

    for (Map.Entry<LocalDateTime, List<CustomLog>> item : logByDateAndUser.entrySet()) {

      lines.add(item.getKey().toString());
      Map<String, Long> logByUserName = item.getValue().stream().collect(
          Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));

      for (Map.Entry<String, Long> item1 : logByUserName.entrySet()) {
        lines.add(item1.getKey() + " - " + item1.getValue());
      }
      lines.add("\n");
    }
    return lines;
  }

}
