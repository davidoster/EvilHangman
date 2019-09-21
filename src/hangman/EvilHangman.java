package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class EvilHangman {
    public static void main(String[] args) {
        File wordBase = new File(args[0]);
        String usageMsgGuess = "Word length must be >= 2", usageMsgWordLen = "Number of guesses must be >= 1.";
        int wordLen = Integer.parseInt(args[1]), numGuess = Integer.parseInt(args[2]);

        // Validate user input for wordLength and numGuesses
        if(wordLen < 2) System.out.println(usageMsgWordLen);
        if(numGuess < 1) System.out.println(usageMsgGuess);
        // Create an instance of the game (unstarted)
        EvilHangmanGame evilGame = new EvilHangmanGame();
        // Attempt to start the game with the dictionary given by the user and the wordLen
        try {
            evilGame.startGame(wordBase, wordLen);

        }
        catch (IOException e) { e.printStackTrace(); }
        catch (EmptyDictionaryException e) { System.out.println(e.getMessage()); }

        // Game loop: while the user still has guesses left, allow them to guess
        while(numGuess > 0 ) {
            prompt(wordLen, numGuess, evilGame);
            try {
                char userGuess = getUserInput();
                evilGame.makeGuess(userGuess);
                numGuess--;
                if(numGuess != 0) System.out.printf("Sorry, there are no %c\'s\n\n", userGuess);
            } catch (GuessAlreadyMadeException e) { System.out.println(e.getMessage()); }
        }
        System.out.println("You lose!!");
        System.out.printf("The word was: %s\n", "FIXME");
    }

    public static char getUserInput() {
        char userInput = ' ';
        while (!Character.isAlphabetic(userInput)) {
            System.out.print("Enter guess: ");
            userInput = new Scanner(System.in).next().charAt(0);
            if(!Character.isAlphabetic(userInput)) System.out.println("Invalid input");
        }
        return userInput;
    }

    public static void prompt(int wordLen, int numGuess, EvilHangmanGame eV) {
        System.out.printf("You have %d guesses left\n", numGuess);
        System.out.print("Used letters: ");
        if(!eV.guessedLetters.isEmpty()) {
            for(Character c : eV.guessedLetters) System.out.printf("%c ", c);
            System.out.println();
        }
        System.out.printf("Word: %s\n", new String(new char[wordLen]).replace("\0", "-"));
    }

}