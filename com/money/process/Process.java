package com.money.process;

import java.sql.SQLException;
import java.util.Scanner;
import com.money.database.MoneyDatabase;
import com.money.validation.*;

public class Process extends Validation
{
	double remainingBalance;
	MoneyDatabase md = new MoneyDatabase();
	String name;
	Scanner sc = new Scanner(System.in);
	
	/*This is main method here the entire process execute. In this method it will take name of the user then it will display the options
	 * like credit, spend, details, exit these options will display to the user, Based on the user chosen option it will execute the respective
	 * operation.*/
	public void executionProcess()
	{
		this.remainingBalance = md.remainingBalance();//the remainingBalance method return the balance in double format and it is assigning to the non-static variable
		String name;
		String input;
		boolean loop = true;
		/*it will take the name from user and it will check whether the name contains only alphabets or not.
		 * if it doesn't contains alphabets or mixing of alphanumeric. it will display error message and repeat the process till user enter only alphabets.*/
		do 
		{
			System.out.println("Enter name:");
			name = sc.nextLine();
		} while (!nameValidation(name));/*Here in nameValidation method we are passing string as a argument and we are checking it contains only alphabets or not.
		if not contains it will return the false*/
		
		this.name = name;// it will assign the name to non-static variable name because name is also important.
		
		/*this loop will repeat till the user press exit option. Because we don't want to run the program everytime. so, when user don't need then user will press
		 * exit option the process or loop will stop our program will stop*/
		while(loop)
		{
			/*it will display the options and user need to choose one option. Here we are taking the option in string format.
			 * Because if we consider to take input option in integer, suddenly if user enter option in string i.e., "s" immediately our program will terminating.
			 * so to avoid the problem take input in string format and check using pattern class.*/
			do 
			{
				System.out.println("\n"+this.name+" choose the options\n\n1.Credit    2.Spend   3.Details   4.Exit\n:");
				input = sc.nextLine();
			} while (!executionProcess_validation(input));/*Here in execution process method we are passing input i.e., string
			as argument to the method. this execution process method will check the whether the string is matching with our pattern or not if not return false.*/
			
			switch(Integer.parseInt(input))//here converting string to int type using wrapper class 
			{
			   case 1:
				   credit(); //this method will take credit info
				   break;
			   case 2:
				   spend();//this method will take spend info
				   break;
			   case 3:
				   md.displayDetails();//this method used to display the details of credit or spend
				   break;
			   case 4:
				   md.connetionClose();//this method close the connection of our database. it's very important to close the database connection.
					sc.close();
				   loop = false;//it will assign false to loop variable and the looping will stop.
			}
		}
	}
	/*This method is used for how much amount credited and who gave the amount i.e., person name*/
	protected void credit()
    {
    	String creditName;
    	String creditAmount;
    	String conformation;
    	/*Take the input of credit amount and check, whether it is in number format or not. it is taking input in string format.
    	 * In case if we enter input in String i.e.,"s",then the program is terminating.
    	 * so for this reason take input in the form of string and check using pattern class. */
    	do
		{
			System.out.println("\n"+this.name+" Enter how much money \'Rs/-\' given to You : ");
    		creditAmount = sc.nextLine();
		} while(!moneyFormat(creditAmount));//here in moneyFormat method we are passing string and checking whether it is matches with our pattern or not.
    	
    	/*it will take the user name in the form of string and check the input should contains only alphabets
    	 * if not display error and repeat the process till user enter correct format*/
    	do 
    	{
    		System.out.println("\n"+this.name+" Enter name who given \" "+creditAmount+" \"Rs/- : ");
    		creditName = sc.nextLine();
		} while(!nameValidation(creditName));/*Here in nameValidation method we are passing string as a argument and we are checking it contains only alphabets or not.
		if not contains it will return the false*/
		
    	/*it is used to conform the entered name and amount.Here Taking the input in string format and checking whether user enter
    	 * only (either 'Y' or 'N') option.if user doesn't press ('Y' | 'N') immediately display an error and repeat the process till user enter
    	 * only (y or n) options. */
		do 
		{
			System.out.println("\n\" "+creditName+" \" gave you \" "+creditAmount+"\" Rs/-");
			System.out.println("\nconform [Y / N] : ");
			conformation = sc.nextLine();
		} while (!yes_no_validation(conformation));
		
		/*Condition if user press 'y' add details to account.
		 * if user press 'n' it repeat the same process for correct details.*/
		if(conformation.equalsIgnoreCase("y"))
		{
			this.remainingBalance += Double.parseDouble(creditAmount);//converting string i.e., creditAmount into double using wrapper class and adding to remainingBalance.
			
			/*adding credit details to database*/
			try 
			{
				md.addCreditDetails(this.name, creditName, Double.parseDouble(creditAmount),remainingBalance);
			} 
			catch (SQLException e) {}
		}
		/*if user press 'N' option repeat this method.*/
		else
		{
			System.out.println("\n\t!! Enter correct details !!\n");
			credit();
		}
    }
	
