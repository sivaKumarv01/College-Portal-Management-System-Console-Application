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

public class Student extends Teacher implements Admin
{
	Statement statement = null;
	DbConnection dbConnection=new DbConnection();
	String sql;
	String oldPassword="";
	public void getStudentDetails(String user,Scanner scanner,String uniqueId)
	{
		try {
			statement = DbConnection.connection.createStatement();
			if(user.equalsIgnoreCase("Teacher")||user.equalsIgnoreCase("Admin"))
			{
				
				sql=dbConnection.getProperty("table.GetAllStudentDetails");
			}
			else
			{
				sql=dbConnection.getProperty("table.StudentDetails")+"'"+uniqueId+"'";
			}
			ResultSet resultSet;
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				System.out.println("Student Name: "+resultSet.getString(1));
				System.out.println("Father's Name: "+resultSet.getString(2));
				System.out.println("Department: "+resultSet.getString(3));
				System.out.println("Year: "+resultSet.getString(4));
				System.out.println("E-Mail: "+resultSet.getString(5));
				System.out.println("PhoneNumber: "+resultSet.getString(6));
				System.out.println("RollNo: "+resultSet.getString(7));
				System.out.println();
				System.out.println();
			}
			Task.toContinue(user,scanner);
		}
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
		//	logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
		}
		catch(Exception exception)
		{
			LoggerService.logError(exception.getMessage());
		//	logger.log(Level.SEVERE,"Exception occured: ",exception);
		}
	}
	public void toResetPassword(String user,Scanner scanner,String uniqueId)
	{
		Admin student=new Student();
		PasswordCheck passwordCheck=new PasswordCheck();
		Statement statement = null;
		try 
		{
			statement = DbConnection.connection.createStatement();
			System.out.println("Enter new password");
			String newPassword=scanner.next();
			System.out.println("re-enter new Password");
			String reenterPassword=scanner.next();
			oldPassword=passwordCheck.getPassword();
			if(!(oldPassword.equalsIgnoreCase(newPassword)) && newPassword.equalsIgnoreCase(reenterPassword))
			{
				sql=dbConnection.getProperty("table.UpdateStudentPassword")+"'"+newPassword+"'"+dbConnection.getProperty("table.Student")+"'"+uniqueId+"'";
			}
			else
			{
				LoggerService.logWarning("OldPassword and NewPassword is same or newpassword and reenterpassword is not same. So,unable to reset password");
		//		logger.log(Level.WARNING,"OldPassword and NewPassword is same or newpassword and reenterpassword is not same. So,unable to reset password");
				student.toResetPassword(user,scanner, uniqueId);
			}
			int row = 0;
			row = statement.executeUpdate(sql);
			if(row==1)
			{
				System.out.println("Password Change Successfully...");
			}
			else
			{
				LoggerService.logWarning("Unable to change Password");
	//			logger.log(Level.SEVERE,"Unable to change Password");
				student.toResetPassword(user, scanner, uniqueId);
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
	public void viewWork(String user,Scanner scanner)
	{
		try 
		{
			Statement statement=DbConnection.connection.createStatement();
			String sql =dbConnection.getProperty("table.Assignment");
			ResultSet resultSet=statement.executeQuery(sql);
			while(resultSet.next())
			{
				System.out.println(resultSet.getString(1)+"         Assignment= "+resultSet.getString(2));
				System.out.println();
			}
			Task.toContinue("Student", scanner);
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
	public void addUser(String user,Scanner scanner)
	{
		Statement statement = null;
		try {
			statement = DbConnection.connection.createStatement();
			System.out.println("Enter Student Name : ");
			String name=scanner.nextLine();
			System.out.println("Enter Father's Name: ");
			String fatherName=scanner.nextLine();
			System.out.println("Enter Department : ");
			String department=scanner.next();
			System.out.println("Enter Year: ");
			String year=scanner.next();
			System.out.println("Enter E-Mail:");
			String eMail=scanner.next();
			System.out.println("Enter phone Number: ");
			String phoneNumber=scanner.next();
			System.out.println("Enter RollNo:");
			String rollNumber=scanner.next();
			int row=0;
			int record = 0;
			int row1 = 0;
			String sql=dbConnection.getProperty("table.InsertStudentUser")+"'"+name+"','"+fatherName+"','"+department+"','"+year+"','"+eMail+"','"+phoneNumber+"','"+rollNumber+"')";
			String student=dbConnection.getProperty("table.InsertStudentDetails")+"'"+rollNumber+"','"+phoneNumber+"')";
			String attendance=dbConnection.getProperty("InsertAttendance")+"'"+name+"','"+rollNumber+"')";
			row=statement.executeUpdate(sql);
			row1 = statement.executeUpdate(attendance);
			record = statement.executeUpdate(student);
			if(row==1&&record==1&&row1==1)
				System.out.println("Student Add Successfully");
			else
			{
				System.out.println("please enter valid information");
			}
			Task.toContinue(user,scanner);
		}
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
	//		logger.log(Level.SEVERE,"sql Exception occured: " , sqlException);
		}
		catch (Exception exception) {
			LoggerService.logError(exception.getMessage());
	//		logger.log(Level.SEVERE,"Exception occured: ",exception);
		}
	}
}
