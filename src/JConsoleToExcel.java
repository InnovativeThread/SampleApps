import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class JConsoleToExcel {

	/**
	 * @param args
	 * @throws ReflectionException 
	 * @throws IntrospectionException 
	 * @throws InstanceNotFoundException 
	 * @throws MalformedObjectNameException 
	 */
	public static void main(String[] args) throws InstanceNotFoundException, IntrospectionException, ReflectionException, MalformedObjectNameException {
		// TODO Auto-generated method stub
		try {
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://10.46.33.150:9998/jmxrmi");//10.46.40.96:9999/jmxrmi");//10.46.40.96:9995/jmxrmi");
			JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
			MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
			
			//String str[]= mbeanServerConnection.getDomains();
	/*		Set<ObjectName> beans =mbeanServerConnection.queryNames(null, null);
			//System.out.println(str.length);
			Set<ObjectName> sorted = new TreeSet<ObjectName>(beans);
			//Set<String> sorted = new TreeSet<String>();
	*/		
		/*	PrintWriter out = new PrintWriter("ObjectNames96.txt");
			int i=0;
			for( ObjectName instance : sorted )
			{			
			    out.println(instance.toString());
			    out.println();
			    i++;
			}
			
		*/	
			//PrintWriter out2 = new PrintWriter("ObjectNamesAttributes96.txt");
			
			XSSFWorkbook workbook = new XSSFWorkbook();

			
			 
			String str[]=mbeanServerConnection.getDomains();
			for(String s:str){
				System.out.println(s);
				int rowCount = 1;
				XSSFSheet sheet = workbook.createSheet(s);
				
				Set<ObjectName> beans =mbeanServerConnection.queryNames(new ObjectName(s+":*"), null);
				//System.out.println(str.length);
				Set<ObjectName> sorted = new TreeSet<ObjectName>(beans);
						
			for( ObjectName instance : sorted )
			{
				MBeanInfo info = mbeanServerConnection.getMBeanInfo(instance);
				MBeanAttributeInfo[] info1=info.getAttributes();
				
				System.out.println(instance.toString());
				//out2.println(instance.toString());
				System.out.println("\t\tAttribute : Data Type");
				//out2.println("\t\tAttribute : Data Type");
				for(MBeanAttributeInfo mbi:info1)	{					
				    System.out.println("\t\t"+mbi.getName()+" : "+mbi.getType()+",   Help:"+mbi.getDescription());				    
				    //out2.println("\t\t"+mbi.getName()+" : "+mbi.getType());
				    
				    Object[][] bookData = {{instance.toString(),mbi.getName(),mbi.getType(),mbi.getDescription()}};
				    for (Object[] aBook : bookData) {
				    	Row row = sheet.createRow(++rowCount);
				    	 
				    	int columnCount = 0;
				    	 
				    	for (Object field : aBook) {
				    		Cell cell = row.createCell(++columnCount);
				    		if (field instanceof String) {
				    			cell.setCellValue((String) field);
				    		} 
				    		else if (field instanceof Integer) {
				    			cell.setCellValue((Integer) field);
				    		}
				    	}				             
				    }				    
				}

			    try (FileOutputStream outputStream = new FileOutputStream("Test1_KafkaZookeeper_Multinode_Follower.xlsx")){//KafkaMBeansAtributes112.xlsx")) {
			    	workbook.write(outputStream);
			    }

			}
		}
			
			
			System.out.println("done  ");//+i);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}