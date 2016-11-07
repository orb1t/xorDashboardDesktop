package xorDashboardDesktop.ui.DashboardEditor.JTableX;

import javax.swing.table.TableCellEditor;
import java.util.Hashtable;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class RowEditorModel {
  private Hashtable data;

  public RowEditorModel() {
    data = new Hashtable();
  }

  public void addEditorForRow(int row, TableCellEditor editor) {
    data.put(new Integer(row), editor);
  }

  public void removeEditorForRow(int row) {
    data.remove(new Integer(row));
  }

  public TableCellEditor getEditor(int row) {
    return (TableCellEditor) data.get(new Integer(row));
  }
}
