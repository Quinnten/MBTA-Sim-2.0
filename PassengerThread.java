import java.util.*;
import java.lang.Thread;

public class PassengerThread extends Thread {

  MBTA mbta;
  Log log;
  Passenger p;

  public PassengerThread(MBTA mbta, Log log, Passenger p){
    this.mbta = mbta;
    this.log = log;
    this.p = p;
  }

  public void run(){
    while(!mbta.nextStop.get(p).equals(null)) {
      if (mbta.onTrain(p)) {
        Train t = mbta.deboardTrain(p);
        Station s = mbta.nextStop.get(p);
        mbta.deboardTrain(t, p, s);
        log.passenger_deboards(p, t, s);
      } else {
        Train t = mbta.boardTrain(p);
        Station s = mbta.passCurrentStation(p);
        log.passenger_boards(p, t, s);
      }
    }
  }
}
