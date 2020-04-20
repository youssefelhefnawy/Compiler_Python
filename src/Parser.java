import java.util.LinkedList;

public class Parser {
	LinkedList<Token> allTokens = null;
	public Parser(LinkedList<Token> inTokens)
	{
		allTokens = inTokens;
	}
	
	public void program() {
		if(!decl_list())
			System.out.println("Syntax error: unexpected token <" + allTokens.peek().TYPE + ">:" + allTokens.peek().VALUE);
	}
	
	boolean decl_list() {
		return decl() && decl_list_alt();
	}
	
	boolean decl_list_alt() {
		return decl() && decl_list_alt() || true;
	}
	
	boolean decl() {
		if(type_spec()) {
			if(allTokens.peek().TYPE.equals("ID")) {
				allTokens.poll();
				return decl_alt();
			}
		}
		return false;
	}
	
	boolean decl_alt() {
		return var_decl() || fun_decl();
	}
	
	boolean var_decl() {
		if(allTokens.peek().TYPE.equals("SEMICOLON")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("LEFT_SQUARE_B")) {
			allTokens.poll();
			if(allTokens.peek().TYPE.equals("RIGHT_SQUARE_B")) {
				allTokens.poll();
				if(allTokens.peek().TYPE.equals("SEMICOLON")) {
					allTokens.poll();
					return true;
				}
			}
		}
		return false;
	}
	
	boolean fun_decl() {
		if(allTokens.peek().TYPE.equals("LEFT_ROUND_B")) {
			allTokens.poll();
			if(params()) {
				if(allTokens.peek().TYPE.equals("RIGHT_ROUND_B")) {
					allTokens.poll();
					return compound_stmt();
				}
			}
		}
		return false;
	}
	
	boolean type_spec() {
		if(allTokens.peek() == null)
			return false;
		if(allTokens.peek().TYPE.equals("VOID")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("BOOL")) {
			allTokens.poll();
			return true;
		}
		else if(allTokens.peek().TYPE.equals("INT")) {
			allTokens.poll();
			return true;
		}
		else if (allTokens.peek().TYPE.equals("FLOAT")) {
			allTokens.poll();
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
		if(type_spec()) {
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
		if(type_spec()) {
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
			if(type_spec()) {
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
