package orTestStandDesktop.ui.DashboardEditor.JTableX.CellEditors;

import orTestStandDesktop.ui.DashboardEditor.ControlProperties.PropPageTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ColCellRenderer extends JLabel implements TableCellRenderer {

    public ColCellRenderer () {
        setIcon(new ColorIcon());
        setBorder(new EmptyBorder(0, 3, 0, 0));
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
	    if ( column == PropPageTableModel.COL_VAL) {
		    Color color = null;//(Color) value;
		    if ( value instanceof Color )
			    color = (Color) value;
		    else if ( value instanceof String )
			    color = Color.decode( (String) value ); //Color.getColor( String.valueOf( value ) );
		    ( (ColorIcon) getIcon() ).setColor( color );
		    setText( String.valueOf( color.getRGB() ) );// "[r=" + color.getRed() + ",g=" + color.getGreen() + ",b=" + color.getBlue() + "]" );
		    return this;
	    } else {
		    return new StrCellEditor(value.toString()).getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
	    }
    }


}
