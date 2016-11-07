package xorDashboardDesktop.ui.helpers;

import java.awt.*;

/**
 * Created by orb1t_ua on 10/29/16.
 */
//class used for scaling images
public final class uiUtils {

	public static void centerWindow ( Window frame ) {
		Dimension dimemsion = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimemsion.width/2-frame.getSize().width/2, dimemsion.height/2-frame.getSize().height/2);
	}
}
