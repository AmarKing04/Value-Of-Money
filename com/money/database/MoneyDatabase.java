package com.money.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import com.money.validation.Validation;

public class MoneyDatabase extends Validation
{
	Statement st =null;
	ResultSet rs=null;
	static Connection con=null;
	String query;
	String queryRemainigBalance;
	String selectQuery;
	
	/* consider static, Because when we execute the program first static blocks will execute.
	 * when we execute the program first it will connect to jdbc.*/
	static
	{
		try 
		{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/value_of_money","root","king");
		} 
		catch (SQLException e) {}
	}
	
	/*This is method will return remaining Balance.It will take only remainingBalance column from database and it will return that balance.*/
	public double remainingBalance()
	{
		try 
		{
			st = con.createStatement();
			rs = st.executeQuery("SELECT remainingBalance FROM person"); //this query will select only remainigBalance column from database.
			while(rs.next())
			{
				return rs.getDouble("remainingBalance");
			}
		} 
		catch (Exception e) {}
		return 0.0;
	}
	
	/*This method is used for add the credit details (i.e.., name,creditName, creditAmount,RemainngBalance) to the database.*/
	public void addCreditDetails(String name,String creditName,double creditAmount,double remainingBalance) throws SQLException 
	{
		st = con.createStatement();
		query = "INSERT INTO person (name,creditName,creditAmount) VALUES(\'"+name+"\',\'"+creditName+"\',"+creditAmount+")";
		st.executeUpdate(query);
		
		/*it will update the remaining Balance*/
		updateBalance(name, remainingBalance);
		st.close();
	}
	
	/*This method is used to add the spend details (i.e., spendReason, spendAmount, remainingBalance)  to the database.*/
	public void addSpendDetails(String name,String spendReason,double spendAmount,double remainingBalance) throws SQLException 
	{
		st = con.createStatement();
		query = "INSERT INTO person (name,spendReason,spendAmount) VALUES(\'"+name+"\',\'"+spendReason+"\',"+spendAmount+")";
		st.executeUpdate(query);
		
		/*it will update the remaining Balance*/
		updateBalance(name, remainingBalance);
		st.close();
	}
	
	/*This method will update the remainingBalance column in the database.*/
	protected void updateBalance(String name,double remainingBalance) throws SQLException
	{
		st = con.createStatement();
		queryRemainigBalance = "UPDATE person SET remainingBalance="+remainingBalance+"where name=\'"+name+"\'";
		st.executeUpdate(queryRemainigBalance);
		st.close();
	}
    
	/*This method is used to display the options to choose which details has to display either credit or spend details*/
	public void  displayDetails()
	{
		String input;
		/*It will display the 2 options and choose one option. taking the option in string format 
		 *Because if we taking in input in number, suddenly if user give input in string the our program will get terminating.
		 *so to avoid this kind of error , take input in string format and checking whether it is our required format or not.
		 *If it is not in our required format the it again repeat.*/
		do 
		{
			System.out.println("\nChoose Which Details You Need\n 1.Credit Details    2.Spend Details\n:");
			input = new Scanner(System.in).nextLine();
			
		} while (!executionProcess_validation(input));//Here i pass one method it will return true or false.if false it will repeat the loop.
		
		/*Switch case used bases on our option it will perform the operation*/
		switch(Integer.parseInt(input))//here i am converting string into primitive input using wrapper class.
		{
		     case 1:
		    	 try 
					{
						creditDetails(); // it will sql exception so, try catch has to use.
					} 
		    	 catch (SQLException e1) {}
		    	 break;
		     case 2:
		    	 try 
					{
						spendDetails();// it will sql exception so, try catch has to use.
					} 
		    	 catch (SQLException e) {}
		    	 break;
		}
	}
	
	/*This method is used to display the spend details.*/
	public void spendDetails() throws SQLException
	{
		double totalSpend = 0.0;
		st = con.createStatement();
		
		/*in this query it will take spend reason and spend amount columns from database where the columns should not contain null values*/
		rs = st.executeQuery("select spendAmount,spendReason from person where spendAmount IS NOT NULL");
		
		System.out.println("\t================================================");
		System.out.println("\t|SPENDREASON\t\t\t SPENDAMOUNT\t|");
		System.out.println("\t================================================");
		while(rs.next())
		{
			totalSpend += rs.getDouble("spendAmount");
			System.out.println("\t  "+rs.getString("spendReason")+"\t\t\t\t "+rs.getDouble("spendAmount"));
		}
		System.out.println("\t================================================");
		System.out.println("\tTotal \t\t\t\t"+totalSpend);
		
		rs.close();
		st.close();//it will close the statement
	}
	
	/*This method is used to display the credit details.*/
	public void creditDetails() throws SQLException
	{
		double totalCredit = 0.0;
		st=con.createStatement();
		
		/*in this query it will take credit name and credit amount columns from database where the columns should not contain null values*/
		rs = st.executeQuery("select creditName,creditAmount from person where creditName IS NOT NULL and creditAmount IS NOT NULL;");

		System.out.println("\t================================================");
		System.out.println("\t|CREDITNAME\t\t\t CREDITAMOUNT\t|");
		System.out.println("\t================================================");
		while(rs.next())
		{
			totalCredit += rs.getDouble("creditAmount");
			System.out.println("\t  "+rs.getString("creditName")+"\t\t\t\t "+rs.getDouble("creditAMount"));
			/*System.out.print("\t  "+rs.getString("creditName").trim());
			System.out.println("\t\t\t\t "+rs.getDouble("creditAMount"));*/
		}
		System.out.println("\t================================================");
		System.out.println("\tTotal \t\t\t\t"+totalCredit);
		rs.close();
		st.close();//it will close the statement
	}
	
	/*This method is used to close the connections of ResultSet and Connection statement*/
	public void connetionClose()
	{
		try 
		{
			con.close();
		} 
		catch (SQLException e) {}
	}
	
}
