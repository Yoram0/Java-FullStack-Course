import java.util.Scanner;
import java.util.Arrays;

public class CalculateTestScores {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter in numbers make sure to space them out with one space: ");
        String scores = sc.nextLine(); //this will throw an error/expection if

        int[] numbers = Arrays.stream(scores.split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

        
        int average = 0;
        int totalScores = numbers.length; //gets the length of the array
        int maxScore = numbers[0]; //initailze it with the first number of the array
        int minScore = numbers[0];
        for(int number : numbers) //for each loop just to get the sum and then divide that by the length of the array
        {
            average += number;
        }
        System.out.println("Average scores: " + average / totalScores);
        
        for(int i = 0; i<numbers.length; i++)
        {
            if(numbers[i] > maxScore) // this will get the highest test score
            {
                maxScore = numbers[i];
            }
            if(numbers[i] < minScore)
            {
                minScore = numbers[i];
            }
        }

        System.out.println("Total: " + average);
        System.out.println("Average: " + average / totalScores);
        System.out.println("Highest: " + maxScore);
        System.out.println("Lowest: " + minScore);

    }
    
}
