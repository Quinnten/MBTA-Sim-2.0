import java.util.*;
import java.lang.*;

public class Passenger extends Entity {

  private static HashMap<String, Passenger> passengers = new HashMap<>();

  private Passenger(String name) { super(name); }

  public static Passenger make(String name) {
    if (passengers.containsKey(name)) {
      return passengers.get(name);
    } else {
      Passenger p = new Passenger(name);
      passengers.put(name, p);
      return p;
    }
  }
}
