import java.io.FileNotFoundException;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Hangman {

    ArrayList<Character> guessedLetters = new ArrayList<Character>(
            chosenWord.length());


    public void setChosenLetter(char chosenLetter) {
        this.chosenLetter = chosenLetter;
    }

    public void setGuessedLetters(ArrayList<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public void readNextChar() {
        char chosenLetter = readNext.charAt(0);
    }

    public static void isGuessed() {
        if (!guessedLetters.contains(chosenLetter)) {
            guessedLetters.add(chosenLetter);
        }
    }

    public static void main(String[] args) {
        try {
            @SuppressWarnings("resource")
            BufferedReader txtReader = new BufferedReader(new FileReader("src/Resources/Hangman.txt"));
            String line = txtReader.readLine();
            ArrayList<String> words = new ArrayList<String>();
            while (line != null) {
                String[] wordsLine = line.split(" ");
                for (String word : wordsLine) {
                    words.add(word);
                }
                line = txtReader.readLine();
            }

            

            Random random = new Random();
            String randomWord = words.get(random.nextInt(words.size()));

            StringBuilder chosenWord = new StringBuilder("none");
            chosenWord = new StringBuilder(randomWord);

        } catch (Exception exception) {
            System.err.printf("Exception caught: %s", exception.toString());
            System.exit(0);

        }


    }

}