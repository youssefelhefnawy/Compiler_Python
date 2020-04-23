import java.util.LinkedList;
import java.util.Stack;

public class Parser {
	LinkedList<Token> allTokens = null;
	Stack<Node> ParseTree; //Contains String Of Grammar Rules

	public Parser(LinkedList<Token> inTokens)
	{
		allTokens = inTokens;
		ParseTree=new Stack<Node>();
	}
	
	void printError() {
		System.out.println("Syntax error: unexpected token <" + allTokens.peek().TYPE + ">:" + allTokens.peek().VALUE);
		System.exit(0);
	}

	//////////////////Parser Functions
	public boolean program(Node Root) {
		Root.addNode(new Node("decllist"));
		Node n1=Root.ChildNodes.get(0);
		if(!decl_list(n1))
		{
			System.out.println("Syntax error: unexpected token <" + allTokens.peek().TYPE + ">:" + allTokens.peek().VALUE);
			return false;
		}
		else 
		{	
			System.out.println("COMPILED SUCCESSFULLY");
			System.out.println("----------------<PARSE TREE>---------------------");
			return true;
		}
	}
	
	boolean decl_list(Node n) {
		Node decl=new Node("decleration");
		Node decl_list_alt=new Node("decleration list alternative");
		
		if(decl(decl) && decl_list_alt(decl_list_alt))
		{
			n.addNode(decl);
			n.addNode(decl_list_alt);
			return true;
		}
		else return false;
	}
	
	boolean decl_list_alt(Node n) 
	{	
		Node n1=new Node("decleration");
		Node n2=new Node("decleration list alternative");

		if( decl(n1) && decl_list_alt(n2))
		{
			n.addNode(n1);
			n.addNode(n2);
			return true;
		}
		else
		{
			return true;
		}
		
	}
	
	boolean decl(Node n) {
		if(allTokens.peek() == null) {
			return false;
		}
		Node n1=new Node("typespec");
		Node n2=new Node("ID");
		Node n3=new Node("decleration_alt");
		n.addNode(n1);
		n.addNode(n2);
		if(type_spec(n1)) {
			
			//ParseTree.add(AdjustParseTree(ParseTree,new Node("Type Spec")));
			if(allTokens.peek().TYPE.equals("ID")) {
				allTokens.poll();
				n.addNode(new Node("ID"));
	
				if(decl_alt(n3))
				{
					n.addNode(n3);
					return true;
				}
				else
					printError();
			}
		}

		return false;
	}
	
	boolean decl_alt(Node n) 
	{	
		Node n1=new Node("var_decl");
		Node n2=new Node("func_decl");

		if(var_decl(n1))
		{	
			n.addNode(n1);
			return true;
		}
		else if(fun_decl(n2))
		{
			n.addNode(n2);
			return true;
		}
		else return false;
		
	}
	
