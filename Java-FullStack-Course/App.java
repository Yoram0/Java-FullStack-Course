import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        //Challenege Questions: Control Flow
        /*
        Scanner scanner = new Scanner(System.in);
        int grade = scanner.nextInt(); //takes in user input for the grade
        if(grade >= 50) //if grade is >= than 50 
        {
            
             System.out.println("Passed");
        }
        else
        {
            System.out.println("Failed");
        }

        if(grade >= 90)
        {
            System.out.println("Grade: " + 'A');
        }
        else if(grade >= 75 && grade <= 89)
        {
            System.out.println("Grade: " + 'B');
        }
        else if(grade >= 60 && grade <= 74)
        {
            System.out.println("Grade: " + 'C');
        }
        else
        {
            System.out.println("Grade: " + 'D');
        }
        */
        // End of Control flow challenge question

        //Start of challenge question: loops

        System.out.print("For Loop: ");
        for(int i = 0; i<=5;i++)
        {
            System.out.print(" " + i);
        }
        System.out.print("\n");
        System.out.print("While Loop: ");
        int i = 0;
        while(i <= 5)
        {
            System.out.print(i + " ");
            i++;
        }
        i = 0;
        System.out.println();
        System.out.print("Do While Loop: ");

        do
        {
            System.out.print(" " + i++);
        }
        while(i <= 5);

        // Calculator Challenge Question
        double num1 = 7;
        double num2 = 3;
        char operator1 = '+';
        char operator2 = '-';
        char operator3 = '/';
        char operator4 = '*';
        String again = "y";
        System.out.println();
        while(again == "y")
        {
            double result = num1 + num2;
            System.out.println("Result: " + result);
            again = "n";
        }

    }

    
}
