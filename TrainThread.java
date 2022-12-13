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
      log.train_moves(t, mbta.currentStation(t), mbta.nextStation(t));
      }
    } catch(Exception e) {
      throw new UnsupportedOperationException("error with run");
    }
  }
}