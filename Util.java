import java.util.Random;
import java.util.List;

public class Util {

    public static int generateRandom(List<String> arr) {

        Random rng = new Random();
        int num = rng.nextInt(arr.size());

        return num;

    }
}