	boolean var_decl(Node n) {
		if(allTokens.peek() == null) {
			return false;
		}
		else if(allTokens.peek().TYPE.equals("SEMICOLON")) {
			allTokens.poll();
			n.addNode(new Node("SEMI COLON"));
			return true;
		}
		else if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			allTokens.poll();
			n.addNode(new Node("LEFT_SQUARE_B"));
			if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
				allTokens.poll();
				n.addNode(new Node("RIGHT_SQUARE_B"));
				if(allTokens.peek().TYPE.equals("SEMICOLON")) {
					allTokens.poll();
					n.addNode(new Node("SEMI COLON"));

					

					return true;
				}
				else
					printError();
			}
			else
				printError();
		}
		return false;
	}
	
	boolean fun_decl(Node n) {
		Node n1=new Node("Params");
		Node n2=new Node("Compound Statement");
		
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
			allTokens.poll();
			n.addNode(new Node("LEFT_ROUND_B"));
			if(params(n1)) {
				if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
					allTokens.poll();
					n.addNode(n1);
					n.addNode(new Node("RIGHT_ROUND_B"));
					if(compound_stmt(n2)) {n.addNode(n2);return true;}
					return false;
				}
				else
					printError();
			}
			else
				printError();
		}
		return false;
	}
	
	boolean type_spec(Node n) 
	{
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("VOID")) {
			allTokens.poll();
			n.addNode(new Node("VOID"));
			return true;
		}
		else if(allTokens.peek().TYPE.equals("BOOL")) {
			allTokens.poll();
			n.addNode(new Node("BOOL"));
			return true;
		}
		else if(allTokens.peek().TYPE.equals("INT")) {
			allTokens.poll();
			n.addNode(new Node("INT"));
			return true;
		}
		else if (allTokens.peek().TYPE.equals("FLOAT")) {
			allTokens.poll();
			n.addNode(new Node("FLOAT"));
			return true;
		}
		return false;
	}
	
	boolean params(Node n) {
				
		Node n1=new Node("Parameter List");
		
		
		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("VOID")) 
		{
			allTokens.poll();
			n.addNode(new Node("VOID"));
			return true;
		}
		if( param_list(n1)) n.addNode(n1); return true;	
	}
	
	boolean param_list(Node n) {
		Node n1=new Node("Parameter");
		Node n2=new Node("Parameter List alternative");
		if(param(n1) && param_list_alt(n2))
		{
			n.addNode(n1);
			n.addNode(n2);
		return true;
		}
		return false;
	}
	
	boolean param_list_alt(Node n) {
		Node n1=new Node("Parameter");
		Node n2=new Node("Parameter List alternative");
		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("COMMA")) {
			allTokens.poll();
			n.addNode(new Node("COMMA"));
			if(param(n1) && param_list_alt(n2))
			{
				n.addNode(n1);
				n.addNode(n2);
				return true;
				
			}
		else
				printError();
		}
		return true;
	}
	
	boolean param(Node n) {
		Node n1=new Node("Parameter_alt");
		Node n2=new Node("type spec");
		if(allTokens.peek() == null) {
			return false;
		}
		if(type_spec(n2)) {
			n.addNode(n2);
			if(allTokens.peek().TYPE.equals("ID")) {
				allTokens.poll();
				n.addNode(new Node("ID"));
				if(param_alt(n1))
				{
					n.addNode(n1);
					return true;
				}
				else
					printError();
			}
		}
		return false;
	}
	
	boolean param_alt(Node n) {
		
		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			allTokens.poll();
			n.addNode(new Node("Left Square Bracket"));
			if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
				allTokens.poll();
				n.addNode(new Node("Right Square Bracket"));
				return true;
			}
			else
				printError();
		}
		return true;
	}
	
	boolean compound_stmt(Node n) {
		Node n1=new Node("Local Declerations");
		Node n2= new Node("Statement List");
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("LEFT_CURLY_B")) {
			allTokens.poll();
			n.addNode(new Node("Left Curly Brackets"));
			if(local_decls(n1) && stmt_list(n2)) {
				n.addNode(n1); n.addNode(n2);
				if(allTokens.peek().TYPE.equals("RIGHT_CURLY_B")) {
					allTokens.poll();
					n.addNode(new Node("Right Curly Brackets"));
					return true;
				}
				else
					printError();
			}
			else
				printError();
		}
		return false;	
	}
	
	boolean stmt_list(Node n) {
		Node n1=new Node("Statement");
		Node n2= new Node("Statement List");
		if(stmt(n1) && stmt_list(n2))
		{
			n.addNode(n1);;
			n.addNode(n2);
		}
		return true;
	}
	
	boolean stmt(Node n) {
		Node n1=new Node("Expression Statement");
		Node n2=new Node("Compound_Statment");
		Node n3=new Node("If Statement");
		Node n4=new Node("While Statement");
		Node n5=new Node("Return Statement");
		Node n6=new Node("Break Statement");
		if(expr_stmt(n1))
		{
			n.addNode(n1);return true;
		}
		else if(compound_stmt(n2)) {
			n.addNode(n2); return true;
		}
		else if(if_stmt(n3)) {n.addNode(n3);return true;}
		else if(while_stmt(n4)) {n.addNode(n4); return true;}
		else if(return_stmt(n5)) {n.addNode(n5); return true;}
		else if(break_stmt(n6)) {n.addNode(n6); return true;}
		else return false;
		
	}
	
	boolean expr_stmt(Node n) {
		Node n1=new Node("Expression");
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("SEMICOLON")) {
			allTokens.poll();
			n.addNode(new Node("SEMICOLON"));
			return true;
		}
		else if(expr(n1)) {
			n.addNode(n1);
			if(allTokens.peek().TYPE.equals("SEMICOLON")) {
				allTokens.poll();
				n.addNode(new Node("SEMICOLON"));
				return true;
			}
		}
		return false;
	}
	
	boolean while_stmt(Node n) {

		Node n1=new Node("Expression ");
		Node n2=new Node("Statement");
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("WHILE")) {
			allTokens.poll();
			n.addNode(new Node("WHILE"));
			if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
				allTokens.poll();
				n.addNode(new Node("Left Round Bracket"));
				if(expr(n1)) {
					n.addNode(n1);
					if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
						allTokens.poll();
						n.addNode(new Node("Right Round Bracket"));
						if (stmt(n2)) {n.addNode(n); return true;}
						else return false;
					}
					else
						printError();
				}
				else
					printError();
			}
			else
				printError();
		}
		return false;
	}
	
	boolean if_stmt(Node n) {
		Node n1=new Node("Expression");
		Node n2=new Node("Statement");
		Node n3= new Node("If Statement Aletr");
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("IF")) {
			allTokens.poll();
			n.addNode(new Node("IF"));
			if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
				allTokens.poll();
				n.addNode(new Node("Left round bracket"));
				if(expr(n1)) {
					n.addNode(n1);
					if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
						allTokens.poll();
						n.addNode(new Node("RIGHT ROUND BRACKET"));
						if(stmt(n2) && if_stmt_alt(n3))
						{
							n.addNode(n2);
							n.addNode(n3);
							return true;
						}
						else return false;
					}
					else
						printError();
				}
				else
					printError();
			}
			else
				printError();
		}
		return false;
	}
	
	boolean if_stmt_alt(Node n) {
		Node n1= new Node("Statement");
		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("ELSE")) {
			allTokens.poll();
			n.addNode(new Node("ELSE"));
			if(stmt(n1))
			{
				n.addNode(n1);
				return true;
			}
			else
				printError();
		}
		return true;
	}
	
	boolean return_stmt(Node n) {
		Node n1 =new Node("Return Statement alternative");
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("RETURN")) {
			allTokens.poll();
			n.addNode(new Node("RETURN"));
			if(return_stmt_alt(n1)) {
				n.addNode(n1);
				if(allTokens.peek().TYPE.equals("SEMICOLON")) {
					allTokens.poll();
					n1.addNode(new Node("SEMICOLON"));
					return true;
				}
				else
					printError();
			}
			else
				printError();
		}
		return false;
	}
	
	boolean return_stmt_alt(Node n) {
		Node n1 =new Node("Expression");
		if (expr(n1)) n.addNode(n1); 
		return true;
	}
	
	boolean break_stmt(Node n) {
		
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("BREAK")) {
			allTokens.poll();
			n.addNode(new Node("BREAK"));
			if(allTokens.peek().TYPE.equals("SEMICOLON")) {
				allTokens.poll();
				n.addNode(new Node("SEMICOLON"));
				return true;
			}
			else
				printError();
		}
		return false;
	}
	
	boolean local_decls(Node n) {
		Node n1 = new Node("Local Decleration");
		Node n2= new Node("Local Delclerations");
		if(local_decl(n1) && local_decls(n2)) {n.addNode(n1); n.addNode(n2);}
		return true;
	}
	
	boolean local_decl(Node n) {
		Node n1=new Node("local decleration alternative");
		Node n2= new Node("type spec");
		if(allTokens.peek() == null) {
			return false;
		}
		if(type_spec(n2)) {
			n.addNode(n2);
			if(allTokens.peek().TYPE.equals("ID")) {
				n.addNode(new Node("ID"));
				allTokens.poll();
				if(local_decl_alt(n1)) {
					n.addNode(n1);
					if(allTokens.peek().TYPE.equals("SEMICOLON")) {
						n.addNode(new Node("SEMICOLON"));
						allTokens.poll();
						return true;
					}
					else
						printError();
				}
				else
					printError();
			}
		}
		return false;
	}
	
	boolean local_decl_alt(Node n) {
		
		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			allTokens.poll();
			n.addNode(new Node("Left square Bracket"));
			if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
				allTokens.poll();
				n.addNode(new Node("RIGHT square brackets"));
				return true;
			}
			else
				printError();
		}
		return true;
	}
	
	boolean expr(Node n) {
		Node n1=new Node("EXPRESSION ID ALTERNATIVE");
		Node n2= new Node("Expression rec alternative");
		Node n3=new Node("EXPRESION");

		if(allTokens.peek() == null) {
			return false;
		}
		if(allTokens.peek().TYPE.equals("ID")) {
			allTokens.poll();
			n.addNode(new Node("ID"));
			if(expr_id_alt(n1) && expr_rec_alt(n2))
			{	n.addNode(n1);
				n.addNode(n2);
				return true;
			}
				
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("NOT")) {
			allTokens.poll();
			n.addNode(new Node("NOT"));
			if(expr(n3) && expr_rec_alt(n2))
			{
				n.addNode(n3);
				n.addNode(n2);
				return true;

			}
			else
			{
				printError();

			}
		}
		else if(allTokens.peek().TYPE.equals("NEGATIVE")) {
			allTokens.poll();
			n.addNode(new Node("Negative"));

			if(expr(n3) && expr_rec_alt(n2))
			{
				n.addNode(n3);
				n.addNode(n2);
				return true;
			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("POSITIVE")) {
			allTokens.poll();
			n.addNode(new Node("Positive"));

			if(expr(n3) && expr_rec_alt(n2))
				{		n.addNode(n3);
				n.addNode(n2);
				return true;
		}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
			allTokens.poll();
			n.addNode(new Node("LEft_Round bracket"));

			if(expr(n3)) {
				if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
					n.addNode(new Node("Right round bracket"));
					n.addNode(n3);
			
					allTokens.poll();
					if( expr_rec_alt(n2))
						{
						n2.addNode(n2);;
						return true;
						}return false;
				}
				else
					printError();
			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("BOOL_LIT")) {
			allTokens.poll();
			n.addNode(new Node("Bool literal"));

			if(expr_rec_alt(n2))
			{
				n.addNode(n2);
				return true;
		
			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("INT_LIT")) {
			allTokens.poll();
			n.addNode(new Node("Integer Literal"));

			if(expr_rec_alt(n2))
			{	n.addNode(n2);
				return true;

			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("FLOAT_LIT")) {
			allTokens.poll();
			n.addNode(new Node("Float literal"));

			if(expr_rec_alt(n2))
			{
				n.addNode(n2);
				return true;
		
			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("NEW")) {
			allTokens.poll();
			n.addNode(new Node("New"));
			Node n4=new Node("type spec");
			if(type_spec(n4)) {
				n.addNode(n4);
				if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
					n.addNode(new Node("Left square bracket"));

					allTokens.poll();
					if(expr(n3)) {
						if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
							allTokens.poll();
							n.addNode(new Node("Right Square bracket"));
							n.addNode(n3);
							if( expr_rec_alt(n2)) {n.addNode(n2);return true;}
							return false;
						}
						else
							printError();
					}
					else
						printError();
				}
				else
					printError();
			}
			else
				printError();
		}
		return false;
	}
	
	boolean expr_id_alt(Node n) {
		Node n1=new Node("EXPRESSION");
		Node n2=new Node("Expression Arr Alternative");
		Node n3=new Node("Arguments");


		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("ASSIGNMENT")) {
			allTokens.poll();
			n.addNode(new Node("ASSIGNMENT"));
			if(expr(n1))
			{
				n.addNode(n1);
				return true;
			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			n.addNode(new Node("Left Square bracket"));
			allTokens.poll();
			if(expr(n1)) {
				n.addNode(n1);
				if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
					allTokens.poll();
					if(expr_arr_alt(n2))
					{
						n.addNode(n2);
						return true;
					}
					else
						printError();
				}
				else
					printError();
			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
			allTokens.poll();
			n.addNode(new Node("left round bracket"));
			if(args(n3)) {
				n.addNode(n3);
				if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
					allTokens.poll();
					n.addNode(new Node("right round bracket"));
					return true;
				}
				else
					printError();
			}
			else
				printError();
		}
		else if(allTokens.peek().TYPE.equals("DOT")) {
			allTokens.poll();
			n.addNode(new Node("DOT"));
			if(allTokens.peek().TYPE.equals("SIZE")) {
				allTokens.poll();
				n.addNode(new Node("SIZE"));
				return true;
			}
			else
				printError();
		}
		return true;
	}
	
	boolean expr_rec_alt(Node n) {

		Node n1=new Node("Expression op alternative");
		Node n2=new Node("Expression");
		Node n3=new Node("expression rec alt");
		if( expr_op_alt(n1) && expr(n2) && expr_rec_alt(n3))
			{
			n.addNode(n1);
			n.addNode(n2);
			n.addNode(n3);
			}return true;
	}

	boolean expr_op_alt(Node n) {
		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("OR")) {
			allTokens.poll();
			n.addNode(new Node("OR"));
			return true;
		}
		else if(allTokens.peek().TYPE.equals("EQUAL")) {
			allTokens.poll();
			n.addNode(new Node("EQUAL"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("NOT EQUAL")) {
			allTokens.poll();
			n.addNode(new Node("NOT EQUAL"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("LESS THAN OR EQUAL")) {
			allTokens.poll();
			n.addNode(new Node("LESS THAN OR EQUAL"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("LESSTHAN")) {
			allTokens.poll();
			n.addNode(new Node("LESS THAN"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("GREATER THAN OR EQUAL")) {
			allTokens.poll();
			n.addNode(new Node("GREATER THAN OR EQUAL"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("GREATER THAN")) {
			allTokens.poll();
			n.addNode(new Node("GREATER THAN"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("AND")) {
			allTokens.poll();
			n.addNode(new Node("AND"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("PLUS")) {
			allTokens.poll();
			n.addNode(new Node("PLUS"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("MINUS")) {
			allTokens.poll();
			n.addNode(new Node("MINUS"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("MULTIPLY")) {
			allTokens.poll();
			n.addNode(new Node("MULT"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("DIVIDE")) {
			allTokens.poll();
			n.addNode(new Node("DIVIDE"));

			return true;
		}
		else if(allTokens.peek().TYPE.equals("MOD")) {
			allTokens.poll();
			n.addNode(new Node("MOD"));

			return true;
		}
		return false;
	}
	
	boolean expr_arr_alt(Node n) {
		Node n1=new Node("Expression");
		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("ASSIGNMENT")) {
			allTokens.poll();
			n.addNode(new Node("Assignment"));
			if(expr(n1))
			{
				n.addNode(n1);
				return true;
				
			}
			else
				printError();
		}
		return true;
	}
	
	boolean args(Node n) {
		Node n1= new Node("ARG LIST");
		if(arg_list(n1))
		{
			n.addNode(n1);
		}
		return true;
	}

	boolean arg_list(Node n) {
		Node n1=new Node("EXPRESSION");
		Node n2= new Node("Argument List alternative");
		if( expr(n1) && arg_list_alt(n2))
		{
			n.addNode(n1);
			n.addNode(n2);
			return true;
		}
		return false;
	}
	
	boolean arg_list_alt(Node n) {
		Node n1=new Node("Expression");
		Node n2= new Node("arg list alternative");
		
		if(allTokens.peek() == null) {
			return true;
		}
		if(allTokens.peek().TYPE.equals("COMMA")) {
			allTokens.poll();
			n.addNode(new Node ("Comma"));
			if(expr(n1) && arg_list_alt(n2))
			{
				n.addNode(n1);
				n.addNode(n2);
				return true;

			}
			else
				printError();
		}
		return true;
	}
	
}
