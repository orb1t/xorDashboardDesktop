package orTestStandDesktop.ui.DashboardEditor.Blocks;

import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.EBlockType;
import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.ImagePanel;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import orTestStandDesktop.ui.DashboardEditor.ResizableComponent.ResizableComponentWrapper;

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
