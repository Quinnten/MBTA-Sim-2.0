import java.util.*;
import java.lang.*;

public class Station extends Entity {

  
  // All stations that have been created
  private static HashMap<String, Station> activeStations = new HashMap<>();

  private Station(String name) { super(name); }

  public static Station make(String name) {
    if (activeStations.containsKey(name)) {
      return activeStations.get(name);
    } else {
      Station s = new Station(name);
      activeStations.put(name, s);
      return s;
    }
  }
}
