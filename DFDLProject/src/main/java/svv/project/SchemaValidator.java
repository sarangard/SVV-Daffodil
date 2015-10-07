package svv.project;

/* Packages required to read a file, store it in buffer and access it.*/
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;



/* Packages required to output the data in an xml format. */
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



/* Packages required for the DFDL processor */
import edu.illinois.ncsa.daffodil.japi.Compiler;
import edu.illinois.ncsa.daffodil.japi.Daffodil;
import edu.illinois.ncsa.daffodil.japi.DataProcessor;
import edu.illinois.ncsa.daffodil.japi.ParseResult;
import edu.illinois.ncsa.daffodil.japi.ProcessorFactory;

/** 
 * Validates a file with a schema and returns output in a XML Format.
 * 
 * @author Sai Manjula
 * @author Sarang Ardhapurkar
 */
public class SchemaValidator 
{	
	public Logger logger = new Logger();
	
	/**
	 * Validates the Data against the given DFDL Schema.
	 * 
	 * @param - DFDLSchema - DFDL Schema to validate data against.
	 * @param - Data - Data that needs to be validated.
	 * 
	 * @return - Nothing
	 */
	public void ValidateSchema(String DFDLSchema, String Data)
	{
		try
		{
			/*
			 * Create an instance of Daffodil Compiler. Set validateDFDLSchemas method to true. 
			 */
			Compiler c = Daffodil.compiler();
			c.setValidateDFDLSchemas(true);

			/*
			 * Get a URL for the example schema resource. 
			 */
			URL schemaUrl = getClass().getResource("/DFDLSchemas/" + DFDLSchema);
			
			/*
			 * Pass the URI using schemaUrl and pass it to a file.
			 * Create an instance of a ProcessorFactory and compile file from the url.
			 */
			File schemaFiles = new File(schemaUrl.toURI());
			ProcessorFactory pf = c.compileFile(schemaFiles);

			/*
			 * Check if there is an error with the ProcessorFactory.
			 */		
			if (pf.isError()) 
			{
				logger.Log("Processor Factory error");
			}

			/*
			 * Get a DataProcessor from the ProcessorFactory. 
			 * We use an XPath here.
			 */
			DataProcessor dp = pf.onPath("/");

			/*
			 * Check if there is an error with the DataProcessor.
			 */
			if (dp.isError()) 
			{
				logger.Log("Data Processor error");
			}

			/*
			 * Get a URL for the example schema resource. 
			 */
			URL dataUrl = getClass().getResource("/TestFiles/" + Data);
			
			/*
			 * Get a File for the test data.
			 */
			File dataFile = new File(dataUrl.toURI());
			FileInputStream fis = new FileInputStream(dataFile);
			ReadableByteChannel rbc = Channels.newChannel(fis);

			/*
			 * We try to parse the sample data.
			 */
			ParseResult output = dp.parse(rbc);
			
			/*
			 *  Print the output from the parse result in the console in an XML format.
			 */
			Document doc = output.result();
			XMLOutputter xo = new XMLOutputter();
			xo.setFormat(Format.getPrettyFormat());
			xo.output(doc, System.out);
		}
		catch(IOException e)
		{
			logger.Log(e.getMessage());
		} 
		catch (URISyntaxException e) 
		{
			logger.Log(e.getMessage());
		}
	}

}
