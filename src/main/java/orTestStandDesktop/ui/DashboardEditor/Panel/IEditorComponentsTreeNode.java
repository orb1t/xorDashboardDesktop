package orTestStandDesktop.ui.DashboardEditor.Panel;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by orb1t_ua on 12.10.16.
 */
public interface IEditorComponentsTreeNode {
  public DefaultMutableTreeNode getComponentNode ();

  public void setComponentNode ( DefaultMutableTreeNode node );

  public DefaultMutableTreeNode getComponentParrentNode ();

  public void setComponentParrentNode ( DefaultMutableTreeNode node );
}
