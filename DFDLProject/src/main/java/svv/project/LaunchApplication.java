package svv.project;

/** 
 * Launches the Application. Start Point for the Program.
 * 
 * @author Sai Manjula
 * @author Sarang Ardhapurkar
 */

public class LaunchApplication 
{
	private static final String EXAMPLE_SCHEMA = "csv.dfdl.xsd";
	private static final String EXAMPLE_DATA = "SimpleCSV.csv";

	public static void main(String[] args) throws Exception 
	{
		SchemaValidator sv = new SchemaValidator();
		
		sv.ValidateSchema(EXAMPLE_SCHEMA, EXAMPLE_DATA);
	}

}
