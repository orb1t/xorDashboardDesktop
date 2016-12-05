package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import com.intellij.uiDesigner.core.GridConstraints;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockIndicator;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlPropertyItem;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.EControlPropertyItemType;
import xorDashboardDesktop.ui.MainForm;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public class Graph2dPlotBlockIndicator extends AbstractBlockIndicator  implements Serializable {


	private orGfxCanvas graph;

	@Override
  public ControlProperties getDefaultProperties() {
    ControlProperties def;
    def = super.getDefaultProperties();
    def.setPropertyValue ( "Name", "Graph2dPlot#"+instancesCount );
    def.setPropertyValue ( "componentClassName", "Graph2dPlotBlockIndicator" );
    def.setPropertyValue ( "Title", "Graph2dPlot" );
    def.setPropertyValue ( "Border", "true" );

	  def.setPropertyValue( "ControlPropertiesMax", "6" );

    return def;
  }


	@Override
	public void createInnerComponent () {
//		super.createInnerComponent();

		JPanel dashboardContainer = new JPanel(new GridLayout(  ));
//	dashboardContainer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		this.add(dashboardContainer);

		this.graph = new orGfxCanvas(MainForm.tableModel, orGfxCanvas.GRAPH_MODE_WINDOW, dashboardContainer.getWidth(), dashboardContainer.getHeight());



		dashboardContainer.add( this.graph, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST/*ANCHOR_CENTER*/, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		setInnerComponent ( dashboardContainer );

		MainForm.tableModel.addTableModelListener( this.graph );
	}


	public Graph2dPlotBlockIndicator () {
    super(new ControlProperties());

    uiProperties = this.getDefaultProperties();


	uiProperties.setProperty( new ControlPropertyItem( "ControlPropertiesMax", EControlPropertyItemType.SYS, "11" ));

	ControlPropertyItem res = new ControlPropertyItem( "Count", EControlPropertyItemType.LST, "2;1;2;" );
	controlProperties.setProperty( res );

	res = new ControlPropertyItem( "Title1", EControlPropertyItemType.STR, "graph1" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Color1", EControlPropertyItemType.COL, String.valueOf(Color.RED.getRGB()) );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Max Val1", EControlPropertyItemType.FLT, "2" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Min Val1", EControlPropertyItemType.FLT, "0" );
	controlProperties.setProperty( res );
	String valList = MainForm.tableModel.getHeaders().get( 0 ) + ";";
	for ( int i = 0; i < MainForm.tableModel.getHeaders().size(); i++ ){ // Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ); i++ ){
		valList += /*i*/ MainForm.tableModel.getHeaders().get( i ) + ";";
	}
	res = new ControlPropertyItem( "pktTableSourceColumn1", EControlPropertyItemType.LST, valList );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Title2", EControlPropertyItemType.STR, "graph2" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Color2", EControlPropertyItemType.COL, String.valueOf(Color.GREEN.getRGB()) );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Max Val2", EControlPropertyItemType.FLT, "1" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "Min Val2", EControlPropertyItemType.FLT, "0" );
	controlProperties.setProperty( res );
	res = new ControlPropertyItem( "pktTableSourceColumn2", EControlPropertyItemType.LST, valList );
	controlProperties.setProperty( res );

		createInnerComponent();
	applyUiProperties();
	resize();
//	this.graph.addColumntToGraph(0);
  }

	@Override
	protected void resize () {
		super.resize();

		this.graph.setSize( getWidth()-20, getHeight() - 20 );

		this.graph.revalidate();
		this.graph.repaint();
		revalidate();
	}


	@Override
	public void tableChanged ( TableModelEvent tableModelEvent ) {
//		for ( int i = 0; i < graph._cnt; i++ ){
//			double tmp = Double.valueOf( MainForm.tableModel.values.get( MainForm.tableModel.values.size() - 1 ).get( MainForm.tableModel.getHeaders().indexOf ( controlProperties.getPropertyValue( "pktTableSourceColumn" + (i+1) ) ) ).toString() );
//			graph.convert(i, (float) tmp, Float.valueOf( controlProperties.getPropertyValue( "Min Val" + (i+1) ) ), Float.valueOf( controlProperties.getPropertyValue( "Max Val" + (i+1) ) ), Color.RED);
//		}

//		if ( null != gauge ) {
//			double tmp = Double.valueOf( MainForm.tableModel.values.get( MainForm.tableModel.values.size() - 1 ).get( MainForm.tableModel.getHeaders().indexOf ( controlProperties.getProperties().get( 4 ).getValue() ) ).toString() );
//			System.out.println( "tableModelEvent = [" + tmp + "]" );
//			if ( gaugeLastVal != tmp )
//				gauge.setValue( tmp );
//		}
	}

	@Override
	public ControlPropertyItem setControlProperty ( int num ) {
		ControlPropertyItem res = null;
		if ( num < Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) )
			res = controlProperties.getProperties().get( num );

//			if ( controlProperties.size() <= Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ) ) {
//				String valList = MainForm.tableModel.getHeaders().get( 0 ) + ";";
//				for ( int i = 0; i < MainForm.tableModel.getHeaders().size(); i++ ){ // Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ) ); i++ ){
//					valList += /*i*/ MainForm.tableModel.getHeaders().get( i ) + ";";
//				}
//				res = new ControlPropertyItem("IndicatorSource#" + this.controlProperties.size(), EControlPropertyItemType.LST, valList);
//				controlProperties.setProperty( res );
//
//			}
			return res;
		}

	@Override
	public int getControlPropertiesCountMax () {
		return Integer.parseInt(  uiProperties.getPropertyValue( "ControlPropertiesMax" ));
	}

	@Override
	public void applyControlProperties () {

		for ( int i = 0; i < this.graph.DRW_IDXs.size(); i++){
			this.graph.removeColumntToGraph( this.graph.DRW_IDXs.get( i ) );
		}
//		this.graph.cbColors.clear();
		for ( int i = 0; i < Integer.parseInt(  controlProperties.getPropertyValue( "Count" ) ); i++ ){
			MainForm.tableModel.setMaxValueOf( MainForm.tableModel.getHeaders().indexOf ( controlProperties.getPropertyValue( "pktTableSourceColumn" + (i+1))), Double.parseDouble( controlProperties.getPropertyValue( "Max Val" + (i+1) ) ) );
			MainForm.tableModel.setMinValueOf( MainForm.tableModel.getHeaders().indexOf ( controlProperties.getPropertyValue( "pktTableSourceColumn" + (i+1))), Double.parseDouble( controlProperties.getPropertyValue( "Min Val" + (i+1) ) ) );
//			this.graph.cbColors.add( Color.decode ( controlProperties.getPropertyValue( "Color" + (i+1) ) ) );
			this.graph.cbColors.set( i, Color.decode ( controlProperties.getPropertyValue( "Color" + (i+1) ) ) );// remove( i );
//			this.graph.cbColors.add( i, Color.decode ( controlProperties.getPropertyValue( "Color" + (i+1) ) ) );
			this.graph.addColumntToGraph ( MainForm.tableModel.getHeaders().indexOf ( controlProperties.getPropertyValue( "pktTableSourceColumn" + (i+1) ) ) );
		}

//		MainForm.tableModel.setMaxValueOf( 0, Integer.MAX_VALUE );
//		MainForm.tableModel.setMinValueOf( 0, 0 );

//		gauge.setTitle( controlProperties.getPropertyValue( "Title" ) );// "OXY flow rate");
//		gauge.setUnitString( controlProperties.getPropertyValue( "Units string" ) );//"mL / sec.");
//		gauge.setMinValue ( Double.parseDouble( controlProperties.getPropertyValue( "Min value" ) ) );// 0);
//		gauge.setMaxValue ( Double.parseDouble( controlProperties.getPropertyValue( "Max value" ) ) );// 1000);
	}

	@Override
	public int getControlPropertiesCount () {
		return this.controlProperties.size();
	}
}
