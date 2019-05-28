package eveniment;

public class Locatie {
  private int Id;
  private String Country, City, Street, Building ;
  private String CloseSpot;
  
  public Locatie(String Country, String City, String Street, String Building, String CloseSpot)
  {
    setCountry(Country);
    setCity(City);
    setStreet(Street);
    setBuilding(Building);
    setCloseSpot(CloseSpot);
  }
  
  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public String getCountry() {
    return Country;
  }

  public void setCountry(String country) {
    Country = country;
  }

  public String getCity() {
    return City;
  }

  public void setCity(String city) {
    City = city;
  }

  public String getStreet() {
    return Street;
  }

  public void setStreet(String street) {
    Street = street;
  }

  public String getBuilding() {
    return Building;
  }

  public void setBuilding(String building) {
    Building = building;
  }

  public String getCloseSpot() {
    return CloseSpot;
  }

  public void setCloseSpot(String closeSpot) {
    CloseSpot = closeSpot;
  }
}
