package xorDashboardDesktop.ui.DashboardEditor.JTableX.CellEditors;

import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Created by orb1t_ua on 17.10.16.
 */
public class CellRendererFactory {

  public static final CellRendererFactory INSTANCE = new CellRendererFactory();

  private CellRendererFactory(){
  }

  public TableCellRenderer getCellRenderer (ControlPropertyItem item){
    switch ( item.getType() ){
      case STR: return new StrCellEditor((String) item.getValue());
      case COL: return new ColCellRenderer();//(String) item.getValue());

      default: return new DefaultTableCellRenderer();
    }
  }
}
