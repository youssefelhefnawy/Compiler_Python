import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
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
	public static Map<String, String[]> HandleGrammar(String Grammar)
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
	public static Queue<Token> HandleInputTokens(String Input)
	{
		String[] InputArray=Input.split("\\n");
		Queue<Token> InputTokens=new LinkedList<Token>();
		Pattern P=Pattern.compile("(<)(.+)(>)");
		for (int i=0;i<InputArray.length;i++)
		{
			
			String[]Temp=InputArray[i].split(":",2);
			
			Temp[0]=Temp[0].replace(" ","");
			
			Matcher m=P.matcher(Temp[0]);
			if (m.find())
			{
				Temp[0]=m.group(2);
			}
			else
			{
				System.out.println("ERROR in Line number"+ i+1);
			}
			
			InputTokens.add(new Token(Temp[0],Temp[1]));
			
		}
		
		return InputTokens;
		
	}
	public static void PrintTokens(Queue<Token> Tokens)
	{
		Token temp=new Token("","");
		while(Tokens.size()>0)
		{	
			temp=Tokens.poll();
			System.out.println("type -->"+temp.TYPE+"        value -->"+temp.VALUE);
			
		}
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String input_file_name="Input.txt";
		String grammar_file_name="MiniC grammar.txt";
		String Input=ReadFile(input_file_name);
		String Grammar=ReadFile(grammar_file_name);
		
		/////////////////////////////
		Map<String, String[]> GrammarRules = new  LinkedHashMap<String,String[]>();	
		GrammarRules=HandleGrammar(Grammar);
		Queue<Token>Tokens=HandleInputTokens(Input);
		PrintTokens(Tokens);
		
		
		
		
		
		
		
//		for(Map.Entry<String,String[]> Rule: GrammarRules.entrySet())
//		{
//			System.out.println("KEY:- "+Rule.getKey()+"VALUE :- "+Rule.getValue());
//		}
//			
	}
	

}
