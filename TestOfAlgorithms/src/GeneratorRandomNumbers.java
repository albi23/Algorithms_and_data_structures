import java.util.Random;

public class GeneratorRandomNumbers {

    public static void main(String[] args){

        GeneratorRandomNumbers generatorRandomNumbers = new GeneratorRandomNumbers();
        generatorRandomNumbers.generator(100,100000);
    }


    void generator(int amountOfNumbers,int from){

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i <= amountOfNumbers;i++){
            System.out.print((rand.nextInt(from)-1000)+" ");
        }
    }
}