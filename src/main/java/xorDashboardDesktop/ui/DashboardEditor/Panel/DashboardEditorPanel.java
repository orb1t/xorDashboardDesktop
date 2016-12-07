package xorDashboardDesktop.ui.DashboardEditor.Panel;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.ContainerBlock;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.ContainerSplitBlock;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlockIndicator;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.IDashboardBlockIndicator;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.orGfxCanvas;
import xorDashboardDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;
import xorDashboardDesktop.ui.DashboardEditor.ControlPropertiesDialog.ControlPropertiesDialog;
import xorDashboardDesktop.ui.DashboardEditor.ControlPropertiesDialog.IAddBlockDialogResults;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.ResizableComponent.ResizableComponentMouseInputAdapter;
import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.ResizableComponent.ResizableComponentWrapper;
import xorDashboardDesktop.ui.MainForm;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by orb1t_ua on 06.10.16.
 */

//TODO: fix Props not being applied after Edit of Block!
public class DashboardEditorPanel extends JPanel implements Serializable, IEditorComponentsTreeNode{
  private final JMenuItem rmCurrentPopupItem;
	private final JMenu removeComponentMenu;
	private orGfxCanvas graphCanvas;
	private JPopupMenu addComponentPopup = new JPopupMenu();

  private IDashboarChangeListener listener;
  public static ContainerBlock dashboardContainer;

  private static String editorPtojectName = "default";
  private static final String editorPtojectExt = ".dash";

  public java.util.List<Component> resizableComponents = new ArrayList<>();
  public Tree<Component> resizableComponentsTree;
  private JComponent lastSelectedComponent;
  private Container lastSelectedContainer;
  public static JComponent ME = null;

  final Point location = new Point();

  private MouseAdapter mouseHndlr;

//  private AbstractBlock newComponentBlock;



  public void setListener(IDashboarChangeListener listener) {
    this.listener = listener;
  }

  public static String getEditorPtojectName() {
    return editorPtojectName;
  }

  public void setEditorPtojectName(String editorPtojectName) {
    this.editorPtojectName = editorPtojectName;
  }

  public static final String getEditorPtojectExt() {
    return editorPtojectExt;
  }


  @Override
  public String toString() {
    return this.editorPtojectName;
  }

  public void updateSelectionChange(JComponent component) {
    component.requestFocus();
    component.repaint();

    lastSelectedComponent.repaint();
    component.invalidate();
    if (!(component instanceof AbstractBlock))
      lastSelectedComponent = dashboardContainer;
    else
      lastSelectedComponent = (JComponent) component;

    lastSelectedComponent.revalidate();
    lastSelectedComponent.repaint();

    if (lastSelectedComponent.getParent() != null) {
      ((JComponent) lastSelectedComponent.getParent()).revalidate();
    }
  }


