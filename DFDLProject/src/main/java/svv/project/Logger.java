package svv.project;

import java.io.*;
import java.net.*;
import java.util.Date;

/**
 * 
 * 
 * @author Sarang
 * @author Sai Manjula
 */
public class Logger 
{
	public static final String filepath = "/Log/log.txt";
	public URL filepathURL = getClass().getResource(filepath);
			
	/**
	 * 
	 * 
	 * @param ExsceptionMessage
	 */
	public void Log(String ExceptionMessage)
	{
		String date = new Date().toString();		
		writeToFile(date + " : " + ExceptionMessage);		
	}
	
	
	/**
	 * 
	 * 
	 * @param msg
	 */
	private void writeToFile(String msg)
	{
		try
		{
			File file = new File(filepathURL.toURI());

			// if file doesnt exists, then create it
			if (!file.exists()) 
			{
				file.createNewFile();
			}

			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			fw.write(msg);
			fw.write(System.lineSeparator());
			fw.close();
		}
		catch(IOException e)
		{
			System.err.println("Couldn't log this message: " +msg);
		} 
		catch (URISyntaxException e) 
		{
			System.err.println("Problem in creating the URI.");
		}
	}
}
