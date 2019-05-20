package rbt;

import TreeInterfaces.Tree;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> implements Tree<T> {

    private RedBlackNode<T> nil = new RedBlackNode<T>();
    private RedBlackNode<T> root = nil;
    private long size = 0;
    private long timeAdd = 0;
    private long timeDel = 0;
    private long timeSearch = 0;
    private long comparision = 0;
    private long modifications = 0;

    public RedBlackTree( ) {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }

    private void leftRotate(RedBlackNode<T> x) {

        leftRotateFixup(x);

        modifications++;
        RedBlackNode<T> y = x.right;
        x.right = y.left;

        comparision++;
        if (!isNil(y.left)) {
            modifications++;
            y.left.parent = x;
        }
        modifications++;
        y.parent = x.parent;

        comparision++;
        if (isNil(x.parent)) {
            modifications++;
            root = y;
        } else if (x.parent.left == x) {
            modifications++;
            comparision++;
            x.parent.left = y;
        } else {
            modifications++;
            comparision += 2;
            x.parent.right = y;
        }
        // Finish of the leftRotate
        modifications += 2;
        y.left = x;
        x.parent = y;
    }


    private void leftRotateFixup(RedBlackNode x) {

        // Case 1: Only x, x.right and x.right.right always are not nil.
        comparision++;
        if (isNil(x.left) && isNil(x.right.left)) {
            modifications+=3;
            x.numLeft = 0;
            x.numRight = 0;
            x.right.numLeft = 1;
        }

        // Case 2: x.right.left also exists in addition to Case 1
        else if (isNil(x.left) && !isNil(x.right.left)) {
            comparision++;
            modifications+=3;
            x.numLeft = 0;
            x.numRight = 1 + x.right.left.numLeft +
                    x.right.left.numRight;
            x.right.numLeft = 2 + x.right.left.numLeft +
                    x.right.left.numRight;
        }

        // Case 3: x.left also exists in addition to Case 1
        else if (!isNil(x.left) && isNil(x.right.left)) {
            comparision+=2;
            modifications+=2;
            x.numRight = 0;
            x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;

        }
        // Case 4: x.left and x.right.left both exist in addtion to Case 1
        else {
            comparision+=3;
            modifications+=2;
            x.numRight = 1 + x.right.left.numLeft +
                    x.right.left.numRight;
            x.right.numLeft = 3 + x.left.numLeft + x.left.numRight +
                    x.right.left.numLeft + x.right.left.numRight;
        }

    }


    private void rightRotate(RedBlackNode<T> y) {

        // Call rightRotateFixup to adjust numRight and numLeft values
        rightRotateFixup(y);

        modifications++;
        // Perform the rotate as described in the course text.
        RedBlackNode<T> x = y.left;
        y.left = x.right;

        // Check for existence of x.right
        comparision++;
        if (!isNil(x.right)){
            modifications++;
            x.right.parent = y;}
        modifications++;
        x.parent = y.parent;

        // y.parent is nil
        comparision++;
        if (isNil(y.parent)){
            modifications++;
            root = x;}

            // y is a right child of it's parent.
        else if (y.parent.right == y){
            modifications++;
            comparision++;
            y.parent.right = x;}

            // y is a left child of it's parent.
        else{
            comparision+=2;
            modifications++;
            y.parent.left = x;}

        modifications+=2;
        x.right = y;

        y.parent = x;

    }


    private void rightRotateFixup(RedBlackNode y) {

        // Case 1: Only y, y.left and y.left.left exists.
        comparision++;
        if (isNil(y.right) && isNil(y.left.right)) {
            modifications+=3;
            y.numRight = 0;
            y.numLeft = 0;
            y.left.numRight = 1;
        }

        // Case 2: y.left.right also exists in addition to Case 1
        else if (isNil(y.right) && !isNil(y.left.right)) {
            comparision++;
            modifications+=3;
            y.numRight = 0;
            y.numLeft = 1 + y.left.right.numRight +
                    y.left.right.numLeft;
            y.left.numRight = 2 + y.left.right.numRight +
                    y.left.right.numLeft;
        }

        // Case 3: y.right also exists in addition to Case 1
        else if (!isNil(y.right) && isNil(y.left.right)) {
            comparision+=2;
            modifications+=2;
            y.numLeft = 0;
            y.left.numRight = 2 + y.right.numRight + y.right.numLeft;

        }

        // Case 4: y.right & y.left.right exist in addition to Case 1
        else {
            comparision+=3;
            modifications+=2;
            y.numLeft = 1 + y.left.right.numRight +
                    y.left.right.numLeft;
            y.left.numRight = 3 + y.right.numRight +
                    y.right.numLeft +
                    y.left.right.numRight + y.left.right.numLeft;
        }

    }


    public void insert(T key) {

        comparision++;
        if (key == null) return;
        size++;
        insert(new RedBlackNode<T>(key));
    }


    private void insert(RedBlackNode<T> z) {

        long time  = System.nanoTime();
        RedBlackNode<T> y = nil;
        RedBlackNode<T> x = root;

        while (!isNil(x)) {
            comparision++;
            y = x;

            if (z.key.compareTo(x.key) < 0) {
                comparision++;
                modifications++;
                x.numLeft++;
                x = x.left;
            }

            else {
                comparision++;
                modifications++;
                x.numRight++;
                x = x.right;
            }
        }
        z.parent = y;

        if (isNil(y)){
            comparision++;
            modifications++;
            root = z;}
        else if (z.key.compareTo(y.key) < 0){
            comparision++;
            modifications++;
            y.left = z;}
        else {
            comparision+=2;
            modifications++;
            y.right = z;
        }
        modifications+=3;
        z.left = nil;
        z.right = nil;
        z.color = RedBlackNode.RED;

        insertFixup(z);

        long finishTime =  System.nanoTime();

        timeAdd+=(finishTime-time);
    }



    private void insertFixup(RedBlackNode<T> z) {

        RedBlackNode<T> y = nil;
        // While there is a violation of the rbt.RedBlackTree properties..
        while (z.parent.color == RedBlackNode.RED) {

            comparision++;
            // If z's parent is the the left child of it's parent.
            if (z.parent == z.parent.parent.left) {
                comparision++;
                modifications++;
                // Initialize y to z 's cousin
                y = z.parent.parent.right;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED) {
                    comparision++;
                    modifications+=4;
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }
                // Case 2: if y is black & z is a right child
                else if (z == z.parent.right) {
                    comparision+=2;
                    modifications++;
                    // leftRotaet around z's parent
                    z = z.parent;
                    leftRotate(z);
                }

                // Case 3: else y is black & z is a left child
                else {
                    comparision+=3;
                    modifications+=2;
                    // recolor and rotate round z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            }

            // If z's parent is the right child of it's parent.
            else {
                comparision+=2;
                modifications++;
                // Initialize y to z's cousin
                y = z.parent.parent.left;

                // Case 1: if y is red...recolor
                if (y.color == RedBlackNode.RED) {
                    modifications+=4;
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                }

                // Case 2: if y is black and z is a left child
                else if (z == z.parent.left) {
                    comparision++;
                    modifications++;
                    // rightRotate around z's parent
                    z = z.parent;
                    rightRotate(z);
                }
                // Case 3: if y  is black and z is a right child
                else {
                    modifications+=2;
                    comparision+=2;
                    // recolor and rotate around z's grandpa
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        // Color root black at all times
        modifications++;
        root.color = RedBlackNode.BLACK;

    }


    public RedBlackNode<T> treeMinimum(RedBlackNode<T> node) {

        // while there is a smaller key, keep going left
        while (!isNil(node.left)){
            comparision++;
            node = node.left;}
        return node;
    }



    public RedBlackNode<T> treeSuccessor(RedBlackNode<T> x) {

        comparision++;
        if (!isNil(x.left))
            return treeMinimum(x.right);

        modifications++;
        RedBlackNode<T> y = x.parent;

        while (!isNil(y) && x == y.right) {
            comparision++;
            x = y;
            y = y.parent;
        }
        return y;
    }

    public void delete(T key) {

        long start = System.nanoTime();
        RedBlackNode<T> z = find(key);

        comparision++;
        if (z == null) return;

        size++;
        RedBlackNode<T> x = nil;
        RedBlackNode<T> y = nil;
        comparision++;
        if (isNil(z.left) || isNil(z.right)) {
            modifications++;
            y = z;
        }
        else y = treeSuccessor(z);

        comparision++;
        modifications++;
        if (!isNil(y.left)) {
            x = y.left;
        } else {
            comparision++;
            x = y.right;
        }

        modifications++;
        x.parent = y.parent;

        modifications++;
        if (isNil(y.parent)){
            comparision++;
            root = x;}
        else if (!isNil(y.parent.left) && y.parent.left == y){
            modifications++;
            comparision++;
            y.parent.left = x;}

        else if (!isNil(y.parent.right) && y.parent.right == y){
            modifications+=2;
            modifications++;
            y.parent.right = x;}

        comparision++;
        if (y != z) {
            modifications++;
            z.key = y.key;
        }

        fixNodeData(x, y);

        if (y.color == RedBlackNode.BLACK)
            removeFixup(x);

        timeDel +=(System.nanoTime() - start);
    }



    private void fixNodeData(RedBlackNode<T> x, RedBlackNode<T> y) {

        // Initialize two variables which will help us traverse the tree
        RedBlackNode<T> current = nil;
        RedBlackNode<T> track = nil;


        // if x is nil, then we will start updating at y.parent
        // Set track to y, y.parent's child
        comparision++;
        if (isNil(x)) {
            modifications++;
            current = y.parent;
            track = y;
        }

        // if x is not nil, then we start updating at x.parent
        // Set track to x, x.parent's child
        else {
            comparision++;
            modifications++;
            current = x.parent;
            track = x;
        }

        // while we haven't reached the root
        while (!isNil(current)) {
            comparision++;
            // if the node we deleted has a different key than
            // the current node
            if (y.key != current.key) {

                comparision++;
                // if the node we deleted is greater than
                // current.key then decrement current.numRight
                if (y.key.compareTo(current.key) > 0){
                    modifications++;
                    current.numRight--;}
                comparision++;
                // if the node we deleted is less than
                // current.key thendecrement current.numLeft
                if (y.key.compareTo(current.key) < 0){
                    modifications++;
                    current.numLeft--;}
            }

            // if the node we deleted has the same key as the
            // current node we are checking
            else {
                comparision++;
                // the cases where the current node has any nil
                // children and update appropriately
                if (isNil(current.left)){
                    modifications++;
                    current.numLeft--;}
                else if (isNil(current.right)){
                    comparision++;
                    modifications++;
                    current.numRight--;}

                    // the cases where current has two children and
                    // we must determine whether track is it's left
                    // or right child and update appropriately
                else if (track == current.right){
                    modifications++;
                    comparision+=2;
                    current.numRight--;}
                else if (track == current.left){
                    comparision+=3;
                    modifications++;
                    current.numLeft--;}
            }

            // update track and current
            modifications++;
            track = current;
            current = current.parent;

        }

    }

    private void removeFixup(RedBlackNode<T> x) {

        RedBlackNode<T> w;

        // While we haven't fixed the tree completely...
        comparision++;
        while (x != root && x.color == RedBlackNode.BLACK) {

            // if x is it's parent's left child
            comparision++;
            if (x == x.parent.left) {
                modifications++;
                // set w = x's sibling
                w = x.parent.right;

                // Case 1, w's color is red.
                comparision++;
                if (w.color == RedBlackNode.RED) {
                    modifications+=3;
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                // Case 2, both of w's children are black
                comparision++;
                if (w.left.color == RedBlackNode.BLACK &&
                        w.right.color == RedBlackNode.BLACK) {
                    modifications+=2;
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }
                // Case 3 / Case 4
                else {
                    comparision++;
                    // Case 3, w's right child is black
                    if (w.right.color == RedBlackNode.BLACK) {
                        modifications+=3;
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // Case 4, w = black, w.right = red
                    modifications+=4;
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }
            // if x is it's parent's right child
            else {
                comparision++;
                modifications++;
                // set w to x's sibling
                w = x.parent.left;

                // Case 1, w's color is red
                comparision++;
                if (w.color == RedBlackNode.RED) {
                    modifications+=3;
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                comparision++;
                // Case 2, both of w's children are black
                if (w.right.color == RedBlackNode.BLACK &&
                        w.left.color == RedBlackNode.BLACK) {
                    modifications+=2;
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                }

                // Case 3 / Case 4
                else {
                    comparision+=2;
                    // Case 3, w's left child is black
                    if (w.left.color == RedBlackNode.BLACK) {
                        modifications+=3;
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    // Case 4, w = black, and w.left = red
                    modifications+=4;
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.left.color = RedBlackNode.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        // set x to black to ensure there is no violation of
        // RedBlack tree Properties
        modifications++;
        x.color = RedBlackNode.BLACK;
    }


    public int search(T toFind) {
        long time  = System.nanoTime();
        RedBlackNode<T> result = find(toFind);
        timeSearch += (System.nanoTime() -time);
        return (result == null) ? 0 : 1;
    }

    public RedBlackNode<T> find(T key) {


        RedBlackNode<T> current = root;

        while (!isNil(current)) {

            comparision++;
            if (current.key.equals(key)){
                comparision++;
                return current;}
            else if (current.key.compareTo(key) < 0){
                comparision++;
                current = current.right;}
            else{
                comparision+=2;
                current = current.left; };
        }
        return null;


    }


    public List<T> getGreaterThan(T key, Integer maxReturned) {
        List<T> list = new ArrayList<T>();
        getGreaterThan(root, key, list);
        return list.subList(0, Math.min(maxReturned, list.size()));
    }


    private void getGreaterThan(RedBlackNode<T> node, T key,
                                List<T> list) {
        if (isNil(node)) {
            return;
        } else if (node.key.compareTo(key) > 0) {
            getGreaterThan(node.left, key, list);
            list.add(node.key);
            getGreaterThan(node.right, key, list);
        } else {
            getGreaterThan(node.right, key, list);
        }
    }


    private boolean isNil(RedBlackNode node) {

        return node == nil;
    }


    public int size() {

        return isNil(root) ? 0 : root.numLeft + root.numRight + 1;
    }


    public void inorder() {

        if (size() == 0) return;
        RedBlackNode<T> min = treeMinimum(root);
        List<T> result = getGreaterThan(min.key, size());
        System.out.print(min.key + " ");
        for (T element : result) System.out.print(element + " ");
        System.out.println();
    }



    public void print() {
        if (isNil(root)) return;
        String output = (root.color == 1) ? "\u001b[31m " + root.key + "\u001b[0m" : "" + root.key;
        System.out.println(output);
        printRecursive(root);
    }

    public void printRecursive(RedBlackNode<T> node) {

        String output;
        if (!isNil(node)) {
            output = (node.left.color == 1) ? "\u001b[31m (" + node.key + ")" + node.left.key + "\u001b[0m" : "(" + node.key + ")" + node.left.key;
            System.out.print(output);
            output = (node.right.color == 1) ? "\u001b[31m (" + node.key + ")" + node.right.key + "\u001b[0m" : "(" + node.key + ")" + node.right.key;
            System.out.print("   <->   " + output + "\n");
            if (!(((node.left).right.key == null && (node.left).left.key == null))) printRecursive(node.left);
            if (!((node.right).right.key == null && (node.right).left.key == null)) printRecursive(node.right);
        }
    }

    public Tree getEmptyStructure(){
        return new RedBlackTree();
    }

    @Override
    public long getTimeDel() {
        return timeDel;
    }

    @Override
    public long getTimeSearch() {
        return timeSearch;
    }
    @Override
    public long getTimeAdd() {
        return timeAdd;
    }

    public long getComparision() {
        return comparision;
    }

    public long getModifications() {
        return modifications;
    }

    public long getSize( ) {
        return this.size;
    }
}
