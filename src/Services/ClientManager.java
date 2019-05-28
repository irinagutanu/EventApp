package Services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import client.Client;

public class ClientManager {
  private static ClientManager instance;
  private Client user;
  
  private static SecureRandom RAND = new SecureRandom();
  private static final int ITERATIONS = 65536;
  private static final int KEY_LENGTH = 512;
  private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
  private Optional<String> Salt;

  private ClientManager(){ }

  public static ClientManager getInstance() {
      if (instance == null) {
          instance = new ClientManager();
      }
      if (instance.user == null) {
          instance.Login();
      }
      return instance;
  }

  public Client getUser() {
      return user;
  }

  public void modifyClient(Client newUser) {
      user = newUser;
  }

  public void Login() {
      Audit.Log("Login");
      Optional<String> userId = Optional.empty();
      int attempts = 3;
      BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("Login");
      boolean foundCredentials = false;
      try {
          while (attempts != 0 && !foundCredentials) {
              --attempts;
              System.out.println("Enter username: ");
              String username = buffer.readLine();
              System.out.println("Enter password: ");
              String password = buffer.readLine();
              userId = Check.verify(username, password);
              if (userId.isPresent() ){
                  foundCredentials = true;
              }
              else {
                  System.out.println("Wrong username or password. Please try again.");
              }
          }
          if (attempts == 0) {
              System.out.println("Exit");
          }
      } catch (IOException e) {
          e.printStackTrace();
      }

      if (userId.isPresent()) {
          user = getUserById(userId.get());
          if (user != null) {
              System.out.println("Login successful");
              System.out.println("Welcome back " + user.getName());
          }
       }
  }

  public void Register() {
      Audit.Log("Register");
      System.out.println("Please complete the following register form: ");
      BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

      try {
          boolean formCompleted = false;
          while(!formCompleted) {
              System.out.println("Enter username: ");
              String username = buffer.readLine();
              System.out.println("Enter first name: ");
              String firstName = buffer.readLine();
              System.out.println("Enter last name: ");
              String lastName = buffer.readLine();
              System.out.println("Enter about yourself: ");
              String aboutMe = buffer.readLine();
              System.out.println("Enter password: ");
              String password = buffer.readLine();
              System.out.println("Confirm password: ");
              Optional<String> passwordHash = setPassword(password);
              System.out.println("Enter birth date (dd/mm/yyyy format): ");
              Date birthDay = new SimpleDateFormat("dd/MM/yyyy").parse(buffer.readLine());

              Client user = new Client(username, firstName, lastName, aboutMe, passwordHash, birthDay);
              formCompleted = true;

              SaveData(user, passwordHash);
          }
      }
      catch (IOException | ParseException e) {
          e.printStackTrace();
      }
      System.out.println("Register successful! Redirecting to login...");
      Login();
  }

  private void SaveData(Client user, Optional<String> passwordHash) {
    String birthDay = new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthday());
    String[] userInfo = {String.valueOf(user.getId()), user.getName(), user.getSurname(), user.getAboutMe(), birthDay};
    String[] credentialsInfo = {user.getUsername(), passwordHash.get(), String.valueOf(user.getId())};

    Save(userInfo, "users.csv");
    Save(credentialsInfo, "credentials.csv");
    Audit.Log("Client saved");
  }
  
  public static void Save(String[] values, String fileName) {
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

  private Client getUserById(String userId) {
    Client user = null;
    try {
        BufferedReader usersBuffer = new BufferedReader(new FileReader("data/users.csv"));
        String line;
        while ((line = usersBuffer.readLine()) != null) {
            String[] info = line.split(",");
            if (userId.equals(info[0])) {
                user = new Client(
                    info[0],
                        info[1],
                        info[2],
                        info[3], 
                        info[4],
                        new SimpleDateFormat("dd/MM/yyyy").parse(info[5]));
                break;
            }
        }
    }
    catch (IOException | ParseException e) {
        e.printStackTrace();
    }
    return user;
  }
  
  public Optional<String> setPassword(String password) {
    Salt = generateSalt(KEY_LENGTH);
    return hashPassword(password, Salt.get());
  }
  
  public static Optional<String> generateSalt (final int length) {

    if (length < 1) {
      System.err.println("error in generateSalt: length must be > 0");
      return Optional.empty();
    }

    byte[] salt = new byte[length];
    RAND.nextBytes(salt);

    return Optional.of(Base64.getEncoder().encodeToString(salt));
  }
  
  public static Optional<String> hashPassword (String password, String salt) {

    char[] chars = password.toCharArray();
    byte[] bytes = salt.getBytes();

    PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

    Arrays.fill(chars, Character.MIN_VALUE);

    try {
      SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
      byte[] securePassword = fac.generateSecret(spec).getEncoded();
      return Optional.of(Base64.getEncoder().encodeToString(securePassword));

    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      System.err.println("Exception encountered in hashPassword()");
      return Optional.empty();

    } finally {
      spec.clearPassword();
    }
  }
  
  public static boolean verifyPassword (String password, String key, String salt) {
    Optional<String> optEncrypted = hashPassword(password, salt);
    if (!optEncrypted.isPresent()) return false;
    return optEncrypted.get().equals(key);
  }
}
