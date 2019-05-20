import bst.BinarySearchTree;
import rbt.RedBlackTree;
import TreeInterfaces.Tree;
import splay.SplayTree;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Controller<T extends Comparable<T>> {

    private String[] inputData;
    private char[] punctuationMarks = {'.', ',', ':', ';', '?', '!', '-', '"'};
    private Tree<T> tree;
    private int numberOfCommand;
    private Scanner scanner;
    private BufferedWriter writer;
    private ArrayList<T> listOfWord = new ArrayList<T>();

    public Controller(String[] inputData) {
        this.scanner = new Scanner(System.in);
        this.inputData = inputData;
        checkTypeOfTree();
    }

    private void checkTypeOfTree() {

        if (!(inputData[0].equals("--type"))) incorrectInput();

        switch (inputData[1]) {
            case "bst":
                tree = new BinarySearchTree<>();
                break;
            case "rbt":
                tree = new RedBlackTree<>();
                break;
            case "splay":
                tree = new SplayTree<>();
                break;
        }
        executeCommand();
    }

    void setNumberOfCommand() {


        System.out.print("Insert number of operations : ");
        try {
            numberOfCommand = scanner.nextInt();
        } catch (Exception ex) {
            incorrectInput();
        }
    }


    void executeCommand() {

        setNumberOfCommand();
        for (int i = 1; i <= numberOfCommand; i++) {

            System.out.println("Available command:\n 1: insert s \n 2: delete s \n 3: search \n 4: load f \n 5: inorder \n 6: print\n 7: Changes\n 8: exit");

            switch (scanner.nextInt()) {

                case 1:
                    tree.insert((T) checkWord(scanner.next()));
                    break;
                case 2:
                    tree.delete((T) scanner.next());
                    break;
                case 3:
                    System.out.println(tree.search((T) scanner.next()));
                    break;
                case 4:
                    load(scanner.next(), false);
                    break;
                case 5:
                    tree.inorder();
                    break;
                case 6:
                    tree.print();
                    break;
                case 7:
                    System.out.println("Modificaton:  "+tree.getModifications());
                    System.out.println("Comprisions: "+tree.getComparision());
                    System.out.println("Total time operations: "+tree.getTimeAdd()+tree.getTimeDel()+tree.getTimeSearch()+" ns");
                    System.out.println("Size of structure:  "+tree.getSize());
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Some mistake !");
                    i--;
            }
            System.out.println("----------------");
        }
    }

    private void incorrectInput() {
        System.err.println("Incorrect input");
        System.exit(1);
    }

    private void load(String nameFile, boolean isTest) {
        String toAdd;
        char whitespace = ' ';
        try {
            FileInputStream is = new FileInputStream(new File("testingFiles/" + nameFile));
            System.setIn(is);
            int x;
            StringBuilder word = new StringBuilder();
            while ((x = System.in.read()) >= 0) {
                if ((char) x != whitespace && (char) x != '\n') {
                    if (!isPunctuationMarks((char) x)) word.append((char) x);
                } else {
                    toAdd = checkWord(word.toString());
                    if (toAdd != null) {
                        listOfWord.add((T) word.toString());
                        if (!isTest) tree.insert((T) toAdd);
                    }
                    word.setLength(0);
                }
            }
        } catch (Exception e) {
            System.err.println("No such file or directory");
        }
    }

    private String checkWord(String toCheck) {

        Pattern pattern = Pattern.compile("[a-zA-Z]*[a-zA-Z]");
        if (!pattern.matcher(toCheck).matches()) {
            pattern = Pattern.compile("[a-zA-Z]");
            if (toCheck.length() > 0 && !pattern.matcher(toCheck.substring(0, 1)).matches())
                toCheck = toCheck.substring(1);                                   // delete first sign
            if (toCheck.length() > 0 && !pattern.matcher(toCheck.substring(toCheck.length() - 1)).matches())
                toCheck = toCheck.substring(0, toCheck.length() - 1); // delete last sign
        }
        return (toCheck.length() > 0) ? toCheck : null;
    }

    private boolean isPunctuationMarks(char sign) {

        for (char c : punctuationMarks)
            if (sign == c) return true;

        return false;
    }

    public void makeSearch(String testFile, String nameOfResultFile, int numberOfRepeats, Tree tree, boolean shuffle) {


        load(testFile + ".txt", true);
        if (shuffle) Collections.shuffle(listOfWord);

        for (Object s : listOfWord) tree.insert((T) s);
        if (shuffle) Collections.shuffle(listOfWord);

        for (int j = 1; j <= numberOfRepeats; j++) {
            for (Object s : listOfWord) tree.search((T) s);
            if (shuffle) Collections.shuffle(listOfWord);

            System.out.print("\r" + ((double) j / (double) numberOfRepeats) * 100 + "%");
        }

        writeResult(nameOfResultFile, numberOfRepeats, tree.getTimeSearch());
    }

    public void makeInsertTest(String testFile, String nameOfResultFile, int numberOfRepeats, Tree tree, boolean shuffle) {

        /*Load to arrray*/
        load(testFile + ".txt", true);

        if (shuffle) Collections.shuffle(listOfWord);

        long time = 0;
        for (int j = 1; j <= numberOfRepeats; j++) {
            Tree chooseTree = tree.getEmptyStructure();
            for (Object s : listOfWord) {
                chooseTree.insert((T) s);
            }
            time += chooseTree.getTimeAdd();
            System.out.print("\r" + ((double) j / (double) numberOfRepeats) * 100 + "%");
            if (shuffle) Collections.shuffle(listOfWord);
        }
        writeResult(nameOfResultFile, numberOfRepeats, time);
    }

    public void makeDeleteTest(String testFile, String nameOfResultFile, int numberOfRepeats, Tree tree, boolean shuffle) {


        load(testFile + ".txt", true); // trzeba będzie w loadzie odkomentować
        if (shuffle) Collections.shuffle(listOfWord);

        long time = 0;
        for (int j = 1; j <= numberOfRepeats; j++) {
            Tree chooseTree = tree.getEmptyStructure();

            for (Object s : listOfWord) chooseTree.insert((T) s);
            for (Object s : listOfWord) chooseTree.delete((T) s);

            time += chooseTree.getTimeDel();
            System.out.print("\r" + ((double) j / (double) numberOfRepeats) * 100 + "%");
            if (shuffle) Collections.shuffle(listOfWord);
        }
        writeResult(nameOfResultFile, numberOfRepeats, time);
    }


    public void makeAllTest(String testFile, String nameOfResultFile, int numberOfRepeats, Tree tree, boolean shuffle) {


        load(testFile + ".txt", true); // trzeba będzie w loadzie odkomentować
        if (shuffle) Collections.shuffle(listOfWord);

        long timeDel = 0,timeAdd =0, timeSearch = 0;
        for (int j = 1; j <= numberOfRepeats; j++) {
            Tree treeType = tree.getEmptyStructure();

            for (Object s : listOfWord) treeType.insert((T) s);
            for (Object s : listOfWord) treeType.search((T) s);
            for (Object s : listOfWord) treeType.delete((T) s);


            timeDel += treeType.getTimeDel();
            timeAdd += treeType.getTimeAdd();
            timeSearch += treeType.getTimeSearch();

            System.out.print("\r" + ((double) j / (double) numberOfRepeats) * 100 + "%");
            if (shuffle) Collections.shuffle(listOfWord);
        }
        writeResult("DELETE_"+nameOfResultFile, numberOfRepeats, timeDel);
        writeResult("INSERT_"+nameOfResultFile, numberOfRepeats, timeAdd);
        writeResult("SEARCH_"+nameOfResultFile, numberOfRepeats, timeSearch);
    }

    private void writeResult(String nameOfResultFile, int numberOfRepeats, long time) {
        try {
            writer = new BufferedWriter(new FileWriter(new File(nameOfResultFile + ".txt")));
            writer.write("" + time / (numberOfRepeats) + "\n");
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
