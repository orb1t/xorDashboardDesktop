package xorDashboardDesktop.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by orb1t_ua on 09.09.16.
 */
public class TelemetryStrPktDefs extends IPktDefs implements Serializable{

  //final List<String> list = ["A", "B", "C"];

  public TelemetryStrPktDefs(){
    this.version = 1;  //                                   0     1         2       3       4       5         6     7     8     9
    this.headers =  new ArrayList<>(Arrays.asList        ( "id", "dir",    "accT", "accX", "accY", "accZ",   "qT", "qX", "qY", "qZ",                                                                                         "ts",  "pT", "pP", "pA"));
    this.valuesMax = new ArrayList<Double>(Arrays. asList( 0.0, 255.0,    50.0,   02.0,    02.0,    02.0,       (double)(Float.MAX_VALUE), (double)(Float.MAX_VALUE), (double)(Float.MAX_VALUE), (double)(Float.MAX_VALUE),    100.0, 50.0, 1000.0, 120.0 ));
    this.valuesMin = new ArrayList<Double>(Arrays. asList( 0.0, 000.0,    00.0,   -2.0,    -2.0,    -2.0,       (double)(Float.MIN_VALUE), (double)(Float.MIN_VALUE), (double)(Float.MIN_VALUE), (double)(Float.MIN_VALUE),    000.0, 00.0, 0700.0, 100.0 ));
    this.skipFromGraph =  new ArrayList<>(Arrays.asList  ( "dir", "qT", "qX", "qY", "qZ" ));
    //this.valuesMax.add();
        //new ArrayList<>(Arrays.asList("dir", "accT", "accX", "accY", "accZ", "qT", "qX", "qY", "qZ", "ts", "pT", "pP", "pA"));
  }
}
