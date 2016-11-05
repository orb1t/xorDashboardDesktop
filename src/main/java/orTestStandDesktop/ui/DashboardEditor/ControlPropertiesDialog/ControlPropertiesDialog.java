package orTestStandDesktop.ui.DashboardEditor.ControlPropertiesDialog;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.IDashboardBlockComander;
import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.IDashboardBlockIndicator;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.PropPageTableModel;
import orTestStandDesktop.ui.DashboardEditor.JTableX.JTableX;
import orTestStandDesktop.ui.DashboardEditor.JTableX.RowEditorModel;
import orTestStandDesktop.ui.DashboardEditor.JTableX.RowRendererModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ControlPropertiesDialog extends JDialog {

	private final AbstractBlock block;
	private ControlProperties uiProps;

	private final DashboardBlockPropertiesTableModel model = null;

	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JPanel mainPanel;
	private JPanel bottomPanel;
	private JPanel panelPaneMain;
	private JTable propertiesTable;
	private JTable controlPropertiesTable;
	private JButton addPropertyButton;
	private JScrollPane uiPropertiesScrollPane;
	private JScrollPane controlPropertiesScrollPane;
	private JPanel uiPropertiesPanel;
	private JPanel controlPropertiesPanel;
	private JLabel lblControlProperties;

	private IAddBlockDialogResults listener = null;

	private final static String[] COLUMN_NAMES = { "Property", "Type", "Value" };
	private static String selectedItem;

	public ControlPropertiesDialog ( AbstractBlock propsBlock, IAddBlockDialogResults dialogResultsListener ) {
		super();
		this.block = propsBlock;
		this.uiProps = propsBlock.getUiProperties();// getDefaultProperties();//uiProps;
		this.listener = dialogResultsListener;


		RowEditorModel rm = new RowEditorModel();
		RowRendererModel rrm = new RowRendererModel();
		propertiesTable = new JTableX( this.uiProps, rm, rrm );
		propertiesTable.setAutoCreateColumnsFromModel( false );
		propertiesTable.setAutoCreateRowSorter( false );// setSortable(false);
//		propertiesTable.c setColumnControlVisible(true);
//		propertiesTable.sct setHorizontalScrollEnabled(true);
		propertiesTable.setColumnSelectionAllowed( true );
		propertiesTable.setRowSelectionAllowed( true );
//    propertiesTable.setDefaultEditor(Double.class, new DefaultCellEditor(new JTextField())); //MySpinnerEditor());

		propertiesTable.getColumnModel().getColumn( PropPageTableModel.COL_NAME ).setPreferredWidth( 350 );
		propertiesTable.getColumnModel().getColumn( PropPageTableModel.COL_VAL ).setPreferredWidth( 600 );

		Dimension d = propertiesTable.getPreferredSize();
		d.height = 350;
		propertiesTable.setPreferredScrollableViewportSize( d );
		propertiesTable.setPreferredScrollableViewportSize( new Dimension( 400, 300 ) );
		pack();

		if ( propsBlock instanceof IDashboardBlockIndicator || propsBlock instanceof IDashboardBlockComander ) {
			rm = new RowEditorModel();
			rrm = new RowRendererModel();
			controlPropertiesTable = new JTableX( ( (AbstractBlock) propsBlock ).getControlProperties(), rm, rrm );
			controlPropertiesTable.setAutoCreateColumnsFromModel( false );
			controlPropertiesTable.setAutoCreateRowSorter( false );// setSortable(false);
//		propertiesTable.c setColumnControlVisible(true);
//		propertiesTable.sct setHorizontalScrollEnabled(true);
			controlPropertiesTable.setColumnSelectionAllowed( true );
			controlPropertiesTable.setRowSelectionAllowed( true );
//    propertiesTable.setDefaultEditor(Double.class, new DefaultCellEditor(new JTextField())); //MySpinnerEditor());

//			controlPropertiesTable.getColumnModel().getColumn( PropPageTableModel.COL_NAME ).setPreferredWidth( 350 );
//			controlPropertiesTable.getColumnModel().getColumn( PropPageTableModel.COL_VAL ).setPreferredWidth( 600 );
//			propertiesTable.ini

			d = controlPropertiesTable.getPreferredSize();
			d.height = 350;
			controlPropertiesTable.setPreferredScrollableViewportSize( d );
			controlPropertiesTable.setPreferredScrollableViewportSize( new Dimension( 400, 300 ) );
			controlPropertiesTable.setVisible( true );

			pack();
		}

		$$$setupUI$$$();
		setContentPane( contentPane );
		setModal( true );
		getRootPane().setDefaultButton( buttonOK );

		this.setTitle( "Add new Control Dialog" );

		if ( Integer.valueOf( uiProps.getPropertyValue( "ControlPropertiesMax" ) ) == block.getControlProperties().size() )
			addPropertyButton.setVisible( false );


		if ( block instanceof IDashboardBlockIndicator || block instanceof IDashboardBlockComander ) {
			setControlPropertiesTableTitle();
		}


		buttonOK.addActionListener( new ActionListener() {
			public void actionPerformed ( ActionEvent e ) {
				onOK();
			}
		} );

		buttonCancel.addActionListener( new ActionListener() {
			public void actionPerformed ( ActionEvent e ) {
				onCancel();
			}
		} );

// call onCancel() when cross is clicked
		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		addWindowListener( new WindowAdapter() {
			public void windowClosing ( WindowEvent e ) {
				onCancel();
			}
		} );

// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction( new ActionListener() {
			public void actionPerformed ( ActionEvent e ) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );

		addPropertyButton.addActionListener( new ActionListener() {
			int cnt = 0;

			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {

				//TODO:!!!
//				actionEvent.get
				if ( block instanceof AbstractBlock ) {// IDashboardBlockIndicator ) {
					int controlPropNum = setControlPropertiesTableTitle();
//					if ( controlPropNum == -1 || controlPropNum < Integer.valueOf( uiProps.getPropertyValue( "ControlPropertiesMax" ) ) ) {
					if ( controlPropNum > 0 || controlPropNum == -1 ) {// < Integer.valueOf( uiProps.getPropertyValue( "ControlPropertiesMax" ) ) ) {
						addPropertyButton.setEnabled( true );
						String valList = "";
						ControlPropertyItem res = ( (AbstractBlock) block ).setControlProperty( ( (AbstractBlock) block ).getControlPropertiesCount() );
//					controlProperties.setProperty( res );
//					ControlPropertyItem src = ( (IDashboardBlockIndicator) block ).setControlProperty();
						( (JTableX) controlPropertiesTable ).addRow( res );
					} else {
						addPropertyButton.setEnabled( false );
					}
				}
			}
		} );
		setControlPropertiesTableTitle();

		setSize( getPreferredSize() );
		pack();

//		this.setLocationByPlatform( false );
//		this.setL
//		this.setLocationRelativeTo( null );


		setVisible( true );
	}

	private int setControlPropertiesTableTitle () {
		int controlPropNum = 0;
		controlPropertiesPanel.setVisible( false );
		if ( Integer.valueOf( uiProps.getPropertyValue( "ControlPropertiesMax" ) ) < 0 ) {
			controlPropNum = -1;
			controlPropertiesPanel.setVisible( true );
		} else if ( Integer.valueOf( uiProps.getPropertyValue( "ControlPropertiesMax" ) ) > 0 ) {
			controlPropNum = ( Integer.valueOf( uiProps.getPropertyValue( "ControlPropertiesMax" ) ) - ( (AbstractBlock) block ).getControlPropertiesCount() );
			; //uiProps.getPropertyValue( "ControlPropertiesMax" );
			controlPropertiesPanel.setVisible( true );
		}
		lblControlProperties.setText( "Control Properties : ( " + ( controlPropNum < 0 ? "NA" : controlPropNum ) + " ) max. left" );
		return controlPropNum;
	}

	private void onOK () {
//TODO: !!!! Collect resulting values and pass to listeners
		for ( int i = 0; i < propertiesTable.getRowCount(); i++ ) {
			Object[] res = new Object[ propertiesTable.getColumnCount() ];
			for ( int j = 0; j < propertiesTable.getColumnCount(); j++ ) {
				res[ j ] = propertiesTable.getValueAt( i, j ).toString().trim();
			}
			uiProps.setPropertyValue( res[ PropPageTableModel.COL_NAME ].toString(), res[ PropPageTableModel.COL_VAL ].toString() );
		}
		block.setUiProperties( this.uiProps );


		if ( Integer.valueOf( uiProps.getPropertyValue( "ControlPropertiesMax" ) ) != 0 )
			if ( block instanceof AbstractBlock ) {// IDashboardBlockIndicator ) {
				for ( int i = 0; i < controlPropertiesTable.getRowCount(); i++ ) {
					Object[] res = new Object[ controlPropertiesTable.getColumnCount() ];
					for ( int j = 0; j < controlPropertiesTable.getColumnCount(); j++ ) {
						res[ j ] = controlPropertiesTable.getValueAt( i, j );
					}

					if ( ( (AbstractBlock) block ).getControlProperties().getProperties().contains( ( (AbstractBlock) block ).getControlProperties().getPropertyItem( res[ PropPageTableModel.COL_NAME ].toString() ) ) )
						( (AbstractBlock) block ).getControlProperties().setPropertyValue( res[ PropPageTableModel.COL_NAME ].toString(), res[ PropPageTableModel.COL_VAL ].toString() );
					else
						( (AbstractBlock) block ).getControlProperties().getProperties().add( ( new ControlPropertyItem( res[ PropPageTableModel.COL_NAME ].toString(), EControlPropertyItemType.valueOf( res[ PropPageTableModel.COL_TYPE ].toString() ), res[ PropPageTableModel.COL_VAL ].toString() ) ) );
				}


//			block.setUiProperties( this.uiProps );
				controlPropertiesPanel.setVisible( true );
//				setControlPropertiesTableTitle();
			}

		this.listener.GetResultsConfig( block, uiProps, true );
		dispose();
	}


	private void onCancel () {
		this.listener.GetResultsConfig( block, uiProps, false );
		dispose();
	}


	private void createUIComponents () {
		mainPanel = new JPanel( null );

		lblControlProperties = new JLabel();
//		lblControlProperties.setText( "Control Properties : ( " + uiProps.getPropertyValue( "ControlPropertiesMax" ) + " ) max. left" );
//		setControlPropertiesTableTitle();
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
		contentPane = new JPanel();
		contentPane.setLayout( new GridLayoutManager( 1, 1, new Insets( 10, 10, 10, 10 ), -1, -1 ) );
		contentPane.setMinimumSize( new Dimension( 384, 384 ) );
		contentPane.setPreferredSize( new Dimension( 384, 384 ) );
		contentPane.setVisible( true );
		panelPaneMain = new JPanel();
		panelPaneMain.setLayout( new GridLayoutManager( 2, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		contentPane.add( panelPaneMain, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridLayoutManager( 1, 3, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		panelPaneMain.add( bottomPanel, new GridConstraints( 1, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		final Spacer spacer1 = new Spacer();
		bottomPanel.add( spacer1, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false ) );
		buttonOK = new JButton();
		buttonOK.setText( "OK" );
		bottomPanel.add( buttonOK, new GridConstraints( 0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		buttonCancel = new JButton();
		buttonCancel.setText( "Cancel" );
		bottomPanel.add( buttonCancel, new GridConstraints( 0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		mainPanel.setLayout( new GridLayoutManager( 4, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		panelPaneMain.add( mainPanel, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		uiPropertiesPanel = new JPanel();
		uiPropertiesPanel.setLayout( new GridLayoutManager( 3, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		mainPanel.add( uiPropertiesPanel, new GridConstraints( 0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		uiPropertiesScrollPane = new JScrollPane();
		uiPropertiesPanel.add( uiPropertiesScrollPane, new GridConstraints( 1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		propertiesTable.setAutoCreateRowSorter( true );
		propertiesTable.setCellSelectionEnabled( true );
		propertiesTable.setDoubleBuffered( true );
		propertiesTable.setShowVerticalLines( true );
		uiPropertiesScrollPane.setViewportView( propertiesTable );
		final JLabel label1 = new JLabel();
		label1.setHorizontalAlignment( 0 );
		label1.setHorizontalTextPosition( 0 );
		label1.setText( " UI Properties :" );
		uiPropertiesPanel.add( label1, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false ) );
		final Spacer spacer2 = new Spacer();
		uiPropertiesPanel.add( spacer2, new GridConstraints( 2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false ) );
		controlPropertiesPanel = new JPanel();
		controlPropertiesPanel.setLayout( new GridLayoutManager( 4, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		controlPropertiesPanel.setVisible( false );
		mainPanel.add( controlPropertiesPanel, new GridConstraints( 3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		final Spacer spacer3 = new Spacer();
		controlPropertiesPanel.add( spacer3, new GridConstraints( 3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, 1, null, null, null, 0, false ) );
		controlPropertiesScrollPane = new JScrollPane();
		controlPropertiesPanel.add( controlPropertiesScrollPane, new GridConstraints( 1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		controlPropertiesScrollPane.setViewportView( controlPropertiesTable );
		addPropertyButton = new JButton();
		addPropertyButton.setText( "Add Property" );
		controlPropertiesPanel.add( addPropertyButton, new GridConstraints( 2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false ) );
		lblControlProperties.setText( "Control Properties :" );
		controlPropertiesPanel.add( lblControlProperties, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$ () {
		return contentPane;
	}
}
