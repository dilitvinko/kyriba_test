package kyriba.task;

import java.util.List;
import kyriba.task.customIO.CustomReader;
import kyriba.task.customIO.CustomWriter;
import kyriba.task.entity.CustomLog;
import kyriba.task.logic.CustomGrouper;
import kyriba.task.menu.ConsoleMenu;

public class Main {

  public static void main(String[] args) {

    ConsoleMenu consoleMenu = new ConsoleMenu();
    consoleMenu.startMenu();
    List<CustomLog> listLogsAfterFilter = CustomReader.readFromDirectoryAndFilter(consoleMenu);
    List<String> listLinesAfterGrouping = CustomGrouper.groupBy(listLogsAfterFilter, consoleMenu);
    CustomWriter
        .writeInFile(consoleMenu.getFileName(), listLogsAfterFilter, listLinesAfterGrouping);

  }
}

