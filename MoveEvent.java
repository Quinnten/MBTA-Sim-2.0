import java.util.*;

public class MoveEvent implements Event {
  public final Train t; public final Station s1, s2;
  public MoveEvent(Train t, Station s1, Station s2) {
    this.t = t; this.s1 = s1; this.s2 = s2;
  }
  public boolean equals(Object o) {
    if (o instanceof MoveEvent e) {
      return t.equals(e.t) && s1.equals(e.s1) && s2.equals(e.s2);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(t, s1, s2);
  }
  public String toString() {
    return "Train " + t + " moves from " + s1 + " to " + s2;
  }
  public List<String> toStringList() {
    return List.of(t.toString(), s1.toString(), s2.toString());
  }






  public void replayAndCheck(MBTA mbta) {
    if (mbta.occupied.get(s2) || !mbta.trainStop.get(t).equals(s1)) {
      throw new UnsupportedOperationException ("error in moving train");
    }

    List<Station> stations = mbta.lines.get(t);
    int index = stations.indexOf(s1);

    if (mbta.forward.get(t)) {
      if (!s1.equals(stations.get(index + 1))) {
        throw new UnsupportedOperationException("error 2 in moving train");
      }
    } else {
      if (!s1.equals(stations.get(index - 1))) {
        throw new UnsupportedOperationException("error 3 in moving train");
      }
    }
  }
}
