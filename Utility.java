import java.lang.Math;

public class Utility {

    // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
    public static int getRandomNumber(int min, int max) {
        int randomizer = min + (int)(Math.random() * ((max - min) + 1));
        return randomizer;
    }

    public static int getSkewedNumber(int min, int max) {
        double x = Math.pow(Math.random(), 2);
        int num = (int)(min + x * (max - min + 1));
        return num;
    }
}