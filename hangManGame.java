import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class hangManGame {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // Declarations
        String wordToGuess = "cinghiale";

        ArrayList<String> charsWordToGuess = new ArrayList<String>(
                Arrays.asList("c", "i", "n", "g", "h", "i", "a", "l", "e"));

        ArrayList<String> charsHint = new ArrayList<String>(
                Arrays.asList("_", "_", "_", "_", "_", "_", "_", "_", "_"));

        int lifeNum = 5;
        int userTry = 0;

        System.out.println("--------------------------------------");
        System.out.println("Indovina la parola a cui sto pensando!");
        System.out.println("Sto pensando ad una parola di " + charsWordToGuess.size() + " lettere");
        System.out.println("Hai " + lifeNum + " vite:");

        for (int i = 0; i < lifeNum; i++) {
            System.out.print("<3 ");
        }

        System.out.println("\nQual e' la tua risposta?");

        // Start asking guesses to user
        while (userTry != lifeNum) {

            String userGuess = reader.nextLine();

            // Escape winning condition
            if (userGuess.equals(wordToGuess)) {
                System.out.println("--------------------------------------");
                System.out.println("Bravo, hai indovinato!");
                System.out.println("La parola era proprio 'cinghiale'.");
                System.out.println("--------------------------------------");

                break;

            } else { // What to do is user misses the word
                userTry++;

                int lifeRemainNum = lifeNum - userTry;

                System.out.println("--------------------------------------");
                System.out.println("Non hai indovinato.");
                System.out.println("Hai " + (lifeRemainNum) + " vite!");
                System.out.println();

                for (int i = 0; i < lifeRemainNum; i++) { // Print remaining lives
                    System.out.print("<3 ");
                }

                System.out.println();

                if (userTry == lifeNum) { // What to do if user losses his whole lives
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

                    break;

                } else { // What to do if user has still some lives

                    System.out.println("\nDai, ti aiuto.");

                    // Show user an hint: which letter is found in a certain position
                    // Generate a random number between 0 and word-to-guess size
                    // Than pick letter at random number index...
                    // ...and register its index
                    Random r = new Random();
                    int ranCharHint = r.nextInt(charsWordToGuess.size());

                    String hint = charsWordToGuess.get(ranCharHint);
                    int hintPosition = charsWordToGuess.indexOf(hint);

                    System.out.println("La lettera n." + (hintPosition + 1) + " e' " + "la " + "'" + hint + "'");

                    // We have a list full of "_" characters
                    // Removes "_" character from "_" char list at picked hint index
                    // Replaces removed "-" character with right letter at that index
                    // Check "_" list: 
                    //      - if it finds "_" it prints "_" again
                    //      - otherwise it prints the picked letter
                    charsHint.remove(hintPosition);
                    charsHint.add(hintPosition, hint);

                    for (int i = 0; i < charsHint.size(); i++) {

                        if (charsHint.get(i).equals("_")) {
                            System.out.print("_ ");

                        } else {
                            System.out.print(charsHint.get(i) + " ");
                        }

                    }

                    System.out.println();
                    System.out.println("\nRiprova ora:");

                }
            }
        }

        reader.close();

    }
}