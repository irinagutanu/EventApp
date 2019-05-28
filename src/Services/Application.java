package Services;

public class Application {
  private Audit logger;
  private ClientManager clientMan;
  private EventManager eventMan;
  
  public void Start() {
    clientMan = ClientManager.getInstance();
  }
  public void Demo() {
    
  }
}
