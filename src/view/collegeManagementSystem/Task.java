package view.collegeManagementSystem;
import model.collegeManagementSystem.*;
import controller.collegeManagementSystem.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Task 
{
	public static void performOperation(String user,Scanner scanner)
	{
		  Course course=new Course();
		  Attendance attendance=new Attendance();
		  Admin admin=new Student();
		  Admin teacher=new Teacher();
		System.out.println();
		if(user.equalsIgnoreCase("Admin"))
		{
			System.out.println("Enter 1 AddUser");
			System.out.println("Enter 2 view StudentDetails");
			System.out.println("Enter 3 view TeacherDetails");
			System.out.println("Enter 4 Add course");
			System.out.println("Enter 5 view Course");
			System.out.println("Enter 6 Delete course");
			System.out.println("Enter 7 view Attendance of Student's");
			String unique=PasswordCheck.name;
			int choice=scanner.nextInt();
			if(choice==1)
			{
				System.out.println("Enter 1 to Add Teacher user");
				System.out.println("Enter 2 to Add Student user");
				int adduser=scanner.nextInt();
				scanner.nextLine();
				if(adduser==1)
				{
					teacher.addUser(user, scanner);
				}
				else if(adduser==2)
				{
					admin.addUser(user, scanner);
				}
				else
				{
					LoggerService.logWarning("Enter Valid Number");
				//	logger.log(Level.WARNING,"");
				}
			}
			else if(choice==2)
			{
					course.getStudentDetails(user,scanner,unique);
			}
			else if(choice==3)
			{
				course.getTeacherDetails(user, scanner);
			}
			else if(choice==4)
			{
				course.addCourse(user,scanner);
			}
			else if(choice==5)
			{
				course.getCourseDetails(user,scanner);
			}
			else if(choice==6)
			{
				course.deleteCourse(user,scanner);
			}
			else if(choice==7)
			{
				attendance.viewAttendance(user,scanner);
			}
			else
			{
				LoggerService.logWarning("Please Enter Correct Choice To Perfrom Task");
				Task.performOperation(user, scanner);
			}
		}
		else if(user.equalsIgnoreCase("teacher"))
		{
			System.out.println("Enter 1 Assign the Work");
			System.out.println("Enter 2 view StudentDetails");
			System.out.println("Enter 3 Edit MyDetails");
			System.out.println("Enter 4 view course");
			System.out.println("Enter 5 Reset password");
			System.out.println("Enter 6 Create Attendance");
			System.out.println("Enter 7 View Attendance");
			System.out.println("Enter 8 Close Attendance");
			int number=PasswordCheck.id;
			String uniqueId=Integer.toString(number);
			int choice=scanner.nextInt();
			if(choice==1)
			{
				course.assignWork(user,scanner);
			}
			else if(choice==2)
			{
				course.getStudentDetails(user,scanner,uniqueId);
			}
			else if(choice==3)
			{
				course.editDetails(user,scanner,uniqueId);
			}
			else if(choice==4)
			{
				course.getCourseDetails(user,scanner);
			}
			else if(choice==5)
			{
				teacher.toResetPassword(user, scanner, uniqueId);
			}
			else if(choice==6)
			{
				attendance.createAttendance(user,scanner);
			}
			else if(choice==7)
			{
				attendance.viewAttendance(user, scanner);
			}
			else if(choice==8)
			{
				attendance.closeAttendance(user, scanner);
			}
			else
			{
				LoggerService.logWarning("Please Enter Correct Choice To Perfrom Task");
				Task.performOperation(user, scanner);
			}
			
		}
		else if(user.equalsIgnoreCase("Student"))
		{
			System.out.println("Enter 1 view Work");
			System.out.println("Enter 2 Edit MyDetails");
			System.out.println("Enter 3 Mark Attendance");
			System.out.println("Enter 4 view course");
			System.out.println("Enter 5 Reset password");
			System.out.println("Enter 6 view Attendance");
			String uniqueId=PasswordCheck.name;
			int choice=scanner.nextInt();
			if(choice==1)
			{
					course.viewWork(user,scanner);
			}
			else if(choice==2)
			{
				course.editDetails(user, scanner, uniqueId);
			}
			else if(choice==3)
			{
				attendance.toMarkAttendance(user,scanner,uniqueId);
			}
			else if(choice==4)
			{
				course.getCourseDetails(user,scanner);
			}
			else if(choice==5)
			{
				admin.toResetPassword(user, scanner, uniqueId);
			}
			else if(choice==6)
			{
				attendance.viewAttendance(scanner, uniqueId);
			}
			else 
			{
				LoggerService.logWarning("Please Enter Correct Choice To Perfrom Task");
				Task.performOperation(user, scanner);
			}
		}
		
	}
	public static void toContinue(String user,Scanner scanner)
	{
		System.out.println("Enter 1 to continue");
		System.out.println("Enter 2 to Exit");
		int choice=scanner.nextInt();
		scanner.nextLine();
		if(choice==1)
		{
			Task.performOperation(user,scanner);
		}
		else if(choice==2)
		{
			System.out.println("            Logout               ");
			try {
				DbConnection.connection.close();
			} catch (SQLException sqlException)
			{
				LoggerService.logError(sqlException.getMessage());
				//logger.log(Level.SEVERE,"sql Exception occured: ",sqlException);
			}
			catch(Exception exception)
			{
				LoggerService.logError(exception.getMessage());
			//	logger.log(Level.SEVERE,"Exception occured: ",exception);
			}
			return;
		}
		else
		{
			LoggerService.logWarning("Enter Valid Number");
			Task.toContinue(user, scanner);
		}
	}
	public void manageUser()
	{
		Scanner scanner=new Scanner(System.in);
		DbConnection dbConnection=new DbConnection();
		PasswordCheck passwordCheck=new PasswordCheck();
		dbConnection.getConnection();
		int choice=passwordCheck.identifyUserType(scanner);
		String user="";
		if(choice==1)
		{
			user="Admin";
			if(passwordCheck.authenticateUser(user,scanner))
			{
				Task.performOperation(user,scanner);
			}
			else
			{
				LoggerService.logWarning("Please Check Your Password");
				//logger.log(Level.WARNING,"Please Check your Password");
				passwordCheck.authenticateUser(user, scanner);
				
			}
		}
		else if(choice==2)
		{
			user="Teacher";
			if(passwordCheck.authenticateUser(user,scanner))
			{
				Task.performOperation(user,scanner);
			}
			else
			{
			
				LoggerService.logWarning("Please Check your Password");
	//			logger.log(Level.WARNING,"Please Check your Password");
				passwordCheck.authenticateUser(user, scanner);
				
			}
		}
		else if(choice==3)
		{
			user="Student";
			if(passwordCheck.authenticateUser(user,scanner))
			{
				Task.performOperation(user,scanner);
			}
			else
			{
				LoggerService.logWarning("Please Check your Password");
	//			logger.log(Level.WARNING,"Please Check your Password");
				passwordCheck.authenticateUser(user, scanner);
				
			}
		}
		else
		{
			LoggerService.logWarning("Enter Valid User");
		//	logger.log(Level.WARNING,"Enter Valid User");
			return;
		}
		
	}

}
