package orTestStandDesktop.ui.helpers;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by orb1t_ua on 10/29/16.
 */
//class used for scaling images
public final class ImageUtils {

	public static Image scaleImage ( int width, int height, BufferedImage filename ) {
		BufferedImage bi;
		try {
			bi = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints( new RenderingHints( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ) );
			g2d.drawImage( filename, 0, 0, width, height, null );
		} catch ( Exception e ) {
			return null;
		}
		return bi;
	}
}
