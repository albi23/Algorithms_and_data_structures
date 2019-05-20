package bst;

 class BinarySearchNode<T extends Comparable<T>> {

    T key;
    BinarySearchNode left, right;

    BinarySearchNode(T item) {
        key = item;
        left = right = null;
    }
}