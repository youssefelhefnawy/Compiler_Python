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
	private Node  AdjustParseTree(Stack<Node> ParseTree,Node Root) 
	{	
		
		int Index=ParseTree.indexOf(Root);
		Index+=1;
		for(;Index<ParseTree.size();Index++)
		{
			Root.addNode(ParseTree.elementAt(Index));
		}
		return Root;
	}
	private void PrintTree(Stack<Node>ParseTree)
	{
		for(Node node:ParseTree) 
		{
			System.out.println(node.getType());
		}
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
		n.addNode(decl);
		n.addNode(decl_list_alt);
		if( decl(decl) && decl_list_alt(decl_list_alt))
			{
			return true;
			}
		else return false;
	}
	
	boolean decl_list_alt(Node n) 
	{	
		Node n1=new Node("decleration");
		Node n2=new Node("decleration list alternative");
		Node n3=new Node("EMPTY");

		if( decl(n1) && decl_list_alt(n2))
		{
			n.addNode(n1);
			n.addNode(n2);
			return true;
		}
		else
		{
			//n.addNode(n3);
			return true;
		}
		
	}
	
	boolean decl(Node n) {
		
		Node n1=new Node("typespec");
		Node n2=new Node("ID");
		Node n3=new Node("decleration_alt");
		n.addNode(n1);
		n.addNode(n2);
		n.addNode(n3);
		if(type_spec(n1)) {
			
			//ParseTree.add(AdjustParseTree(ParseTree,new Node("Type Spec")));
			if(allTokens.peek().TYPE.equals("ID")) {
				allTokens.poll();
			//	ParseTree.add(new Node("ID"));
				
	
				if(decl_alt(n3));
				{
				//	ParseTree.add(AdjustParseTree(ParseTree,new Node("Decleration Alternative")));
					
					
					return true;
				}
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
		
		if(allTokens.peek().TYPE.equals("SEMICOLON")) {
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
			}
		}
		return false;
	}
	
	boolean fun_decl(Node n) {
		if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
			allTokens.poll();
			n.addNode(new Node("LEFT_ROUND_B"));
			if(params()) {
				if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
					allTokens.poll();
					n.addNode(new Node("RIGHT_ROUND_B"));
					return compound_stmt();
				}
			}
		}
		return false;
	}
	
	boolean type_spec(Node n) {
		if(allTokens.peek() == null)
			return false;
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
	
	boolean params() {
		if(allTokens.peek().TYPE.equals("VOID")) {
			allTokens.poll();
			return true;
		}
		return param_list() || true;
	}
	
	boolean param_list() {
		return param() && param_list_alt();
	}
	
	boolean param_list_alt() {
		if(allTokens.peek().TYPE.equals("COMMA")) {
			allTokens.poll();
			return param() && param_list_alt() || true;
		}
		return true;
	}
	
	boolean param() {
		if(type_spec(null)) {
			if(allTokens.peek().TYPE.equals("ID")) {
				allTokens.poll();
				return param_alt();
			}
		}
		return false;
	}
	
	boolean param_alt() {
		if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			allTokens.poll();
			if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
				allTokens.poll();
				return true;
			}
		}
		return true;
	}
	
	boolean compound_stmt() {
		if(allTokens.peek().TYPE.equals("LEFT_CURLY_B")) {
			allTokens.poll();
			if(local_decls() && stmt_list()) {
				if(allTokens.peek().TYPE.equals("RIGHT_CURLY_B")) {
					allTokens.poll();
					return true;
				}
			}
		}
		return false;	
	}
	
	boolean stmt_list() {
		return stmt() && stmt_list() || true;
	}
	
	boolean stmt() {
		return expr_stmt() || compound_stmt() || if_stmt() || while_stmt() || return_stmt() || break_stmt();
	}
	
	boolean expr_stmt() {
		if(allTokens.peek().TYPE.equals("SEMICOLON")) {
			allTokens.poll();
			return true;
		}
		else if(expr()) {
			if(allTokens.peek().TYPE.equals("SEMICOLON")) {
				allTokens.poll();
				return true;
			}
		}
		return false;
	}
	
	boolean while_stmt() {
		if(allTokens.peek().TYPE.equals("WHILE")) {
			allTokens.poll();
			if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
				allTokens.poll();
				if(expr()) {
					if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
						allTokens.poll();
						return stmt();
					}
				}
			}
		}
		return false;
	}
	
	boolean if_stmt() {
		if(allTokens.peek().TYPE.equals("IF")) {
			allTokens.poll();
			if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
				allTokens.poll();
				if(expr()) {
					if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
						allTokens.poll();
						return stmt() && if_stmt_alt();
					}
				}
			}
		}
		return false;
	}
	
	boolean if_stmt_alt() {
		if(allTokens.peek().TYPE.equals("ELSE")) {
			allTokens.poll();
			return stmt() || true;
		}
		return true;
	}
	
	boolean return_stmt() {
		if(allTokens.peek().TYPE.equals("RETURN")) {
			allTokens.poll();
			if(return_stmt_alt()) {
				if(allTokens.peek().TYPE.equals("SEMICOLON")) {
					allTokens.poll();
					return true;
				}
			}
		}
		return false;
	}
	
	boolean return_stmt_alt() {
		return expr() || true;
	}
	
	boolean break_stmt() {
		if(allTokens.peek().TYPE.equals("BREAK")) {
			allTokens.poll();
			if(allTokens.peek().TYPE.equals("SEMICOLON")) {
				allTokens.poll();
				return true;
			}
		}
		return false;
	}
	
	boolean local_decls() {
		return local_decl() && local_decls() || true;
	}
	
	boolean local_decl() {
		if(type_spec(null)) {
			if(allTokens.peek().TYPE.equals("ID")) {
				allTokens.poll();
				if(local_decl_alt()) {
					if(allTokens.peek().TYPE.equals("SEMICOLON")) {
						allTokens.poll();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	boolean local_decl_alt() {
		if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			allTokens.poll();
			if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
				allTokens.poll();
				return true;
			}
		}
		return false;
	}
	
	boolean expr() {
		if(allTokens.peek().TYPE.equals("ID")) {
			allTokens.poll();
			return expr_id_alt() && expr_rec_alt();
		}
		else if(allTokens.peek().TYPE.equals("NOT")) {
			allTokens.poll();
			return expr() && expr_rec_alt();
		}
		else if(allTokens.peek().TYPE.equals("NEGATIVE")) {
			allTokens.poll();
			return expr() && expr_rec_alt();
		}
		else if(allTokens.peek().TYPE.equals("POSITIVE")) {
			allTokens.poll();
			return expr() && expr_rec_alt();
		}
		else if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
			allTokens.poll();
			if(expr()) {
				if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
					allTokens.poll();
					return expr_rec_alt();
				}
			}
		}
		else if(allTokens.peek().TYPE.equals("BOOL_LIT")) {
			allTokens.poll();
			return expr_rec_alt();
		}
		else if(allTokens.peek().TYPE.equals("INT_LIT")) {
			allTokens.poll();
			return expr_rec_alt();
		}
		else if(allTokens.peek().TYPE.equals("FLOAT_LIT")) {
			allTokens.poll();
			return expr_rec_alt();
		}
		else if(allTokens.peek().TYPE.equals("NEW")) {
			allTokens.poll();
			if(type_spec(null)) {
				if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
					allTokens.poll();
					if(expr()) {
						if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
							allTokens.poll();
							return expr_rec_alt();
						}
					}
				}
			}
		}
		return false;
	}
	
	boolean expr_id_alt() {
		if(allTokens.peek().TYPE.equals("ASSIGNMENT")) {
			allTokens.poll();
			return expr() || true;
		}
		else if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			allTokens.poll();
			if(expr()) {
				if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
					allTokens.poll();
					return expr_arr_alt() || true;
				}
			}
		}
		else if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
			allTokens.poll();
			if(args()) {
				if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
					allTokens.poll();
					return true;
				}
			}
		}
		else if(allTokens.peek().TYPE.equals("DOT")) {
			allTokens.poll();
			if(allTokens.peek().TYPE.equals("SIZE")) {
				allTokens.poll();
				return true;
			}
		}
		return true;
	}
	
	boolean expr_rec_alt() {
		return expr_op_alt() && expr() && expr_rec_alt() || true;
	}

	boolean expr_op_alt() {
		if(allTokens.peek().TYPE.equals("OR")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("EQUAL")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("NOT EQUAL")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("LESS THAN OR EQUAL")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("LESSTHAN")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("GREATER THAN OR EQUAL")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("GREATER THAN")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("AND")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("PLUS")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("MINUS")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("MULTIPLY")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("DIVIDE")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("MOD")) {
			allTokens.poll();
			return true;
		}
		return true;
	}
	
	boolean expr_arr_alt() {
		if(allTokens.peek().TYPE.equals("ASSIGNMENT")) {
			allTokens.poll();
			return expr() || true;
		}
		return true;
	}
	
	boolean args() {
		return arg_list() || true;
	}

	boolean arg_list() {
		return expr() && arg_list_alt();
	}
	
	boolean arg_list_alt() {
		if(allTokens.peek().TYPE.equals("COMMA")) {
			allTokens.poll();
			return expr() && arg_list_alt() || true;
		}
		return true;
	}
	
}
