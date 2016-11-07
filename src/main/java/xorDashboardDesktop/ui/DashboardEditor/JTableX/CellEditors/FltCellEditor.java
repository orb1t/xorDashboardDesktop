package xorDashboardDesktop.ui.DashboardEditor.JTableX.CellEditors;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class FltCellEditor extends AbstractCellEditor implements TableCellEditor {
  private JSpinner spinner = null;
  private SpinnerNumberModel model = null;

  public static final double SPINNER_DEF_MIN_VAL = 0;
  public static final double SPINNER_DEF_MAX_VAL = 10000;
  public static final double SPINNER_DEF_CUR_VAL = 50;
  public static final double SPINNER_DEF_STP_VAL = 1;


  public FltCellEditor() {
    this(new SpinnerNumberModel ( SPINNER_DEF_CUR_VAL, SPINNER_DEF_MIN_VAL, SPINNER_DEF_MAX_VAL, SPINNER_DEF_STP_VAL));
  }

  public FltCellEditor(double val) {
    this(new SpinnerNumberModel ( val, SPINNER_DEF_MIN_VAL, SPINNER_DEF_MAX_VAL, SPINNER_DEF_STP_VAL));
  }

  public FltCellEditor(SpinnerNumberModel model ) {
    this.model = model;
    spinner = new JSpinner(model);

    spinner.addChangeListener(new ChangeListener() {

      public void stateChanged(ChangeEvent e) {
      }
    });
  }


  public Object getCellEditorValue() {
    Double value = (Double) spinner.getValue();
    return value;
  }


  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    return spinner;
  }
}
