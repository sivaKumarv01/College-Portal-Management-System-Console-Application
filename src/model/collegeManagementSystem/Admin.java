package model.collegeManagementSystem;

import java.util.Scanner;

public interface Admin {
	public abstract void addUser(String user,Scanner scanner);
	public abstract void toResetPassword(String user,Scanner scanner,String uniqueId);

}
