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

public class Course extends Student
{
	DbConnection dbConnection=new DbConnection();
	public void addCourse(String user,Scanner scanner)
	{
		Statement statement = null;
		int row = 0;
		try 
		{
			statement = DbConnection.connection.createStatement();
			System.out.println("Enter Course Name");
			String courseName=scanner.next();
			System.out.println("Enter Course Id");
			String courseId=scanner.next();
			String sql=dbConnection.getProperty("table.InsertCourse")+"'"+courseName+"','"+courseId+"')";
			row = statement.executeUpdate(sql);
			if(row==1)
			{
				System.out.println("Course Add Successfully");
			}
			else
			{
				System.out.println("Failed to Add Course");
			}
			Task.toContinue(user,scanner);
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
	public void deleteCourse(String user,Scanner scanner)
	{
		Statement statement = null;
		int row =0;
		try 
		{
			statement = DbConnection.connection.createStatement();
			System.out.println("Enter Course Name");
			String courseName=scanner.next();
			System.out.println("Enter Course Id");
			String courseId=scanner.next();
			String sql=dbConnection.getProperty("table.DeleteCourse")+"'"+courseId+"'";
				row=statement.executeUpdate(sql);
			if(row==1)
			{
				System.out.println("Course Delete Successfully");
			}
			else
			{
				LoggerService.logWarning("Failed to Delete Course");
		//		logger.log(Level.WARNING,"Failed to Delete Course");
			}
			Task.toContinue(user,scanner);
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
	public void getCourseDetails(String user,Scanner scanner)
	{
		Statement statement =null;
		try 
		{
			statement = DbConnection.connection.createStatement();
			String sql=dbConnection.getProperty("table.CourseDetails");   
			ResultSet resultSet = null;
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				System.out.print("Course Name: "+resultSet.getString(1)+"  ");
				System.out.println("Course Id: "+resultSet.getString(2));
				System.out.println();
			}
			Task.toContinue(user,scanner);
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
