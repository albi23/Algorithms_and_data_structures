public  class SelectSort extends Algorithms {



    void selectSortAsc(int elements , int[] dataToSort){

        long startTime = System.nanoTime();

        while (indexOfArray < elements){
            int min = dataToSort[indexOfArray];
            for (int i = indexOfArray+1; i< elements ;i++){
//                log("I compare "+dataToSort[i]+" with "+min);
                if(dataToSort[i] < min) {min = dataToSort[i]; tempIndex = i;}
                comprisionKey++;
            }

            if (dataToSort[indexOfArray] != min){
               // log("I replace "+dataToSort[tempIndex]+" with "+min);
                dataToSort[tempIndex] = dataToSort[indexOfArray] ;
                dataToSort[indexOfArray] = min;
                switchKey++;
            }
            indexOfArray++;
        }
        long endTime = System.nanoTime();
        durationOfAlgorithm = (endTime-startTime)/1000;
        printResult(dataToSort,elements);
    }

    void selectSortDesc(int elements , int[] dataToSort){

        long startTime = System.nanoTime();

        while (indexOfArray < elements){
            int max = dataToSort[indexOfArray];
            for (int i = indexOfArray+1; i< elements;i++){
              //  log("I compare "+dataToSort[i]+" with "+max);
                if(dataToSort[i] > max) {max = dataToSort[i]; tempIndex = i;}
                comprisionKey++;
            }
            if (dataToSort[indexOfArray] != max){
              //  log("I replace "+dataToSort[tempIndex]+" with "+max);
                dataToSort[tempIndex] = dataToSort[indexOfArray] ;
                dataToSort[indexOfArray] = max;
                switchKey++;
            }
            indexOfArray++;
        }
        long endTime = System.nanoTime();
        durationOfAlgorithm = (endTime-startTime)/1000;
        printResult(dataToSort, elements);
    }


}
