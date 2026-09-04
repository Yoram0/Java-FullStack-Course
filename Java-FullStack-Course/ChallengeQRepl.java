import java.util.Scanner;

public class ChallengeQRepl
{
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int balanceAmount = 0;
        double depoistAmount;
        System.out.println("1->Check Balance \n 2->Depoist \n 3->Withdraw \n 3->Exit: ");
       
        int userInput = sc.nextInt();
        int counter = 0;
        while(userInput != 4 || userInput >= 0)
        {
            userInput = sc.nextInt();
            switch (userInput) {
                case 1:
                    {
                        System.out.println(balanceAmount);
                    }
                case 2:
                    {
                        System.out.println("Depoist Amount: " );
                        depoistAmount = sc.nextInt();
                        break;
                    }
                case 3:
                    {
                        
                    }
                case 4:
                    {
                        
                    }
            }
            

            
        }
    }
}