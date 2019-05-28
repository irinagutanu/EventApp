package Services;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class Audit {
  private static String fileName;

  public Audit(String fName) {
      fileName = fName;
  }

  public static void Log(String message) {
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      try {
          FileWriter fileWriter = new FileWriter("data/" + fileName, true);
          String[] values = {String.valueOf(timestamp), message};
          fileWriter.append("\n");
          fileWriter.append(String.join(",", values));
          fileWriter.append(",");
          fileWriter.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}
