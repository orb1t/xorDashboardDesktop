package orTestStandDesktop.ui.DashboardEditor.Blocks.Proto;

import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;

import java.io.Serializable;

/**
 * Created by orb1t_ua on 10/22/16.
 */
public abstract class AbstractBlockIndicator extends AbstractBlock implements Serializable, IDashboardBlock, IDashboardBlockIndicator {

//	ControlProperties

//	private int controlProperties

	public AbstractBlockIndicator ( ControlProperties uiProperties ) {
		super( uiProperties );

//		this.controlProperties = new ControlProperties();
	}



}
