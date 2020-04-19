import java.util.LinkedList;

public class Parser {
	LinkedList<Token> allTokens = null;
	public Parser(LinkedList<Token> inTokens)
	{
		allTokens = inTokens;
	}
	
	public void program() {
		decl_list();
	}
	
	void decl_list() {
		decl();
		decl_list_alt();
	}
	
	void decl_list_alt() {
		decl();
		decl_list_alt();
		
		else if(allTokens.peek() == null) {
			allTokens.poll();
			return true;
		}
		else {
			System.out.println("Syntax error: unexpected token <" + allTokens.peek().TYPE + ">: " + allTokens.peek().VALUE);
			return false;
		}
	}
	
	void decl() {
		return var_decl() || fun_decl();
	}
	
	void var_decl() {
		if(type_spec())
		
		
	}
