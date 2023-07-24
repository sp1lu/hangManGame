import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // Initialize game and create player
        Game game = new Game(); // Creates a list of words and fills it from txt file
        List<String> wordsToGuessList = new ArrayList<>();
        /* wordsToGuessList = game.fetchWordsToGuess(); */

        System.out.println("Inserire un testo");
        String userText = reader.nextLine();
        String cleanedText = game.cleanText(userText);

        wordsToGuessList = Arrays.asList(cleanedText.split(" "));
        String wordToGuess = game.chooseWordtoGuess(wordsToGuessList);

        /* String wordToGuess = game.chooseWordtoGuess(wordsToGuessList); */ // Generates a random number to choose the word to guess
        List<String> charsWordToGuess = game.splitWord(wordToGuess); // Creates an empty list and fills it with the word-to-guess letters
        List<String> charsHint = game.hideLetters(wordToGuess); // Creates an empty list and fill it with "_" characters
        Stack<Integer> uniqueNums = new Stack<>(); // Creates an empty stack; it'll be useful later!

        System.out.println("--------------------------------------");
        System.out.println("Indovina la parola a cui sto pensando!");
        System.out.println("Dai, ti faccio scegliere. Quante vite vuoi avere?");

        Player player = new Player(game.howManyLives()); // Choose how many tries you wanna have and print them
        System.out.println("Hai " + player.getLives() + " vite:");

        game.printRemainingLives(player.getLives());

        // The game begins!
        System.out.println("\nSto pensando ad una parola di " + charsWordToGuess.size() + " lettere");
        System.out.println("\nQual e' la tua risposta?");

        String userGuess;

        // Start asking guesses to user
        Boolean hasWon = false;
        List<String> triedWords = new ArrayList<String>();

        while (player.getTries() != player.getLives()) {

            userGuess = reader.nextLine().toLowerCase();
            triedWords.add(userGuess);

            if (userGuess.equals(wordToGuess)) { // Escape winning condition
                game.won(wordToGuess, player);

                hasWon = true;

                break;

            } else { // What to do if user misses the word
                game.missMsg(player);

                if (player.getTries() == player.getLives()) { // What to do if user losses his whole lives
                    game.gameOver();

                    break;

                } else { // What to do if user has still some lives
                    game.giveHint(charsWordToGuess, charsHint, uniqueNums);

                }
            }
        }

        reader.close();

        Util.createGameLog(wordToGuess, player, hasWon, triedWords);

    }
}