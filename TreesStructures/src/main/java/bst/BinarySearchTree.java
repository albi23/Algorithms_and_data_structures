package bst;

import TreeInterfaces.Tree;

import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {

    private BinarySearchNode<T> root;
    private long timeAdd = 0;
    private long size = 0;
    private long timeDel = 0;
    private long timeSearch = 0;
    private long comparision = 0;
    private long modifications = 0;

    public BinarySearchTree() {
        root = null;
    }


    public void delete(T key) {

        long start = System.nanoTime();
        root = deleteRec(root, key);
        timeDel += (System.nanoTime() - start);
    }

    BinarySearchNode<T> deleteRec(BinarySearchNode<T> root, T key) {
        comparision++;
        if (root == null) return root;

        size--;
        comparision++;
        /* Otherwise, recur down the tree */
        if (key.compareTo(root.key) < 0) {
            modifications++;
            root.left = deleteRec(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            modifications++;
            comparision++;
            root.right = deleteRec(root.right, key);
        } else {
            comparision += 2;
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            modifications += 2;
            root.key = (T) minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }


    T minValue(BinarySearchNode<T> root) {
        T minv = root.key;
        while (root.left != null) {
            comparision++;
            minv = (T) root.left.key;
            root = root.left;
        }
        return minv;
    }

    private boolean search2(T toFind) {

        comparision++;
        if (toFind == null) return false;
        BinarySearchNode<T> currentNode = root;

        while (currentNode != null) {

            comparision++;
            if (toFind.compareTo(currentNode.key) < 0) {
                currentNode = currentNode.left;
            } else if (toFind.compareTo(currentNode.key) > 0) {
                comparision++;
                currentNode = currentNode.right;
            } else {
                comparision += 2;
                return true;
            }
        }
        return false;
    }

    public int search(T toFind) {
        long start = System.nanoTime();
        search2(toFind);
        timeSearch += (System.nanoTime() - start);
        return 1;
    }

    public void insert(T key) {
        comparision++;
        if (key == null) return;
        size++;
        insertRec(key);
    }

    private void insertRec(T key) {

        long time = System.nanoTime();


        comparision++;
        if (this.root == null) {
            modifications++;
            this.root = new BinarySearchNode<T>(key);
            return;
        }

        BinarySearchNode<T> parentNode = null;
        BinarySearchNode<T> currentNode = this.root;
        while (currentNode != null) {
            comparision++;
            parentNode = currentNode;
            if (key.compareTo(currentNode.key) >= 0) {
                currentNode = currentNode.right;
            } else {
                comparision += 2;
                currentNode = currentNode.left;
            }
        }

        comparision++;
        if (key.compareTo(parentNode.key) >= 0) {
            modifications++;
            parentNode.right = new BinarySearchNode<>(key);
        } else {
            modifications++;
            comparision++;
            parentNode.left = new BinarySearchNode<>(key);
        }
        long finishTime = System.nanoTime();

        timeAdd += (finishTime - time);
    }

    public void inorder() {
        List<T> list = new ArrayList<>();
        inorderRec(root, list);
        System.out.println();
    }

    @Override
    public long getTimeAdd() {
        return timeAdd;
    }

    void inorderRec(BinarySearchNode<T> root, List<T> list) {


        if (root != null) {

            Stack<BinarySearchNode> nodes = new Stack<>();
            nodes.push(root);
            list.add(root.key);
            while (!nodes.isEmpty()) {
                BinarySearchNode<T> top = nodes.pop();
                if (top.right != null) {
                    nodes.push(top.right);
                    list.add((T) top.right.key);
                }
                if (top.left != null) {
                    nodes.push(top.left);
                    list.add((T) top.left.key);
                }
            }
            Collections.sort(list);
            for (int i = list.size() - 1; i >= 0; i--) {
                System.out.println(list.get(i));
            }
        }

    }

    public void print() {

        if (root != null) {
            System.out.println("             " + root.key);
            printRecursive(root);
        }
    }

    private void printRecursive(BinarySearchNode<T> node) {

        if (node != null) {

            if (node.left != null)
                System.out.print((node.right == null) ? "(" + node.key + ") L: " + node.left.key + "\n" : "(" + node.key + ") L: " + node.left.key);
            if (node.right != null)
                System.out.print((node.left == null) ? "(" + node.key + ") P: " + node.right.key + "\n" : "  <--> (" + node.key + ") P: " + node.right.key + "\n");

            if (node.left != null) printRecursive(node.left);
            if (node.right != null) printRecursive(node.right);
        }
    }


    public Tree getEmptyStructure() {
        return new BinarySearchTree();
    }


    public long getTimeDel() {
        return timeDel;
    }

    public long getTimeSearch() {
        return timeSearch;
    }

    public long getComparision() {
        return comparision;
    }

    public long getModifications() {
        return modifications;
    }

    public long getSize() {
        return this.size;
    }
}