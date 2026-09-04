import java.util.Scanner;

public class WordAnalyzerQ {
    public static void main (String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to word analyzer");
        // variables used as counters to store the amount of vowels constoants digits etc
        String userInput = sc.nextLine();
        int digitCounter = 0;
        int consoantCounter = 0;
        int vowelCounter = 0;
        int spaceCounter = 0;

        char[] charArray = userInput.toCharArray();

        int lengthofArray = charArray.length;

        for(int i = 0; i<=lengthofArray-1; i++)
        {
            if(Character.isDigit(charArray[i]))
            {
                digitCounter++;
                lengthofArray--;
            }
            if(charArray[i] == ' ')
            {
                    spaceCounter++;
                    lengthofArray--;
            }
            if(Character.isLetter(charArray[i])) //if statement that checks if the car is a letter
            { //then another if statement that will check if its a vowel
                if ("AEIOUaeiou".indexOf(charArray[i]) != -1) {
                    System.out.println(charArray[i] + " is a vowel.");
                    vowelCounter++;
                } else { //if not a vowel then a constant so just use a else statement
                    System.out.println(charArray[i] + " is a consonant.");
                    consoantCounter++;
                }
            }
        }
        System.out.println("Characters: " + lengthofArray);
        System.out.println("Vowels: " + vowelCounter);
        System.out.println("Consonants: " + consoantCounter);
        System.out.println("Digit: " + digitCounter);
        System.out.println("Spaces: " + spaceCounter);
        
    }
}
