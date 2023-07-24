import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Util {

    //
    // Generate random numbers
    //
    public static int generateRandom(List<String> arr) {

        Random rng = new Random();
        int num = rng.nextInt(arr.size());

        return num;

    }

    //
    // Print game logs
    //
    public static void createGameLog(String wordToGuess, Player player, Boolean hasWon, List<String> triedWordsArray) {
        String log = "./gameLog.csv";

        String triedWordsString = String.join(",", triedWordsArray);
        String gameData = wordToGuess + ","
                        + player.getLives() + ","
                        + player.getTries() + ","
                        + hasWon + ","
                        + triedWordsString;

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(log, true);
            fileWriter.append("\n");
            fileWriter.append(gameData);

            System.out.println("Log creato");

        } catch (Exception e) {
            System.out.println("Errore nalla creazione del log");
            e.printStackTrace();

        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {
                System.out.println("Errore durante il flush del filewriter");
                e.printStackTrace();
            }

        }
    }
}
