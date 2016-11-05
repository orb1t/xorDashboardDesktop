package orTestStandDesktop.ui.DashboardEditor.Panel;

//import ResizableComponentWrapper;

import orTestStandDesktop.ui.DashboardEditor.DashboardComponentsTree;
import orTestStandDesktop.ui.DashboardEditor.FileBrowserTree.FileBrowserTree;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class DashboardPanel extends JPanel implements Serializable, TreeSelectionListener {
	private final JFrame frame;
	private DashboardEditorPanel dashboardEditorPanel;

	private JPanel contentPane;
	private JPanel dashboardEditorContainerPanel;
	private JScrollPane componentsTreePanel;
	private JTree componentsTree;
	private JPanel profilesContainerPanel;
	private JScrollPane profilesTreePanel;
	private JButton loadButton;
	private JTextField profileNameTextField;
	private JButton saveButton;
	private JTree treeProfiles;

	private DashboardPanel ME;

	public DashboardPanel ( JFrame frame ) {
		super();
		this.frame = frame;
		ME = this;
		setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		$$$setupUI$$$();
		profileNameTextField.setText( dashboardEditorPanel.getEditorPtojectName() + dashboardEditorPanel.getEditorPtojectExt() );
		add( contentPane, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );

		saveButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				String profName = "defaultBoard.dash";
				if ( profileNameTextField.getText().length() > 0 )
					profName = profileNameTextField.getText();
				dashboardEditorPanel.saveDashboard( profName );
				( (FileBrowserTree) treeProfiles ).updateContent();
			}
		} );
		loadButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				String profName = "defaultBoard.dash";
				if ( profileNameTextField.getText().length() > 0 )
					profName = profileNameTextField.getText();
				dashboardEditorPanel.loadDashboard( profName );
				DashboardComponentsTree.INSTANCE.update( dashboardEditorPanel.getEditorPtojectName(), dashboardEditorPanel );
				componentsTree = DashboardComponentsTree.INSTANCE;

				frame.pack();
				revalidate();
				repaint();
			}
		} );
	}


	public static void main ( String[] args ) {
		JFrame frame = new JFrame( "Dashboard Panel Test MAIN" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		DashboardPanel dialog = new DashboardPanel( frame );

		frame.add( dialog );
		frame.setLocationByPlatform( true );
		frame.setSize( 640, 480 );
		frame.setVisible( true );
	}

	private void createUIComponents () {
		dashboardEditorContainerPanel = new JPanel();
		dashboardEditorContainerPanel.setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );

		profileNameTextField = new JTextField();

		dashboardEditorPanel = new DashboardEditorPanel( DashboardEditorPanel.getEditorPtojectName() );

		componentsTree = DashboardComponentsTree.INSTANCE;
		componentsTree.addTreeSelectionListener( this );

		dashboardEditorPanel.setListener( (DashboardComponentsTree) componentsTree );
		dashboardEditorContainerPanel.add( dashboardEditorPanel, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );


		this.treeProfiles = FileBrowserTree.INSTANCE;
		for ( int i = 0; i < treeProfiles.getRowCount(); i++ ) {
			treeProfiles.expandPath( treeProfiles.getPathForRow( i ) );
		}
		// Add a listener
		this.treeProfiles.addTreeSelectionListener( new TreeSelectionListener() {
			public void valueChanged ( TreeSelectionEvent e ) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
						                                                       .getPath().getLastPathComponent();
				System.out.println( "You selected " + node );
				profileNameTextField.setText( node.toString() );
			}
		} );

	}

	@Override
	public void valueChanged ( TreeSelectionEvent treeSelectionEvent ) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) componentsTree.getLastSelectedPathComponent();
		if ( node == null ) return;

		if ( node.getUserObject() instanceof JComponent ) {
			JComponent nodeInfo = (JComponent) node.getUserObject();
			dashboardEditorPanel.updateSelectionChange( nodeInfo );
			dashboardEditorPanel.invalidate();
			if ( dashboardEditorPanel.getParent() != null ) {
				( (JComponent) dashboardEditorPanel.getParent() ).revalidate();
			}
			dashboardEditorPanel.revalidate();
			dashboardEditorPanel.repaint();

			System.out.println( nodeInfo.toString() );
		}
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
		contentPane.setPreferredSize( new Dimension( 400, 600 ) );
		final JSplitPane splitPane1 = new JSplitPane();
		splitPane1.setOneTouchExpandable( true );
		contentPane.add( splitPane1, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension( 200, 200 ), null, 0, false ) );
		final JSplitPane splitPane2 = new JSplitPane();
		splitPane2.setMaximumSize( new Dimension( -1, -1 ) );
		splitPane2.setMinimumSize( new Dimension( -1, -1 ) );
		splitPane2.setOneTouchExpandable( true );
		splitPane2.setOrientation( 0 );
		splitPane2.setPreferredSize( new Dimension( -1, -1 ) );
		splitPane2.setResizeWeight( 0.0 );
		splitPane1.setLeftComponent( splitPane2 );
		componentsTreePanel = new JScrollPane();
		componentsTreePanel.setMaximumSize( new Dimension( -1, -1 ) );
		componentsTreePanel.setMinimumSize( new Dimension( -1, -1 ) );
		componentsTreePanel.setPreferredSize( new Dimension( -1, -1 ) );
		splitPane2.setLeftComponent( componentsTreePanel );
		componentsTree.setAutoscrolls( true );
		componentsTree.setMaximumSize( new Dimension( -1, -1 ) );
		componentsTree.setMinimumSize( new Dimension( -1, -1 ) );
		componentsTree.setPreferredSize( new Dimension( -1, -1 ) );
		componentsTree.setRootVisible( true );
		componentsTree.setVisibleRowCount( 5 );
		componentsTreePanel.setViewportView( componentsTree );
		profilesContainerPanel = new JPanel();
		profilesContainerPanel.setLayout( new GridLayoutManager( 3, 2, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		splitPane2.setRightComponent( profilesContainerPanel );
		loadButton = new JButton();
		loadButton.setText( "Load" );
		profilesContainerPanel.add( loadButton, new GridConstraints( 2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		saveButton = new JButton();
		saveButton.setText( "Save" );
		profilesContainerPanel.add( saveButton, new GridConstraints( 2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		profilesTreePanel = new JScrollPane();
		profilesContainerPanel.add( profilesTreePanel, new GridConstraints( 0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		treeProfiles.setMaximumSize( new Dimension( -1, -1 ) );
		treeProfiles.setMinimumSize( new Dimension( -1, -1 ) );
		treeProfiles.setPreferredSize( new Dimension( -1, -1 ) );
		profilesTreePanel.setViewportView( treeProfiles );
		profileNameTextField.setText( "" );
		profilesContainerPanel.add( profileNameTextField, new GridConstraints( 1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension( 150, -1 ), null, 0, false ) );
		splitPane1.setRightComponent( dashboardEditorContainerPanel );
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$ () {
		return contentPane;
	}
}
