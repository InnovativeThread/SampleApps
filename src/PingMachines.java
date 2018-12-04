import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class PingMachines {
	  // Sends ping request to a provided IP address 
	  public static String sendPingRequest(String ipAddress) throws UnknownHostException, IOException 
	  { 
	    InetAddress host = InetAddress.getByName(ipAddress); 
	  //  System.out.println("Sending Ping Request to " + ipAddress); 
	    if (host.isReachable(5000)) {
	      System.out.println(ipAddress+" Reachable "+host.getHostName()); 
	      return "Reachable";
	    }
	    else{
	      System.out.println(ipAddress+" Un-Reachable"); 
	      return "Un-Reachable";
	    }
	  } 
	  
	  public static void main(String[] args) throws UnknownHostException, IOException, SAXException, ParserConfigurationException 
	  {
			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet("list");
			
			int rowCount = 0;
			
			Scanner scanner = new Scanner(new File("list.csv"));
			Scanner dataScanner = null;
		/*	while (scanner.hasNextLine()) {
				dataScanner = new Scanner(scanner.nextLine());
				System.out.println(scanner.);
			/*	dataScanner.useDelimiter(",");
				while (dataScanner.hasNext()) {
					String data = dataScanner.next();
					
				}*
			}*/
			
		  	BufferedReader br =  new BufferedReader(new FileReader("list.txt"));
		  	String line=null;
		  	Row row = sheet.createRow(++rowCount);
		  	Cell cell = row.createCell(1);
		  	cell.setCellValue("Status");
		  	cell = row.createCell(2);
		  	cell.setCellValue("IPAddress");
		  	cell = row.createCell(3);
		  	cell.setCellValue("VM_Name");
		  	cell = row.createCell(4);
		  	cell.setCellValue("Location");
		  	while((line = br.readLine())!=null){
		  		row = sheet.createRow(++rowCount);
		  		String[] record = line.split(",");			
				int columnCount = 0;				
				cell = row.createCell(++columnCount);
				cell.setCellValue(sendPingRequest(record[0]));				
				for(String data:record){										
					cell = row.createCell(++columnCount);
					cell.setCellValue(data);
					/*cell = row.createCell(++columnCount);
					cell.setCellValue(sendPingRequest(ipAddress));
					*/
				}
				try (FileOutputStream outputStream = new FileOutputStream("machines.xlsx")) {
			    	workbook.write(outputStream);
			    }
		  	}
			workbook.close();
			
	  }

}
