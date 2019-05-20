import TreeInterfaces.Tree;
import bst.BinarySearchTree;
import rbt.RedBlackTree;
import splay.SplayTree;

public class MainClass {

    public static void main(String[] args) {

        Controller controller = new Controller(args);

        /**
         * Block for test
         * */
        String[] files = {"sample","lotr","aspell_wordlist","KJB"};
        String[] nameStructures = {"RBT","SPLAY","BINARY"};
        int counter = 1;
        boolean shuffle = true;
        Tree[] treesObject = {new RedBlackTree(),new SplayTree(),new BinarySearchTree()};

        for (int j = 0; j < treesObject.length ; j++) {
            for (String file : files) {

                controller.makeAllTest(file,"_"+nameStructures[j]+"_"+file+"_"+shuffle,100,treesObject[j],shuffle);
                System.out.println("\n"+counter++);
            }
        }
    }

}