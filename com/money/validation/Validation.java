package com.money.validation;

import java.util.regex.Pattern;

public class Validation 
{
	/*it is used for name validation purpose.Here we pass string as argument and check whether the string should
	 * contains only alphabets or not. if it contains only alphabets return true.
	 * if the string contains only numbers are combination of numbers and alphabets then display the error message*/
	protected static boolean nameValidation(String name)
    {
    	boolean check = Pattern.matches("[a-zA-Z ]+", name);
    	if(!check)
    	{
    		System.out.println("\n\t!!ERROR Enter only alphabets !!\n");
    		return check;
    	}
    	return check;
    }
	/*it is used for money validation purpose.Here we pass string as argument and check whether the string should
	 * contains only numbers or not. if it contains only numbers return true.
	 * if the string contains only alphabets are combination of numbers and alphabets then display the error message*/
	protected static boolean moneyFormat(String money)
    {
    	boolean check = Pattern.matches("[0-9.]+", money);
    	if(!check)
    	{
    		System.out.println("\n\t!!ERROR wrong input!!\n");
    		return check;
    	}
    	return check;
    }
    /*it is used for 'y' or 'n' option checking purpose. Here i am passing string as argument and checking
     * whether that string contains either 'y' or 'n' char. if it contains either 'y' or 'n' return  true.
     * if it doesn't contains 'y' or 'n' immediately display error.*/
	protected static boolean yes_no_validation(String check)
    {
		/*Here i am converting the string to lowerCase because, if user enter upper case then the string doen't match and it will return false.
		 * so to avoid this error i convert to LowerCase.*/
    	boolean yn = Pattern.matches("[y|n]", check.toLowerCase());
    	if(!yn)
    	{
    		System.out.println("\n\t!!ERROR invalid option!!\n");
    		return yn;
    	}
    	return yn;
    }
	
	/*This method is used to take the string as input and it will return the boolean.In this method i will pass string and i will check is it 
	 * matches with the pattern or not. If it i matches with pattern it will return true. 
	 * If it doesn't matches with the pattern it will return false.*/
	public static boolean executionProcess_validation(String options)
	{
		/*Here the pattern i consider is number from 1-4.when we pass string as a argument to this method, the string should contains 
		 * only 1-4 numbers only. If it doesn't matches with pattern, it will display an message and return false. */
		boolean num = Pattern.matches("[1|2|(3|4)?]", options);
		if(!num)
		{
			System.out.println("\n\t!!ERROR Wrong option !! \n");
			return num;
		}
		return num;
	}
}
