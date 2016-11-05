package orTestStandDesktop.models;

import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by orb1t_ua on 10.07.16.
 */
public class pktTableModel extends AbstractTableModel implements Serializable {

  public ArrayList<Object> newRowTmp;

    public ArrayList<ArrayList<Object>> values = new ArrayList<>();
  public IPktDefs pktDefs;

  public void prepareNewRow(){
    this.newRowTmp = new ArrayList<>();
  }

  public ArrayList<Object> getHeaders(){
    return this.pktDefs.getHeaders();
  }

  public void setHeaders(IPktDefs defs){
    this.pktDefs = defs;
  }

  public pktTableModel(IPktDefs defs){
    setHeaders(defs);
    }

  public ArrayList<Object> getSkipFromGraph(){
    return this.pktDefs.skipFromGraph;
  }

  public boolean isSkipFromGraph ( String val ){
    return this.pktDefs.getSkipFromGraph().contains(val);
  };

    public int getRowCount() {
      return this.values.size();
    }

    public int getColumnCount() {
      return pktDefs.getHeaders().size(); //( null != values ? this.values.get(0).size() : 0 );
    }

    public Object getValueAt(int row, int column) {
      return this.values.get(row).get(column);
    }

  public Object getMaxValueOf(int column) {
    return this.pktDefs.valuesMax.get(column);
  }
  public Object setMaxValueOf(int column, double val) {
    return this.pktDefs.valuesMax.set(column, val);
  }

  public Object getMinValueOf(int column) {
    return this.pktDefs.valuesMin.get(column);
  }
  public Object setMinValueOf(int column, double val) {
    return this.pktDefs.valuesMin.set(column, val);
  }

    public void addRow(ArrayList<Object> objects) {
      this.values.add(objects);
      //this.pktDefs.valuesMax.set(0, (double) (this.values.size() - 1));
      fireTableRowsInserted(this.values.size()-1, this.values.size()-1);
    }
  }
