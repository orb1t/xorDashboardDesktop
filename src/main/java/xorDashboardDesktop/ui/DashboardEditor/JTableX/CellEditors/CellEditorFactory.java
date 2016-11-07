package xorDashboardDesktop.ui.DashboardEditor.JTableX.CellEditors;

import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.Arrays;

/**
 * Created by orb1t_ua on 17.10.16.
 */
public class CellEditorFactory {

  public static final CellEditorFactory INSTANCE = new CellEditorFactory();

  private CellEditorFactory(){
  }

  public TableCellEditor getCellEditor (ControlPropertyItem item){
    switch ( item.getType() ){
      case BIN: return new BinCellEditor(Boolean.getBoolean(item.getValue()));
      case FLT: {
        if ( item.getValues().length == 4 )
          return new FltCellEditor ( new SpinnerNumberModel(Integer.valueOf(item.getValue(0)).intValue(), Integer.valueOf(item.getValue(1)).intValue(), Integer.valueOf(item.getValue(2)).intValue(), Integer.valueOf(item.getValue(3)).intValue()) );
        else
          return new FltCellEditor ( Double.valueOf(item.getValue()).doubleValue() );
      }
      case STR: return new StrCellEditor((String) item.getValue());
      case LST: {

        LstCellEditor tmp = new LstCellEditor((Object[]) Arrays.copyOfRange(item.getValues(), 1, item.getValues().length) );
        tmp.setCellEditorValue(item.getValue());
        return tmp;
      }
	    case COL: return new ColCellEditor();//item.getValue());

      default: return new StrCellEditor((String) item.getValue());
    }
  }
}
