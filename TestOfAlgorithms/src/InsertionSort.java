public class InsertionSort extends Algorithms {

    private int mode ;

    public InsertionSort(int mode) {
        this.mode = mode;
    }

    void insertionSort(int elements , int[] dataToSort){

        long startTime = System.nanoTime();


        int actualIndex = 1;
        while (actualIndex < elements){

            int tempValue = dataToSort[actualIndex];

            for (int i = actualIndex-1; i >= 0; i--){

                comprisionKey++;
                //log("I compare "+dataToSort[i]+"  with "+tempValue);
                if(mode == 1){
                    if(dataToSort[i] > tempValue){
                        if (i == 0){
                            switchKey++;
                            dataToSort[i+1]=dataToSort[i];
                            dataToSort[i] = tempValue;
                            break;
                        }
                        //log("I replace "+dataToSort[i+1]+"  with  "+dataToSort[i]);
                        switchKey++;
                        dataToSort[i+1]=dataToSort[i];
                    }
                    else if (dataToSort[i] <= tempValue ){
                        switchKey++;
                        dataToSort[i+1] =tempValue;
                        break;
                    }
                }else {
                    if(dataToSort[i] < tempValue){
                        if (i == 0){
                            switchKey++;
                            dataToSort[i+1]=dataToSort[i];
                            dataToSort[i] = tempValue;
                            break;
                        }
                        //log("I replace "+dataToSort[i+1]+"  with  "+dataToSort[i]);
                        switchKey++;
                        dataToSort[i+1]=dataToSort[i];
                    }
                    else if (dataToSort[i] >= tempValue ){
                        switchKey++;
                        dataToSort[i+1] =tempValue;
                        break;
                    }
                }
            }
            actualIndex++;
        }
        long endTime = System.nanoTime();
        durationOfAlgorithm = (endTime-startTime)/1000;
        printResult(dataToSort,elements);

    }

    private void swap(int[] arr, int firstIndex, int secondIndex) {
       // log("I replace " + arr[firstIndex] + " with " + arr[secondIndex]);
        switchKey++;
        int tem = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = tem;
    }


}
