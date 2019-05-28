package Services;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import client.Organizator;
import eveniment.Eveniment;
import eveniment.Locatie;

public class EventManager {
  private static String userId;
  private static EventManager instance;
  private static Eveniment event;
  private static Locatie loc;

  private EventManager(Organizator user){
      event = new Eveniment(user);
      userId = user.getId();
  }

  static EventManager getInstance(Organizator user) {
      if (instance == null) {
          return instance = new EventManager(user);
      }
      return instance;
  }

  public void addEvent( Organizator org, String name, Locatie loc, java.util.Date date, int aprox) {
      Audit.Log("Create event locally");
      Eveniment newEvent = new Eveniment(org, name, loc, date, aprox);
      event.EventsId.add(newEvent.getId());
      SaveEvent(newEvent, userId);
  }

  public void addLocation(String Country, String City, String Street, String Building, String CloseSpot) {
      Audit.Log("Create location");
      Locatie newLoc = new Locatie(Country, City, Street, Building, CloseSpot);
      loc = newLoc;
      SaveLoc(loc, userId);
  }
  
  public static void SaveEvent(Eveniment event, String userId) {
    String[] values = {
            String.valueOf(event.getId()),
            userId,
            event.getName(),
            event.getLocation().getCountry() + ", " + event.getLocation().getCity() + ", " + event.getLocation().getBuilding(),
            String.valueOf(event.getApproximateDuration()), 
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getDate())};
    Save(values, "events.csv");
    Audit.Log("Event saved");
}
  
  public static void SaveLoc(Locatie loc, String userId) {
    String[] values = {
          String.valueOf(loc.getId()), 
            loc.getCountry(), 
            loc.getCity(),
            loc.getBuilding(), 
            loc.getStreet(),
            loc.getCloseSpot()};
    Save(values, "locations.csv");
    Audit.Log("Location saved");
}

  private static void Save(String[] values, String fileName) {
    try {
      FileWriter fileWriter = new FileWriter("data/" + fileName, true);
      fileWriter.append("\n");
      fileWriter.append(String.join(",", values));
      fileWriter.append(",");
      fileWriter.close();
  } catch (IOException e) {
      e.printStackTrace();
  }
  }
}
