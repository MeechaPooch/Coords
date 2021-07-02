package meechapooch.coords.utils;

import java.util.Random;

public class Randomz {
    public static Random r = new Random();
    public static int intRange(int min, int max) {
        return r.nextInt(max-min)+min;
    }
}