  private void configureDashboardContainer(){
    ControlProperties tmp = dashboardContainer.getDefaultProperties();
    tmp.setPropertyValue("Top", "0");
    tmp.setPropertyValue("Left", "0");
    tmp.setPropertyValue("Width", String.valueOf( getMaximumSize().getWidth()));
    tmp.setPropertyValue("Height", String.valueOf( getMaximumSize().getHeight()));
    dashboardContainer.setUiProperties(tmp);
    add(dashboardContainer, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    resizableComponents.add(dashboardContainer.getInnerComponent());
    dashboardContainer.addMouseListener(mouseHndlr);
    dashboardContainer.getInnerComponent().addMouseListener(mouseHndlr);
    dashboardContainer.applyUiProperties();

    dashboardContainer.setName( editorPtojectName );
    dashboardContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Dashboard Editor : " + editorPtojectName ));
    dashboardContainer.getInnerComponent().setBackground(Color.DARK_GRAY);

    lastSelectedContainer = dashboardContainer;
    lastSelectedComponent = (JComponent) lastSelectedContainer;

  }

  public DashboardEditorPanel(String editorPtojectName) {
    super();
    ME = this;

    mouseHndlr = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("ME = [" + lastSelectedComponent + "]");
//                updateSelectionChange ((JComponent) mouseEvent.getContainerComponent());

        mouseEvent.getComponent().requestFocus();
        mouseEvent.getComponent().repaint();
        lastSelectedComponent.repaint();
        if (!(mouseEvent.getComponent() instanceof AbstractBlock)) //ResizableComponentWrapper))
          lastSelectedComponent = ((DashboardEditorPanel)ME).dashboardContainer;
        else
          lastSelectedComponent = (JComponent) mouseEvent.getComponent();
        lastSelectedComponent.repaint();

        showPopup(mouseEvent);
      }

      private void showPopup(MouseEvent me) {
        if (me.isPopupTrigger()) {
          updateContainer((JComponent) me.getComponent());
          Point mousePnt = me.getPoint();
          location.setLocation(mousePnt);
          Point componentPnt = lastSelectedComponent.getLocation();
          if ( lastSelectedContainer != dashboardContainer && lastSelectedContainer != dashboardContainer.getInnerComponent() ) {
            if (removeComponentMenu.getMenuComponentCount() < 1 )// getComponentIndex(rmCurrentPopupItem) < 0)
              removeComponentMenu.add(rmCurrentPopupItem);
//              addComponentPopup.add(rmCurrentPopupItem);

	          rmCurrentPopupItem.setVisible( true );

            Container tmp = getContainersWrapper(lastSelectedContainer);
            if (tmp == null) {
              tmp = lastSelectedComponent;
            }
            final Container finalTmp = tmp;
            rmCurrentPopupItem.setAction(new AbstractAction(tmp.getName()) {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                Container tmp = getContainersWrapper(lastSelectedContainer);
                if (tmp == null) {
                  tmp = lastSelectedComponent;
                }
                if ( tmp instanceof AbstractBlock ) {
                  Container tmpPrnt = tmp.getParent();
                  tmpPrnt.remove(finalTmp);

                  lastSelectedComponent = (JComponent) tmpPrnt;
                  lastSelectedContainer = tmpPrnt;

                  lastSelectedComponent.revalidate();
                  lastSelectedComponent.repaint();
                  lastSelectedContainer.repaint();
                }

                listener.dashboardChanged((AbstractBlock) dashboardContainer);
              }
            });

	          if ( tmp != dashboardContainer && tmp != dashboardContainer.getInnerComponent() ) {

		          if ( ( addComponentPopup.getComponent( 0 ) instanceof JMenuItem ) &&
				               ( (JMenuItem) addComponentPopup.getComponent( 0 ) ).getText().contains( "Edit" ) )
//	          if ( addComponentPopup.getComponent( 0 ).getName().contains( "Edit" ) )
			          addComponentPopup.remove( 0 );
		          addComponentPopup.insert( new AbstractAction( "Edit : " + tmp.getName() ) {
			          @Override
			          public void actionPerformed ( ActionEvent actionEvent ) {
				          Container tmp = getContainersWrapper( lastSelectedContainer );
				          if ( tmp == null ) {
					          tmp = lastSelectedComponent;
				          }
				          if ( tmp instanceof AbstractBlock ) {

					          ControlProperties tmpProps = ( (AbstractBlock) tmp).getUiProperties(); //getDefaultProperties();
					          tmpProps.setPropertyValue("Top", String.valueOf( tmp.getLocation().getY() ) );// String.valueOf(location.getY()));
					          tmpProps.setPropertyValue("Left", String.valueOf( tmp.getLocation().getX() ) ); //String.valueOf(location.getX()));
					          tmpProps.setPropertyValue("Width", String.valueOf( tmp.getSize().getWidth() ) );// String.valueOf(location.getY()));
					          tmpProps.setPropertyValue("Height", String.valueOf( tmp.getSize().getHeight() ) ); //String.valueOf(location.getX()));
					          ( (AbstractBlock) tmp).setUiProperties(tmpProps);

					          ControlPropertiesDialog dialog = new ControlPropertiesDialog( (AbstractBlock) tmp, new AddComponentAction( "EditBlock" ) {
					          } );
				          }
			          }
		          }, 0 );
	          }

          }
          else {
	          rmCurrentPopupItem.setVisible( false );
          }
          addComponentPopup.show(lastSelectedComponent, (int) (me.getX() + componentPnt.getX()), (int) (me.getY() + componentPnt.getY()));
        }
      }

    };

    setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

    dashboardContainer = new ContainerBlock();
    configureDashboardContainer();

    resizableComponentsTree = new Tree<Component>(lastSelectedComponent);

	  JMenu tmpMenu = new JMenu( "Add : " );
	  JMenu tmp = new JMenu( "Native Controls" );
	  tmp.add(new AddComponentAction("ContainerBlock") { });
	  tmp.add(new AddComponentAction("ContainerSplitBlock") { });
	  tmp.add(new AddComponentAction("LabelBlock") { });
	  tmp.add(new AddComponentAction("LabelBlockIndicator") { });
	  tmp.add(new AddComponentAction("ButtonBlockComander") { });
	  tmp.add(new AddComponentAction("SliderBlockCommander") { });
	  tmp.add(new AddComponentAction("RepeatingBlockCommander") { });
	  tmpMenu.add( tmp );

	  tmp = new JMenu( "Dashboard Controls" );
	  tmp.add(new AddComponentAction("RadialGaugeBlockIndicator") { });
	  tmp.add(new AddComponentAction("DisplayCircularBlockIndicator") { });
	  tmp.add(new AddComponentAction("DisplayRectangularBlockIndicator") { });
	  tmp.add(new AddComponentAction("LinearBlockIndicator") { });
	  tmp.add(new AddComponentAction("LinearBargraphBlockIndicator") { });

	  tmp.add(new AddComponentAction("Radial1SquareBlockIndicator") { });
	  tmp.add(new AddComponentAction("Radial1VerticalBlockIndicator") { });
	  tmp.add(new AddComponentAction("Radial2TopBlockIndicator") { });
	  tmp.add(new AddComponentAction("RadialBargraphBlockIndicator") { });
	  tmp.add(new AddComponentAction("Graph2dPlotBlockIndicator") { });
	  tmpMenu.add( tmp );

	  tmp = new JMenu( "SCADA Blocks" );
	  tmp.add(new AddComponentAction("RadialGaugeBlockIndicator") { });
	  tmp.add(new AddComponentAction("Graph2dPlotBlockIndicator") { });
	  tmpMenu.add( tmp );

	  addComponentPopup.addSeparator();
    addComponentPopup.add( tmpMenu );
    addComponentPopup.addSeparator();
	  removeComponentMenu = new JMenu( "Remove : " );
	  rmCurrentPopupItem = removeComponentMenu.add("Current");
	  rmCurrentPopupItem.setVisible( false );
	  removeComponentMenu.add(new AbstractAction("All") {
		  @Override
		  public void actionPerformed(ActionEvent actionEvent) {
			  dashboardContainer.getInnerComponent().removeAll();

			  lastSelectedComponent = (JComponent) dashboardContainer;
			  lastSelectedContainer = dashboardContainer;

			  lastSelectedComponent.revalidate();
			  lastSelectedComponent.repaint();
			  lastSelectedContainer.repaint();

			  listener.dashboardChanged((AbstractBlock) dashboardContainer);
		  }
	  });

	  addComponentPopup.add( removeComponentMenu );

    setPreferredSize(new Dimension(500, 500));
    setSize(500, 500);

