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

public class Attendance extends Teacher
{
	DbConnection dbConnection=new DbConnection();
	public void viewAttendance(String user,Scanner scanner)
	{
		Statement statement = null;
		try {
			statement = DbConnection.connection.createStatement();
			String sql=dbConnection.getProperty("table.StudentAttendance");
			ResultSet resultSet = null;
			resultSet = statement.executeQuery(sql);
	
				while(resultSet.next())
				{
					System.out.println( "Student Name: "+resultSet.getString(1));
					System.out.println("RollNumber: "+resultSet.getString(2));
					System.out.println("Attendance Percentage: "+resultSet.getString(4));
				}
				Task.toContinue(user, scanner);
		}
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
		//	logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
		}
		catch (Exception exception) {
			LoggerService.logError(exception.getMessage());
			//logger.log(Level.SEVERE,"Exception occured: ",exception);
	}
	}
	public void viewAttendance(Scanner scanner,String uniqueId)
	{
		Statement statement = null;
		try {
			statement = DbConnection.connection.createStatement();
			String rollNumber=uniqueId;
			String sql=dbConnection.getProperty("table.Attendance")+"'"+rollNumber+"'";
			ResultSet resultSet = null;
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				System.out.println("Student Name: "+resultSet.getString(1));
				System.out.println("RollNumber: "+resultSet.getString(2));
				System.out.println("Attendance Percentage : "+resultSet.getString(4));
			}
			Task.toContinue("student", scanner);
		} 
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
		//	logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
		}
		catch (Exception exception) 
		{
			LoggerService.logError(exception.getMessage());
		//	logger.log(Level.SEVERE,"Exception occured: ",exception);
		}
	}
	public void createAttendance(String user, Scanner scanner)
	{
		Statement statement = null;
		try {
			statement = DbConnection.connection.createStatement();
			String attendance="A";
			String sql=dbConnection.getProperty("table.UpdateAttendance")+"'"+attendance+"'";
			String query=dbConnection.getProperty("WorkingDays");
			ResultSet resultSet = null;
			resultSet = statement.executeQuery(query);
			int WorkingDays=0;
			while(resultSet.next())
			{
				WorkingDays=resultSet.getInt(1);
			}
			WorkingDays++;
			String update=dbConnection.getProperty("table.UpdateWorkingDays")+WorkingDays;
			int record = 0;
			int row = 0;
			row = statement.executeUpdate(sql);
			if(row>=1 || record>=1)
			{
				System.out.println("Attendance Created");
			}
			Task.toContinue(user, scanner);
		}
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
		//	logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
		}
		catch (Exception exception)
		{
			LoggerService.logError(exception.getMessage());
		//	logger.log(Level.SEVERE,"Exception occured: ",exception);
		}
	}
	public void toMarkAttendance(String user,Scanner scanner,String uniqueId)
	{
		Attendance attendance=new Attendance();
		Statement statement = null;
		try {
			System.out.println("Enter Present : ");
			String present=scanner.next();
			if(present.equalsIgnoreCase("Present"))
			{
				present="p";
				String sql=dbConnection.getProperty("table.UpdateAttendance")+"'"+present+"' where RollNo='"+uniqueId+"'";
				int row = 0;
				row = statement.executeUpdate(sql);
				if(row==1)
				{
					System.out.println("Present Mark Successfully");
				}
			}
			else
			{
			//	logger.log(Level.WARNING,"Please enter valid Details");
				LoggerService.logWarning("Please enter valid Details");
				attendance.toMarkAttendance(user, scanner, uniqueId);
			}
			Task.toContinue(user, scanner);
		} 
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
		//	logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
		}
		catch (Exception exception) 
		{
			LoggerService.logError(exception.getMessage());
		//	logger.log(Level.SEVERE,"Exception occured: ",exception);
		}
		
	}
	
}
