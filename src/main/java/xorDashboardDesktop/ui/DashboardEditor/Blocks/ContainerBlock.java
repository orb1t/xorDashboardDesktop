package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.EBlockType;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.ImagePanel;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ResizableComponent.ResizableComponentWrapper;

import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class ContainerBlock extends AbstractBlock implements Serializable {


  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Container#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "ContainerBlock" );
    def.setPropertyValue ( "Title", "ContainerTitle" );
    return def;
  }

  public ContainerBlock() {
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
	public void applyUiProperties () {
		super.applyUiProperties();
	}

	@Override
  public EBlockType getBlockType() {
    return EBlockType.CONTAINER;
  }

	@Override
	public void createInnerComponent () {
//		super.createInnerComponent();
		panelContentBlock = new ImagePanel(null);
		setInnerComponent ( panelContentBlock );
	}

}
