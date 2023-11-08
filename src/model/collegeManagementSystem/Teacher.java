package model.collegeManagementSystem;
import controller.collegeManagementSystem.DbConnection;
import controller.collegeManagementSystem.LoggerService;
import view.collegeManagementSystem.Task;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
public class Teacher implements Admin
{
	String sql;
	int row=0;
	int record=0;
	DbConnection dbConnection=new DbConnection();
	public void getTeacherDetails(String user,Scanner scanner) 
	{
		Statement statement = null;
		try 
		{
			statement = DbConnection.connection.createStatement();
			if(user.equalsIgnoreCase("Admin"))
			{
				
				sql=dbConnection.getProperty("table.GetAllTeacherDetails");   
			}
			else
			{
				System.out.println("Enter Your Id:");
				String teacherId=scanner.next();
				sql=dbConnection.getProperty("table.TeacherDetails")+"'"+teacherId+"'";
			}
			ResultSet resultSet;
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
		    {
				System.out.println("Teacher Name: "+resultSet.getString(1));
				System.out.println("Department: "+resultSet.getString(2));
				System.out.println("E-Mail: "+resultSet.getString(3));
				System.out.println("PhoneNumber: "+resultSet.getString(4));
				System.out.println("TeacherId: "+resultSet.getString(5));
				System.out.println();
				System.out.println();
		    }
			Task.toContinue(user,scanner);
		}
		catch(SQLException sqlException)
		{
			LoggerService.logError(sqlException.getMessage());
			//logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
		}
		catch (Exception exception) {
			LoggerService.logError(exception.getMessage());
			//logger.log(Level.SEVERE,"Exception occured: ",exception);
		}
		
	}
	
