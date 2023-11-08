package model.collegeManagementSystem;
import controller.collegeManagementSystem.DbConnection;
import controller.collegeManagementSystem.LoggerService;
import view.collegeManagementSystem.Task;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordCheck
{
	static private String password;
	public  static int id;
	public static String name;
	DbConnection dbConnection=new DbConnection();
	public boolean authenticateUser(String user,Scanner scanner)
	{
		boolean loggedIn=false;
		PasswordCheck passwordCheck=new PasswordCheck();
		scanner.nextLine();
		System.out.println("Enter UserName:");
		String userName=scanner.nextLine();
		name=userName;
		System.out.println("Enter Password:");
		String userpassword=scanner.next();
		String sql="";
		Statement statement = null;
		try 
		{
			statement = DbConnection.connection.createStatement();
			if(user.equalsIgnoreCase("Teacher"))
			{
				System.out.println("Enter your Id: ");
				id=scanner.nextInt();
				scanner.nextLine();
				sql=dbConnection.getProperty("table.TeacherUser");
			}
			else if(user.equalsIgnoreCase("Admin"))
			{
				
				sql=dbConnection.getProperty("table.AdminUser");
			}
			else
			{
				sql=dbConnection.getProperty("table.StudentUser");
			}
			ResultSet resultset = null;
			resultset = statement.executeQuery(sql);
			while(resultset.next())
			{
				name=resultset.getString(1);
				password=resultset.getString(2);
				if(password.equalsIgnoreCase(userpassword)&& name.equalsIgnoreCase(userName))
				{
					passwordCheck.setPassword(password);
					LoggerService.logInfo("Welcome "+name);
					System.out.println("             Login Successfully            ");
					loggedIn=true;
				}
			}
			
		}
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
			
		//	logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
		}
		return loggedIn;	
	}
	public void setPassword(String newPassword)
	{
		password=newPassword;
	}
	public  String getPassword()
	{
		return password;
	}
	public int identifyUserType(Scanner scanner)
	{
		System.out.println("Login as");
		System.out.println("Enter 1 to Admin login");
		System.out.println("Enter 2 to Teacher login");
		System.out.println("Enter 3 to Student login");
		int choice=scanner.nextInt();
		return choice;
	}
}
