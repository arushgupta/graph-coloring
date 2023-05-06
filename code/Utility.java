package code;
import java.util.Random;
import java.lang.StrictMath;

public class Utility {

    // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
    public static int getRandomNumber(int min, int max) {
        int randomizer = min + (int)(StrictMath.random() * ((max - min) + 1));
        return randomizer;
    }

    // Uses random() to generate a number favoring the lower values and then transforming to graph size
    public static int getSkewedNumber(int size) {
        int num = (int)(size * (1 - StrictMath.sqrt(StrictMath.random())));
        return num;
    }

    public static int getCustomNumber(int size) {
        int num = (int)(size * StrictMath.sqrt(StrictMath.random()));
        return num;
    }

    public static int getShuffledIndex(int i) {
        Random random = new Random();
        int num = random.nextInt(i + 1);
        return num;
    }

}
