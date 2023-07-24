import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // Creates a list of words and fills it from txt file
        Game game = new Game();
        List<String> wordsToGuessList = new ArrayList<>();
        wordsToGuessList = game.fetchWordsToGuess();

        // Generates a random number to choose the word to guess
        Random rng = new Random();
        int randomWordToGuessIndex = rng.nextInt(wordsToGuessList.size());

        String wordToGuess = wordsToGuessList.get(randomWordToGuessIndex);

        // Creates an empty list and fills it with the word-to-guess letters
        List<String> charsWordToGuess = new ArrayList<>();
        for (int i = 0; i < wordToGuess.length(); i++) {
            charsWordToGuess.add(String.valueOf(wordToGuess.charAt(i)));
        }

        // Creates an empty list and fill it with "_" characters
        List<String> charsHint = new ArrayList<>();
        for (int i = 0; i < wordToGuess.length(); i++) {
            charsHint.add("_");
        }

        // Creates an empty list; it'll be useful later!
        Stack<Integer> uniqueNums = new Stack<>();

        System.out.println("--------------------------------------");
        System.out.println("Indovina la parola a cui sto pensando!");
        

        // Choose how many tries you wanna have
        System.out.println("Dai, ti faccio scegliere. Quante vite vuoi avere?");
        Player player = new Player(game.howManyLives());

        System.out.println("Hai " + player.getLives() + " vite:");

        for (int i = 0; i < player.getLives(); i++) {
            System.out.print("<3 ");
        }

        // The game begins!
        System.out.println("\nSto pensando ad una parola di " + charsWordToGuess.size() + " lettere");
        System.out.println("\nQual e' la tua risposta?");

        String userGuess;

        // Start asking guesses to user
        while (player.getTries() != player.getLives()) {

            userGuess = reader.nextLine().toLowerCase();

            // Escape winning condition
            if (userGuess.equals(wordToGuess)) {
                System.out.println("--------------------------------------");
                System.out.println("Bravo, hai indovinato!");
                System.out.println("La parola era proprio \"%s\".".formatted(wordToGuess));
                System.out.println("--------------------------------------");

                break;

                // What to do if user misses the word
            } else {
                player.tries++;

                int lifeRemainNum = player.getLives() - player.getTries();

                System.out.println("--------------------------------------");
                System.out.println("Non hai indovinato.");
                System.out.println("Hai " + (lifeRemainNum) + " vite!");
                System.out.println();

                // Print remaining lives
                for (int i = 0; i < lifeRemainNum; i++) {
                    System.out.print("<3 ");
                }

                System.out.println();

                if (player.tries == player.getLives()) { // What to do if user losses his whole lives
                    game.gameOver();

                    break;

                } else { // What to do if user has still some lives
                    game.giveHint(charsWordToGuess, charsHint, uniqueNums);

                }
            }
        }

        reader.close();

    }
}