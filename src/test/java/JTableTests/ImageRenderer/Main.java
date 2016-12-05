package JTableTests.ImageRenderer;

/**
 * Created by orb1t_ua on 10/28/16.
 */
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout());

		MyTableModel model = new MyTableModel();

		JTable table = new JTable(model);
		table.setRowHeight(80);
		table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		JScrollPane pane = new JScrollPane(table);
		frame.getContentPane().add(BorderLayout.CENTER, pane);
		frame.setSize(500, 400);
		frame.setVisible(true);
	}
}

class MyTableModel extends AbstractTableModel {
	public Object getValueAt(int row, int column) {
		return "" + (row * column);
	}

	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		return 5;
	}
}

class ImageRenderer extends DefaultTableCellRenderer {
	JLabel lbl = new JLabel();

	ImageIcon icon = new ImageIcon(getClass().getResource("/icons/common_pump.png"));

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                               boolean hasFocus, int row, int column) {
		lbl.setText((String) value);
		lbl.setIcon(icon);
		return lbl;
	}
}
