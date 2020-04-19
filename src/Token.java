
public class Token {
	String TYPE;
	String VALUE;
	public Token (String t,String v)
	{
		TYPE=t;
		VALUE=v;
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
