package xorDashboardDesktop.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import xorDashboardDesktop.helpers.serial.SerialReader;
import xorDashboardDesktop.models.TestStandStrPktDefs;
import xorDashboardDesktop.models.pktTableModel;
import xorDashboardDesktop.ui.DashboardEditor.Panel.DashboardPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by orb1t_ua on 01.10.16.
 */
public class MainForm extends JFrame {
	private JPanel panelContentMain;
	private JPanel panelBottomSerial;
	private JPanel panelTopConnection;
	private JTextField txConnectionPath;
	private JComboBox cbSerialSpeed;
	private JButton connectButton;
	private JButton disconnectButton;
	private JPanel panelBottomMain;
	private JSplitPane splitConnection_Main;
	private JTextArea txSerialLog;
	private JTextField txSerialInput;
	private JButton sendButton;
	private JScrollPane scrolSerialLog;
	private JSplitPane splitDashboard_Serial;
	private JPanel dashboardEditorContainer;
	private JButton newPktTableRowButton;
	private JButton a100pktTblRowsButton;

	public static final pktTableModel tableModel = new pktTableModel( new TestStandStrPktDefs() );
	public static SerialReader serial;

	public static void toggle ( JSplitPane sp, boolean collapse ) {
		try {
			BasicSplitPaneDivider bspd = ( (BasicSplitPaneUI) sp.getUI() ).getDivider();
			Field buttonField = BasicSplitPaneDivider.class.
					                                               getDeclaredField( collapse ? "rightButton" : "leftButton" );
			buttonField.setAccessible( true );
			JButton button = (JButton) buttonField.get( ( (BasicSplitPaneUI) sp.getUI() ).getDivider() );
			button.getActionListeners()[ 0 ].actionPerformed( new ActionEvent( bspd, MouseEvent.MOUSE_CLICKED,
					                                                                 "bum" ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public MainForm () {
		super( "orTestStand Desktop Dashboard v 0.0.0" );
//		me = this;
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );


		$$$setupUI$$$();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		dashboardEditorContainer.setPreferredSize( new Dimension( screen.width / 2, screen.height / 2 ) );

		setContentPane( this.panelContentMain );
		this.setPreferredSize( this.getPreferredSize() ); //new Dimension(600, 600));


		switchSerialPanel( false );


		connectButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				disconnectButton.setEnabled( true );
				connectButton.setEnabled( false );

				switchSerialPanel( true );

//				txSerialLog.append( "test\n" );


				serial = new SerialReader( txConnectionPath.getText(), Integer.valueOf( cbSerialSpeed.getSelectedItem().toString() ), txSerialLog );

				if ( splitConnection_Main.getLeftComponent().isVisible() ) {
					splitConnection_Main.setDividerLocation( 0.0 );
				}

			}
		} );

		disconnectButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				disconnectButton.setEnabled( false );
				connectButton.setEnabled( true );

				switchSerialPanel( false );
//        if (splitConnection_Main.getLeftComponent().isVisible()) {
//          splitConnection_Main.setDividerLocation(0.1);
//        }
			}
		} );


		this.splitDashboard_Serial.setDividerLocation( 1.0d );
		newPktTableRowButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
//				Object[] params = new Object[tableModel.getRowCount()];
//				ArrayList<Object> params = new ArrayList();

//				tableModel.addRow( params );

				addPktTblRow();


			}
		} );
		a100pktTblRowsButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				for ( int i = 0; i < 100; i++ ) {
					addPktTblRow();
				}
			}
		} );


		pack();
		this.setLocationByPlatform( false );
