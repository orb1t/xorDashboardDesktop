package orTestStandDesktop.ui.DashboardEditor.JTableX.CellEditors;

import javax.swing.*;
import java.awt.*;

/**
 * Created by orb1t_ua on 16.10.16.
 */
public class LstCellEditor extends DefaultCellEditor{

	private final Object[] model;

	public LstCellEditor( Object[] model) {
    super( new JComboBox( model ) );
	  this.model = model;
  }




  public Object getCellEditorValue() {
	  StringBuilder tmp = new StringBuilder( this.delegate.getCellEditorValue().toString() + ";" );
	  for (  Object o : model ){
		  System.out.println( o.toString() );
		  tmp.append( o.toString() + ";" );
	  }
	  System.out.println( "model = " + model + " : " + model );//.toString() );
	  System.out.println( "tmp = " + tmp.toString() );//.toString() );
	  return  tmp.toString();//this.delegate.getCellEditorValue();
  }

public void setCellEditorValue(Object o) {
  ((JComboBox)this.editorComponent).setSelectedItem(o);
  }


  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    return this.editorComponent;
  }

//	@Override
//	protected void fireEditingStopped () {
//		super.fireEditingStopped();
//	}
}
