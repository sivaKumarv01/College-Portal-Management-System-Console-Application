package controller.collegeManagementSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection
{
	public static Connection connection=null;
	Properties properties=new Properties();
	public Connection getConnection()
	{
		try
		{
			FileInputStream fileInputStream=new FileInputStream(".\\src\\com.collegeManagementSystem.files\\Database.properties");
			properties.load(fileInputStream);
			String driver=properties.getProperty("database.driver");
			String url=properties.getProperty("database.url");
			String username=properties.getProperty("database.username");
			String password=properties.getProperty("database.password");
			Class.forName(driver);
			connection=DriverManager.getConnection(url,username,password);
		}
		catch(Exception exception)
		{
			System.out.println(exception);
		}
		return connection;
	}
	public String getProperty(String property)
	{
		String query=null;
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(".\\src\\com.collegeManagementSystem.files\\Database.properties");
				properties.load(fileInputStream);
			} catch (IOException exception) {
				
				System.out.println(exception);
			}
			query=properties.getProperty(property);
		
		return query;
		
	}

}
