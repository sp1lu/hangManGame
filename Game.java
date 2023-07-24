import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Game {
    public Game() {
    }

    //
    // Clean text and choose a word from it
    //
    public String chooseWordFromText(Game game, List<String> wordsToGuessList) {

        String userText;
        String wordToGuess;

        while (true) {
            Scanner reader = new Scanner(System.in);

            try {

                userText = reader.nextLine();

                String cleanedText = Util.cleanText(userText);

                wordsToGuessList = Arrays.asList(cleanedText.split(" "));
                wordToGuess = game.chooseWordtoGuess(wordsToGuessList);

                

                if (wordToGuess.length() >= 5) {
                    break;
                }

                System.out.println("Il testo che hai inserito non contiene parole che abbiano almeno cinque lettere.");
                System.out.println("Riprova con un altro testo.");

            } catch (InputMismatchException e) {
                System.out.println("Non fare il furbo; non hai inserito un numero!");
                System.out.println("Riprova con un altro testo.");

                reader.next();
            }

            reader.close();

        }

        return wordToGuess;
    }

    //
    // Fetch words-to-guess
    //
    public List<String> fetchWordsToGuess() {
        String wordsTxt = "words.txt";
        String line = "";
        List<String> wordsToGuessList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(wordsTxt))) {

            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                wordsToGuessList = Arrays.asList(words);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordsToGuessList;
    }

    //
    // Generates a random number and choose the word to guess
    //
    public String chooseWordtoGuess(List<String> wordsToGuessList) {
        int randomWordToGuessIndex = Util.generateRandom(wordsToGuessList);
        String wordToGuess = wordsToGuessList.get(randomWordToGuessIndex);

        return wordToGuess;
    }

    //
    // Creates an empty list and fills it with the word-to-guess letters
    //
    public List<String> splitWord(String wordToGuess) {
        List<String> charsWordToGuess = new ArrayList<>();
        for (int i = 0; i < wordToGuess.length(); i++) {
            charsWordToGuess.add(String.valueOf(wordToGuess.charAt(i)));
        }

        return charsWordToGuess;
    }

    //
    // Creates an empty list and fill it with "_" characters
    //
    public List<String> hideLetters(String wordToGuess) {
        List<String> charsHint = new ArrayList<>();
        for (int i = 0; i < wordToGuess.length(); i++) {
            charsHint.add("_");
        }

        return charsHint;
    }

    //
    // Ask for lives
    //
    public int howManyLives() {
        Scanner reader = new Scanner(System.in);
        int playerLives;

        while (true) {

            try {
                int userLifeChoiche = reader.nextInt();

                if (userLifeChoiche > 0 && userLifeChoiche <= 10) {
                    playerLives = userLifeChoiche;

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

        return playerLives;
    }

    //
    // Print current lives
    //
    public void printRemainingLives(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print("<3 ");
        }
    }

    //
    //  What to do if user misses the word
    //
    public void missMsg(Player player) {
        player.tries++;

        int lifeRemainNum = player.getLives() - player.getTries();

        System.out.println("--------------------------------------");
        System.out.println("Non hai indovinato.");
        System.out.println("Hai " + (lifeRemainNum) + " vite!");
        System.out.println();

        printRemainingLives(lifeRemainNum);

        System.out.println();
    }

    //
    // Give hint
    //
    public void giveHint(List<String> lettersList, List<String> hintsList, Stack<Integer> uniqueNums) {

        // Keep generating a random number between none and word-to-guess size until it finds a never-picked number
        int randCharHint;

        // It does it until there are hints to give user
        if (uniqueNums.size() != lettersList.size() - 1) {
            System.out.println("\nDai, ti aiuto.");

            while (true) {
                randCharHint = Util.generateRandom(lettersList);

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

    //
    // Game won
    //
    public void won(String wordToGuess, Player player) {
        System.out.println("--------------------------------------");
        System.out.println("Bravo, hai indovinato!");
        System.out.println("La parola era proprio \"%s\".".formatted(wordToGuess));
        System.out.println("--------------------------------------");

        player.tries++;
    }

    //
    // Game over
    //
    public void gameOver() {
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
}
