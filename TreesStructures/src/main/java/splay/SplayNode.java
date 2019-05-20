package splay;

public class SplayNode<T extends Comparable<T>> {
    SplayNode<T> left, right, parent;
    T key;

    public SplayNode() {
        this(null, null, null, null);
    }

    public SplayNode(T key) {
        this(key, null, null, null);
    }


    public SplayNode(T key, SplayNode<T> left, SplayNode<T> right, SplayNode<T> parent) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.key = key;
    }
}

