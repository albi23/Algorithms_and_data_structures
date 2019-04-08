import java.io.PrintWriter;

/**
 * Klasa nadrzędna dla każdego algorytmu, trzyma te pola poniżej, głównie służy do wypisywania
 * i nie powtarzania kodu
 */


public class Algorithms {

     int indexOfArray =0;
     int tempIndex;
     int comprisionKey = 0;
     int switchKey = 0;
     long durationOfAlgorithm;
     private String message;



    void printResult(int[] dataToSort, int elements){
        log("============================");
        log("Comprision beetwen key: "+comprisionKey);
        log("Switch beetwen key: "+switchKey);
        log("Elements to sort : "+elements);
        log("Time of sorting by using "+this.getClass().getName()+" : "+durationOfAlgorithm+"µs\n\n");
        for (int i: dataToSort) System.err.print(i+" ");

        log("\n============================");

        message= this.getClass().getName()+": "+elements+","+comprisionKey+","+switchKey+","+durationOfAlgorithm;
//        log(+elements+","+comprisionKey+","+switchKey+","+durationOfAlgorithm+","+(double)comprisionKey/elements+","+(double)switchKey/elements);
        indexOfArray =0;
        comprisionKey =0;
        switchKey =0;
    }

     void log(String message){
        System.err.println(message);
    }


    public  String getMessage(){
        return this.message;
    }
}
