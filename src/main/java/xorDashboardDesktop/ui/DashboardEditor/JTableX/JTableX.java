package xorDashboardDesktop.ui.DashboardEditor.JTableX;

import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.PropPageTableModel;
import xorDashboardDesktop.ui.DashboardEditor.JTableX.CellEditors.CellEditorFactory;
import xorDashboardDesktop.ui.DashboardEditor.JTableX.CellEditors.CellRendererFactory;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class JTableX extends JTable {

	private PropPageTableModel model;
	protected RowEditorModel rm;
  protected RowRendererModel rrm;

	Object[] longValues;// = new Object[]//model.longValues;

	private void initColumnSizes(JTable table) {
		PropPageTableModel model = ( PropPageTableModel ) table.getModel();
		TableColumn column = null;
		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;

		TableCellRenderer headerRenderer = table.getTableHeader()
				                                   .getDefaultRenderer();
		for (int i = 0; i < 5; i++) {
			column = table.getColumnModel().getColumn(i);
			comp = headerRenderer.getTableCellRendererComponent(null, column
					                                                          .getHeaderValue(), false, false, 0, 0);
			headerWidth = comp.getPreferredSize().width;
			comp = table.getDefaultRenderer(model.getColumnClass(i))
					       .getTableCellRendererComponent(table, longValues[i], false,
							       false, 0, i);
			cellWidth = comp.getPreferredSize().width;
//			if (DEBUG) {
				System.out.println("Initializing width of column " + i + ". "
						                   + "headerWidth = " + headerWidth + "; cellWidth = "
						                   + cellWidth);
//			}
			//XXX: Before Swing 1.1 Beta 2, use setMinWidth instead.
			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
		}
	}

  public JTableX() {
    super();
    rm = null;
    rrm = null;
  }

  public JTableX(TableModel tm) {
    super(tm);
    rm = null;
    rrm = null;
  }

  public JTableX(TableModel tm, TableColumnModel cm) {
    super(tm, cm);
    rm = null;
    rrm = null;
  }

  public JTableX(TableModel tm, TableColumnModel cm, ListSelectionModel sm) {
    super(tm, cm, sm);
    rm = null;
    rrm = null;
  }

  public JTableX(int rows, int cols) {
    super(rows, cols);
    rm = null;
    rrm = null;
  }

  public JTableX(final Vector rowData, final Vector columnNames) {
    super(rowData, columnNames);
    rm = null;
    rrm = null;
  }

  public JTableX(final Object[][] rowData, final Object[] colNames) {
    super(rowData, colNames);
    rm = null;
    rrm = null;
  }

  public JTableX(TableModel tm, RowEditorModel rm) {
    super(tm, null, null);
    this.rm = rm;
    rrm = null;
  }


	public boolean addRow ( ControlPropertyItem newRow ){
		Object[] o = new Object[PropPageTableModel.colNames.length];
		o[PropPageTableModel.COL_ID] = model.getRowCount();
		o[PropPageTableModel.COL_NAME] = newRow.getName();
		o[PropPageTableModel.COL_TYPE] = newRow.getType();
		o[PropPageTableModel.COL_VAL] = newRow.getValue();
		if (newRow.getType() != EControlPropertyItemType.SYS) {
			model.addRow(o);
			this.rm.addEditorForRow( model.getRowCount()-1, CellEditorFactory.INSTANCE.getCellEditor(newRow));
			if (newRow.getType() == EControlPropertyItemType.COL) {
//				setRowHeight( model.getRowCount()-1, 64 );
				this.rrm.addRendererForRow(model.getRowCount()-1, CellRendererFactory.INSTANCE.getCellRenderer(newRow));
			}
//			this.setRo
//        this.rrm.addRendererForRow(rowNum, CellRendererFactory.INSTANCE.getCellRenderer(s));
//			rowNum++;
		}
//		else		{
//			idx--;}
		System.out.println("addRow = [" + o[PropPageTableModel.COL_NAME] + " : " + o[PropPageTableModel.COL_TYPE] + "] = " + o[PropPageTableModel.COL_VAL]);
		return true;
	}

  public JTableX(ControlProperties properties, RowEditorModel rm, RowRendererModel rrm) {
    super();
    this.rm = rm;
    this.rrm = rrm;

    // TODO: set up CellEditors for ROWs, based on TableModel's Items TYPE
    model = new PropPageTableModel(PropPageTableModel.colNames, 0);
    setModel(model);
//	  this.longValues = new Object[model.getColumnCount()-1];
//
//    int rowNum = 0;
//    int idx = 0;
    for (ControlPropertyItem s : properties.getProperties()) {
      addRow( s );
//	    for ( int i = 0; i < model.getColumnCount(); i++ ){
//		    Object tmp = model.getValueAt( model.getRowCount()-1, i );
//		    if ( tmp.toString().length() > this.longValues[i].toString().length() )
//		      this.longValues[i] = tmp;
//	    }
    }
//
//	  initColumnSizes( this );

  }

  public void setRowEditorModel(RowEditorModel rm) {
    this.rm = rm;
  }

  public RowEditorModel getRowEditorModel() {
    return this.rm;
  }

  public TableCellEditor getCellEditor(int row, int col) {
    TableCellEditor tmpEditor = null;

    if (null != rm)
      tmpEditor = rm.getEditor(row);
    if (null != tmpEditor)
      return tmpEditor;
    return super.getCellEditor(row, col);
  }


  public void setRowRendererModel(RowRendererModel rrm) {
    this.rrm = rrm;
  }

  public RowRendererModel getRowRendererModel() {
    return this.rrm;
  }

  public TableCellRenderer getCellRenderer(int row, int col) {
    TableCellRenderer tmpRenderer = null;

    if (null != rrm)
      tmpRenderer = rrm.getRenderer(row);
    if (null != tmpRenderer)
      return tmpRenderer;
    return super.getCellRenderer(row, col);
  }


}
