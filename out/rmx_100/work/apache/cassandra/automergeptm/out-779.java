package org.apache.cassandra.utils.btree;
import java.util.Comparator;
import static org.apache.cassandra.utils.btree.BTree.getBranchKeyEnd;
import static org.apache.cassandra.utils.btree.BTree.getKeyEnd;
import static org.apache.cassandra.utils.btree.BTree.getLeafKeyEnd;
import static org.apache.cassandra.utils.btree.BTree.isLeaf;
import static org.apache.cassandra.utils.btree.BTree.NEGATIVE_INFINITY;
import static org.apache.cassandra.utils.btree.BTree.POSITIVE_INFINITY;

/**
 * An internal class for searching and iterating through a tree.  As it traverses the tree,
 * it adds the nodes visited to a stack.  This allows us to backtrack from a child node
 * to its parent.
 *
 * As we navigate the tree, we destructively modify this stack.
 *
 * Path is only intended to be used via Cursor.
 */
public class Path<V extends java.lang.Object> {
  static enum Op {
    CEIL,
    FLOOR,
    HIGHER,
    LOWER
  }

  Object[][] path;

  byte[] indexes;

  byte depth;

  Path() {
  }

  Path(int depth, Object[] btree) {
    this.path = new Object[depth][];
    this.indexes = new byte[depth];
    this.path[0] = btree;
  }

  void init(Object[] btree) {
    int depth = BTree.depth(btree);
    if (path == null || path.length < depth) {
      path = new Object[depth][];
      indexes = new byte[depth];
    }
    path[0] = btree;
  }

  /**
     * Find the provided key in the tree rooted at node, and store the root to it in the path
     *
     * @param comparator the comparator defining the order on the tree
     * @param target     the key to search for
     * @param mode       the type of search to perform
     * @param forwards   if the path should be setup for forward or backward iteration
     * @param <V>
     */
  <V extends java.lang.Object> boolean find(Comparator<V> comparator, Object target, Op mode, boolean forwards) {
    Object[] node = path[depth];
    int lb = indexes[depth];
    assert lb == 0 || forwards;
    pop();
    while (true) {
      int keyEnd = getKeyEnd(node);
      int i = BTree.find(comparator, target, node, lb, keyEnd);
      lb = 0;
      if (i >= 0) {
        push(node, i);
        switch (mode) {
          case HIGHER:
          successor();
          break;
          case LOWER:
          predecessor();
        }
        return true;
      }
      if (!isLeaf(node)) {
        i = -i - 1;
        push(node, forwards ? i - 1 : i);
        node = (Object[]) node[keyEnd + i];
        continue;
      }
      i = -i - 1;
      switch (mode) {
        case FLOOR:
        case LOWER:
        i--;
      }
      if (i < 0) {
        push(node, 0);
        predecessor();
      } else {
        if (i >= keyEnd) {
          push(node, keyEnd - 1);
          successor();
        } else {
          push(node, i);
        }
      }
      return false;
    }
  }

  boolean isRoot() {
    return depth == 0;
  }

  void pop() {
    depth--;
  }

  Object[] currentNode() {
    return path[depth];
  }

  byte currentIndex() {
    return indexes[depth];
  }

  void push(Object[] node, int index) {
    path[++depth] = node;
    indexes[depth] = (byte) index;
  }

  void setIndex(int index) {
    indexes[depth] = (byte) index;
  }

  byte findSuccessorParentDepth() {
    byte depth = this.depth;
    depth--;
    while (depth >= 0) {
      int ub = indexes[depth] + 1;
      Object[] node = path[depth];
      if (ub < getBranchKeyEnd(node)) {
        return depth;
      }
      depth--;
    }
    return -1;
  }

  void successor() {
    Object[] node = currentNode();
    int i = currentIndex();
    if (!isLeaf(node)) {
      node = (Object[]) node[getBranchKeyEnd(node) + i + 1];
      while (!isLeaf(node)) {
        push(node, -1);
        node = (Object[]) node[getBranchKeyEnd(node)];
      }
      push(node, 0);
      return;
    }
    i += 1;
    if (i < getLeafKeyEnd(node)) {
      setIndex(i);
      return;
    }
    while (!isRoot()) {
      pop();
      i = currentIndex() + 1;
      node = currentNode();
      if (i < getKeyEnd(node)) {
        setIndex(i);
        return;
      }
    }
    setIndex(getKeyEnd(node));
  }

  void predecessor() {
    Object[] node = currentNode();
    int i = currentIndex();
    if (!isLeaf(node)) {
      node = (Object[]) node[getBranchKeyEnd(node) + i];
      while (!isLeaf(node)) {
        i = getBranchKeyEnd(node);
        push(node, i);
        node = (Object[]) node[i * 2];
      }
      push(node, getLeafKeyEnd(node) - 1);
      return;
    }
    i -= 1;
    if (i >= 0) {
      setIndex(i);
      return;
    }
    while (!isRoot()) {
      pop();
      i = currentIndex() - 1;
      if (i >= 0) {
        setIndex(i);
        return;
      }
    }
    setIndex(-1);
  }

  Object currentKey() {
    return currentNode()[currentIndex()];
  }

