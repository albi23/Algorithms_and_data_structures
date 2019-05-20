package rbt;

class RedBlackNode<T extends Comparable<T>>  {

     static final int BLACK = 0;
     static final int RED = 1;
	public T key;

    RedBlackNode<T> parent;
    RedBlackNode<T> left;
    RedBlackNode<T> right;

     int numLeft ;
     int numRight;
    public int color;

    RedBlackNode(){
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
        left = null;
        right = null;
    }

	RedBlackNode(T key){
        this();
        this.key = key;
	}
}

