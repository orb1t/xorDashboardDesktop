package orTestStandDesktop.ui.DashboardEditor.Blocks;

import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.EBlockType;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import orTestStandDesktop.ui.DashboardEditor.ResizableComponent.ResizableComponentWrapper;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class LabelBlock extends AbstractBlock  implements Serializable {

  @Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Label#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "LabelBlock" );
    def.setPropertyValue ( "Title", "LabelTitle" );
    def.setPropertyValue ( "Border", "false" );
    return def;
  }

  public LabelBlock () {
    super(null);
    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();
	  createInnerComponent();
  }

  @Override
  public ResizableComponentWrapper getContainerComponent() {
    return this;
  }

	@Override
	public void applyUiProperties () {
		super.applyUiProperties();
		((JLabel)getInnerComponent()).setText(uiProperties.getPropertyValue("Title"));
	}

	@Override
  public EBlockType getBlockType() {
    return EBlockType.INDICATOR;
  }

	@Override
	public void createInnerComponent () {
		super.createInnerComponent();
		setInnerComponent ( new JLabel( uiProperties.getPropertyValue("Title")) );
	}

}
