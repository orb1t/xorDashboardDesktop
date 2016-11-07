package xorDashboardDesktop.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by orb1t_ua on 09.09.16.
 */
public abstract class IPktDefs implements Serializable {
  int version = 0;// = 0;
  ArrayList<Object> headers = null;// = new ArrayList<>();
  ArrayList<Object> skipFromGraph = null;// = new ArrayList<>();
  public ArrayList<Double> valuesMax = null;
  public ArrayList<Double> valuesMin = null;

  int getVersion(){
    return this.version;
  }


  public ArrayList<Double> getValuesMin(){return  this.valuesMin;}
  public ArrayList<Double> getValuesMax(){return  this.valuesMax;}

  ArrayList<Object> getHeaders(){
    return this.headers;
  }

  ArrayList<Object> getSkipFromGraph(){
    return this.skipFromGraph;
  }
}
