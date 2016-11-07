package xorDashboardDesktop.ui.DashboardEditor.JTableX.CellEditors;

import javax.swing.*;
import java.awt.*;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class BinCellEditor extends DefaultCellEditor{

  public BinCellEditor(boolean val) {
    super( new JCheckBox( "", val ) );
  }




  public Object getCellEditorValue() {
    return  this.delegate.getCellEditorValue();
  }


  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    return this.editorComponent;
  }
}
