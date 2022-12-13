import java.util.*;
import java.io.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import com.google.gson.*;

public class MBTA {

  public HashMap<Train, List<Station>> lines = new HashMap<>();
  public HashMap<Passenger, List<Station>> trips = new HashMap<>();

  //New maps 
  public HashMap<Train, Station> trainStop = new HashMap<>();
  public HashMap<Train, Boolean> forward = new HashMap<>();
  public HashMap<Station, Boolean> occupied = new HashMap<>();
  public HashMap<Station, List<Passenger>> stationPass = new HashMap<>();
  public HashMap<Train, List<Passenger>> trainPass = new HashMap<>();
  public HashMap<Passenger, Station> nextStop = new HashMap<>();



  // Creates an initially empty simulation
  public MBTA() { }

  // Adds a new transit line with given name and stations
  public void addLine(String name, List<String> stations) {
    Train t = Train.make(name);

    // Make every string in parameter into a station 
    ArrayList<Station> stationList = new ArrayList<>();
    for (String s : stations) {
      stationList.add(Station.make(s));
      Boolean b = false;
      occupied.put(Station.make(s), b);
      ArrayList<Passenger> sPass = new ArrayList<>();
      stationPass.put(Station.make(s), sPass);
    }
    
    ArrayList tPass = new ArrayList<Passenger>();
    trainPass.put(t, tPass);
    trainStop.put(t, stationList.get(0));
    occupied.put(stationList.get(0), true);
    forward.put(t, true);
    lines.put(t, stationList);

  }

  // Adds a new planned journey to the simulation
  public void addJourney(String name, List<String> stations) {
    Passenger p = Passenger.make(name);

    ArrayList<Station> stationList = new ArrayList<>();
    for (String s : stations) {
      stationList.add(Station.make(s));
    }

    stationPass.get(stationList.get(0)).add(p);
    trips.put(p, stationList);
    nextStop.put(p, stationList.get(1));
  }

  // Return normally if initial simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkStart() {
    for (Map.Entry<Passenger, List<Station>> mapEl : trips.entrySet()) {
      Passenger p = mapEl.getKey();
      List<Station> values = mapEl.getValue();


      if (!nextStop.get(p).equals(values.get(1))) {
        throw new UnsupportedOperationException("passenger mapping failed");
      }
    }

    for (Map.Entry<Train, List<Station>> mapEl : lines.entrySet()) {
      Train t = mapEl.getKey();
      List<Station> values = mapEl.getValue();

      if (!trainStop.get(t).equals(values.get(0)) || !forward.get(t) || !trainPass.get(t).isEmpty()) {
        throw new UnsupportedOperationException("train mapping failed");
      }
    }
      
}

  // Return normally if final simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkEnd() {
    for (Map.Entry<Passenger, List<Station>> mapEl : trips.entrySet()) {
      Passenger p = mapEl.getKey();

      if (!nextStop.get(p) == null) {
        throw new UnsupportedOperationException("passenger checkEnd failed");
      }
    }

    for (Map.Entry<Train, List<Station>> mapEl : lines.entrySet()) {
      Train t = mapEl.getKey();
      List<Station> values = mapEl.getValue();

      if (!trainPass.get(t).isEmpty()) {
        throw new UnsupportedOperationException("train checkEnd failed");
      }
    }

  }

  // reset to an empty simulation
  public void reset() {
    HashMap<Train, List<Station>> l = new HashMap<>();
    lines = l;
    HashMap<Passenger, List<Station>> t = new HashMap<>();
    trips = t;
    //New maps 
    HashMap<Train, Station> ts = new HashMap<>();
    trainStop = ts;
    HashMap<Train, Boolean> f = new HashMap<>();
    forward = f;
    HashMap<Station, Boolean> o = new HashMap<>();
    occupied = o;
    HashMap<Station, List<Passenger>> sp = new HashMap<>();
    stationPass = sp;
    HashMap<Train, List<Passenger>> tp = new HashMap<>();
    trainPass = tp;
    HashMap<Passenger, Station> ns = new HashMap<>();
    nextStop = ns;
  }

  // adds simulation configuration from a file
  public void loadConfig(String filename) {
      try {
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
      
        String data = " ";
        while (myReader.hasNextLine()) {
          data = data.concat(myReader.nextLine());
        }

        Gson gson = new Gson();
        Converter c = gson.fromJson(data, Converter.class);

        for (Map.Entry<String, List<String>> mapElement : c.lines.entrySet()) {
          String key = mapElement.getKey();
          List<String> values = mapElement.getValue();
          if (lines.containsKey(key)) {
            throw new UnsupportedOperationException("Can not add two lines with the same name");
          }
          addLine(key, values);
        }

        for (Map.Entry<String, List<String>> mapElement : c.trips.entrySet()) {
          String key = mapElement.getKey();
          List<String> values = mapElement.getValue();
          if (trips.containsKey(key)) {
            throw new UnsupportedOperationException("Can not add two passemngers with the same name");
          }
          addJourney(key, values);
        }
        myReader.close();

      } catch(Exception e) {
          e.printStackTrace();
      }
  }


  public void moveTrain(Train t, Station s1, Station s2) {
    Boolean f = false;
    Boolean tr = true;

    trainStop.replace(t, s2);
    occupied.replace(s1, f);
    occupied.replace(s2, tr);

    List<Station> stations = lines.get(t);

    if (s2.equals(stations.get(0))){
      forward.replace(t, tr);
    } else if (s2.equals(stations.get(stations.size() - 1))) {
      forward.replace(t, f);
    }
  }

  public void boardTrain(Train t, Passenger p, Station s) {
    trainPass.get(t).add(p);
    stationPass.get(s).remove(p);
  }

  public void deboardTrain(Train t, Passenger p, Station s) {
    trainPass.get(t).remove(p);
    stationPass.get(s).add(p);

    List<Station> journey = trips.get(p);
    
    int index = journey.indexOf(nextStop.get(p));

    if (index == journey.size() - 1) {
      nextStop.replace(p, null);
    } else {
      nextStop.replace(p, journey.get(index + 1));
    }
  }

  public Station nextStation(Train t) {
    List<Station> stations = lines.get(t);
    int index = stations.indexOf(currentStation(t));

    if (forward.get(t)) {
      return stations.get(index + 1);
    } else {
      return stations.get(index - 1);
    }
  }
 

  public Station currentStation(Train t) {
    return trainStop.get(t);
 }


  public boolean running() {
    for (Map.Entry<Passenger, List<Station>> mapEl : trips.entrySet()) {
      Passenger p = mapEl.getKey();

      if (!nextStop.get(p) == null) {
        return false;
      }
    }

    return true;
  }
}


