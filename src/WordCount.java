import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class WordCount {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(new File("D:/Eclipse_Luna_Workspace/SampleApps/Attribute_List.txt"));
		Map m=new HashMap();
		int count;//=0;
		while(sc.hasNext())
		{
			String s=sc.nextLine();
			//System.out.println(s);
			if(m.containsKey(s)){
				count=(Integer)m.get(s);
				count++;
				m.put(s, count);
				//System.out.println(s+" "+count);
			}
			else
				m.put(s, 1);
		}
		Set s=m.entrySet();
		Iterator i=s.iterator();
		while(i.hasNext()){
			System.out.println(i.next().toString().split("=")[0]+"\t\t"+i.next().toString().split("=")[1]);//+"\t\t\t"+m.get(i.toString()))//);
		}
	}

}
