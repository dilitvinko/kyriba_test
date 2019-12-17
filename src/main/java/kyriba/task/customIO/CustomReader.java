package kyriba.task.customIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kyriba.task.entity.CustomLog;
import kyriba.task.logic.CustomPredicate;
import kyriba.task.menu.ConsoleMenu;

public class CustomReader {

  private static final String DELIMETER = " --- ";

  public static List<CustomLog> readFromDirectoryAndFilter(ConsoleMenu consoleMenu) {

    Path pathDir = Paths.get("logsDir/");
    Predicate<CustomLog> logPredicate = CustomPredicate.createPredicate(consoleMenu);
    List<CustomLog> listLogsAfterFilter = new CopyOnWriteArrayList<>(new ArrayList<>());
    ExecutorService service = Executors.newFixedThreadPool(consoleMenu.getCountThreads());

    try (Stream<Path> pathStream = Files.walk(pathDir)) {
      pathStream
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName().toString().endsWith(".txt"))
          .forEach(pathFile -> service.execute(() -> {
                try (Stream<String> lineStream = Files.lines(pathFile)) {
                  listLogsAfterFilter.addAll(lineStream.map(
                      line -> new CustomLog(line.split(DELIMETER)[0], line.split(DELIMETER)[1],
                          line.split(DELIMETER)[2]))
                      .filter(logPredicate)
                      .collect(Collectors.toList()));
                } catch (IOException e) {
                  e.printStackTrace();
                }
              })
          );
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Do not accept new task, perform remaining tasks
    service.shutdown();
    // Wait 10 минут.
    try {
      service.awaitTermination(10, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return listLogsAfterFilter;
  }
}