  int compareTo(Path that, boolean forwards) {
    int d = Math.min(this.depth, that.depth);
    for (int i = 0; i <= d; i++) {
      int c = this.indexes[i] - that.indexes[i];
      if (c != 0) {
        return c;
      }
    }
    d = this.depth - that.depth;
    return forwards ? d : -d;
  }
}


<<<<<<< Unknown file: This is a bug in JDime.
=======
/**
 * An internal class for searching and iterating through a tree.  As it traverses the tree,
 * it adds the nodes visited to a stack.  This allows us to backtrack from a child node
 * to its parent.
 *
 * As we navigate the tree, we destructively modify this stack.
 *
 * Path is only intended to be used via Cursor.
 */
class Path {
  static enum Op {
    CEIL,
    FLOOR,
    HIGHER,
    LOWER
  }

  Object[][] path;

  byte[] indexes;

  byte depth;

  Path() {
  }

  Path(int depth) {
    this.path = new Object[depth][];
    this.indexes = new byte[depth];
  }

  void ensureDepth(Object[] btree) {
    int depth = BTree.depth(btree);
    if (path == null || path.length < depth) {
      path = new Object[depth][];
      indexes = new byte[depth];
    }
  }

  void moveEnd(Object[] node, boolean forwards) {
    push(node, getKeyEnd(node));
    if (!forwards) {
      predecessor();
    }
  }

  void moveStart(Object[] node, boolean forwards) {
    push(node, -1);
    if (forwards) {
      successor();
    }
  }

  /**
     * Find the provided key in the tree rooted at node, and store the root to it in the path
     *
     * @param node       the tree to search in
     * @param comparator the comparator defining the order on the tree
     * @param target     the key to search for
     * @param mode       the type of search to perform
     * @param forwards   if the path should be setup for forward or backward iteration
     * @param <V>
     */
  <V extends java.lang.Object> void find(Object[] node, Comparator<V> comparator, Object target, Op mode, boolean forwards) {
    depth = -1;
    if (target instanceof BTree.Special) {
      if (target == POSITIVE_INFINITY) {
        moveEnd(node, forwards);
      } else {
        if (target == NEGATIVE_INFINITY) {
          moveStart(node, forwards);
        } else {
          throw new AssertionError();
        }
      }
      return;
    }
    while (true) {
      int keyEnd = getKeyEnd(node);
      int i = BTree.find(comparator, target, node, 0, keyEnd);
      if (i >= 0) {
        push(node, i);
        switch (mode) {
          case HIGHER:
          successor();
          break;
          case LOWER:
          predecessor();
        }
        return;
      }
      i = -i - 1;
      if (!isLeaf(node)) {
        push(node, forwards ? i - 1 : i);
        node = (Object[]) node[keyEnd + i];
        continue;
      }
      switch (mode) {
        case FLOOR:
        case LOWER:
        i--;
      }
      if (i < 0) {
        push(node, 0);
        predecessor();
      } else {
        if (i >= keyEnd) {
          push(node, keyEnd - 1);
          successor();
        } else {
          push(node, i);
        }
      }
      return;
    }
  }

  private boolean isRoot() {
    return depth == 0;
  }

  private void pop() {
    depth--;
  }

  Object[] currentNode() {
    return path[depth];
  }

  byte currentIndex() {
    return indexes[depth];
  }

  private void push(Object[] node, int index) {
    path[++depth] = node;
    indexes[depth] = (byte) index;
  }

  void setIndex(int index) {
    indexes[depth] = (byte) index;
  }

  void successor() {
    Object[] node = currentNode();
    int i = currentIndex();
    if (!isLeaf(node)) {
      node = (Object[]) node[getBranchKeyEnd(node) + i + 1];
      while (!isLeaf(node)) {
        push(node, -1);
        node = (Object[]) node[getBranchKeyEnd(node)];
      }
      push(node, 0);
      return;
    }
    i += 1;
    if (i < getLeafKeyEnd(node)) {
      setIndex(i);
      return;
    }
    while (!isRoot()) {
      pop();
      i = currentIndex() + 1;
      node = currentNode();
      if (i < getKeyEnd(node)) {
        setIndex(i);
        return;
      }
    }
    setIndex(getKeyEnd(node));
  }

  void predecessor() {
    Object[] node = currentNode();
    int i = currentIndex();
    if (!isLeaf(node)) {
      node = (Object[]) node[getBranchKeyEnd(node) + i];
      while (!isLeaf(node)) {
        i = getBranchKeyEnd(node);
        push(node, i);
        node = (Object[]) node[i * 2];
      }
      push(node, getLeafKeyEnd(node) - 1);
      return;
    }
    i -= 1;
    if (i >= 0) {
      setIndex(i);
      return;
    }
    while (!isRoot()) {
      pop();
      i = currentIndex() - 1;
      if (i >= 0) {
        setIndex(i);
        return;
      }
    }
    setIndex(-1);
  }

  Object currentKey() {
    return currentNode()[currentIndex()];
  }

  int compareTo(Path that, boolean forwards) {
    int d = Math.min(this.depth, that.depth);
    for (int i = 0; i <= d; i++) {
      int c = this.indexes[i] - that.indexes[i];
      if (c != 0) {
        return c;
      }
    }
    d = this.depth - that.depth;
    return forwards ? d : -d;
  }
}
>>>>>>> commits-rmx_100/apache/cassandra/acf1b1801352cdc6103325ff4498c2020dcc1c32/Path-51207ba.java
