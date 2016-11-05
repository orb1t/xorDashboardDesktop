package orTestStandDesktop.ui.DashboardEditor.Blocks.Proto;

import orTestStandDesktop.ui.helpers.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by orb1t_ua on 10/29/16.
 */
public class ImagePanel extends JPanel {

		private transient BufferedImage bufferedImage;
	private transient Image scaledImage;
	private Dimension imageDimensions;
//		public int width;
//		public int height;

	class resizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			//Recalculate the variable you mentioned
			setScale( e.getComponent().getWidth(), e.getComponent().getHeight()  );
		}
	}

//	private ComponentListener resizeListener = new ComponentAdapter(){
//		public void componentResized(ActionEvent e){
//			// recalculate value
//			if ( null != bufferedImage){
//				Object tmp = e.getSource();
//				if ( tmp instanceof )
//				setScale( tmp );
//			}
//		}
//	};

		public ImagePanel () {
			this(null);
		}

	public boolean setImage(File img){
		try {
			if ( img.exists() && img.isFile() ) {
				// Prepare img only! will be displayed in paintComponent of ResizableComponentWrapper
				bufferedImage = ImageIO.read( img );
				return true;
	//			    this.image = ImageUtils.scaleImage( this.getSize().width, this.getSize().height, bufferedImage );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return false;
	}

	public void setScale ( int width, int height){
		if ( null != bufferedImage)
      this.scaledImage = ImageUtils.scaleImage( this.getSize().width, this.getSize().height, bufferedImage );
	}

	public ImagePanel ( LayoutManager o ) {
		super(o);

		this.addComponentListener(new resizeListener());

//		try {
//			String res = "/icons/common_pump.png";
//
//			String crntPath = Paths.get( "" ).toAbsolutePath().toString();
//			System.out.println( crntPath );
//			bufferedImage = ImageIO.read( new File( crntPath + res ) );
//		} catch ( Exception e ) {
//			e.printStackTrace();
//		}

		//so we can set the JPanel preferred size to the image width and height
//			ImageIcon ii = new ImageIcon( image );
//			width = mainPanel.getWidth(); //ii.getIconWidth();
//			height = mainPanel.getHeight();// ii.getIconHeight();
	}

	//so our panel is the same size as image
		@Override
		public Dimension getPreferredSize () {
			return new Dimension( imageDimensions.width, imageDimensions.height );
		}

		@Override
		public void paintComponent ( Graphics g ) {
			super.paintComponent( g );

			if ( scaledImage == null ) {
				return;
			}

//			width = image.getWidth( this );
//			height = image.getHeight( this );
//
//			g.drawImage( ImageUtils.scaleImage( this.getWidth(), this.getHeight(), image ), 0, 0, null );
//			g.drawImage( ImageUtils.scaleImage( this.getWidth(), this.getHeight(), image ), 0, 0, null );
			g.drawImage( scaledImage, 0, 0, null );
		}
	}
