package orTestStandDesktop.ui.DashboardEditor.Blocks.Proto;

import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import orTestStandDesktop.ui.DashboardEditor.ResizableComponent.ResizableComponentWrapper;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public interface IDashboardBlock extends Serializable{

  public EBlockType getBlockType ();
	public ResizableComponentWrapper getContainerComponent ();
  public JComponent getInnerComponent ();


	public int getUiPropertiesCount ();
  public void setUiProperties ( ControlProperties newProperties );
  public ControlProperties getUiProperties ();
	public void applyUiProperties ();


	public ControlPropertyItem setControlProperty ( int num );
	public ControlPropertyItem getControlProperty ( int num );
	public int getControlPropertiesCount ();
	public int getControlPropertiesCountMax ();
	public ControlProperties getControlProperties ();
	public void applyControlProperties ();
}
