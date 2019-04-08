public class HeapSort extends Algorithms {

    private int mode;

    public HeapSort(int mode) {
        this.mode = mode;
    }

    void heapSort(int elements, int[] dataToSort) {

        long startTime = System.nanoTime();

        int startSizeOfHeap = elements;
        for (int i = (startSizeOfHeap / 2 - 1); i >= 0; i--)
            validateHeap(dataToSort, startSizeOfHeap, i);

        for (int i = elements - 1; i > 0; i--) {
            swap(dataToSort, 0, i);
            validateHeap(dataToSort, --startSizeOfHeap, 0);
        }


        durationOfAlgorithm = (System.nanoTime() - startTime) / 1000;
        printResult(dataToSort, elements);
    }

    /**
     * Tak wiem Tempka powinna wjechać jako rozwidlenie, ale copy and paste jest szybsze ;) [szczegół to nawiasy w ifach]
     *
     * @param array
     * @param heapSize
     * @param parentIndex
     */
    private void validateHeap(int[] array, int heapSize, int parentIndex) {

        int maxIndex = parentIndex;
        int leftChildren = parentIndex * 2 + 1;
        int rightChildren = parentIndex * 2 + 2;

        if (mode == 2) {
            if (leftChildren < heapSize && array[leftChildren] < array[maxIndex]) {
                //    log("I compare " + array[leftChildren] + " with " + array[maxIndex]);
                comprisionKey++;
                maxIndex = leftChildren;
            }

            if (rightChildren < heapSize && array[rightChildren] < array[maxIndex]) {
                //  log("I compare " + array[rightChildren] + " with " + array[maxIndex]);
                comprisionKey++;
                maxIndex = rightChildren;
            }
        } else {
            if (leftChildren < heapSize) {
                //    log("I compare " + array[leftChildren] + " with " + array[maxIndex]);
                comprisionKey++;
                if (array[leftChildren] > array[maxIndex])maxIndex = leftChildren;
            }

            if (rightChildren < heapSize) {
                //   log("I compare " + array[rightChildren] + " with " + array[maxIndex]);
                comprisionKey++;
                if(array[rightChildren] > array[maxIndex])maxIndex = rightChildren;
            }
        }

        if (maxIndex != parentIndex) {
            swap(array, maxIndex, parentIndex);
            validateHeap(array, heapSize, maxIndex);
        }

    }

    private void swap(int[] arr, int firstIndex, int secondIndex) {
        //  log("I replace " + arr[firstIndex] + " with " + arr[secondIndex]);
        switchKey++;
        int tem = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = tem;
    }
}