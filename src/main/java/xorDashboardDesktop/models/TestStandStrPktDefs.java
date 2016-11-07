package xorDashboardDesktop.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by orb1t_ua on 09.09.16.
 */
public class TestStandStrPktDefs extends IPktDefs implements Serializable{
/*
Serial.print(pktsCnt);  // #0 : Num of PKT in CURRENT SECOND

  uint16_t ts = millis();//micros();
  Serial.print(",");
  Serial.print ( ts - tsTmp );  // #1 : DeltaT - time passed from last PKT was sent up to current Millisecond
//  Serial.print ( "," );
  tsTmp = ts;

  // put your main code here, to run repeatedly:
  flowMetterLoop(); // #2, 3, 4 :   Flow rate ( L/min ), Current Liquid Flowing ( mL/Sec ), TTL Output Liquid Quantity ( mL )
  servoPotLoop();   // #5, 6 :  Analog Pot Val, Servo Pos Val ( scaled )
  relaysControlLoop();  // #7, 8 : Rel#0 Val, Rel#1 Val
  Serial.println();
 */
  //final List<String> list = ["A", "B", "C"];

  public TestStandStrPktDefs(){
    this.version = 1;                 //                    0     1       2       3       4       5           6       7         8       9
    this.headers =  new ArrayList<>(Arrays.asList        ( "id", "#@s",   "dT",   "rate", "flow", "ttl",      "pot",  "srv",   "rel0", "rel1"));
    this.valuesMax = new ArrayList<Double>(Arrays. asList( Double.valueOf( Integer.MAX_VALUE), 512.0,    1024.0, 10.0,   512.0,  65535.0,    1023.0, 180.0,   1.0,    1.0));
      this.valuesMin = new ArrayList<Double>(Arrays. asList( 0.0, 000.0,    00.0,   0.0,    0.0,    0.0,        0.0,    0.0,   0.0,    0.0));
//    this.skipFromGraph =  new ArrayList<>(Arrays.asList  ( "dir", "qT", "qX", "qY", "qZ" ));
    //this.valuesMax.add();
        //new ArrayList<>(Arrays.asList("dir", "accT", "accX", "accY", "accZ", "qT", "qX", "qY", "qZ", "ts", "pT", "pP", "pA"));
  }
}
