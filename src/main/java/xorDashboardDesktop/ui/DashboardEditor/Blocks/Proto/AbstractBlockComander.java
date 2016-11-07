package xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto;

import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;

import java.io.Serializable;

/**
 * Created by orb1t_ua on 10/22/16.
 */
public abstract class AbstractBlockComander extends AbstractBlock implements Serializable, IDashboardBlock, IDashboardBlockComander {

//	ControlProperties

//	private int controlProperties

	public AbstractBlockComander ( ControlProperties properties ) {
		super( properties );

//		this.controlProperties = new ControlProperties();
	}



}
