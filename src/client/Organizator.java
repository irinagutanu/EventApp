package client;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import eveniment.Eveniment;

public class Organizator extends Client{
  Set<Eveniment> Events;
  
  public Organizator(String username, String name, String surname, String aboutme, String password, Date birthday)
  {
    super(username, name, surname, aboutme, password, birthday);
    Events = new HashSet<Eveniment>();
  }
  
  public void AddEventToOrganizer(Eveniment ev)
  {
    Events.add(ev);
  }
  
  public void RemoveEventFromOrganizer(Eveniment ev)
  {
    Events.remove(ev);
  }
  
}