//	  startGraphs();
  }



  public DashboardEditorPanel(String editorPtojectName, IDashboarChangeListener listener) {
    this(editorPtojectName);
    this.setListener(listener);
  }

  public void saveDashboard(String profName) {
    try {
      FileOutputStream fileOut = new FileOutputStream(profName);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this.dashboardContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getComponentsTreeBounds(String key, Component c )
  {
    key = key + c.getName();
    String position = String.format( "%d,%d,%d,%d", c.getX(), c.getY(), c.getWidth(), c.getHeight() );
    String class_ = c.getClass().getCanonicalName().toString();
    System.out.println(" SAVE ["+class_+"] : key = [" + key + "] -- > " + position );
    c.addMouseListener(mouseHndlr);
	  if ( c instanceof AbstractBlock){
		  ((AbstractBlock)c).createInnerComponent();
		  ((AbstractBlock)c).applyControlProperties();
		  ((AbstractBlock)c).applyUiProperties();
	  }
	  if ( c instanceof IDashboardBlockIndicator )
		  MainForm.tableModel.addTableModelListener( (TableModelListener) c );
    resizableComponents.add(c);
    if ( c instanceof Container ) {
      key = key + "/";
      Container container = (Container) c;
      for ( Component child : container.getComponents() ) {
        getComponentsTreeBounds(key, child);
      }
    }
  }

  public void loadDashboard(String profName) {
    try {
      this.removeAll();
      resizableComponents.clear();

      FileInputStream fileIn = new FileInputStream(profName);
      ObjectInputStream in = new ObjectInputStream(fileIn);

      this.dashboardContainer = (ContainerBlock) in.readObject();
      AbstractBlock tmpFirstAbstract = null;
      for (Component cmp : this.dashboardContainer.getComponents()) {
        if (cmp instanceof AbstractBlock) {
          if ( null == tmpFirstAbstract )
            tmpFirstAbstract = (AbstractBlock) cmp;
          ResizableComponentMouseInputAdapter resizeListener = new ResizableComponentMouseInputAdapter((ResizableComponentWrapper) cmp);
          ((ResizableComponentWrapper) cmp).addMouseListener(resizeListener);
          ((ResizableComponentWrapper) cmp).addMouseMotionListener(resizeListener);


          if ( cmp instanceof ContainerSplitBlock ){
            ((ContainerSplitBlock)cmp).getRigthPane().addMouseListener(resizeListener);
            ((ContainerSplitBlock)cmp).getRigthPane().addMouseMotionListener(resizeListener);
            ((ContainerSplitBlock)cmp).getLeftPane().addMouseListener(resizeListener);
            ((ContainerSplitBlock)cmp).getLeftPane().addMouseMotionListener(resizeListener);
          } else {
            ((AbstractBlock)cmp).getInnerComponent().addMouseListener(resizeListener);
            ((AbstractBlock)cmp).getInnerComponent().addMouseMotionListener(resizeListener);
          }
        }
      }

      getComponentsTreeBounds("", this.dashboardContainer);

      lastSelectedComponent = ((DashboardEditorPanel)ME).dashboardContainer;
      lastSelectedContainer = lastSelectedComponent;

      setEditorPtojectName(profName.contains(".") ? profName.substring(0,profName.indexOf(".")) : profName);
      configureDashboardContainer();
      this.add(this.dashboardContainer, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

      listener.dashboardChanged(this.dashboardContainer);

    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }

  }

  AbstractBlock getContainersWrapper(Container container) {
    Container tmp = container;
    do {
      if (tmp instanceof AbstractBlock)
        return (AbstractBlock) tmp;
      tmp = tmp.getParent();
    } while (tmp != null);
    return null;
  }


  abstract class AddComponentAction extends AbstractAction implements IAddBlockDialogResults {

    private String Name;

    @Override
    public void GetResultsConfig ( AbstractBlock resultComponent, ControlProperties resultComponentProperties, boolean result ) {
      if ( result ) {
        System.out.println("resultComponentProperties : " + resultComponentProperties.toString() + "\n");
	      if ( resultComponent instanceof IDashboardBlockIndicator )
        System.out.println("resultComponentProperties : " + ((AbstractBlockIndicator)resultComponent).getControlProperties().toString() + "\n");

	      if ( resultComponent.isFirstConfigured() ) {
		      resultComponent.setConfigured();

		      resizableComponents.add( resultComponent );
		      if ( resultComponent instanceof ContainerSplitBlock ) {
			      resizableComponents.add( ( (JSplitPane) ( (ContainerSplitBlock) resultComponent ).getInnerComponent() ).getLeftComponent() );
			      resizableComponents.add( ( (JSplitPane) ( (ContainerSplitBlock) resultComponent ).getInnerComponent() ).getRightComponent() );
			      ( (JSplitPane) ( (ContainerSplitBlock) resultComponent ).getInnerComponent() ).getLeftComponent().addMouseListener( mouseHndlr ); //setLeftComponent(new JPanel(null));
			      ( (JSplitPane) ( (ContainerSplitBlock) resultComponent ).getInnerComponent() ).getRightComponent().addMouseListener( mouseHndlr ); //setRightComponent(new JPanel(null));
		      } else {
			      resultComponent.getInnerComponent().addMouseListener( mouseHndlr );
			      resizableComponents.add( resultComponent.getInnerComponent() );
		      }


		      resultComponent.addMouseListener( mouseHndlr );

		      Container tmp = getContainersWrapper( lastSelectedContainer );
		      if ( tmp == null ) {
			      tmp = lastSelectedComponent;
		      }

		      resultComponent.setComponentParrentNode( new DefaultMutableTreeNode( tmp ) );

		      lastSelectedContainer.add( resultComponent, 0 );

	      }

	      resultComponent.setUiProperties( resultComponentProperties );
	      resultComponent.applyUiProperties();
	      resultComponent.applyControlProperties();

        lastSelectedComponent.repaint();
        resultComponent.invalidate();
        lastSelectedComponent.revalidate();
        lastSelectedComponent.repaint();
        lastSelectedContainer.repaint();


        lastSelectedComponent = (JComponent) resultComponent;
        listener.dashboardChanged(resultComponent);
      } else
        System.out.println("resultComponentProperties = [ CANCELED ! ]");
    }

    public AddComponentAction(String name) {
      super(name);
      Name = name;
    }


    public void actionPerformed(ActionEvent ae) {
      Class<?> klazz = null;
      Object cmp = null;
	    AbstractBlock newComponentBlock = null;
      String componentName = "xorDashboardDesktop.ui.DashboardEditor.Blocks." + this.Name;

      try {
        klazz = Class.forName(componentName);

//	      Class.
        cmp = klazz.newInstance();
        if (cmp instanceof AbstractBlock)
          newComponentBlock = (AbstractBlock) cmp;
      } catch (Exception e) {
        e.printStackTrace();
      }

      ControlProperties tmp = newComponentBlock.getDefaultProperties();
      tmp.setPropertyValue("Top", String.valueOf(location.getY()));
      tmp.setPropertyValue("Left", String.valueOf(location.getX()));
      newComponentBlock.setUiProperties(tmp);
      ControlPropertiesDialog dialog = new ControlPropertiesDialog(newComponentBlock, this);

//      dialog.pack();
//      dialog.setVisible(true);
//	    Color.decode(  )
    }
  }


  private void updateContainer(Component cmp) {
    if ((null != cmp) && (resizableComponents.indexOf(cmp) >= 0)) {
      lastSelectedComponent = (JComponent) cmp;
    } else
      lastSelectedComponent = ((DashboardEditorPanel)ME).dashboardContainer;

    if ((null != lastSelectedComponent) && (lastSelectedComponent instanceof Container))
      lastSelectedContainer = (Container) lastSelectedComponent;

    System.out.println("updateContainer -> [" + lastSelectedContainer + "]");
    System.out.println("updateComponent -> [" + lastSelectedComponent + "]\n");
  }






  @Override
  public DefaultMutableTreeNode getComponentNode() {
    return dashboardContainer.getComponentNode();
  }

  @Override
  public void setComponentNode(DefaultMutableTreeNode node) {
    dashboardContainer.setComponentNode(node);
  }

  @Override
  public DefaultMutableTreeNode getComponentParrentNode() {
    return dashboardContainer.getComponentNode();
  }

  @Override
  public void setComponentParrentNode(DefaultMutableTreeNode node) {
    dashboardContainer.setComponentParrentNode(node);
  }
}
