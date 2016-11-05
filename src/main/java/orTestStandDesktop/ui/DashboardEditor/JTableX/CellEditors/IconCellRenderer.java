package orTestStandDesktop.ui.DashboardEditor.JTableX.CellEditors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class IconCellRenderer extends JLabel implements TableCellRenderer {

    public IconCellRenderer() {
        setBorder(new EmptyBorder(0, 3, 0, 0));
        setHorizontalAlignment(CENTER);
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setIcon((Icon) value);
        return this;
    }


}
