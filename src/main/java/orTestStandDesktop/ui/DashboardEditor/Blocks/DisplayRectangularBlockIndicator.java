package orTestStandDesktop.ui.DashboardEditor.Blocks;

import eu.hansolo.steelseries.gauges.DisplayRectangular;
import eu.hansolo.steelseries.gauges.Lcd;
import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockIndicator;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import orTestStandDesktop.ui.MainForm;

import javax.swing.event.TableModelEvent;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class DisplayRectangularBlockIndicator extends AbstractBlockIndicator implements Serializable {

	private transient DisplayRectangular gauge = null;
	private double gaugeLastVal = 0.0f;

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "DisplayRectangular#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "DisplayRectangularBlockIndicator" );
    def.setPropertyValue ( "Title", "DisplayRectangularTitle" );
    def.setPropertyValue ( "Border", "true" );

	  def.setPropertyValue( "ControlPropertiesMax", "5" );

    return def;
  }

  public DisplayRectangularBlockIndicator ( ControlProperties uiProperties ) {
    super(uiProperties);
    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();
	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "5" ));//String.valueOf( MainForm.tableModel.getHeaders().size()) ) );

	  gauge = new DisplayRectangular();
	  gauge.setTitle("OXY flow rate");
	  gauge.setUnitString("mL / sec.");
	  gauge.setMinValue(0);
	  gauge.setMaxValue(1000);

	  setInnerComponent ( gauge );//new JTextArea( uiProperties.getPropertyValue("Title")) );
	  MainForm.tableModel.addTableModelListener( this );
  }

	@Override
	public void createInnerComponent () {
		super.createInnerComponent();

		gauge = new DisplayRectangular();
		gauge.setTitle("OXY flow rate");
		gauge.setUnitString("mL / sec.");
		gauge.setMinValue(0);
		gauge.setMaxValue(1000);

		setInnerComponent ( gauge );//new JTextArea( uiProperties.getPropertyValue("Title")) );
		MainForm.tableModel.addTableModelListener( this );
	}


	public DisplayRectangularBlockIndicator () {
    super(new ControlProperties());

    uiProperties = this.getDefaultProperties();
	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "5" ));//String.valueOf( MainForm.tableModel.getHeaders().size()) ) );



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

		createInnerComponent();
  }



	@Override
	public void tableChanged ( TableModelEvent tableModelEvent ) {
		if ( null != gauge ) {
			double tmp = Double.valueOf( MainForm.tableModel.values.get( MainForm.tableModel.values.size() - 1 ).get( MainForm.tableModel.getHeaders().indexOf ( controlProperties.getProperties().get( 4 ).getValue() ) ).toString() );
			System.out.println( "tableModelEvent = [" + tmp + "]" );
			if ( gaugeLastVal != tmp ) {
				gauge.setValue( tmp );
				if ( gauge instanceof Lcd )
					((Lcd)gauge).setLcdValue( tmp );
			}
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
