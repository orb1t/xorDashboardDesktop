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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.util.*;
import java.util.Timer;

import static xorDashboardDesktop.ui.MainForm.serial;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class RepeatingBlockCommander extends AbstractBlockIndicator implements Serializable {

	private JPanel repeaterPanel;
	private JSpinner spnrRepeaterFreq;
	private JComboBox cbRepeaterMode;
	private JCheckBox chkRepeaterEnabled;
	private JLabel lbCommand;
	private JPanel commandPanel;
	private RepeatingTimerTask repeatingTask;
	private Timer repeatingTimer;

	private int cmdNumIdx = 0;
	private int cmdNumOffset = 0;
	private long repeatingTaskPeriod = 1000;
	private String unitString;
	private int propNumIdx;

	class RepeatingTimerTask extends TimerTask {
		@Override
		public void run () {
//			System.out.println("working at fixed rate delay");
			if ( controlProperties.getProperties().size() > 0 ) {
				if ( ( cmdNumIdx + cmdNumOffset ) > controlProperties.getProperties().size()-1 )
					cmdNumIdx = 0;
				System.out.println( "actionEvent = [" + cmdNumIdx + "] : " + controlProperties.getProperties().get( cmdNumIdx + cmdNumOffset ).getValue() + "\n" );
				// TODO: write this.controlProperties.getProperties().get( cmdNumIdx ).getValue() to Serial!
				if ( null != serial && serial.connected )
					serial.serialWrite ( controlProperties.getProperties().get( cmdNumIdx + cmdNumOffset ).getValue() + "\r\n");
				cmdNumIdx++;
			}
		}
	}

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();

    def.setPropertyValue ( "Name", "RepeatingCommander#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "RepeatingBlockCommander" );
    def.setPropertyValue ( "Title", "RepeatingCommander" );
//    def.setPropertyValue ( "Border", "false" );

	  def.setPropertyValue( "ControlPropertiesMax", "-1" );

    return def;
  }


	@Override
	public void createInnerComponent () {
		super.createInnerComponent();

		repeaterPanel = new JPanel();
		repeaterPanel.setLayout( new GridLayoutManager( 3, 3, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
//		JRootPane contentPane = getRootPane();
		add( repeaterPanel);//, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false ) );

		repeaterPanel.setBorder( BorderFactory.createTitledBorder( "repeaterPanel" ) );
		spnrRepeaterFreq = new JSpinner();
		repeaterPanel.add( spnrRepeaterFreq, new GridConstraints( 0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension( 128, -1 ), new Dimension( 128, -1 ), null, 0, false ) );
		cbRepeaterMode = new JComboBox();
		final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
		defaultComboBoxModel1.addElement( "Hz" );
		defaultComboBoxModel1.addElement( "kHz" );
		defaultComboBoxModel1.addElement( "times per Second" );
		defaultComboBoxModel1.addElement( "times per Minute" );
		defaultComboBoxModel1.addElement( "times per Hour" );
		cbRepeaterMode.setModel( defaultComboBoxModel1 );
		cbRepeaterMode.setSelectedIndex( 0 );
		repeaterPanel.add( cbRepeaterMode, new GridConstraints( 0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false ) );
		chkRepeaterEnabled = new JCheckBox();
		chkRepeaterEnabled.setText( "Enabled" );
		repeaterPanel.add( chkRepeaterEnabled, new GridConstraints( 1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		commandPanel = new JPanel();
		commandPanel.setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		repeaterPanel.add( commandPanel, new GridConstraints( 2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false ) );
		lbCommand = new JLabel();
		lbCommand.setText( "Command :" );
		commandPanel.add( lbCommand, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );


		repeatingTask = new RepeatingTimerTask();
		repeatingTimer = new java.util.Timer();

		spnrRepeaterFreq.addChangeListener( new ChangeListener() {
			@Override
			public void stateChanged ( ChangeEvent changeEvent ) {
				JSpinner source = (JSpinner) changeEvent.getSource();
				float value = (float) source.getValue();
				lbCommand.setText( "Command : " + value + " " + cbRepeaterMode.getSelectedItem().toString() );
				if ( chkRepeaterEnabled.isSelected() ) {
					stopRepeaterTask();
					startRepeaterTask();
				}
//				if ( ( controlProperties.getProperties().size() > 0 ) && ( controlProperties.getPropertyValue("Command") != "0" ) ) {
//					System.out.println( "ChangeEvent = [ " + controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) + "\n" + e );
//					// TODO: write this.controlProperties.getPropertyValue("Command").replace( "$VAL", String.valueOf( value ) ) to Serial!
//				}
				if ( controlProperties.getProperties().size() > 0 ) {
					if ( propNumIdx > controlProperties.getProperties().size()-1 )
						propNumIdx = 0;
					System.out.println( "actionEvent = [" + propNumIdx + "] : " + controlProperties.getProperties().get( propNumIdx ).getValue() + "\n" );// + actionEvent );
					// TODO: write this.controlProperties.getProperties().get( propNumIdx ).getValue() to Serial!
					if ( null != serial && serial.connected )
						serial.serialWrite ( controlProperties.getProperties().get( propNumIdx ).getValue() + "\r\n");
					propNumIdx++;
				}
			}
		} );

		chkRepeaterEnabled.addItemListener( new ItemListener() {
			@Override
			public void itemStateChanged ( ItemEvent itemEvent ) {
				System.out.println( "itemEvent = [" + ( (JCheckBox) itemEvent.getItemSelectable() ).isSelected() + "]" );
				if ( ( (JCheckBox) itemEvent.getItemSelectable() ).isSelected() ) {
					startRepeaterTask();
				} else {
					stopRepeaterTask();
				}
			}
		} );



		MainForm.tableModel.addTableModelListener( this );
	}


	private void startRepeaterTask() {
		if ( null != repeatingTimer ) {
			repeatingTimer = new java.util.Timer();
			repeatingTask = new RepeatingTimerTask();
			updateTaskPeriod();
			// scheduling the task at fixed rate delay
			repeatingTimer.scheduleAtFixedRate ( repeatingTask, 0, repeatingTaskPeriod);
		}
	}

	private void stopRepeaterTask(){
		if ( null != repeatingTimer) {
			repeatingTimer.cancel();
			repeatingTimer.purge();
		}
	}

	private void updateTaskPeriod(){
		repeatingTaskPeriod = (long) (( 1.0 / Float.parseFloat( spnrRepeaterFreq.getValue().toString() ) ) * 1000);
	}


	public RepeatingBlockCommander () {
    super(new ControlProperties());

    uiProperties = this.getDefaultProperties();
//	  uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "5" ));
		uiProperties.setPropertyValue( "ControlPropertiesMax", "-1" );

		ControlPropertyItem res = new ControlPropertyItem( "MinVal", EControlPropertyItemType.FLT, "0" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "MaxVal", EControlPropertyItemType.FLT, "60" );
	controlProperties.setProperty( res );
		res = new ControlPropertyItem( "DefVal", EControlPropertyItemType.FLT, "1.5" );
		controlProperties.setProperty( res );
		res = new ControlPropertyItem( "StepVal", EControlPropertyItemType.FLT, "0.5" );
		controlProperties.setProperty( res );

		res = new ControlPropertyItem( "Units", EControlPropertyItemType.LST, "Hz;Hz;kHz;");//times per Second;times per Minute;times per Hour" );
		controlProperties.setProperty( res );

//		res = new ControlPropertyItem( "Command", EControlPropertyItemType.STR, "cmd $VAL" );
//		controlProperties.setProperty( res );

	String valList = MainForm.tableModel.getHeaders().get( 0 ) + ";";
	for ( int i = 0; i < MainForm.tableModel.getHeaders().size(); i++ ){
		valList += MainForm.tableModel.getHeaders().get( i ) + ";";
	}
	res = new ControlPropertyItem( "pktTableSourceColumn", EControlPropertyItemType.LST, valList );
	controlProperties.setProperty( res );

		cmdNumOffset = 6;

		this.createInnerComponent();
//		slider.setValue( Float.valueOf( controlProperties.getPropertyValue( "DefVal" ) ).intValue() );
		applyControlProperties ();
//		applyUiProperties();

		setInnerComponent ( repeaterPanel );
  }


	@Override
	public void tableChanged ( TableModelEvent tableModelEvent ) {
//		if ( null != slider ) {
//			double tmp = Double.valueOf( MainForm.tableModel.values.get( MainForm.tableModel.values.size() - 1 ).get( MainForm.tableModel.getHeaders().indexOf( controlProperties.getProperties().get( 5 ).getValue() ) ).toString() );
//			if ( ( tmp >=  sliderLastVal + 5 ) || ( tmp < sliderLastVal - 5 )  ){
//				sliderLastVal = slider.getValue();
//				slider.setValue( (int) tmp );
//				System.out.println( "tableModelEvent = [" + tmp + "]" );
//				applyControlProperties ();
//			}
//		}
	}

	@Override
	public ControlPropertyItem setControlProperty ( int num ) {
		ControlPropertyItem res = null;
		if ( -1 == Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) ) {
			res = new ControlPropertyItem("Command #" + this.controlProperties.size(), EControlPropertyItemType.STR, "");
			controlProperties.setProperty( res );
		}
		return res;
		//return super.setControlProperty( num );
	}


	@Override
	public void applyControlProperties () {
		 unitString = controlProperties.getPropertyValue( "Units" );
//
//		slider.setMinimum( Float.valueOf( controlProperties.getPropertyValue( "MinVal" ) ).intValue() );
//		slider.setMaximum( Float.valueOf( controlProperties.getPropertyValue( "MaxVal" ) ).intValue() );

		SpinnerNumberModel mdl = new SpinnerNumberModel( Float.valueOf( controlProperties.getPropertyValue( "DefVal" ) ),
				                                               Float.valueOf( controlProperties.getPropertyValue( "MinVal" ) ),
				                                               Float.valueOf( controlProperties.getPropertyValue( "MaxVal" ) ),
				                                               Float.valueOf( controlProperties.getPropertyValue( "StepVal" ) ) );
		spnrRepeaterFreq.setModel( mdl );

		lbCommand.setText( "Command : " + spnrRepeaterFreq.getValue().toString() + " " + cbRepeaterMode.getSelectedItem().toString() );

//		lbTitle.setText( uiProperties.getPropertyValue( "Title" ) );
//		lbValue.setText( "Value : " + slider.getValue() + " "  + unitString );
//
//		int majTickSpc = slider.getMaximum() / 10 * 2; // 10
//		int minTickSpc = 2;//majTickSpc / 5;
//
//		slider.setMinorTickSpacing( minTickSpc );
//		slider.setMajorTickSpacing( majTickSpc );
//		slider.setPaintTicks( true );
//		slider.setPaintLabels( true );
	}


//	@Override
//	public void actionPerformed ( ActionEvent actionEvent ) {
//
//	}
}
