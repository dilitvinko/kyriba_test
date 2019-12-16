import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    final String DELIMETER = " --- ";


    Path pathes = Paths.get("logsDir/");
    Stream<String> lineStream1 = null;
    List<String> a = new ArrayList<>();
    final List<CustomLog> listLogsAfterFilter = new CopyOnWriteArrayList<>(new ArrayList<>());

//    try (Stream<Path> paths = Files.walk(pathes)){
//      listLogsAfterFilter = paths
//          .filter(Files::isRegularFile)
//          .flatMap(p -> {
//            try {
//              return Files.lines(p);
//            } catch (IOException e) {
//              e.printStackTrace();
//            }
//            return null;
//          }).map(line -> new CustomLog(line.split(DELIMETER)[0], line.split(DELIMETER)[1], line.split(DELIMETER)[2]))
//          .filter(p -> p.getUserName().equals("Dima"))
//          .collect(Collectors.toList());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//      listLogsAfterFilter.size();



    ExecutorService service = Executors.newFixedThreadPool(2);

    try (Stream<Path> pathStream = Files.walk(pathes)){
      pathStream
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName().toString().endsWith(".txt"))
          .forEach(pathFile -> service.execute(() -> {
                try (Stream<String> lineStream = Files.lines(pathFile)) {
                  listLogsAfterFilter.addAll(lineStream.map(line -> new CustomLog(line.split(DELIMETER)[0], line.split(DELIMETER)[1], line.split(DELIMETER)[2]))
                      .filter(log -> true)
                      .collect(Collectors.toList()));
                      //.forEach(log -> System.out.println("Поток: " + Thread.currentThread().getName() + ". Файл: " + pathFile.toString() + ". LOG: " + log));

                } catch (FileNotFoundException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
          })

          );
    } catch (IOException e) {
      e.printStackTrace();
    }
    // Новые задачи более не принимаем, выполняем только оставшиеся.
    service.shutdown();
    // Ждем завершения выполнения потоков не более 10 минут.
    try {
      service.awaitTermination(10, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(listLogsAfterFilter.size());


    Path path = Paths.get("file.txt");

    LocalDateTime localDateTime1 = LocalDateTime.parse("2019-12-12T10:39:23");
    LocalDateTime localDateTime2 = LocalDateTime.parse("2019-12-12T20:39:23");

    Predicate<CustomLog> userNamePredicate = customLog -> true;
    Predicate<CustomLog> dateTimePredicate = customLog -> true;
    Predicate<CustomLog> wordsConsistPredicate = customLog -> true;
    Predicate<CustomLog> wordsConsistPredicate123 = userNamePredicate.and(dateTimePredicate);

// Группировка по ЮзерНэйм
//      Map<String, Long> logByUserName = logStream.collect(
//          Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));
//
//      for(Map.Entry<String, Long> item : logByUserName.entrySet()){
//
//        System.out.println(item.getKey() + " - " + item.getValue());
//      }
//      Группировка по дате
//      Map<LocalDateTime, Long> logByDate = listLogsAfterFilter.stream()
//          .collect(Collectors.groupingBy(e ->
//          LocalDateTime.from(e.getDateTime().truncatedTo(ChronoUnit.DAYS)), Collectors.counting())
//      );
//
//      for(Map.Entry<LocalDateTime, Long> item : logByDate.entrySet()){
//
//        System.out.println(item.getKey() + " - " + item.getValue());
//      }
//      Группировка по юзернэйм и дате

//      Map<LocalDateTime, List<CustomLog>> logByDateAndUser = listLogsAfterFilter.stream()
//          .collect(Collectors.groupingBy(e ->
//              LocalDateTime.from(e.getDateTime().truncatedTo(ChronoUnit.DAYS)))
//          );
//
//      for(Map.Entry<LocalDateTime, List<CustomLog>> item : logByDateAndUser.entrySet()){
//
//        System.out.println(item.getKey());
//        Map<String, Long> logByUserName = item.getValue().stream().collect(
//            Collectors.groupingBy(CustomLog::getUserName, Collectors.counting()));
//
//        for(Map.Entry<String, Long> item1 : logByUserName.entrySet()){
//
//          System.out.println(item1.getKey() + " - " + item1.getValue());
//        }
//        System.out.println();
//      }



    ConsoleReader reader = new ConsoleReader();
//    reader.readForPredicate();
//    reader.readForGrouping();
    //reader.readForThreads();
    //CustomPredicate.createPredicate(reader);
    //read to list logs using predicate
    List<String> list = CustomGrouper.groupByTimeUnitAndUserName(listLogsAfterFilter, ChronoUnit.DAYS);
    CustomWriter.writeInFile("log.txt" , list);
    CustomGrouper.groupBy(reader);
    //CustomGrouper.groupByUserName(listLogsAfterFilter);









    System.out.println("5. Count of threads used to process files ");
    System.out.println("7. Path or filename to output file: ");

  }



}

//4.2. groupingBy(classifier, collectingAndThen(maxBy(comparator), Optional::get))
//https://habr.com/ru/post/337350/

