import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	public static String ReadFile(String filename)
	{
		File fileinstance=new File(filename);
        BufferedReader buffer=null;
		try {
			buffer = new BufferedReader(new FileReader(fileinstance));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  String Wholetext=""; 
	        String temp="";
	        try {
				while ((temp = buffer.readLine()) != null) 
					Wholetext+=temp+"\n";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Wholetext;
		
	}
	public static Map<String, String[]> ParseGrammar(String Grammar)
	{
		String[] GrammarArray=Grammar.split("\\n");
		Map<String, String[]>GrammarRules= new  LinkedHashMap<String,String[]>();
		
		for(int i=0;i<GrammarArray.length;i++)
		{
//			String[] Temp = GrammarArray[i].split("~");
//			String []TempArr=new[];
//			GrammarRules.put(Temp[0], Temp[1]);
		}
		return GrammarRules;
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String input_file_name="Input.txt";
		String grammar_file_name="MiniC grammar.txt";
		//String Input=ReadFile(input_file_name);
		String Grammar=ReadFile(grammar_file_name);
		/////////////////////////////
		Map<String, String[]> GrammarRules = new  LinkedHashMap<String,String[]>();	
		GrammarRules=ParseGrammar(Grammar);
		for(Map.Entry<String,String[]> Rule: GrammarRules.entrySet())
		{
			System.out.println("KEY:- "+Rule.getKey()+"VALUE :- "+Rule.getValue());
		}
		
		//Map<String, String>GrammarRules= new  LinkedHashMap<String,String>();

	
	}
	

}
