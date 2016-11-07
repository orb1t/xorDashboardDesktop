package xorDashboardDesktop.ui.DashboardEditor.JTableX.CellEditors;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class StrCellEditor extends DefaultCellEditor implements TableCellRenderer{

  private static final Font NORMAL_FONT =
      new Font("Serif", Font.ITALIC, 18);
  private static final Font SELECTED_FONT =
      new Font("Serif", Font.ITALIC + Font.BOLD, 18);
  private static final Color SELECTED_COLOR = Color.RED;
  private static final Color NORMAL_COLOR = Color.BLACK;


  public StrCellEditor(String val) {
    super( new JTextField( val ) );
  }


  public Object getCellEditorValue() {
    return  this.delegate.getCellEditorValue();
  }


  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    return this.editorComponent;
  }

  @Override
  public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int i, int i1) {
    if (o == null) return null;
    String rating = (String) o.toString();

    JLabel lbl = new JLabel(rating);

//    Color color =
//        rating.equals(Movie.RATINGS[Movie.NR]) ? Color.red :
//            rating.equals(Movie.RATINGS[Movie.G]) ? Color.orange :
//                rating.equals(Movie.RATINGS[Movie.PG]) ? Color.green :
//                    rating.equals(Movie.RATINGS[Movie.PG13]) ? Color.blue :
//                        rating.equals(Movie.RATINGS[Movie.R]) ? Color.magenta :
//                            Color.black;
    lbl.setText(rating);
    lbl.setFont(b ? SELECTED_FONT : NORMAL_FONT);
    lbl.setForeground(b ? SELECTED_COLOR : NORMAL_COLOR);
    return lbl;
  }
}
