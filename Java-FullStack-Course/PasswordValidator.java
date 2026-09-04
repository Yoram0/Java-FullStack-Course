import java.util.Scanner;

public class PasswordValidator {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("The password must be 8 Characters");
        System.out.println("Contain at least one uppercase letter");
        System.out.println("Contain at least one number");
        System.out.println("Enter in password: ");
        String password = sc.nextLine();

        char[] charArray = password.toCharArray();
        boolean upperCounter = false;
        boolean numCounter = false;
        boolean lowerBool = false;

        int lengthofArray = charArray.length;

        if(lengthofArray < 8)
        {
            System.out.println("Invaild password try again");
            return;
        }

        for(int i = 0; i<=lengthofArray-1;i++)
        {

           if(Character.isUpperCase(charArray[i])) //loops through char array to see if any char is uppercase if so set boolean to true
           {
                upperCounter = true;
           }
           if(Character.isDigit(charArray[i])) //sets boolean to true if there is a digit in the char array
           {
                numCounter = true;
           }

           if(Character.isLowerCase(charArray[i]))
           {
               lowerBool = true;
           }
            // System.out.println("Counter check: " + lowerBool); //was just using to test output
        }

        if(lowerBool && numCounter && upperCounter)
        {
            System.out.println("Valid password");
        }
        else {
            System.out.println("Invalid password");
        }




    }
}
