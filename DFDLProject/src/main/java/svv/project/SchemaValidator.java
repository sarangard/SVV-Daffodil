/* A program to validate whether a file type adheres to a particular schema or not.*/
package svv.project;

/* Packages required to read a file, store it in buffer and access it.*/
import java.io.File;
import java.io.FileInputStream;
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
 * Validates a file with a schema and returns output in a xml format. 
 * @author SaiManjula
 * @author Sarang
 */
public class SchemaValidator 
{
	private static final String EXAMPLE_SCHEMA = "csv.dfdl.xsd";
	private static final String EXAMPLE_DATA = "SimpleCSV.csv";
	
	public void Execute() throws Exception 
	{
		/**
		 * Create an instance of Daffodil Compiler. Set validateDFDLSchemas method to true. 
		 */
		Compiler c = Daffodil.compiler();
		c.setValidateDFDLSchemas(true);

		/**
		 * Get a URL for the example schema resource. 
		 * 
		 */
		URL schemaUrl = getClass().getResource("/DFDLSchemas/" + EXAMPLE_SCHEMA);
		
		/**
		 * Pass the URI using schemaUrl and pass it to a file.
		 * 
		 * 
		 * Create an instance of a ProcessorFactory and compile file from the url.
		 */
		File schemaFiles = new File(schemaUrl.toURI());
		ProcessorFactory pf = c.compileFile(schemaFiles);

		/**
		 * Check if there is an error with the ProcessorFactory.
		 */		
		if (pf.isError()) 
		{
			throw new Exception("Processor Factory error");
		}

		/**
		 * Get a DataProcessor from the ProcessorFactory. 
		 * We use an XPath here.
		 */
		DataProcessor dp = pf.onPath("/");

		/**
		 * Check if there is an error with the DataProcessor.
		 */
		if (dp.isError()) 
		{
			throw new Exception("Data Processor error");
		}

		/**
		 * Get a URL for the example schema resource. 
		 * 
		 */
		URL dataUrl = getClass().getResource("/TestFiles/" + EXAMPLE_DATA);
		
		/**
		 * Get a File for the test data.
		 */
		File dataFile = new File(dataUrl.toURI());
		FileInputStream fis = new FileInputStream(dataFile);
		ReadableByteChannel rbc = Channels.newChannel(fis);

		/**
		 * We try to parse the sample data.
		 */
		ParseResult output = dp.parse(rbc);

		/**
		 * Check if there is any error with ParseResult.
		 */
		if (output.isError()) 
		{
			throw new Exception("Parse Result error");
		}

		/**
		 *  Print the output from the parse result in the console in an XML format.
		 */
		Document doc = output.result();
		XMLOutputter xo = new XMLOutputter();
		xo.setFormat(Format.getPrettyFormat());
		xo.output(doc, System.out);
	}

}
