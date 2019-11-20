package com.money.mainexecution;

import java.sql.SQLException;
import com.money.process.Process;


public class MainExecution 
{ 
	static
	{
		title();
	}
	public static void main(String[] args) throws SQLException
	{
		Process p= new Process();
		p.executionProcess();
		
	}
	
	public static void title()
	{
		String title = "Value Of Money";
		int len = title.length()*2;
		for(int r = 1;r <= 3; r++)
		{
			for(int sp = 1;sp <= 30; sp++)
			{
				System.out.print(" ");
			}
			for(int c = 1;c <= len ;c++)
			{
				if(r == 1 || r == 3)
				{
					System.out.print("*");
				}
				if(r == 2 & c == 10)
				{
					for(int sp = 1;sp <= 5; sp++)
					{
						System.out.print(" ");
					}
					System.out.print(title);
					for(int sp = 1;sp <= 7; sp++)
					{
						System.out.print(" ");
					}
				}
				if((c == 1 & r == 2) | (c == len & r == 2))
				{
					System.out.print("|");
				}
				
			}
			System.out.println("");
		}
	}
}
