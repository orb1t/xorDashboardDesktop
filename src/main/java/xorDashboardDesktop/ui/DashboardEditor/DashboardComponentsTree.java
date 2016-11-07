package xorDashboardDesktop.ui.DashboardEditor; /**
 * Created by orb1t_ua on 01.10.16.
 */
//import xorDashboardDesktop.ui.DashboardEditor.FileBrowserTree.FileNode;
//import xorDashboardDesktop.ui.DashboardEditor.FileBrowserTree.UpdateContentNodes;

import xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import xorDashboardDesktop.ui.DashboardEditor.Panel.DashboardEditorPanel;
import xorDashboardDesktop.ui.DashboardEditor.Panel.IDashboarChangeListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public final class  DashboardComponentsTree extends JTree implements IDashboarChangeListener {
  public DefaultMutableTreeNode getTreeNode() {
    return treeNode;
  }

  public void setTreeNode(DefaultMutableTreeNode treeNode) {
    this.treeNode = treeNode;
  }

  public DefaultMutableTreeNode getTreeRoot() {
    return treeRoot;
  }

  public void setTreeRoot(DefaultMutableTreeNode treeRoot) {
    this.treeRoot = treeRoot;
  }

//  public DefaultMutableTreeNode getTreeProject() {
//    return treeProject;
//  }
//
//  public void setTreeProject(DefaultMutableTreeNode treeProject) {
//    this.treeProject = treeProject;
//  }

  private DefaultMutableTreeNode treeNode;
  private DefaultMutableTreeNode treeRoot;

//  private DefaultMutableTreeNode treeProject;
  private DefaultTreeModel treeModel;

  private DashboardEditorPanel editorPanel = null;

  public static DashboardComponentsTree INSTANCE = new DashboardComponentsTree("default", (DashboardEditorPanel) DashboardEditorPanel.ME);


//  public JTree getTree() { return tree; }

  public void update(String editorPtojectName, DashboardEditorPanel rootPane) {
    editorPanel = rootPane;
    INSTANCE = new DashboardComponentsTree(editorPtojectName, rootPane);
  }

  private DashboardComponentsTree(String editorPtojectName, DashboardEditorPanel root) {
    super();
    editorPanel = root;

//    treeProject = new DefaultMutableTreeNode(editorPtojectName);//
//    dashboardEditorPanel.rootNode = treeNode;
//    treeNode = treeRoot;

//    treeRoot = new DefaultMutableTreeNode(editorPtojectName);//root);
    treeRoot = new DefaultMutableTreeNode(root.dashboardContainer);
    this.rebuildTree("", treeRoot, root.dashboardContainer);
    if ( treeRoot.getChildCount() >=1 )
      treeRoot = treeRoot.getNextNode();
//    root.rootNode = treeNode;
    DashboardEditorPanel.dashboardContainer.setComponentParrentNode(treeRoot);// = treeNode;
    DashboardEditorPanel.dashboardContainer.setComponentNode(treeRoot);// = treeNode;
    treeNode = treeRoot;

//    treeProject.add(treeRoot);


//    tree = new JTree(treeModel);
//    treeModel = new DefaultTreeModel(treeProject);
    treeModel = new DefaultTreeModel(treeRoot);//.getChildAt(0));
    this.setModel(treeModel);
//    this.set
//    this.setShowsRootHandles(true);

    this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setShowsRootHandles(false);
//    this.rootVisible = false;
    this.setRootVisible(false);

//    updateContent();
  }


  public void updateContent() {
//    if ( null != treeProject) {
    if ( null != treeRoot) {
//      treeProject.removeAllChildren();
      treeRoot.removeAllChildren();
      treeModel.reload();
    }
  }

  private void rebuildTree(String key,DefaultMutableTreeNode root, Component c )
  {
    key = key + c.getName();
    String position = String.format( "%d,%d,%d,%d", c.getX(), c.getY(), c.getWidth(), c.getHeight() );
    String class_ = c.getClass().getCanonicalName().toString();// getName().toString();
    System.out.println(" SAVE ["+class_+"] : key = [" + key + "] -- > " + position );


    DefaultMutableTreeNode tmpRoot = root;
//    if ( null == root && c instanceof AbstractBlock)
//      root = new DefaultMutableTreeNode(c);
//
//    tmpRoot = root;

    if ( c instanceof AbstractBlock) {
      tmpRoot = new DefaultMutableTreeNode(c);

//    else
//      tmpRoot = root;
    }

    if ( c instanceof Container ) {
      key = key + "/";
      Container container = (Container) c;
      for ( Component child : container.getComponents() ) {
        rebuildTree(key, tmpRoot, child);
      }
    }
//    if ( ! tmpRoot.getUserObject().toString().equals ( "DashboardEditorPanel" ) && ! root.equals ( tmpRoot ) )
    if ( null == root ){
      root = tmpRoot;
    } else
    if ( ! root.equals ( tmpRoot ) )
      root.add(tmpRoot);
    else
      root = tmpRoot;
//    root = new DefaultMutableTreeNode();
////    root.getRoot().
//    root = tmpRoot;
  }

  @Override
  public void dashboardChanged(AbstractBlock component) {
    treeRoot.removeAllChildren();



//    treeRoot = new DefaultMutableTreeNode();
    DefaultMutableTreeNode tmp = (DefaultMutableTreeNode) treeRoot.clone();
    this.rebuildTree("", tmp, editorPanel.dashboardContainer);
    treeRoot = (DefaultMutableTreeNode) tmp.getChildAt(0);// getFirstLeaf();
    treeRoot.setParent(null);
//    this.setShowsRootHandles(true);
//    this.setModel(null);
//    this.setTreeRoot(treeRoot);
//    if ( treeRoot.getChildCount() >=1 )
//      treeRoot = treeRoot.getNextNode();

////    treeRoot = new DefaultMutableTreeNode(editorPanel.dashboardContainer);
////    root.rootNode = treeNode;
//    DashboardEditorPanel.dashboardContainer.setComponentParrentNode(treeRoot);// = treeNode;
//    DashboardEditorPanel.dashboardContainer.setComponentNode(treeRoot);// = treeNode;
//    treeNode = treeRoot;
//
////    treeProject.add(treeRoot);
//
//    //    tree = new JTree(treeModel);
////    treeModel = new DefaultTreeModel(treeProject);
    treeModel = new DefaultTreeModel(treeRoot);
    this.setModel(null);
    this.setModel(treeModel);

//    if ( treeRoot.getChildCount() >= 1 ) {
//      MutableTreeNode tmpNod = (MutableTreeNode) treeRoot.getChildAt(1);
//      treeRoot.remove(0);
//      treeRoot.add(tmpNod);
//    }


/*
//    if (newComponent instanceof AbstractBlock) {
//      AbstractBlock tmp = (AbstractBlock) newComponent;

      DefaultMutableTreeNode tmpRoot = null;
      int idx = 0;//
      if (component.getComponentParrentNode() == null) {
        tmpRoot = treeRoot;
        component.setComponentParrentNode(treeRoot);
      }
//      else {
        tmpRoot = component.getComponentParrentNode();
        idx = treeRoot.getIndex(tmpRoot);
//      }



      if ( idx < 0 ) {
//        tmpRoot.add(component.getComponentNode());
        treeRoot.add(component.getComponentNode());
      } else {
        treeRoot.remove(idx);

//        treeNode.add(component.getComponentNode());
        tmpRoot.add(component.getComponentNode());

//      treeRoot.add(treeNode);
//        treeRoot.insert(treeNode, idx - 1 );//(idx > 0 ? idx - 1 : 0));
        if ( idx == 1 )
          treeRoot.add(tmpRoot);
        else
          treeRoot.insert(tmpRoot, idx - 1 );//(idx > 0 ? idx - 1 : 0));
      }

//      treeModel.reload(treeRoot);

      this.expandRow( treeRoot.getIndex(component.getComponentNode()) );//treeNode.getIndex(tmp.getComponentNode()));
//    }
*/

    this.revalidate();
    this.updateUI();

    System.out.println("newComponent = [" + component.getWidth() + ", " + component.getHeight() + "]");
  }

//  @Override
//  public void dashboardChanged(AbstractBlock newComponent) {
//
//  }
}
