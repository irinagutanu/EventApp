package client;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import eveniment.Eveniment;

public class Client 
{
  private String Id;
  private String Name;
  private String Surname;
  private String Username;
  @SuppressWarnings("unused")
  private Optional<String> Password;
  private String AboutMe;
  private Date Birthday;
  private List<Eveniment> PastEvents;
  
  public Client(String username, String name, String surname, String aboutme, Optional<String> passwordHash, Date birthday) {
    Id = IDGenerator.getUniqueId();
    setUsername(username);
    setName(name);
    setSurname(surname);
    setPasswordHash(passwordHash);
    setAboutMe(aboutme);
    setBirthday(birthday);
  }
  
  public Client(String gotId, String username, String name, String surname, String aboutme, Date birthday) {
    Id = gotId;
    setUsername(username);
    setName(name);
    setSurname(surname);
    setAboutMe(aboutme);
    setBirthday(birthday);
  }
  
  public void setPasswordHash(Optional<String> hash) {
    Password = hash;
  }
  
  public String getId() {
    return Id;
  }
  public String getName() {
    return Name;
  }
  public void setName(String name) {
    Name = name;
  }
  public String getSurname() {
    return Surname;
  }
  public void setSurname(String surname) {
    Surname = surname;
  }
  public String getUsername() {
    return Username;
  }
  public void setUsername(String username) {
    Username = username;
  }
  public String getAboutMe() {
    return AboutMe;
  }
  public void setAboutMe(String aboutMe) {
    AboutMe = aboutMe;
  }
  public Date getBirthday() {
    return Birthday;
  }
  public void setBirthday(Date birthday) {
    Birthday = birthday;
  }
  public List<Eveniment> getPastEvents() {
    return PastEvents;
  }
  public void setPastEvents(List<Eveniment> pastEvents) {
    PastEvents = pastEvents;
  }
}