	public void assignWork(String user,Scanner scanner) 
	{
		Statement statement;
		try
		{
			statement = DbConnection.connection.createStatement();
			System.out.println("Enter Course Name:");
			String courseName=scanner.next();
			scanner.nextLine();
			System.out.println("Type the Work what student should do");
			String assignment=scanner.nextLine();
			sql=dbConnection.getProperty("table.UpdateAssignment")+"'"+assignment+"' "+dbConnection.getProperty("table.Course")+" '"+courseName+"'";
			int row=statement.executeUpdate(sql);
			if(row==1)
			{
				System.out.println("Work Assign Successfully");
			}
			else
			{
				LoggerService.logWarning("unable to assign work");
			//	logger.log(Level.WARNING,"unable to assign work");
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
	public void editDetails(String user,Scanner scanner,String uniqueId)
	{
	
		Statement statement = null;
		try {
			statement = DbConnection.connection.createStatement();
			if("Teacher".equalsIgnoreCase(user))
			{
				System.out.println("Enter Teacher Name: ");
				String name=scanner.nextLine();
				scanner.nextLine();
				System.out.println("Enter Department: ");
				String department=scanner.next();
				scanner.nextLine();
				System.out.println("Enter PhoneNumber: ");
				String phoneNumber=scanner.next();
				System.out.println("Enter E-Mail:");
				String eMail=scanner.next();
				sql=dbConnection.getProperty("table.UpdateTeacherDetails")+"'"+name+"',Department='"+department+"',PhoneNumber='"+phoneNumber+"',Email='"+eMail+"' where teacher_id='"+uniqueId+"'"; 
				row=statement.executeUpdate(sql);
				if(row==1)
				{
					System.out.println(" Details edit Successfully");
				}
				else
				{
					LoggerService.logWarning("please enter valid information");
				//	logger.log(Level.WARNING,"please enter valid information");
				}
				Task.toContinue(user,scanner);
			}
		else
		{
			System.out.println("Enter Student Name : ");
			String name=scanner.next();
			scanner.nextLine();
			System.out.println("Enter Father's Name: ");
			String fatherName=scanner.next();
			scanner.nextLine();
			System.out.println("Enter Department : ");
			String department=scanner.next();
			System.out.println("Enter Year: ");
			String year=scanner.next();
			System.out.println("Enter E-Mail:");
			String eMail=scanner.next();
			System.out.println("Enter phone Number: ");
			String phoneNumber=scanner.next();
			int row=0;
			String sql=dbConnection.getProperty("table.UpdateStudentDetails")+"'"+name+"',fatherName='"+fatherName+"',Department='"+department+"',Year='"+year+"',Email='"+eMail+"',PhoneNumber='"+phoneNumber+"'  where Rollno='"+uniqueId+"'"; 
			row=statement.executeUpdate(sql);
			if(row==1)
				System.out.println("Details edit Successfully");
			else
			{
				LoggerService.logWarning("please enter valid information");
			//	logger.log(Level.WARNING,"please enter valid information");
			}
			Task.toContinue(user,scanner);
		}
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
	public void closeAttendance(String user,Scanner scanner)
	{
		Statement statement;
		try {
			statement = DbConnection.connection.createStatement();
			String sql=dbConnection.getProperty("table.AttendanceClose");
			ResultSet resultSet=statement.executeQuery(sql);
			String attendance="";
			String rollNumber="";
			String present="";
			String absent="";
			while(resultSet.next())
			{
				attendance= resultSet.getString(3);
				rollNumber=resultSet.getString(2);
				if(attendance.equalsIgnoreCase("p"))
				{
					Statement statementOne=DbConnection.connection.createStatement();
					present=dbConnection.getProperty("table.AttendanceClose")+" where RollNo='"+rollNumber+"'";
					ResultSet query=statementOne.executeQuery(present);
					int workingDays=0;
					int attend=0;
					while(query.next())
					{
						workingDays=query.getInt(5);
						attend=query.getInt(6);
					}
					attend++;
					float percentage=(attend*100/workingDays);
					String update=dbConnection.getProperty("table.AttendanceUpdate")+"'"+percentage+"',Number_Of_Days_Present='"+attend+"' where RollNo='"+rollNumber+"'";
					record=statementOne.executeUpdate(update);
					query.close();
				}
				else
				{
					Statement statementTwo=DbConnection.connection.createStatement();
					absent=dbConnection.getProperty("table.AttendanceClose")+" where RollNo='"+rollNumber+"'";
					ResultSet query=statementTwo.executeQuery(absent);
					int workingDays=0;
					int attend=0;
					while(query.next())
					{
						workingDays=query.getInt(5);
						attend=query.getInt(6);
					}
					float percentage=(attend*100)/workingDays;
					String update=dbConnection.getProperty("table.AttendanceUpdate")+"'"+percentage+"' where RollNo='"+rollNumber+"'";
					record=statementTwo.executeUpdate(update);
					query.close();
				}
				System.out.println("Attendance close Successfully");
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
	public void toResetPassword(String user,Scanner scanner,String uniqueId)
	{
		Admin teacher=new Teacher();
		PasswordCheck passwordCheck=new PasswordCheck();
		Statement statement = null;
		String oldPassword=null;
		try 
		{
			statement = DbConnection.connection.createStatement();
			System.out.println("Enter New Password");
			String newPassword=scanner.next();
			System.out.println("Again Enter New Password");
			String reenterNewPassword=scanner.next();
			oldPassword=passwordCheck.getPassword();
			if((!(oldPassword.equalsIgnoreCase(newPassword))) && newPassword.equals(reenterNewPassword))
			{
				sql=dbConnection.getProperty("table.UpdateTeacherPasswrod")+"'"+newPassword+"' where TeacherId="+uniqueId+"";
			}
			else
			{
				System.out.println("OldPassword and NewPassword is same So,unable to reset password");
				teacher.toResetPassword(user,scanner, uniqueId);
			}
			int row = 0;
			row = statement.executeUpdate(sql);
			if(row==1)
			{
				System.out.println("Password Change Successfully...");
				passwordCheck.setPassword(newPassword);
			}
			else
			{
				LoggerService.logWarning("Unable to change Password");
			//	logger.log(Level.WARNING,"Unable to change Password");
				teacher.toResetPassword(user, scanner, uniqueId);
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
			//logger.log(Level.SEVERE,"Exception occured: ",exception);
		}
		
	}
	public void addUser(String user, Scanner scanner) {
		// TODO Auto-generated method stub
		Statement statement = null;
		try {
			statement = DbConnection.connection.createStatement();
			System.out.println("Enter Teacher Name: ");
			String name=scanner.nextLine();
			System.out.println("Enter Department: ");
			String department=scanner.next();
			System.out.println("Enter Teacher_id: ");
			int teacherId=scanner.nextInt();
			System.out.println("Enter PhoneNumber: ");
			String phoneNumber=scanner.next();
			System.out.println("Enter E-Mail:");
			String eMail=scanner.next();
			String sql=dbConnection.getProperty("table.InsertStduentDetails")+"'"+name+"','"+department+"','"+eMail+"','"+phoneNumber+"','"+teacherId+"')";
			String teacher=dbConnection.getProperty("table.InsertStudentUser")+"'"+name+"','"+phoneNumber+"',"+teacherId+")";
			int row = 0;
			int record = 0;
			row = statement.executeUpdate(sql);
			record = statement.executeUpdate(teacher);
			if(row==1&&record==1)
			{
				System.out.println("Teacher Add Successfully");
			}
			else
			{
				LoggerService.logWarning("Please enter Valid Information");
			//	logger.log(Level.WARNING,"Please enter Valid Information");
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
