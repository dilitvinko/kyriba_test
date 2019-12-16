import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomWriter {

  public static void writeInFile(String fileName, List<String> dataToFile){

    try {
      Path path = Files.createFile(Paths.get(fileName));
      Files.write(path, dataToFile, StandardCharsets.UTF_8);
      System.out.println(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
