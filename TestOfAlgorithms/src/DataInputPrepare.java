import java.util.InputMismatchException;
import java.util.Scanner;

public class DataInputPrepare {

    private int rangOfElements;
    private int[] inputNumbers;


    public DataInputPrepare(){

        preparingInputData();
    }
    private int[] preparingInputData() {

        rangOfElements = scanRangeOfArray();
        inputNumbers = new int[rangOfElements];

        System.out.println("Please insert your number, separated by space ");
        Scanner scanner = new Scanner(System.in);

        int indexOfArray = 0;
        boolean guard;

        while (indexOfArray <rangOfElements) {
            guard = true;
            int newNumber = 0;

            try {  newNumber = scanner.nextInt(); }
            catch (InputMismatchException ex){
                System.err.println("You must enter the number.");
                guard = false;
            }
            if(!guard){
                scanner = new Scanner(System.in);
                continue;
            }
            inputNumbers[indexOfArray] = newNumber;
            indexOfArray++;
        }
        scanner.close();

        return inputNumbers;
    }

    private int scanRangeOfArray() {

        System.out.println("Please insert the amount od numbers to sort: ");
        Scanner scanIn=  new Scanner(System.in);

        int rangOfElements= 0;
        boolean isInorrect = true;

        while (isInorrect) {
            try {
                rangOfElements = scanIn.nextInt();
                isInorrect = false;
            } catch (InputMismatchException ex) {
                System.err.println("You must enter the number.");
            }
            scanIn = new Scanner(System.in);
        }
        return rangOfElements;
    }

    public int getRangOfElements() {
        return rangOfElements;
    }
    public int[] getInputNumbers() {
        return inputNumbers;
    }


}
