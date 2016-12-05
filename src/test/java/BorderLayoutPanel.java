import javax.swing.*;
import java.awt.*;

/**
 * Created by orb1t_ua on 10/29/16.
 */
public class BorderLayoutPanel {

	private JFrame mainFrame;
	private JButton btnLeft, btnRight, btnTop, btnBottom, btnCenter;

	public BorderLayoutPanel() {
		mainFrame = new JFrame("Border Layout Example");
		btnLeft = new JButton("LEFT");
		btnRight = new JButton("RIGHT");
		btnTop = new JButton("TOP");
		btnBottom = new JButton("BOTTOM");
		btnCenter = new JButton("CENTER");
	}

	public void SetLayout() {
		mainFrame.add(btnTop, BorderLayout.NORTH);
		mainFrame.add(btnBottom, BorderLayout.SOUTH);
		mainFrame.add(btnLeft, BorderLayout.EAST);
		mainFrame.add(btnRight, BorderLayout.WEST);
		mainFrame.add(btnCenter, BorderLayout.CENTER);
//        mainFrame.setSize(200, 200);
//        or
		mainFrame.pack();


		//take up the default look and feel specified by windows themes
		mainFrame.setDefaultLookAndFeelDecorated(true);

		//make the window startup position be centered
		mainFrame.setLocationRelativeTo( null );

//		centerWindow(mainFrame);

		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);


		mainFrame.setVisible(true);
	}

	public static void main ( String[] args ) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				BorderLayoutPanel pnl = new BorderLayoutPanel();
				pnl.SetLayout();
			}
		});

//		pnl.setVisible( true );
//		System.exit( 0 );
	}
}

