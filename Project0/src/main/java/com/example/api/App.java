package com.example.api;
import java.util.Scanner;
import com.example.repo.DatabaseConnection;
import com.example.repo.Transaction;
import com.example.business.accounts;
import com.example.business.accounts.*;
import java.math.BigDecimal;
public class App 
{
    public static void main( String[] args )
    {
        int userInput = 0;
        Scanner scanner = new Scanner(System.in);
        Scanner pinScanner = new Scanner(System.in);
        Scanner amountSc = new Scanner(System.in);
        Scanner initalUserInput = new Scanner(System.in);
        accounts acc = new accounts();
        boolean userLogin = false;
        String pinInput = "";
        BigDecimal amount;
        int accountID;
        int recevierID;
        int currentAccountID = -1; // Initialize with a default value
        System.out.println("Welcome to Revanture bank");
        do
        {
            if(userLogin == false) {
            System.out.println("-----------------------");
            System.out.println("1->Login in");
            System.out.println("2->Register Account: ");
            System.out.println("3->Exit");
            System.out.println("-----------------------");
            userInput = scanner.nextInt();


            switch (userInput) {
                case 1:
                    {
                        System.out.println("Enter in account id");
                        accountID = initalUserInput.nextInt();
                        System.out.println("Enter in pin: ");
                        pinInput = initalUserInput.nextLine();
                        pinInput = initalUserInput.nextLine();
                        userLogin = acc.login(accountID, pinInput);
                        currentAccountID = accountID;
                        break;
                    }
                case 2:
                    {
                        System.out.println("Enter in pin to register account account id will be provided to you after pin is made ");
                        pinInput = initalUserInput.nextLine();
                        int accountCheck = acc.createAccount(pinInput);
                        if(accountCheck >= 1)
                        {
                            userLogin = true;
                            currentAccountID = accountCheck;
                        }
                        
                        break;
                    }
                case 3:
                    {
                        System.exit(0);
                        break;
                    }
            
                default:
                    System.out.println("Please choose an option from the menu above");
                    break;
            }
        }

            if(userLogin == true)
            {
            System.out.println("-------------------------");
            System.out.println("1->Depoist");
            System.out.println("2->Withdraw");
            System.out.println("3->Transfer");
            System.out.println("4->Get Balance");
            System.out.println("5->Transaction History");
            System.out.println("6->Logout");
            System.out.println("7->Exit");
            System.out.println("-------------------------");
            System.out.print("Select a choice -> ");
            userInput = scanner.nextInt();

            switch (userInput) {
                        
                case 1: //will ask user to enter in pin
                {  
                    System.out.println("Enter in depoist amount: ");
                    amount = amountSc.nextBigDecimal();
                    acc.depoist(pinInput, amount, currentAccountID);
                    System.out.println("Total balance is now: " + acc.getBalance(currentAccountID, pinInput));
                    break;
                }
                case 2: //this case deals with withdrawing from an account
                {
                    System.out.println("Enter in amount to withdraw: ");
                    amount = amountSc.nextBigDecimal();
                    acc.withdraw(currentAccountID, pinInput, amount);

                    break;
                }
                case 3: //this case deals with transferring
                {
                    System.out.println("Enter in amount to transfer: ");
                    amount = pinScanner.nextBigDecimal();
                    pinScanner.nextLine();
                    System.out.println("Enter the recipient's Account ID: ");
                    recevierID = pinScanner.nextInt();
                    acc.transfer(currentAccountID, pinInput, recevierID, amount);
                    break;
                }
                case 4: //this case just helps the user get their balance so they can make a better decision if they want to withdraw or transfer
                {
                    BigDecimal balance = acc.getBalance(currentAccountID, pinInput); //call the method once and then verifiy if its null or not
                    if(balance != null)
                    {
                        System.out.println("Balance of account is: " + balance);
                    }
                    else {
                        System.out.println("Could not retrive balance please try again");
                    }
                    break;
                }
                case 5:
                    {   
                    System.out.println("\n--- Transaction History ---");
                    java.util.List<Transaction> history = acc.getHistory(currentAccountID, pinInput);
                    if (history.isEmpty()) 
                    {
                        System.out.println("No transactions found.");
                    } 
                    else 
                    {
                        for (Transaction t : history) {
                            System.out.println(t);
                        }
                    }
                    break;
                    }
                case 6:
                    {
                        userLogin = false;
                        break;
                    }
                
            }
        }
        } while(userInput != 7);
    }
}
