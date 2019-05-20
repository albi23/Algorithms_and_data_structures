package splay;

import TreeInterfaces.Tree;

import java.util.Stack;

public class SplayTree<T extends Comparable<T>> implements Tree<T> {

    private SplayNode<T> root;
    private long size = 0;
    private long timeAdd = 0;
    private long timeDel = 0;
    private long timeSearch = 0;
    private long comparision = 0;
    private long modifications = 0;


    public SplayTree() {
        root = null;
    }


    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public void insert(T key) {

        long time = System.nanoTime();

        comparision++;
        if (key == null) return;
        SplayNode<T> z = root;
        SplayNode<T> p = null;
        while (z != null) {
            p = z;
            // if z.key is < than the current key, go left
            comparision++;
            if (key.compareTo(p.key) > 0) {
                z = z.right;
            } else {
                comparision++;
                z = z.left;
            }
        }
        modifications += 3;
        z = new SplayNode<>();
        z.key = key;
        z.parent = p;
        comparision++;
        if (p == null) {
            modifications++;
            root = z;
        } else if (key.compareTo(p.key) > 0) {
            comparision++;
            modifications++;
            p.right = z;
        } else {
            modifications++;
            comparision += 2;
            p.left = z;
        }
        Splay(z);
        size++;

        long finishTime = System.nanoTime();

        timeAdd += (finishTime - time);
    }


    public void makeLeftChildParent(SplayNode<T> c, SplayNode<T> p) {
        comparision++;
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
            throw new RuntimeException("WRONG");

        comparision++;
        if (p.parent != null) {
            comparision++;
            if (p == p.parent.left) {
                modifications++;
                p.parent.left = c;
            } else {
                comparision++;
                modifications++;
                p.parent.right = c;
            }
        }
        comparision++;
        if (c.right != null) {
            modifications++;
            c.right.parent = p;
        }

        modifications += 4;
        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    public void makeRightChildParent(SplayNode c, SplayNode p) {
        comparision++;
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
            throw new RuntimeException("WRONG");

        comparision++;
        if (p.parent != null) {
            comparision++;
            if (p == p.parent.left) {
                modifications++;
                p.parent.left = c;
            } else {
                modifications++;
                comparision++;
                p.parent.right = c;
            }
        }
        comparision++;
        if (c.left != null) {
            modifications++;
            c.left.parent = p;
        }
        modifications += 4;
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }


    private void Splay(SplayNode<T> x) {

        while (x.parent != null) {
            comparision++;
            SplayNode<T> Parent = x.parent;
            SplayNode<T> GrandParent = Parent.parent;
            if (GrandParent == null) {
                comparision++;
                if (x == Parent.left) {
                    makeLeftChildParent(x, Parent);
                } else {
                    comparision++;
                    makeRightChildParent(x, Parent);
                }
            } else {
                comparision++;
                if (x == Parent.left) {
                    comparision++;
                    if (Parent == GrandParent.left) {
                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    } else {
                        comparision++;
                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                } else {
                    comparision++;
                    if (Parent == GrandParent.left) {
                        comparision++;
                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    } else {
                        comparision++;
                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        modifications++;
        root = x;
    }


    public void delete(T key) {
        SplayNode<T> node = findNode(key);
        delete(node);
    }


    private void delete(SplayNode<T> node) {

        long start = System.nanoTime();
        comparision++;
        if (node == null)
            return;

        Splay(node);
        comparision++;
        if ((node.left != null) && (node.right != null)) {
            SplayNode<T> min = node.left;
            while (min.right != null) {
                comparision++;
                min = min.right;
            }

            modifications += 4;
            min.right = node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
        } else if (node.right != null) {
            comparision++;
            modifications++;
            node.right.parent = null;
            root = node.right;
        } else if (node.left != null) {
            comparision += 2;
            modifications += 2;

            node.left.parent = null;
            root = node.left;
        } else {
            comparision += 3;
            modifications++;
            root = null;
        }
        modifications += 3;
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        size--;

        timeDel += (System.nanoTime() - start);
    }


    public int search(T key) {

        long start = System.nanoTime();
        SplayNode<T> result = findNode(key);
        timeSearch += (System.nanoTime() - start);

        return (result != null) ? 1 : 0;
    }


    private SplayNode<T> findNode(T key) {

        SplayNode<T> PrevNode = null;
        SplayNode<T> z = root;
        comparision++;
        while (z != null) {
            comparision++;
            PrevNode = z;
            if (key.compareTo(z.key) > 0) {
                comparision++;
                z = z.right;
            } else if (key.compareTo(z.key) < 0) {
                comparision++;
                z = z.left;
            } else if (key.compareTo(z.key) == 0) {
                comparision++;
                Splay(z);
                return z;
            }

        }
        comparision++;
        if (PrevNode != null) {
            Splay(PrevNode);
            return null;
        }
        return null;
    }


    public void inorder() {
        inorder(root);
        System.out.println();
    }


    private void inorder(SplayNode<T> node) {
        Stack<SplayNode> nodes = new Stack<>();
        nodes.push(node);
        while (!nodes.isEmpty()) {

            SplayNode<T> top = nodes.pop();

            if (top.left != null) nodes.push(top.left);
            System.out.print(" " + top.key);
            if (top.right != null) nodes.push(top.right);
        }
    }

    public void print() {

        if (root.key == null) return;
        System.out.println(root.key);
        printRecursive(root);
    }

    private void printRecursive(SplayNode<T> node) {

        if (node.key != null) {

            if (node.left != null)
                System.out.print((node.right == null) ? "[" + node.key + "] L: " + node.left.key + "\n" : "[" + node.key + "] L: " + node.left.key);
            if (node.right != null)
                System.out.print((node.left == null) ? "[" + node.key + "] P: " + node.right.key + "\n" : "  <--> [" + node.key + "] P: " + node.right.key + "\n");

            if (node.left != null) printRecursive(node.left);
            if (node.right != null) printRecursive(node.right);
        }
    }

    public Tree getEmptyStructure() {
        return new SplayTree();
    }

    @Override
    public long getTimeAdd() {
        return this.timeAdd;
    }

    @Override
    public long getTimeDel() {
        return this.timeDel;
    }

    @Override
    public long getTimeSearch() {
        return this.timeSearch;
    }

    public long getComparision() {
        return this.comparision;
    }

    public long getModifications() {
        return this.modifications;
    }

    public long getSize() {
        return this.size;
    }
}