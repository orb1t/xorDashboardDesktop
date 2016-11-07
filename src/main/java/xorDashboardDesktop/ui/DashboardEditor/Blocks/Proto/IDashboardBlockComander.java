package xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public interface IDashboardBlockComander extends Serializable, ActionListener {

	@Override
	void actionPerformed ( ActionEvent actionEvent );
}
