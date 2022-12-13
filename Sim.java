import java.io.*;
import java.util.*;

public class Sim {

  public static void run_sim(MBTA mbta, Log log) {
    List<Thread> trainThreads = new ArrayList<>();
    List<Thread> passThreads = new ArrayList<>();


    for (Map.Entry<Train, List<Station>> mapEl : mbta.lines.entrySet()) {
      Train t = mapEl.getKey();
      trainThreads.add(new TrainThread(mbta, log, t));
    }

    for (Map.Entry<Passenger, List<Station>> mapEl : mbta.trips.entrySet()) {
      Passenger p = mapEl.getKey();
      passThreads.add(new PassengerThread(mbta, log, p));
    }
    
    for (Thread t : trainThreads) {
      t.start();
    }

    for (Thread p : passThreads) {
      p.start();
    }

    for (Thread t : trainThreads) {
      try {
        t.join();
      } catch(Exception e) {
        throw new UnsupportedOperationException("join problems");
      }
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("usage: ./sim <config file>");
      System.exit(1);
    }

    MBTA mbta = new MBTA();
    mbta.loadConfig(args[0]);

    Log log = new Log();

    run_sim(mbta, log);

    String s = new LogJson(log).toJson();
    PrintWriter out = new PrintWriter("log.json");
    out.print(s);
    out.close();

    mbta.reset();
    mbta.loadConfig(args[0]);
    Verify.verify(mbta, log);
  }
}
