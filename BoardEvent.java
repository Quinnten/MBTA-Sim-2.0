import java.util.*;

public class BoardEvent implements Event {
  public final Passenger p; public final Train t; public final Station s;
  public BoardEvent(Passenger p, Train t, Station s) {
    this.p = p; this.t = t; this.s = s;
  }
  public boolean equals(Object o) {
    if (o instanceof BoardEvent e) {
      return p.equals(e.p) && t.equals(e.t) && s.equals(e.s);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(p, t, s);
  }
  public String toString() {
    return "Passenger " + p + " boards " + t + " at " + s;
  }
  public List<String> toStringList() {
    return List.of(p.toString(), t.toString(), s.toString());
  }






  public void replayAndCheck(MBTA mbta) {
    if (!mbta.lines.get(t).contains(mbta.nextStop.get(p)) || !mbta.stationPass.get(s).contains(p) || !mbta.trainStop.get(t).equals(s)) {
      throw new UnsupportedOperationException("Error in boarding");
    }



  //   // Only move if the passenger is still traveling
  //   System.out.println("Will this ven work?");

  //   if (!s.getPassengers().contains(p)) {
  //     throw new UnsupportedOperationException("The passenger " + p.toString() + " is not located at this station " +s.toString());
  //   }
    
  //   if (p.currDest() == null) {
  //     throw new UnsupportedOperationException("Passenger " + p.toString() + " is done with their journey and can no longer board");
  //   } else if (!t.currStation().equals(s)) {
  //     throw new UnsupportedOperationException("Can't board a passenger at a station they're not at");
  //   }

  //   // make sure that the train is going to the Passenger's destination
  //   List<Station> stations = mbta.lines.get(t);
  //   if (!stations.contains(p.currDest())) {
  //     throw new UnsupportedOperationException(t.toString() + " will not bring " + p.toString() + "to their destination of " + p.currDest().toString());
  //   }

  //   /*update the state
  //     Add passenger to train 
  //     remove passenger from station 
  //   */
  //   t.addPassenger(p);
  //   s.removePassenger(p);

  // System.out.println("About to leave method?");
   
  }
}
