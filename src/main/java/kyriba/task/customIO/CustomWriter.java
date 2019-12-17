package kyriba.task.customIO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import kyriba.task.entity.CustomLog;

public class CustomWriter {

  public static void writeInFile(String fileName, List<CustomLog> logsAfterFilter,
      List<String> dataGroupingToFile) {

    logsAfterFilter
        .stream()
        .map(log -> dataGroupingToFile.add(log.toString()))
        .collect(Collectors.toList());

    try {
      Path path = Files.createFile(Paths.get(fileName));
      Files.write(path, dataGroupingToFile, StandardCharsets.UTF_8);
      System.out.println(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
