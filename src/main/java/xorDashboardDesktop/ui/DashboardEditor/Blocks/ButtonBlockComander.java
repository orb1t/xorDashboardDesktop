package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockComander;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.EBlockType;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.ResizableComponent.ResizableComponentWrapper;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;

import static xorDashboardDesktop.ui.MainForm.serial;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class ButtonBlockComander extends AbstractBlockComander  implements Serializable {

	private int propNumIdx = 0;
	private int cmdNextIdx = 0;
	private ArrayList cmds = new ArrayList(  );
	private boolean initialized = false;


	@Override
	public void createInnerComponent () {
		super.createInnerComponent();

		JButton newBtn = new JButton(  );

//		Collections.addAll(cmds, fieldParams.commands.split(";"));

		if ( cmds.size() >= 1 )
			initialized = true;

		setInnerComponent ( newBtn );
		((JButton)getInnerComponent()).addActionListener( this );
	}


	@Override
	public void actionPerformed ( ActionEvent actionEvent ) {

		if ( this.controlProperties.getProperties().size() > 0 ) {
			if ( propNumIdx > this.controlProperties.getProperties().size()-1 )
				propNumIdx = 0;
			System.out.println( "actionEvent = [" + propNumIdx + "] : " + this.controlProperties.getProperties().get( propNumIdx ).getValue() + "\n" + actionEvent );
			// TODO: write this.controlProperties.getProperties().get( propNumIdx ).getValue() to Serial!
			if ( null != serial && serial.connected )
				serial.serialWrite ( this.controlProperties.getProperties().get( propNumIdx ).getValue() + "\r\n");
			propNumIdx++;
		}
	}

	@Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Button#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "ButtonBlockComander" );
    def.setPropertyValue ( "Title", "ButtonTitle" );
    def.setPropertyValue ( "Border", "false" );

	  def.setPropertyValue( "ControlPropertiesMax", "-1" );

    return def;
  }

  public ButtonBlockComander () {
    super(null);
    if (null == uiProperties )
      uiProperties = this.getDefaultProperties();

	  uiProperties.setPropertyValue( "ControlPropertiesMax", "-1" );

	  createInnerComponent ();
  }

  @Override
  public ResizableComponentWrapper getContainerComponent() {
    return this;
  }

	@Override
	public void applyUiProperties () {
		super.applyUiProperties();
		((JButton)getInnerComponent()).setText(uiProperties.getPropertyValue("Title"));
	}

	@Override
	public ControlPropertyItem setControlProperty ( int num ) {
		ControlPropertyItem res = null;
		if ( -1 == Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) ) {
			res = new ControlPropertyItem("Command #" + this.controlProperties.size(), EControlPropertyItemType.STR, "");
			controlProperties.setProperty( res );
		}
		return res;
		//return super.setControlProperty( num );
	}

	@Override
  public EBlockType getBlockType() {
    return EBlockType.CONTROL;
  }

	@Override
	public void applyControlProperties () {
		super.applyControlProperties();
	}


}
