package tst;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eu.hansolo.steelseries.gauges.AbstractGauge;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.orGfxCanvas;
import xorDashboardDesktop.ui.helpers.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import static xorDashboardDesktop.ui.MainForm.tableModel;

public class testDlg extends JDialog {
	private final testDlg me;
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JPanel mainPanel;
	private JButton button1;
	private JButton button2;
	private JLabel lbTitme;
	private JLabel lbValue;
	private JSlider slider1;

	private orGfxCanvas graphCanvas;

	java.util.List<AbstractGauge> gauge = new ArrayList<>();

	private int col = 1;
	private int row = 1;
	private int num = 0;

	public testDlg () {
		setContentPane( contentPane );
		setModal( true );
		getRootPane().setDefaultButton( buttonOK );
		this.me = this;

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


//		JPanel tmpCnt = new JPanel(  ) ;
		mainPanel.setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		mainPanel.setSize( 300, 300 );
		this.setLocation( 250, 250 );
//		graphCanvas = new orGfxCanvas( tableModel, orGfxCanvas.GRAPH_MODE_WINDOW, mainPanel.getWidth(), mainPanel.getHeight() );

//		graphCanvas.addColumntToGraph( 0 );
//	  //this.graphCanvas.addMouseListener(this.graphCanvas);

//		tmpCnt.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
//		mainPanel.add( graphCanvas, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST/*ANCHOR_CENTER*/, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false ) );
//		dashboardContainer.getInnerComponent().add(tmpCnt);//, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST/*ANCHOR_CENTER*/, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));


		button1.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				tableModel.prepareNewRow();
				tableModel.newRowTmp.add( tableModel.getRowCount() );
				Random rnd = new Random();
				for ( int i = 0; i < tableModel.getColumnCount() - 1; i++ ) {
					tableModel.newRowTmp.add( rnd.nextFloat() );
				}

				System.out.println( "[" + tableModel.newRowTmp.get( 0 ) + ":" + tableModel.newRowTmp.get( 1 ) + "] rate: " + tableModel.newRowTmp.get( 2 ) + " l/m, flow: " + tableModel.newRowTmp.get( 3 ) + " ml/s, ttl: " + tableModel.newRowTmp.get( 4 ) + " ml | "
						                    + "pot: " + tableModel.newRowTmp.get( 5 ) + ", srv: " + tableModel.newRowTmp.get( 6 ) + " | "
						                    + "rl0: " + tableModel.newRowTmp.get( 7 ) + ", rl1: " + tableModel.newRowTmp.get( 8 ) + "\n" );

				tableModel.addRow( tableModel.newRowTmp );
			}
		} );
		button2.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed ( ActionEvent actionEvent ) {
				//ImageComponent img = new ImageComponent();
				//mainPanel.add( img, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false ) );
				//img.setVisible( true );
				pack();
				revalidate();
				repaint();
//				String p = "eu.hansolo.eu.hansolo.steelseries.gauges.";
////				gauge.add( mkGauge("DigitalRadial", p) );
//				gauge.add( mkGauge( "DisplayCircular", p ) ); //*
//
//				( (Lcd) gauge.get( gauge.size() - 1 ) ).setLcdValue( 123.45 );
			}
		} );
	}


	private AbstractGauge mkGauge ( String name, String path ) {
		Class<?> klazz = null;
		Object cmp = null;
		JComponent newComponentBlock = null;
		String componentName = path + name;
//

		try {
			klazz = Class.forName( componentName );

//	      Class.
			cmp = klazz.newInstance();
			if ( cmp instanceof AbstractGauge ) {
				AbstractGauge abstrBlock = (AbstractGauge) cmp;

//        newComponentBlock.set
				abstrBlock.setTitle( "OXY flow rate" );
				abstrBlock.setUnitString( "mL / sec." );
				abstrBlock.setMinValue( 0 );
				abstrBlock.setValue( 50 );
				abstrBlock.setMaxValue( 100 );
				newComponentBlock = abstrBlock;
			} else if ( cmp instanceof JComponent ) {
				newComponentBlock = (JComponent) cmp;

//        newComponentBlock.set
//        newComponentBlock.setTitle("OXY flow rate");
//        newComponentBlock.setUnitString("mL / sec.");
//        newComponentBlock.setMinValue(0);
//        newComponentBlock.setMaxValue(1000);
			}


			JPanel tmp = new JPanel( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
			tmp.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( new Color( -9983301 ) ), "#" + num + "  " + ( row - 1 ) + ":" + ( col - 1 ) + " " + name ) );
			num++;
//        tmp.getBorder().`
//        mainPanel.add(newComponentBlock, new GridConstraints(row-1, col-1, 1, 1, GridConstraints.ANCHOR_NORTHWEST/*ANCHOR_CENTER*/, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
			tmp.add( newComponentBlock, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST/*ANCHOR_CENTER*/, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false ) );
			mainPanel.add( tmp, new GridConstraints( row - 1, col - 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST/*ANCHOR_CENTER*/, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false ) );

			col++;
			if ( col == 7 ) {
				row++;
				col = 1;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		this.me.revalidate();
		return (AbstractGauge) newComponentBlock;
	}

	private void onOK () {
// add your code here
		dispose();
	}

	private void onCancel () {
// add your code here if necessary
		dispose();
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$ () {
		contentPane = new JPanel();
		contentPane.setLayout( new GridLayoutManager( 2, 2, new Insets( 10, 10, 10, 10 ), -1, -1 ) );
		final JPanel panel1 = new JPanel();
		panel1.setLayout( new GridLayoutManager( 1, 4, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		contentPane.add( panel1, new GridConstraints( 1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false ) );
		final Spacer spacer1 = new Spacer();
		panel1.add( spacer1, new GridConstraints( 0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false ) );
		final JPanel panel2 = new JPanel();
		panel2.setLayout( new GridLayoutManager( 1, 2, new Insets( 0, 0, 0, 0 ), -1, -1, true, false ) );
		panel1.add( panel2, new GridConstraints( 0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false ) );
		buttonOK = new JButton();
		buttonOK.setText( "OK" );
		panel2.add( buttonOK, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		buttonCancel = new JButton();
		buttonCancel.setText( "Cancel" );
		panel2.add( buttonCancel, new GridConstraints( 0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		button1 = new JButton();
		button1.setText( "newPktModelRow" );
		panel1.add( button1, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		button2 = new JButton();
		button2.setText( "Button" );
		panel1.add( button2, new GridConstraints( 0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false ) );
		mainPanel = new JPanel();
		mainPanel.setLayout( new GridLayoutManager( 2, 2, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		contentPane.add( mainPanel, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false ) );
		final Spacer spacer2 = new Spacer();
		mainPanel.add( spacer2, new GridConstraints( 0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false ) );
		final Spacer spacer3 = new Spacer();
		mainPanel.add( spacer3, new GridConstraints( 1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		final JPanel panel3 = new JPanel();
		panel3.setLayout( new GridLayoutManager( 3, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		mainPanel.add( panel3, new GridConstraints( 1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		panel3.setBorder( BorderFactory.createTitledBorder( "sliderBlockPanel" ) );
		lbTitme = new JLabel();
		lbTitme.setText( "Title" );
		panel3.add( lbTitme, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		final JPanel panel4 = new JPanel();
		panel4.setLayout( new GridLayoutManager( 1, 1, new Insets( 0, 0, 0, 0 ), -1, -1 ) );
		panel3.add( panel4, new GridConstraints( 1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		slider1 = new JSlider();
		panel4.add( slider1, new GridConstraints( 0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		lbValue = new JLabel();
		lbValue.setText( "Value : " );
		panel3.add( lbValue, new GridConstraints( 2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		final Spacer spacer4 = new Spacer();
		contentPane.add( spacer4, new GridConstraints( 0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false ) );
		final Spacer spacer5 = new Spacer();
		contentPane.add( spacer5, new GridConstraints( 1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false ) );
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$ () {
		return contentPane;
	}


	class ImageComponent extends JComponent {

		private Image image;
		public int width;
		public int height;

		public ImageComponent () {
			try {
				String res = "/icons/common_pump.png";

//				URL url = Thread.currentThread().getContextClassLoader().getResource( res );
//				URL url = Loader.class.getClassLoader().getResource( res );
//				URL url = ClassLoader.getSystemResource( res );
				URL url = System.class.getResource( res );
				String crntPath = Paths.get( "" ).toAbsolutePath().toString();
				System.out.println( crntPath );
//				image = ImageUtils.scaleImage( mainPanel.getWidth(), mainPanel.getHeight(), ImageIO.read( new File( crntPath + res ) ) ); //Thread.currentThread().getContextClassLoader().getResource( res ) ) );// new URL( "http://harmful.cat-v.org/software/_java/java-evil-edition.png" ) ) );
				image = ImageIO.read( new File( crntPath + res ) );
//				image = ImageUtils.scaleImage( mainPanel.getWidth(), mainPanel.getHeight(),  ); //Thread.currentThread().getContextClassLoader().getResource( res ) ) );// new URL( "http://harmful.cat-v.org/software/_java/java-evil-edition.png" ) ) );
				//image =  ImageIO.read(new URL("http://harmful.cat-v.org/software/_java/java-evil-edition.png"));//uses images scale
			} catch ( Exception e ) {
				e.printStackTrace();
			}

			//so we can set the JPanel preferred size to the image width and height
//			ImageIcon ii = new ImageIcon( image );
			width = mainPanel.getWidth(); //ii.getIconWidth();
			height = mainPanel.getHeight();// ii.getIconHeight();
		}

		//so our panel is the same size as image
		@Override
		public Dimension getPreferredSize () {
			return new Dimension( width, height );
		}

		@Override
		public void paintComponent ( Graphics g ) {
			super.paintComponent( g );

			if ( image == null ) {
				return;
			}

			width = image.getWidth( this );
			height = image.getHeight( this );

			g.drawImage( ImageUtils.scaleImage( mainPanel.getWidth(), mainPanel.getHeight(), (BufferedImage) image ), 0, 0, null );
		}
	}


	public static void main ( String[] args ) {
		testDlg dialog = new testDlg();
//		dialog.pack();
//		dialog.setVisible( true );
		System.exit( 0 );
	}

}
