package xorDashboardDesktop.ui.DashboardEditor.ControlPropertiesDialog;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by orb1t_ua on 12.10.16.
 */
public class DashboardBlockPropertiesTableModel extends DefaultTableModel {
//    private double[][] data = null;


  static final String[] cols = {"Property", "Type", "Value"};//{"col0", "col1", "col2", "col3", "col4", "col5", "col6"};
  static final String[] typs = {"BIN", "FLT", "STR", "LST"};//{"col0", "col1", "col2", "col3", "col4", "col5", "col6"};
  static final int nb_rows = 100;

  private ArrayList<ArrayList<Object>> data = new ArrayList<>();
  private ArrayList<Object> hdrs = new ArrayList<>(); //Arrays. asList(new String[]{"Property", "Type", "Value"});


  public DashboardBlockPropertiesTableModel() {
    super();
    Collections.addAll(hdrs, cols);
  }


  public void addRow(ArrayList<Object> var1) {
    data.add(var1);
    fireTableDataChanged();
  }

  public int getRowCount() {
    return (data != null) ? data.size() : 0;
  }

  public int getColumnCount() {
    return hdrs.size();
  }

  public String getColumnName(int column) {
    return hdrs.get(column).toString();
  }

  public Class getColumnClass(int columnIndex) {
    return hdrs.get(columnIndex).getClass();
  }


  public Object getValueAt(int row, int column) {
    return data.get(row).get(column);
  }


  public boolean isCellEditable(int row, int column) {
    if (column <= 1)
      return false;
    return true;
  }


  public void setValueAt(Object aValue, int row, int column) {
    data.get(row).set(column, aValue);
  }

}
