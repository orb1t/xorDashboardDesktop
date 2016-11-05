package orTestStandDesktop.ui.DashboardEditor.Panel;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @param <T> Object's type in the tree.
 * @author ycoppel@google.com (Yohann Coppel)
 */
public class Tree<T> implements Serializable {

  private T head;

  private ArrayList<Tree<T>> leafs = new ArrayList<Tree<T>>();

  private Tree<T> parent = null;

  private HashMap<T, Tree<T>> locate = new HashMap<T, Tree<T>>();


  public static void main(String[] args) {
//    JFrame frm = new JFrame();


    Tree<String> cTree = new Tree<String>("root");

    cTree.addLeaf("l00");
    cTree.addLeaf("l01");
    cTree.addLeaf("l02");

    cTree.addLeaf("l01", "l10");
    cTree.addLeaf("l02", "l11");

    cTree.addLeaf("l02", "l20");
    cTree.addLeaf("l11", "l21");

    System.out.println(cTree.printTree(3));
    System.exit(0);
  }


  public Tree(T head) {
    this.head = head;
    locate.put(head, this);
  }

  public void addLeaf(T root, T leaf) {
    if (locate.containsKey(root)) {
      locate.get(root).addLeaf(leaf);
    } else {
      addLeaf(root).addLeaf(leaf);
    }
  }

  public Tree<T> addLeaf(T leaf) {
    Tree<T> t = new Tree<T>(leaf);
    leafs.add(t);
    t.parent = this;
    t.locate = this.locate;
    locate.put(leaf, t);
    return t;
  }

  public Tree<T> setAsParent(T parentRoot) {
    Tree<T> t = new Tree<T>(parentRoot);
    t.leafs.add(this);
    this.parent = t;
    t.locate = this.locate;
    t.locate.put(head, this);
    t.locate.put(parentRoot, t);
    return t;
  }

  public T getHead() {
    return head;
  }

  public Tree<T> getTree(T element) {
    return locate.get(element);
  }

  public Tree<T> getParent() {
    return parent;
  }

  public Collection<T> getSuccessors(T root) {
    Collection<T> successors = new ArrayList<T>();
    Tree<T> tree = getTree(root);
    if (null != tree) {
      for (Tree<T> leaf : tree.leafs) {
        successors.add(leaf.head);
      }
    }
    return successors;
  }

  public Collection<Tree<T>> getSubTrees() {
    return leafs;
  }

  public static <T> Collection<T> getSuccessors(T of, Collection<Tree<T>> in) {
    for (Tree<T> tree : in) {
      if (tree.locate.containsKey(of)) {
        return tree.getSuccessors(of);
      }
    }
    return new ArrayList<T>();
  }

  @Override
  public String toString() {
    return printTree(5);
  }

  private static final int indent = 2;

  public String printTree(int increment) {
    String s = "";
    String inc = "";
    for (int i = 0; i < increment; ++i) {
      inc = inc + " ";
    }
    s = inc + head;
    for (Tree<T> child : leafs) {
      s += "\n" + child.printTree(increment + indent);
    }
    return s;
  }


  public void populateJTree(DefaultTreeModel tree, DefaultMutableTreeNode top) {
    String s = head.getClass().getSimpleName().toString() + " : " + ((Component) head).getName();
    DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(s);

    int tmpIdx = tree.getIndexOfChild(top, treeNode);
    if (tmpIdx == -1) {
      top.add(treeNode);
    }

    for (Tree<T> child : leafs) {
      child.populateJTree(tree, treeNode); //printTree(0);
    }
  }
}
