package xorDashboardDesktop.ui.DashboardEditor.FileBrowserTree; /*
*
 * Created by orb1t_ua on 01.10.16.
 */

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.Serializable;

public final class FileBrowserTree extends JTree implements Serializable{
  private DefaultMutableTreeNode root;
  private DefaultTreeModel treeModel;

  private String mask;
  private File fileRoot;
  FileNode node;

  public static final FileBrowserTree INSTANCE = new FileBrowserTree("./", ".dash");


  public class UpdateContentNodes  implements Serializable, Runnable {

    private DefaultMutableTreeNode root;

    private File fileRoot;

    private String mask;

    public UpdateContentNodes(String msk, File fileRoot,
                              DefaultMutableTreeNode root) {
      this.mask = msk;
      this.fileRoot = fileRoot;
      this.root = root;
    }

    @Override
    public void run() {
      updateContent(fileRoot, root);
    }

    public void updateContent(File fileRoot,
                              DefaultMutableTreeNode node) {
      File[] files = fileRoot.listFiles();
      if (files == null) return;

      for (File file : files) {
        if (file.getName().contains(this.mask)) {
          DefaultMutableTreeNode childNode =
              new DefaultMutableTreeNode(new FileNode(file));
          node.add(childNode);
          if (file.isDirectory()) {
            updateContent(file, childNode);
          }
        }
      }
    }


  }

  public class FileNode  implements Serializable {

    private File file;

    public FileNode(File file) {
      this.file = file;
    }

    @Override
    public String toString() {
      String name = file.getName();
      if (name.equals("")) {
        return file.getAbsolutePath();
      } else {
        return name;
      }
    }
  }

  public void setRoot ( String path, String msk ){
    this.mask = msk;
    fileRoot = new File(path);
    node = new FileNode(fileRoot);
    root = new DefaultMutableTreeNode(node);
    treeModel = new DefaultTreeModel(root);

    this.setModel(treeModel);
    this.setShowsRootHandles(true);

    this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.setShowsRootHandles(true);

    updateContent();
  }

  private FileBrowserTree ( String rootPath, String msk ){
    super();
    this.setRoot(rootPath, msk);
  }



  public void updateContent(){
    UpdateContentNodes ccn =
        new UpdateContentNodes(this.mask, fileRoot, root);
    root.removeAllChildren();
    ccn.updateContent(fileRoot, root);
    treeModel.reload();
  }

}
