package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import eu.hansolo.steelseries.gauges.Radial1Square;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockIndicator;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import xorDashboardDesktop.ui.MainForm;

import javax.swing.event.TableModelEvent;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class Radial1SquareBlockIndicator extends AbstractBlockIndicator implements Serializable {

	private transient Radial1Square gauge = null;
	private double gaugeLastVal = 0.0f;

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Radial1Square#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "Radial1SquareBlockIndicator" );
    def.setPropertyValue ( "Title", "Radial1SquareTitle" );
    def.setPropertyValue ( "Border", "true" );

	  def.setPropertyValue( "ControlPropertiesMax", "5" );

    return def;
  }

  /*public Radial1SquareBlockIndicator ( ControlProperties uiProperties ) {
    super(uiProperties);
    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();
	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "5" ));//String.valueOf( MainForm.tableModel.getHeaders().size()) ) );

	  createInnerComponent();
  }*/

	@Override
	public void createInnerComponent () {
		super.createInnerComponent();

		gauge = new Radial1Square();
		gauge.setTitle("OXY flow rate");
		gauge.setUnitString("mL / sec.");
		gauge.setMinValue(0);
		gauge.setMaxValue(1000);

		setInnerComponent ( gauge );//new JTextArea( uiProperties.getPropertyValue("Title")) );
		MainForm.tableModel.addTableModelListener( this );
	}


	public Radial1SquareBlockIndicator () {
    super(new ControlProperties());

    uiProperties = this.getDefaultProperties();
	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "5" ));//String.valueOf( MainForm.tableModel.getHeaders().size()) ) );

	gauge = new Radial1Square();
	gauge.setTitle("OXY flow rate");
	gauge.setUnitString("mL / sec.");
	gauge.setMinValue(0);
	gauge.setMaxValue(1000);

	ControlPropertyItem res = new ControlPropertyItem( "Title", EControlPropertyItemType.STR, "OXY flow rate" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Units string", EControlPropertyItemType.STR, "mL / sec." );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Min value", EControlPropertyItemType.FLT, "0" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Max value", EControlPropertyItemType.FLT, "9999" );
	controlProperties.setProperty( res );
	String valList = MainForm.tableModel.getHeaders().get( 0 ) + ";";
	for ( int i = 0; i < MainForm.tableModel.getHeaders().size(); i++ ){ // Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ); i++ ){
		valList += /*i*/ MainForm.tableModel.getHeaders().get( i ) + ";";
	}
	res = new ControlPropertyItem( "pktTableSourceColumn", EControlPropertyItemType.LST, valList );
	controlProperties.setProperty( res );
	//ControlProperties:
	// 0. Title
	// 1. Units string
	// 2. Min val
	// 3. Max val
	// 4. pktTableSourceColumn


	setInnerComponent ( gauge );//new JTextArea( uiProperties.getPropertyValue("Title")) );
	  MainForm.tableModel.addTableModelListener( this );
  }



	@Override
	public void tableChanged ( TableModelEvent tableModelEvent ) {
		if ( null != gauge ) {
			double tmp = Double.valueOf( MainForm.tableModel.values.get( MainForm.tableModel.values.size() - 1 ).get( MainForm.tableModel.getHeaders().indexOf ( controlProperties.getProperties().get( 4 ).getValue() ) ).toString() );
			System.out.println( "tableModelEvent = [" + tmp + "]" );
			if ( gaugeLastVal != tmp )
				gauge.setValue( tmp );
		}
	}

	@Override
	public ControlPropertyItem setControlProperty ( int num ) {
		ControlPropertyItem res = null;
		if ( num < Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) )
			res = controlProperties.getProperties().get( num );
		return res;
	}


	@Override
	public void applyControlProperties () {
		gauge.setTitle( controlProperties.getPropertyValue( "Title" ) );// "OXY flow rate");
		gauge.setUnitString( controlProperties.getPropertyValue( "Units string" ) );//"mL / sec.");
		gauge.setMinValue ( Double.parseDouble( controlProperties.getPropertyValue( "Min value" ) ) );// 0);
		gauge.setMaxValue ( Double.parseDouble( controlProperties.getPropertyValue( "Max value" ) ) );// 1000);
	}


}
