import java.util.*;
import java.lang.Thread;

public class TrainThread extends Thread {
  
  MBTA mbta;
  Log log;
  Train t;

  public TrainThread(MBTA mbta, Log log, Train t){
    this.mbta = mbta;
    this.log = log;
    this.t = t;
  }

  public void run(){
    try {
      while (mbta.running()) {
      sleep(500);
      Station s1 = mbta.currentStation(t);
      Station s2 = mbta.nextStation(t);
      mbta.moveTrain(t, s1, s2);
      log.train_moves(t, s1, s2);
      }
    } catch(Exception e) {
      throw new UnsupportedOperationException("error with run");
    }
  }
}
