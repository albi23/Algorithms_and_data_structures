public class QuickSort extends Algorithms {


    public void sort(int elements, int[] dataToSort) {
        long startTime = System.nanoTime();
        QuickSort(dataToSort, 0, elements - 1);
        long endTime = System.nanoTime();
        durationOfAlgorithm = (endTime - startTime) / 1000;
        printResult(dataToSort, elements);
    }

    void QuickSort(int[] arr, int left, int right) {

        comprisionKey++;
        if (left >= right) return;
        int border = partition(arr, left, right);

        if (border - left < right - border) {
            QuickSort(arr, left, border - 1);
            QuickSort(arr, border + 1, right);
        }else {
            QuickSort(arr, border + 1, right);
            QuickSort(arr, left, border - 1);
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = generatePivot(arr, left, right);
        int border = left - 1;
        int counter = left;

        while (counter < right) {

            comprisionKey++;
            if (arr[counter] < pivot) {
                border++;
                if (border != counter) swap(arr, border, counter);
            }
            counter++;
        }

        border++;
        if (border != right) swap(arr, border, right);
        return border;
    }

    private void swap(int[] arr, int firstIndex, int secondIndex) {
//        log("I replace " + arr[firstIndex] + " with " + arr[secondIndex]);
        switchKey++;
        int tem = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = tem;
    }

    //function which take middle value of pivot in array

    private int generatePivot(int[] array, int left, int right) {
        int middleValue = left + (right - left) / 2;
        int pivotValue = array[middleValue];
        swap(array, middleValue, right);
        return pivotValue;
    }
}
