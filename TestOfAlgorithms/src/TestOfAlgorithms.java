import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class TestOfAlgorithms {

    private int[] rangeOfN;
    private int numberOfRepeat;
    private String nameOfFile;
    private Algorithms algorithms = new Algorithms();


    public TestOfAlgorithms(String nameOfFile, int numberOfRepeat) {

        this.numberOfRepeat = numberOfRepeat;
        this.nameOfFile = nameOfFile + ".txt";
        rangeOfN = new int[100];
        fillRangeOfN();
    }

    void fillRangeOfN() {
        int index = 0;
        for (int i = 100; i <= 10000; i += 100) {
            rangeOfN[index] = i;
            index++;
        }
    }

    void testAlgorithm() {

        SelectSort selectSort = new SelectSort();
        InsertionSort insertionSort = new InsertionSort(1);
        HeapSort heapSort = new HeapSort(1);
        QuickSort quickSort = new QuickSort();

        selectSort.log("\"N\",\"Comprision\",switch,duration,C/N,S/N");
        try (PrintWriter writer = new PrintWriter(nameOfFile, String.valueOf(StandardCharsets.UTF_8))) {
            for (int j = 1; j <= numberOfRepeat; j++) {
                writer.println("\n\n");
                for (int n : rangeOfN) {

                    int[] arrToTestSelect = fillRandomizeMode(n);
                    int[] arrToTestInsert = Arrays.copyOf(arrToTestSelect, arrToTestSelect.length);
                    int[] arrToTestHeap = Arrays.copyOf(arrToTestSelect, arrToTestSelect.length);
                    int[] arrToTestQuick = Arrays.copyOf(arrToTestSelect, arrToTestSelect.length);

                    selectSort.selectSortAsc(arrToTestSelect.length, arrToTestSelect);
                    writer.println(selectSort.getMessage());

                    insertionSort.insertionSort(arrToTestInsert.length, arrToTestInsert);
                    writer.println(insertionSort.getMessage());

                    heapSort.heapSort(arrToTestHeap.length, arrToTestHeap);
                    writer.println(heapSort.getMessage());

                    quickSort.sort(arrToTestQuick.length, arrToTestQuick);
                    writer.println(quickSort.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int[] fillRandomizeMode(int n) {
        Random rand = new Random(System.nanoTime());

        int[] randomizeArray = new int[n];
        for (int i = 0; i < randomizeArray.length; i++)
            randomizeArray[i] = rand.nextInt(100000);

        return randomizeArray;
    }

}
