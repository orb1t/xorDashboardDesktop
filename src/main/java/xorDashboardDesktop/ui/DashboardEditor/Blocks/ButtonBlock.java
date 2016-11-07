package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.EBlockType;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ResizableComponent.ResizableComponentWrapper;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class ButtonBlock extends AbstractBlock implements Serializable {

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Button#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "ButtonBlock" );
    def.setPropertyValue ( "Title", "ButtonTitle" );
    def.setPropertyValue ( "Border", "false" );
    return def;
  }

  public ButtonBlock() {
    super(null);
    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();

	  createInnerComponent ();
  }

  @Override
  public ResizableComponentWrapper getContainerComponent() {
    return this;
  }

  @Override
  public EBlockType getBlockType() {
    return EBlockType.CONTAINER;
  }

	@Override
	public void createInnerComponent () {
		setInnerComponent ( new JButton( uiProperties.getPropertyValue("Title")) );
	}

}
