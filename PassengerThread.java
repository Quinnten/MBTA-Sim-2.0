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
        log.passenger_deboards(p, mbta.deboardTrain(p), mbta.nextStop.get(p));
      } else {
        log.passenger_boards(p, mbta.boardTrain(p), mbta.passCurrentStation(p));
      }
    }
  }
}