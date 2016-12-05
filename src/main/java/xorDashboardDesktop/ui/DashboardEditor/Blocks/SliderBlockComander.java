package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockComander;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.EBlockType;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.ResizableComponent.ResizableComponentWrapper;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class SliderBlockComander extends AbstractBlockComander  implements Serializable, ChangeListener {

	private int propNumIdx = 0;
	private JSlider slider;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel sliderPanel;
	private JLabel lbValue;
	private JLabel lbTitle;

	@Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Slider#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "SliderBlockComander" );
    def.setPropertyValue ( "Title", "SliderTitle" );
    def.setPropertyValue ( "Border", "false" );
    def.setPropertyValue ( "Command", "srv $VAL" );

//	  def.setPropertyValue( "ControlPropertiesMax", "-1" );

    return def;
  }

  public SliderBlockComander () {
    super(new ControlProperties());
//    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();
//	  uiProperties.setPropertyValue( "ControlPropertiesMax", "0" ); //Property( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "-1" ) );

	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "3" ));//String.valueOf( MainForm.tableModel.getHeaders().size()) ) );

	  ControlPropertyItem res = new ControlPropertyItem( "Max Val", EControlPropertyItemType.FLT, "200" );
	  controlProperties.setProperty( res );
	  res = new ControlPropertyItem( "Min Val", EControlPropertyItemType.FLT, "0" );
	  controlProperties.setProperty( res );
	  res = new ControlPropertyItem( "Def Val", EControlPropertyItemType.FLT, "100" );
	  controlProperties.setProperty( res );


	  createInnerComponent ();
	  applyUiProperties();

  }

  @Override
  public ResizableComponentWrapper getContainerComponent() {
    return this;
  }

	@Override
	public void applyUiProperties () {
		super.applyUiProperties();
//		((JSlider)getInnerComponent()).setText(uiProperties.getPropertyValue("Title"));
		slider.setMinimum( Float.valueOf( controlProperties.getPropertyValue( "Min Val" ) ).intValue() );
		slider.setMaximum( Float.valueOf( controlProperties.getPropertyValue( "Max Val" ) ).intValue() );
		slider.setValue( Float.valueOf( controlProperties.getPropertyValue( "Def Val" ) ).intValue() );

		lbTitle.setText( uiProperties.getPropertyValue( "Title" ) );
		lbValue.setText( "Value : " + slider.getValue() );

		int majTickSpc = slider.getMaximum() / 10 * 2; // 10
		int minTickSpc = 2;//majTickSpc / 5;

		slider.setMinorTickSpacing( minTickSpc );
		slider.setMajorTickSpacing( majTickSpc );
		slider.setPaintTicks( true );
		slider.setPaintLabels( true );
	}

	@Override
	public ControlPropertyItem setControlProperty ( int num ) {
		ControlPropertyItem res = null;
		if ( num < Integer.parseInt( uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) )
			res = controlProperties.getProperties().get( num );

//		if ( -1 == Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) ) {
//			res = new ControlPropertyItem("Command #" + this.controlProperties.size(), EControlPropertyItemType.STR, "");
//			controlProperties.setProperty( res );
//		}
		return res;
		//return super.setControlProperty( num );
	}

	@Override
  public EBlockType getBlockType() {
    return EBlockType.CONTROL;
  }

	@Override
	public void applyControlProperties () {
		super.applyControlProperties();
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

		setInnerComponent ( sliderPanel );//new JSlider( uiProperties.getPropertyValue("Title")) );

		slider.addChangeListener( new ChangeListener() {
			public void stateChanged ( ChangeEvent e ) {
				JSlider source = (JSlider) e.getSource();
				int value = (int) source.getValue();
				lbValue.setText( "Value : " + value );
//				if ( ( controlProperties.getProperties().size() > 0 ) && ( controlProperties.getPropertyValue("Command") != "0" ) ) {
//					System.out.println( "ChangeEvent = [ " + controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) + "\n" + e );
//					// TODO: write this.controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) to Serial!
//				}
			}
		} );

		applyControlProperties ();
		applyUiProperties();
	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			int value = (int)source.getValue();
			if ( ( this.controlProperties.getProperties().size() > 0 ) && ( this.controlProperties.getPropertyValue("Command") != "0" ) ) {
				System.out.println( "ChangeEvent = [ " + this.controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) + "\n" + e );
				// TODO: write this.controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) to Serial!
			}
		}
	}

	@Override
	public void actionPerformed ( ActionEvent actionEvent ) {

	}
}
