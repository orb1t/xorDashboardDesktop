package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockIndicator;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import xorDashboardDesktop.ui.MainForm;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class LabelBlockIndicator extends AbstractBlockIndicator  implements Serializable {

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Label#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "LabelBlockIndicator" );
    def.setPropertyValue ( "Title", "LabelTitle" );
    def.setPropertyValue ( "Border", "false" );

	  def.setPropertyValue( "ControlPropertiesMax", String.valueOf( MainForm.tableModel.getHeaders().size()) );

    return def;
  }

/*  public LabelBlockIndicator ( ControlProperties uiProperties ) {
    super(uiProperties);
    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();
	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, String.valueOf( MainForm.tableModel.getHeaders().size()) ) );

createInnerComponent();
  }*/

	@Override
	public void createInnerComponent () {
		super.createInnerComponent();
		setInnerComponent ( new JTextArea( uiProperties.getPropertyValue("Title")) );
//		setControlProperty(0);
		MainForm.tableModel.addTableModelListener( this );
	}


	public LabelBlockIndicator () {
    super(new ControlProperties());// yItem("nullItem", EControlPropertyItemType.SYS, ""));
//    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();
	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, String.valueOf( MainForm.tableModel.getHeaders().size()) ) );
//    setInnerComponent ( new JLabel( uiProperties.getPropertyValue("Title")) );
	setInnerComponent ( new JTextArea( uiProperties.getPropertyValue("Title")) );
//		setControlProperty(0);
	  MainForm.tableModel.addTableModelListener( this );
  }



	@Override
	public void tableChanged ( TableModelEvent tableModelEvent ) {
		String resSrt = "";
//		for ( int i = 0; i < this.controlProperties.size(); i++){
		((JTextArea)getInnerComponent()).setText( "" );
		for ( ControlPropertyItem itm : this.controlProperties.getProperties()) {
			//TODO: !!!

			resSrt = itm.getName() + " = " + ((ArrayList) MainForm.tableModel.values.get( MainForm.tableModel.values.size() - 1 )).get( MainForm.tableModel.getHeaders().indexOf( itm.getValue( 0 ) ) );
			((JTextArea)getInnerComponent()).append( "\n" + resSrt );
		}
//		((JTextArea)getInnerComponent()).setText( resSrt );
	}

	@Override
	public ControlPropertyItem setControlProperty ( int num ) {
//	  controlProperties.setPropertyValue ( "pktModelColumn", MainForm.tableModel.toString());
//	  controlProperties.setPropertyValue ( "pktModelColumn", MainForm.tableModel.toString());
			ControlPropertyItem res = null;
			if ( controlProperties.size() <= Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) ) {
				String valList = MainForm.tableModel.getHeaders().get( 0 ) + ";";
				for ( int i = 0; i < MainForm.tableModel.getHeaders().size(); i++ ){ // Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ); i++ ){
					valList += /*i*/ MainForm.tableModel.getHeaders().get( i ) + ";";
				}
				res = new ControlPropertyItem("IndicatorSource#" + this.controlProperties.size(), EControlPropertyItemType.LST, valList);
				controlProperties.setProperty( res );

			}
			return res;
		}

	@Override
	public int getControlPropertiesCountMax () {
		return Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ));
	}

	@Override
	public int getControlPropertiesCount () {
		return this.controlProperties.size();//super.getControlPropertiesCount();
	}
}
