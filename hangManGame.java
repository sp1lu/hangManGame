import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class hangManGame {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        /* System.out.println("Inserisci una frase");
        String userPhrase = reader.nextLine();
        
        List<String> splittedPhrase = Arrays.asList(userPhrase.replaceAll("[^\\p{L}\\s]", "").split(" "));
        
        List<String> wordsToGuessList = new ArrayList<>();
        for (String word : splittedPhrase) {
            wordsToGuessList.add(word);
        }
        
        System.out.println(wordsToGuessList); */

        // Creates a list of words and fills it
        List<String> wordsToGuessList = new ArrayList<>();

        wordsToGuessList.add("episodio");
        wordsToGuessList.add("sindaco");
        wordsToGuessList.add("ultimo");
        wordsToGuessList.add("colorato");
        wordsToGuessList.add("innocente");
        wordsToGuessList.add("borraccia");
        wordsToGuessList.add("tastiera");
        wordsToGuessList.add("acqua");
        wordsToGuessList.add("playstation");
        wordsToGuessList.add("palazzo");
        wordsToGuessList.add("colonna");
        wordsToGuessList.add("registro");
        wordsToGuessList.add("burocrazia");
        wordsToGuessList.add("videogioco");
        wordsToGuessList.add("impiccato");

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

        int userTry = 0;
        int lifeNum = howManyLives();

        System.out.println("Hai " + lifeNum + " vite:");

        for (int i = 0; i < lifeNum; i++) {
            System.out.print("<3 ");
        }

        // The game begins!
        System.out.println("\nSto pensando ad una parola di " + charsWordToGuess.size() + " lettere");
        System.out.println("\nQual e' la tua risposta?");
        /* String userGuess = reader.nextLine(); */

        String userGuess;

        // Start asking guesses to user
        while (userTry != lifeNum) {

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
                userTry++;

                int lifeRemainNum = lifeNum - userTry;

                System.out.println("--------------------------------------");
                System.out.println("Non hai indovinato.");
                System.out.println("Hai " + (lifeRemainNum) + " vite!");
                System.out.println();

                // Print remaining lives
                for (int i = 0; i < lifeRemainNum; i++) {
                    System.out.print("<3 ");
                }

                System.out.println();

                if (userTry == lifeNum) { // What to do if user losses his whole lives
                    gameOver();

                    break;

                } else { // What to do if user has still some lives
                    giveHint(charsWordToGuess, charsHint, uniqueNums);

                }
            }
        }

        reader.close();

    }

    ////////////////////////////////////////////////////////////////
    // How many lives?
    ////////////////////////////////////////////////////////////////

    public static int howManyLives() {
        Scanner reader = new Scanner(System.in);
        int number;

        while (true) {

            try {
                int userLifeChoiche = reader.nextInt();

                if (userLifeChoiche > 0 && userLifeChoiche <= 10) {
                    number = userLifeChoiche;

                    break;

                } else if (userLifeChoiche < 0) {
                    System.out.println("Numeri negativi? Really?!");
                    System.out.println("Riprova. Quante vite vuoi avere?");

                    continue;

                } else if (userLifeChoiche == 0) {
                    System.out.println("Zero vite? Sei un fantasma o sei solo stupido?");
                    System.out.println("Riprova. Quante vite vuoi avere?");

                    continue;

                } else {
                    System.out.println("Non esagerare! Sono buono, mica stupido! Più di 10 no, dai...");
                    System.out.println("Riprova. Quante vite vuoi avere?");

                    continue;
                }

            } catch (InputMismatchException e) {
                System.out.println("Non fare il furbo; non hai inserito un numero!");
                System.out.println("Riprova. Quante vite vuoi avere?");

                reader.next();
            }

            reader.close();

        }

        return number;
    }

    ////////////////////////////////////////////////////////////////
    // Game Over
    ////////////////////////////////////////////////////////////////

    public static void gameOver() {
        System.out.println("--------------------------------------");
        System.out.println("Ops, hai perso tutte le vite!");
        System.out.println("  +---+");
        System.out.println("  |   |");
        System.out.println("  O   |");
        System.out.println(" /|\\  |");
        System.out.println(" / \\  |");
        System.out.println("      |");
        System.out.println("=======");
        System.out.println("GAME OVER!");
        System.out.println("--------------------------------------");
    }

    ////////////////////////////////////////////////////////////////
    // Give user hint
    ////////////////////////////////////////////////////////////////

    public static void giveHint(List<String> lettersList, List<String> hintsList,
            Stack<Integer> uniqueNums) {

        // Keep generating a random number between none and word-to-guess size until it finds a never-picked number
        int randCharHint;

        // It does it until there are hints to give user
        if (uniqueNums.size() != lettersList.size() - 1) {
            System.out.println("\nDai, ti aiuto.");

            while (true) {
                Random rng = new Random();
                randCharHint = rng.nextInt(lettersList.size());

                if (!uniqueNums.contains(randCharHint)) {
                    uniqueNums.add(randCharHint);

                    break;
                }
            }

            // Than pick letter at random generated number index...
            // ...and register its index
            int lastRandNum = uniqueNums.lastElement();

            String hint = lettersList.get(lastRandNum);

            // Set at that index the corrisponding letter in the word-to-guess...
            //...and print the hint
            hintsList.set(lastRandNum, hint);

            for (int i = 0; i < hintsList.size(); i++) {

                if (!hintsList.get(i).equals("_")) {
                    System.out.print(hintsList.get(i) + " ");

                } else {
                    System.out.print("_ ");
                }
            }

        } else { // If it has no hints to give anymore
            System.out.println("Davvero?! Dai, ti sto dando la soluzione!");
            for (String letter : lettersList) {
                System.out.print(letter + " ");
            }
        }

        // Print a "try again" message anyway
        System.out.println();
        System.out.println("\nRiprova ora:");
    }
}