package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.EBlockType;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.ResizableComponent.*;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class ContainerSplitBlock extends AbstractBlock  implements Serializable {

  public JPanel getLeftPane() {
    return leftPane;
  }

  public void setLeftPane(JPanel leftPane) {
    this.leftPane = leftPane;
  }

  private JPanel leftPane;

  public JPanel getRigthPane() {
    return rigthPane;
  }

  public void setRigthPane(JPanel rigthPane) {
    this.rigthPane = rigthPane;
  }

  private JPanel rigthPane;

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "SplitContainer#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "ContainerSplitBlock" );
    def.setPropertyValue ( "Title", "SplitContainerTitle" );

    def.setProperty(new ControlPropertyItem("Orientation", EControlPropertyItemType.LST, "HORIZONTAL_SPLIT;VERTICAL_SPLIT;HORIZONTAL_SPLIT;") );
    def.setProperty(new ControlPropertyItem("LeftComponent", EControlPropertyItemType.LST, "JPanel") );
    def.setProperty(new ControlPropertyItem("RightComponent", EControlPropertyItemType.LST, "JPanel") );

    return def;
  }

  public ContainerSplitBlock() {
    super(null);
    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();

	  createInnerComponent();
  }

  @Override
  public void applyUiProperties (){
    super.applyUiProperties();
    ((JSplitPane)panelContentBlock).setOrientation ( ( uiProperties.getPropertyValue("Orientation").equals("VERTICAL_SPLIT") ? 0 : 1 ) );
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
		super.createInnerComponent();
		rigthPane = new JPanel(null);
		leftPane = new JPanel(null);
		setInnerComponent ( new JSplitPane( 0, leftPane, rigthPane) );
	}


}
