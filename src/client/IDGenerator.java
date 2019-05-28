package client;
import java.util.UUID;

public class IDGenerator {
  public static String getUniqueId() {
      String uniqueID = UUID.randomUUID().toString();
      return uniqueID;
  }
}
