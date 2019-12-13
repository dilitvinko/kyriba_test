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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
//    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"));
////        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file.txt"))
//    ) {
//
////      bufferedWriter.write(String.valueOf(LocalDateTime.now()) + " qawe" + " advssdklld"+"\n");
////      bufferedWriter.write(String.valueOf(LocalDateTime.now()) + " asdzqawe" + " |Vasdfsdklld"+"\n");
////      bufferedWriter.write(String.valueOf(LocalDateTime.now()) + " qaxzcvweas" + " fdasfasdklld"+"\n");
////      bufferedWriter.write(String.valueOf(LocalDateTime.now()) + " ASWDqdsvawe" + " asdvasdfklld"+"\n");
////      bufferedWriter.close();
//
//      String line;
//      while ((line = bufferedReader.readLine()) != null) {
//        String data[] = line.split(" ");
//        System.out.println(line);
//        LocalDateTime localDateTime = LocalDateTime.parse(data[0]);
//        System.out.println(data[0] + " - " + localDateTime.toString());
//        System.out.println(data[1]);
//        System.out.println(data[2]);
//        System.out.println();
//      }
//
//      System.out.println();
//
//      List<String> stringList = new ArrayList<>();
//      stringList.add("asd sdf");
//      stringList.add("1asd sdf");
//      stringList.add("232323 2asd ");
//      stringList.add("3asd dsfsdf sdf s f");
//
//      List<TestClass> list = stringList.stream().map(str -> new TestClass(str.split(" ")[0]))
//          .collect(Collectors.toList());
//      System.out.println(list);
//
//
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }


    Path path = Paths.get("file.txt");

    LocalDateTime localDateTime1 = LocalDateTime.parse("2019-12-12T10:39:23");
    LocalDateTime localDateTime2 = LocalDateTime.parse("2019-12-12T20:39:23");

    try (Stream<String> lineStream = Files.lines(path)) {
      List<CustomLog> listLog = lineStream
          .map(line -> new CustomLog(line.split(" --- ")[0], line.split(" --- ")[1], line.split(" --- ")[2]))
//          .filter(log -> log.getUserName().equals("qaxzcvweas"))
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




  }

}

//4.2. groupingBy(classifier, collectingAndThen(maxBy(comparator), Optional::get))
//https://habr.com/ru/post/337350/

