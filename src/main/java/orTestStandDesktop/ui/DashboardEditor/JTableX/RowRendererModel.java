package orTestStandDesktop.ui.DashboardEditor.JTableX;

import javax.swing.table.TableCellRenderer;
import java.util.Hashtable;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class RowRendererModel {
  private Hashtable data;

  public RowRendererModel() {
    data = new Hashtable();
  }

  public void addRendererForRow(int row, TableCellRenderer renderer) {
    data.put(new Integer(row), renderer);
  }

  public void removeRendererForRow(int row) {
    data.remove(new Integer(row));
  }

  public TableCellRenderer getRenderer(int row) {
    return (TableCellRenderer) data.get(new Integer(row));
  }
}
