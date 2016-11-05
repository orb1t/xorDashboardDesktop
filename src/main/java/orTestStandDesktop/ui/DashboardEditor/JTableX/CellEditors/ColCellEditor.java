package orTestStandDesktop.ui.DashboardEditor.JTableX.CellEditors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class ColCellEditor extends BasicCellEditor {
    private JColorChooser colorChooser = new JColorChooser();
    private JDialog dialog;

    private JPanel panel;

    public ColCellEditor () {
	    super();
	    JLabel label = new JLabel();
        label.setIcon(new ColorIcon());
        label.setBorder(new EmptyBorder(0, 3, 0, 0));

        editorComponent = label;

        delegate = new EditorDelegate() {
            private EventObject eventObject;

            public boolean startCellEditing(EventObject anEvent) {
                eventObject = anEvent;
                return true;
            }

            public void setValue(Object value) {
                startCellEditing(null);
	            if ( value instanceof String )
                colorChooser.setColor( Color.decode( (String) value ) );// Color.getColor( (String) value ));
	            else
                colorChooser.setColor((Color) value);
            }

            public Object getCellEditorValue() {
                return colorChooser.getColor();//.getRGB();
            }

            public void cancelCellEditing() {
                if (dialog != null)
                    dialog.setVisible(false);
                super.cancelCellEditing();
            }

            public boolean stopCellEditing() {
                if (dialog != null)
                    dialog.setVisible(false);
                return super.stopCellEditing();
            }

            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                if ("cancel".equalsIgnoreCase(actionCommand)) {
                    cancelCellEditing();
                } else if ("ok".equalsIgnoreCase(actionCommand)) {
                    stopCellEditing();
                } else if ("starter".equalsIgnoreCase(actionCommand)) {
                    dialog = JColorChooser.createDialog(null, "Color Chooser", true, colorChooser, this, this);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            dialog.setVisible(true);
                        }
                    });
                    super.startCellEditing(eventObject);
                }
            }
        };

        panel = new JPanel();
        panel.setLayout(new GridLayout(  ) );//ExtendedTableLayout(new double[][]{{-1, 2, 15}, {-1}}));
        panel.add(editorComponent, "0,0");
        panel.setOpaque(false);
        JButton starter = new JButton("...");
        starter.setActionCommand("starter");
        starter.addActionListener(delegate);
        panel.add(starter, "2,0");


    }

	@Override
	public Object getCellEditorValue () {
		Object res = super.getCellEditorValue();
		if ( res instanceof Color )
			res = new String( String.valueOf( ( (Color) res ).getRGB() ) );
		return res;
	}

	public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        Color color = null;
	    if ( value instanceof Color)
	      color=(Color) value;
	    else
	    if ( value instanceof String)
		    color= Color.decode( (String) value ); //Color.getColor( String.valueOf( value ) );
        JLabel label = (JLabel) editorComponent;
        ((ColorIcon) label.getIcon()).setColor(color);
        label.setText( String.valueOf( color.getRGB() ) );//"[r=" + color.getRed() + ",g=" + color.getGreen() + ",b=" + color.getBlue() + "]");
        return panel;
    }

}