//		this.setL
		this.setLocationRelativeTo( null );


		setVisible( true );

		sendButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				if ( serial != null )
					serial.serialWrite( txSerialInput.getText() + "\r\n" );
				txSerialInput.setText( "" );
			}
		} );
	}

	private void addPktTblRow () {
		tableModel.prepareNewRow();
		tableModel.newRowTmp.add( tableModel.getRowCount() );
		Random rnd = new Random();
		for ( int i = 0; i < tableModel.getColumnCount() - 1; i++ ) {
			tableModel.newRowTmp.add( rnd.nextFloat() );
		}

		txSerialLog.append( "[" + tableModel.newRowTmp.get( 0 ) + ":" + tableModel.newRowTmp.get( 1 ) + "] rate: " + tableModel.newRowTmp.get( 2 ) + " l/m, flow: " + tableModel.newRowTmp.get( 3 ) + " ml/s, ttl: " + tableModel.newRowTmp.get( 4 ) + " ml | "
				                    + "pot: " + tableModel.newRowTmp.get( 5 ) + ", srv: " + tableModel.newRowTmp.get( 6 ) + " | "
				                    + "rl0: " + tableModel.newRowTmp.get( 7 ) + ", rl1: " + tableModel.newRowTmp.get( 8 ) + "\n" );

		tableModel.addRow( tableModel.newRowTmp );
	}

	private void switchSerialPanel ( boolean on ) {
//    panelBottomSerial.setVisible(on);
		toggle( this.splitDashboard_Serial, !on );
		if ( on )
			this.splitDashboard_Serial.setDividerLocation( this.splitDashboard_Serial.getLastDividerLocation() );
		else
			this.splitDashboard_Serial.setDividerLocation( 1.0d );
		txSerialInput.setEnabled( on );
		txSerialInput.setEditable( on );
		sendButton.setEnabled( on );
	}

	private void createUIComponents () {
		this.panelContentMain = new JPanel();
		setContentPane( panelContentMain );

		DashboardPanel dashboardPanel = new DashboardPanel( this );

		dashboardEditorContainer = new JPanel();
		dashboardEditorContainer.setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		dashboardEditorContainer.setPreferredSize( new Dimension( -1, 400 ) );

//		dashboardPanel.setVisible( true );
		dashboardEditorContainer.add( dashboardPanel, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
	}


	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$ () {
		createUIComponents();
		panelContentMain.setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		panelContentMain.setMinimumSize( new Dimension( 800, 600 ) );
		panelContentMain.setPreferredSize( new Dimension( 800, 600 ) );
		splitConnection_Main = new JSplitPane();
		splitConnection_Main.setOneTouchExpandable( true );
		splitConnection_Main.setOrientation( 0 );
		panelContentMain.add( splitConnection_Main, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension( 200, 200 ), null, 0, false ) );
		panelTopConnection = new JPanel();
		panelTopConnection.setLayout( new GridLayoutManager( 1, 7, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		panelTopConnection.setMinimumSize( new Dimension( -1, 35 ) );
		panelTopConnection.setPreferredSize( new Dimension( -1, 35 ) );
		splitConnection_Main.setLeftComponent( panelTopConnection );
		txConnectionPath = new JTextField();
		txConnectionPath.setText( "/dev/ttyUSB0" );
		panelTopConnection.add( txConnectionPath, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension( 150, -1 ), null, 0, false ) );
		final Spacer spacer1 = new Spacer();
		panelTopConnection.add( spacer1, new GridConstraints( 0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false ) );
		cbSerialSpeed = new JComboBox();
		final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
		defaultComboBoxModel1.addElement( "115200" );
		defaultComboBoxModel1.addElement( "57600" );
		cbSerialSpeed.setModel( defaultComboBoxModel1 );
		cbSerialSpeed.setName( "cbSerialSpeed" );
		panelTopConnection.add( cbSerialSpeed, new GridConstraints( 0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		connectButton = new JButton();
		connectButton.setFocusCycleRoot( false );
		connectButton.setText( "Connect" );
		panelTopConnection.add( connectButton, new GridConstraints( 0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		disconnectButton = new JButton();
		disconnectButton.setEnabled( false );
		disconnectButton.setText( "Disconnect" );
		panelTopConnection.add( disconnectButton, new GridConstraints( 0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		newPktTableRowButton = new JButton();
		newPktTableRowButton.setText( "newPktTableRow" );
		panelTopConnection.add( newPktTableRowButton, new GridConstraints( 0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		a100pktTblRowsButton = new JButton();
		a100pktTblRowsButton.setText( "+100pktTblRows" );
		panelTopConnection.add( a100pktTblRowsButton, new GridConstraints( 0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		panelBottomMain = new JPanel();
		panelBottomMain.setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		splitConnection_Main.setRightComponent( panelBottomMain );
		splitDashboard_Serial = new JSplitPane();
		splitDashboard_Serial.setDividerLocation( 393 );
		splitDashboard_Serial.setLastDividerLocation( -1 );
		splitDashboard_Serial.setOneTouchExpandable( true );
		splitDashboard_Serial.setOrientation( 0 );
		splitDashboard_Serial.setResizeWeight( 1.0 );
		panelBottomMain.add( splitDashboard_Serial, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension( -1, 400 ), null, 0, false ) );
		dashboardEditorContainer.setPreferredSize( new Dimension( -1, 400 ) );
		splitDashboard_Serial.setLeftComponent( dashboardEditorContainer );
		panelBottomSerial = new JPanel();
		panelBottomSerial.setLayout( new GridLayoutManager( 2, 2, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		panelBottomSerial.setMinimumSize( new Dimension( -1, -1 ) );
		panelBottomSerial.setPreferredSize( new Dimension( -1, -1 ) );
		panelBottomSerial.setVisible( true );
		splitDashboard_Serial.setRightComponent( panelBottomSerial );
		txSerialInput = new JTextField();
		txSerialInput.setDragEnabled( false );
		txSerialInput.setEnabled( false );
		panelBottomSerial.add( txSerialInput, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension( 150, -1 ), null, 1, false ) );
		sendButton = new JButton();
		sendButton.setEnabled( false );
		sendButton.setText( "Send" );
		panelBottomSerial.add( sendButton, new GridConstraints( 0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false ) );
		scrolSerialLog = new JScrollPane();
		scrolSerialLog.setAutoscrolls( false );
		scrolSerialLog.setDoubleBuffered( true );
		scrolSerialLog.setHorizontalScrollBarPolicy( 30 );
		scrolSerialLog.setVerticalScrollBarPolicy( 22 );
		panelBottomSerial.add( scrolSerialLog, new GridConstraints( 1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension( -1, 32 ), new Dimension( -1, 32 ), null, 0, false ) );
		txSerialLog = new JTextArea();
		scrolSerialLog.setViewportView( txSerialLog );
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$ () {
		return panelContentMain;
	}
}
