import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class AgentXMLToExcel {

	/**
	 * @param args
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
		public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
			// TODO Auto-generated method stub
			File fXmlFile = new File("kh8.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();

			
			NodeList nList = doc.getElementsByTagName("attribute_group");
			

			 XSSFWorkbook workbook = new XSSFWorkbook();
		        
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);				
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				
				NodeList nodeList=nNode.getChildNodes();
				
				Element eE =  (Element)nNode;
				
			//	System.out.println("------------------------------------------------------"+nodeList.getLength()+"------------------------------------------------------");
				

				
				int rowCount = 0;
				
				XSSFSheet sheet = workbook.createSheet(temp+eE.getAttribute("name"));
				
				
				for(int i=0;i<nodeList.getLength();i++){
					Node node=nodeList.item(i);
					
					
					if(node.getNodeName().equalsIgnoreCase("attribute")){
						Element eElement =  (Element) node;
												
						String dataType="";
						
						if(node.getChildNodes().item(1).getNodeName().contains("Gauge"))
							 dataType="Integer";
						else
							dataType="String";
						
						
					/*	System.out.println("KH8_"+eE.getAttribute("name").toUpperCase()+"		"+
								eElement.getAttribute("caption")+"		"+
								"KH8"+eE.getAttribute("table_id")+"		"+
								eElement.getAttribute("column_id")+"		"+
								eElement.getAttribute("help_text"));//node.getNodeName());
					*/
					
				         
				        Object[][] bookData = {{"KH8_"+eE.getAttribute("name").toUpperCase(),eElement.getAttribute("caption"), 
				        		"KH8"+eE.getAttribute("table_id"),eElement.getAttribute("column_id"),
				        		dataType,
				        		eElement.getAttribute("help_text")
				        }};			          
				 
				        
				         
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
				         
				        try (FileOutputStream outputStream = new FileOutputStream("AttributeGroup.xlsx")) {
				            workbook.write(outputStream);
				       }
				}
			}
		}
			System.out.println("Successfully completed.......");
	}
}
