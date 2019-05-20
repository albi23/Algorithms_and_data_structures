package TreeInterfaces;

public interface Tree<T> {

    void insert(T key);

    int search(T toFind);

    void delete(T key);

    void inorder();

    long getTimeAdd();

    long getTimeDel();

    long getTimeSearch();

    void print();

    Tree getEmptyStructure();

    long getComparision();

    long getModifications();

    long getSize( );

}
