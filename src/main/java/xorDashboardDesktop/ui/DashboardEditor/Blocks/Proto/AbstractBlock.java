package xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto;

import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import xorDashboardDesktop.ui.DashboardEditor.Panel.IEditorComponentsTreeNode;
import xorDashboardDesktop.ui.DashboardEditor.ResizableComponent.ResizableBorder;
import xorDashboardDesktop.ui.DashboardEditor.ResizableComponent.ResizableComponentWrapper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Paths;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public abstract class AbstractBlock extends ResizableComponentWrapper implements Serializable, IDashboardBlock, IEditorComponentsTreeNode {

	protected static int instancesCount = 0;

  protected ControlProperties uiProperties = null;
  protected ControlProperties controlProperties = null;

  protected transient ResizableComponentWrapper container;

  private DefaultMutableTreeNode componentNode = null;
  private DefaultMutableTreeNode parrentNode = null;

	@Override
	public EBlockType getBlockType () {
		return EBlockType.INDICATOR;
	}

	@Override
	public int getControlPropertiesCount () {
		return this.controlProperties.size();
	}

	@Override
	public int getControlPropertiesCountMax () {
		return Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ));//0;
	}

	@Override
	public ControlProperties getControlProperties () {
		return this.controlProperties;
	}

	@Override
	public void applyControlProperties () {
//		if ( null == panelContentBlock )
//			createInnerComponent();
	}

	public void setConfigured ( ) {
		this.firstConfigured = false;
	}

	private boolean firstConfigured = true;

//	public ControlProperties getDefaultProperties (){return uiProperties;};

  public AbstractBlock(ControlProperties uiProperties ) {
    super();
	  instancesCount++;
    this.uiProperties = uiProperties;
	  this.controlProperties = new ControlProperties();
    this.container = this;

    componentNode = new DefaultMutableTreeNode(this);

	  //applyUiProperties();
//	  setSize( Integer.parseInt( uiProperties.getPropertyValue("Width") ), Integer.parseInt( uiProperties.getPropertyValue("Height") ) );
//	  this.setSize ( new Dimension( Double.valueOf( uiProperties.getPropertyValue("Width")).intValue(), Double.valueOf( uiProperties.getPropertyValue("Height")).intValue() ) );
  }


	public void createInnerComponent(){
		if ( null != this.panelContentBlock ){
			this.remove( this.panelContentBlock );
			this.panelContentBlock = null;
		}
		if ( this.getComponents().length > 0 )
			this.removeAll();
	}

  public JComponent getInnerComponent() {
    return panelContentBlock;
  }

  public ResizableComponentWrapper getContainerComponent() {
    return container;
  }

  public int getUiPropertiesCount () {
    return uiProperties.size();
  }

  public void applyUiProperties (){

//	  if ( null == panelContentBlock )
//		  createInnerComponent();

    if ( null != uiProperties ) {
      this.setName( uiProperties.getPropertyValue("Name"));
      this.panelContentBlock.setName( uiProperties.getPropertyValue("Name") + "Content");


        Border brd = this.getBorder();
        if (brd instanceof ResizableBorder)
            ((ResizableBorder) brd).setTitle ( Boolean.valueOf( uiProperties.getPropertyValue("Border") ) ? uiProperties.getPropertyValue("Title") : "" );
//	    else
//	        ((JComponent)this).

      this.setLocation(Double.valueOf( uiProperties.getPropertyValue("Left")).intValue(), Double.valueOf( uiProperties.getPropertyValue("Top")).intValue());

      this.setSize ( new Dimension( Double.valueOf( uiProperties.getPropertyValue("Width")).intValue(), Double.valueOf( uiProperties.getPropertyValue("Height")).intValue() ) );


	    if ( null != panelContentBlock && ( panelContentBlock instanceof ImagePanel ) ) {
		    File tmp = new File( uiProperties.getPropertyValue( "BackgroundImage" ) );
		    ((ImagePanel)panelContentBlock).setImage( tmp );
		    ((ImagePanel)panelContentBlock).setScale( panelContentBlock.getWidth(), panelContentBlock.getHeight() );
	    }

    }
  }

  public void setUiProperties ( ControlProperties newProperties) {
    this.uiProperties = newProperties;
//	  applyUiProperties();
    this.revalidate();
    this.repaint();
  }

  public ControlProperties getUiProperties () {
    return uiProperties;
  }

	@Override
	public ControlPropertyItem setControlProperty ( int num ) {
		return null;
	}

	@Override
	public ControlPropertyItem getControlProperty ( int num ) {
		return ( null != controlProperties ? (num <= controlProperties.size() ? controlProperties.getProperties().get( num ) : null ) : null);
	}

	public ControlProperties getDefaultProperties(){
    ControlProperties def;
    def = new ControlProperties();
    def.setProperty(new ControlPropertyItem("Name", EControlPropertyItemType.STR, "AbstractBlock#"+instancesCount));
    def.setProperty(new ControlPropertyItem("componentClassName", EControlPropertyItemType.SYS, "AbstractBlock") );
    def.setProperty(new ControlPropertyItem("Title", EControlPropertyItemType.STR, "AbstractBlockTitle") );
//    def.setProperty(new ControlPropertyItem("TitleFont", EControlPropertyItemType.STR, new Font("Bitstream Vera Sans Mono", Font.BOLD, 12)) );
    def.setProperty(new ControlPropertyItem("TitleColor", EControlPropertyItemType.COL, String.valueOf( (new Color(-4478958)).getRGB() )) );
    def.setProperty(new ControlPropertyItem("Left", EControlPropertyItemType.FLT, "0") );
    def.setProperty(new ControlPropertyItem("Top", EControlPropertyItemType.FLT, "0") );
    def.setProperty(new ControlPropertyItem("Width", EControlPropertyItemType.FLT, "256") );
    def.setProperty(new ControlPropertyItem("Height", EControlPropertyItemType.FLT, "128") );
    def.setProperty(new ControlPropertyItem("Border", EControlPropertyItemType.BIN, "true") );

    def.setProperty(new ControlPropertyItem("BackgroundImage", EControlPropertyItemType.STR, Paths.get( "" ).toAbsolutePath().toString() + "/icons/common_pump.pn" ) );
    def.setProperty(new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "0" ) );

    return def;
  }

  public void setInnerComponent(JComponent innerComponent) {
    if ( null != this.panelContentBlock ) {
	    this.remove( this.panelContentBlock );
    }
      this.panelContentBlock = innerComponent;

      this.panelContentBlock.setSize ( new Dimension( Double.valueOf( uiProperties.getPropertyValue("Width")).intValue(), Double.valueOf( uiProperties.getPropertyValue("Height")).intValue() ) );

      this.add(this.panelContentBlock);
  }

	@Override
	public DefaultMutableTreeNode getComponentNode() {
		return this.componentNode;
	}

	@Override
	public void setComponentNode(DefaultMutableTreeNode node) {
		this.componentNode = node;
	}

	@Override
	public DefaultMutableTreeNode getComponentParrentNode() {
		return this.parrentNode;
	}

	@Override
	public void setComponentParrentNode(DefaultMutableTreeNode node) {
		this.parrentNode = node;
	}

	public boolean isFirstConfigured () {
		return firstConfigured;
	}
}
