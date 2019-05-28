package eveniment;

import java.util.ArrayList;

import client.Client;
import client.IDGenerator;
import client.Organizator;

public class Eveniment {
  private String Id;
  private String Name;
  private Locatie Location;
  enum Type{
    BUSINESS, EDUCATION, ENTERTAINMENT, MUSIC, CHARITY, PERSONALITY, KIDS
  }
  private String OrganizerId;
  private java.util.Date Data;
  private int approximateDuration;
  private ArrayList<String> ClientsId;
  public ArrayList<String> EventsId;
  
  public Eveniment(Organizator org, String name, Locatie loc, java.util.Date date, int aprox) {
    setId();
    setName(name);
    setLocation(loc);
    setDate(date);
    setOrganizerId(org);
    setApproximateDuration(aprox);
    ClientsId = new ArrayList<String>();
  }
  
  public Eveniment(Organizator org) {
    setId();
  }
  
  public void addClient(Client cl)
  {
    ClientsId.add(cl.getId());
  }
  
  public void removeClient(Client cl) {
    ClientsId.remove(cl.getId());
  }
  
  @SuppressWarnings("unlikely-arg-type")
  public boolean checkClient(Client cl)
  {
    return ClientsId.contains(cl);
  }
  
  public String getId() {
    return Id;
  }
  public void setId() {
    Id = IDGenerator.getUniqueId();
  }
  public String getName() {
    return Name;
  }
  public void setName(String name) {
    Name = name;
  }
  public Locatie getLocation() {
    return Location;
  }
  public void setLocation(Locatie location) {
    Location = location;
  }
  public String getOrganizerId() {
    return OrganizerId;
  }
  public void setOrganizerId(Organizator org) {
    OrganizerId = org.getId();
  }
  public java.util.Date getDate() {
    return Data;
  }
  public void setDate(java.util.Date date) {
    Data = date;
  }
  public int getApproximateDuration() {
    return approximateDuration;
  }
  public void setApproximateDuration(int approximateDuration) {
    this.approximateDuration = approximateDuration;
  }

  public ArrayList<String> getEventsId() {
    return EventsId;
  }

  public void setEventsId(ArrayList<String> eventsId) {
    EventsId = eventsId;
  }
  
}
