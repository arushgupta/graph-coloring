package code;
import java.lang.Math;
import java.util.Random;

public class Utility {

    // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
    public static int getRandomNumber(int min, int max) {
        int randomizer = min + (int)(Math.random() * ((max - min) + 1));
        return randomizer;
    }

    // Uses random() to generate a number favoring the lower values and then transforming to graph size
    public static int getSkewedNumber(int size) {
        int num = (int)(size * (1 - Math.sqrt(Math.random())));
        return num;
    }

    public static int getCustomNumber(int size) {
        int num = (int)(size * Math.sqrt(Math.random()));
        return num;
    }

    public static int getShuffledIndex(int i) {
        Random random = new Random();
        int num = random.nextInt(i + 1);
        return num;
    }

}
