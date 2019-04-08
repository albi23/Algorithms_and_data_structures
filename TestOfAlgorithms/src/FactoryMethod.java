public class FactoryMethod {

    private static final String[] command = {"--type", "--stat"};
    private static final String[] algorithmOfSort = {"select", "insert", "heap", "quick", "mquick"};
    private static final String[] typeOfSort = {"--asc", "--desc"};


    void chooserMethodToSort(String[] incomeData) {

        if (incomeData[0].equals(command[0])) {

            DataInputPrepare dataInputPrepare = new DataInputPrepare();
            if (incomeData[1].equals(algorithmOfSort[0])) {

                SelectSort selectSort = new SelectSort();

                if (incomeData[2].equals(typeOfSort[0])) {
                    selectSort.selectSortAsc(dataInputPrepare.getRangOfElements(), dataInputPrepare.getInputNumbers());
                } else if (incomeData[2].equals(typeOfSort[1])) {
                    selectSort.selectSortDesc(dataInputPrepare.getRangOfElements(), dataInputPrepare.getInputNumbers());
                } else {
                    System.err.println("Wrond type of sort");
                    System.exit(1);
                }

            } else if (incomeData[1].equals(algorithmOfSort[1])) {

                InsertionSort insertionSort = new InsertionSort(1);
                if (incomeData[2].equals(typeOfSort[0])) {
                    insertionSort.insertionSort(dataInputPrepare.getRangOfElements(),dataInputPrepare.getInputNumbers());
                } else if (incomeData[2].equals(typeOfSort[1])) {
                    new InsertionSort(2).insertionSort(dataInputPrepare.getRangOfElements(),dataInputPrepare.getInputNumbers());
                } else {
                    System.err.println("Wrond type of sort");
                    System.exit(1);
                }

            } else if (incomeData[1].equals(algorithmOfSort[2])) {

                HeapSort heapSort = new HeapSort(1);
                if (incomeData[2].equals(typeOfSort[0])) {
                    heapSort.heapSort(dataInputPrepare.getRangOfElements(),dataInputPrepare.getInputNumbers());
                } else if (incomeData[2].equals(typeOfSort[1])) {
                    HeapSort hs = new HeapSort(2);
                    hs.heapSort(dataInputPrepare.getRangOfElements(),dataInputPrepare.getInputNumbers());

                } else {
                    System.err.println("Wrond type of sort");
                    System.exit(1);
                }

            } else if (incomeData[1].equals(algorithmOfSort[3])) {

                if (incomeData[2].equals(typeOfSort[0])) {
                    new QuickSort().sort(dataInputPrepare.getRangOfElements(),dataInputPrepare.getInputNumbers());
                } else if (incomeData[2].equals(typeOfSort[1])) {

                    //quick desc
                } else {
                    System.err.println("Wrond type of sort");
                    System.exit(1);
                }
            } else if (incomeData[1].equals(algorithmOfSort[4])) {
                //call combinations of sorts quick and insert
            } else {
                log("Select correct algorithm");
            }
        } else if (incomeData[0].equals(command[1])) {

            try{
                int K = Integer.parseInt(incomeData[2]);
                TestOfAlgorithms testOfAlgoritms = new TestOfAlgorithms(incomeData[1],K);
                testOfAlgoritms.testAlgorithm();

            }catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        } else {
            log("Your input dada in incorect!");
        }
    }

    void log(String message) {
        System.err.println(message);

    }
}


/// --type insert --asc

//--type select --desc  -? wtf

//--stat dupa 5
