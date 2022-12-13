import java.util.*;
import java.lang.*;

public class Train extends Entity {

  private static HashMap<String, Train> activeLines = new HashMap<>();

  private Train(String name) { super(name); }

  public static Train make(String name) {
    if (activeLines.containsKey(name)) {
      return activeLines.get(name);
    } else {
      Train t = new Train(name);
      activeLines.put(name, t);
      return t;
    }
  }
}
