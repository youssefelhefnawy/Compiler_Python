
public class Token {
	String TYPE;
	String VALUE;
	
	public Token(String type, String value) {
		TYPE = type;
		VALUE = value;
	}

	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String type) {
		TYPE = type;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String value) {
		VALUE = value;
	}
	
}
