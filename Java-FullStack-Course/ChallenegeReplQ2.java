import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ChallenegeReplQ2 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to my REPL App");
        System.out.println("Available Commmands: ");

        while(true)
        {
            System.out.println(" 1->Add \n 2->Subtract \n 3->Multiply \n 4->Divide \n 5->Random \n 6->Reverse \n 7->Quit");
            System.out.print(">");
            int userInput = sc.nextInt();
            

            switch (userInput) {
                case 1: //add case
                    {
                        System.out.print("Enter first number: ");
                        int number1 = sc.nextInt();
                        System.out.print("Enter second number: ");
                        int number2 = sc.nextInt();
                        int result = number1 + number2;
                        System.out.println("Result: " + result);
                        break; //breaks out of the switch case that asks user to enter in a number again
                    }
                case 2: //subtract case
                    {
                        System.out.print("Enter first number: ");
                        int number1 = sc.nextInt();
                        System.out.print("Enter second number: ");
                        int number2 = sc.nextInt();
                        int result = number1 - number2;
                        System.out.println("Result: " + result);
                        break;
                    }
                case 3: //Multiply case
                    {
                        System.out.print("Enter first number: ");
                        int number1 = sc.nextInt();
                        System.out.print("Enter second number: ");
                        int number2 = sc.nextInt();
                        int result = number1 * number2;
                        System.out.println("Result: " + result);
                        break;
                    }
                case 4: //Divide case
                    {
                        System.out.print("Enter first number: ");
                        double number1 = sc.nextInt();
                        System.out.print("Enter second number: ");
                        double number2 = sc.nextInt();
                        double result = number1 / number2;
                        System.out.println("Result: " + result);
                    }
                case 5: //Random number case
                    {
                        System.out.println("Enter high number: ");
                        int high = sc.nextInt();
                        System.out.println("Enter low number: ");
                        int low = sc.nextInt();
                        int randomNum = ThreadLocalRandom.current().nextInt(low, high + 1);
                        System.out.println("Random number: " + randomNum);
                        break;
                    }
                case 6: //Reverse a string case
                    {
                        System.out.println("Enter a string: ");
                        String userString = sc.next();
                        int length = userString.length();
                        System.out.println("Length: " + length);
                        for(int i = length - 1; i >= 0; i--)
                        {
                            char c = userString.charAt(i);
                            System.out.print(c);
                        }
                        break;
                    }
                case 7:
                    {
                        return;
                    }
                default:
                {
                    System.out.println("Incorrect input please type a number from the following list of commands");
                }
            }
        }
        
    }
}