	/*This method is used for how much amount spend and for what reason*/
	protected void spend()
	{
		String spendAmount;
		String spendReason;
		String yn;
		/*Check Remaining Balance not equal to Zero(0). If Balance not equal to zero continue process.
		 * else displaying the message i.e,'NO BALANCE AVAILABLE' */
		
		if(this.remainingBalance != 0)
		{
			/*taking the input,how much amount did user spend. Here also taking the input in the form of string
			 * because if i take input in double in some cases if user entered string the program is terminating.
			 * so take input in string format then check whether the input should contains only number or not. if any alphabets is there immediately 
			 * display error message and repeat the process till user enter correct format. */
			do 
			{
				System.out.println("Enter how much amount you spend : ");
				spendAmount = sc.nextLine();
			} while (!moneyFormat(spendAmount));
			
			/*Check SpendAmount should be less than remaining Balance or not. If spendAmount is less than remaining  balance continue to next process.
			 * else display an error message.'miss-match happened'*/
			
			if(Double.parseDouble(spendAmount) <= this.remainingBalance) 
			{
				/*taking for what reason he spend the amount. Taking the input in the form of string. */
				System.out.println("Enter for what reason you spend \""+spendAmount+"\" Rs/- : ");
				spendReason = sc.nextLine();
				
				/*it is used to conform the entered spendAmount and Reason.Here Taking the input in string format and checking whether user enter
		    	 * only (either 'Y' or 'N') option.if user doesn't press ('Y' | 'N') immediately display an error and repeat the process till user enter
		    	 * only (y or n) options. */
				do 
				{
					System.out.println("you spend  \""+spendAmount+"\" for \""+spendReason+"\"");
					System.out.println("\n conform [Y / N]");
					yn = sc.nextLine();
				} while (!yes_no_validation(yn));
				
				/*condition if user press 'Y' add the details to account. if user press 'N' again repeat this method.*/
				if(yn.equalsIgnoreCase("y"))
				{
					this.remainingBalance -= Double.parseDouble(spendAmount);/*converting string i.e., spendAmount to double using wrapper class and 
					subtracting from main balance*/
					
					/*adding data to the database */
					try 
					{
						md.addSpendDetails(this.name, spendReason, Double.parseDouble(spendAmount), this.remainingBalance);
					} 
					catch (NumberFormatException | SQLException e) {}
				}
				/*if user press 'N' option.*/
				else
				{
					System.out.println("\n");
					spend();
				}
			}
			/*Displaying message.if spend amount is less than remaining balance */
			else
			{
				System.out.println("\n");
				System.out.println(this.name+"\t!!mis match happend you spend amount \" "+spendAmount+" \" Rs/- But your balance is \" "+this.remainingBalance+" \" Rs/- !!\n");
			}	
		}
		/*displaying message. If there is no balance.*/
		else
		{
			System.out.println("\n\t There is no balance. check your balance once "+this.remainingBalance+"\n");
			/*display the option to check the remaining balance */
		}
	}
}
