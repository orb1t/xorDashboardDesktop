package JTableTests.ShadingRows;

/**
 * Created by orb1t_ua on 10/28/16.
 */


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Main {
	public static void main(String[] argv) {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Col1", new Object[] { Color.red });
		model.addRow(new Object[] { Color.green });
		model.addRow(new Object[] { Color.blue });

		JTable table = new JTable(model) {
			public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
				Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
				if (rowIndex % 2 == 0 && !isCellSelected(rowIndex, vColIndex)) {
					c.setBackground(Color.yellow);
				} else {
					c.setBackground(getBackground());
				}
				return c;
			}
		};

		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout());



//		JTable table = new JTable(model);
		table.setRowHeight(80);
//		table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		JScrollPane pane = new JScrollPane(table);
		frame.getContentPane().add(BorderLayout.CENTER, pane);
		frame.setSize(500, 400);
		frame.setVisible(true);
	}
}
