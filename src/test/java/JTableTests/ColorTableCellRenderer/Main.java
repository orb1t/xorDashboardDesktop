package JTableTests.ColorTableCellRenderer;

/**
 * Created by orb1t_ua on 10/28/16.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Main {
	public static void main(String[] argv) {
		DefaultTableModel model = new DefaultTableModel() {
			public Class getColumnClass(int mColIndex) {
				int rowIndex = 0;
				Object o = getValueAt(rowIndex, mColIndex);
				if (o == null) {
					return Object.class;
				} else {
					return o.getClass();
				}
			}
		};
		JTable table = new JTable(model);
		model.addColumn("Col1", new Object[] { Color.red });
		model.addRow(new Object[] { Color.green });
		model.addRow(new Object[] { Color.blue });

		table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());

		JFrame f = new JFrame();
		f.setSize(300,300);
		f.add(new JScrollPane(table));
		f.setVisible(true);
	}
}

class ColorTableCellRenderer extends JLabel implements TableCellRenderer {
	Color curColor;
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                               boolean hasFocus, int rowIndex, int vColIndex) {
		if (curColor instanceof Color) {
			curColor = (Color) value;
		} else {
			curColor = table.getBackground();
		}
		return this;
	}

	public void paint(Graphics g) {
		g.setColor(curColor);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
}
