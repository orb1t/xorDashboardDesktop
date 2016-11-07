package xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public interface IDashboardBlockIndicator extends Serializable, TableModelListener {
	@Override
	void tableChanged ( TableModelEvent tableModelEvent );
}
