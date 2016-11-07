package xorDashboardDesktop.ui.DashboardEditor.ControlProperties;

import javax.swing.table.DefaultTableModel;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class PropPageTableModel extends DefaultTableModel implements Serializable {

  public static final int COL_ID = 0;
  public static final int COL_NAME = 1;
  public static final int COL_TYPE = 2;
  public static final int COL_VAL = 3;

  public static final String[] colNames = {"Id", "Name", "Type", "Value"};


  public PropPageTableModel(String[] colNames, int i) {
    super(colNames,i);
  }


  public Object getValueAt(int row, int col) {
    return super.getValueAt(row, col);
  }

  public boolean isCellEditable(int row, int col) {
    if (col != COL_VAL)
      return false;
    return true;
  }
}
