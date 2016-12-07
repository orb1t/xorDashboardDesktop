package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockIndicator;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import xorDashboardDesktop.ui.MainForm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class SliderBlockCommander extends AbstractBlockIndicator implements Serializable {






//	private transient Slider slider = null;
	private double sliderLastVal = 0.0f;

	private JSlider slider;
	private JPanel sliderPanel;
	private JLabel lbValue;
	private JLabel lbTitle;
	private String unitString;

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();

    def.setPropertyValue ( "Name", "SliderCommander#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "SliderBlockCommander" );
    def.setPropertyValue ( "Title", "SliderCommander" );
    def.setPropertyValue ( "Border", "false" );

	  def.setPropertyValue( "ControlPropertiesMax", "5" );

    return def;
  }


	@Override
	public void createInnerComponent () {
		super.createInnerComponent();

		sliderPanel = new JPanel();
		sliderPanel.setLayout( new GridLayoutManager( 3, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		lbValue = new JLabel();
		sliderPanel.add( lbValue, new GridConstraints( 2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension( -1, 16 ), new Dimension( -1, 16 ), null, 0, false ) );
		lbTitle = new JLabel();
		sliderPanel.add( lbTitle, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension( -1, 16 ), new Dimension( -1, 16 ), null, 0, false ) );
		slider = new JSlider();
		sliderPanel.add( slider, new GridConstraints( 1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension( -1, 64 ), new Dimension( -1, 64 ), null, 1, false ) );

		slider.addChangeListener( new ChangeListener() {
			public void stateChanged ( ChangeEvent e ) {
				JSlider source = (JSlider) e.getSource();
				int value = (int) source.getValue();
				lbValue.setText( "Value : " + value + " " + unitString );
					System.out.println( "ChangeEvent = [ " + controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) + " ]\n" + e );
//					// TODO: write this.controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) to Serial!
			}
		} );
		MainForm.tableModel.addTableModelListener( this );
	}


	public SliderBlockCommander () {
    super(new ControlProperties());

    uiProperties = this.getDefaultProperties();
	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "5" ));


		ControlPropertyItem res = new ControlPropertyItem( "MinVal", EControlPropertyItemType.FLT, "0" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "MaxVal", EControlPropertyItemType.FLT, "1000" );
	controlProperties.setProperty( res );
		res = new ControlPropertyItem( "DefVal", EControlPropertyItemType.FLT, "500" );
		controlProperties.setProperty( res );

		res = new ControlPropertyItem( "Units", EControlPropertyItemType.STR, "mL / sec." );
		controlProperties.setProperty( res );

		res = new ControlPropertyItem( "Command", EControlPropertyItemType.STR, "cmd $VAL" );
		controlProperties.setProperty( res );

	String valList = MainForm.tableModel.getHeaders().get( 0 ) + ";";
	for ( int i = 0; i < MainForm.tableModel.getHeaders().size(); i++ ){
		valList += MainForm.tableModel.getHeaders().get( i ) + ";";
	}
	res = new ControlPropertyItem( "pktTableSourceColumn", EControlPropertyItemType.LST, valList );
	controlProperties.setProperty( res );



		this.createInnerComponent();
		slider.setValue( Float.valueOf( controlProperties.getPropertyValue( "DefVal" ) ).intValue() );
		applyControlProperties ();
//		applyUiProperties();

		setInnerComponent ( sliderPanel );
  }


	@Override
	public void tableChanged ( TableModelEvent tableModelEvent ) {
		if ( null != slider ) {
			double tmp = Double.valueOf( MainForm.tableModel.values.get( MainForm.tableModel.values.size() - 1 ).get( MainForm.tableModel.getHeaders().indexOf( controlProperties.getProperties().get( 5 ).getValue() ) ).toString() );
			if ( ( tmp >=  sliderLastVal + 5 ) || ( tmp < sliderLastVal - 5 )  ){
				sliderLastVal = slider.getValue();
				slider.setValue( (int) tmp );
				System.out.println( "tableModelEvent = [" + tmp + "]" );
				applyControlProperties ();
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
		unitString = controlProperties.getPropertyValue( "Units" );

		slider.setMinimum( Float.valueOf( controlProperties.getPropertyValue( "MinVal" ) ).intValue() );
		slider.setMaximum( Float.valueOf( controlProperties.getPropertyValue( "MaxVal" ) ).intValue() );

		lbTitle.setText( uiProperties.getPropertyValue( "Title" ) );
		lbValue.setText( "Value : " + slider.getValue() + " "  + unitString );

		int majTickSpc = slider.getMaximum() / 10 * 2; // 10
		int minTickSpc = 2;//majTickSpc / 5;

		slider.setMinorTickSpacing( minTickSpc );
		slider.setMajorTickSpacing( majTickSpc );
		slider.setPaintTicks( true );
		slider.setPaintLabels( true );
	}


}
