import java.lang.Math;

public class Utility {

    // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
    public static int get_random_number(int min, int max) {
        int randomizer = min + (int)(Math.random() * ((max - min) + 1));
        return randomizer;
    }
}
