import java.util.ArrayList;

public class Node {
	String TYPE;
	public ArrayList<Node> ChildNodes = null;
	
	public Node(String type) 
	{
		TYPE = type;
	}
	public Node()
	{
		;
	}
	
	String getType() {
		return TYPE;
	}

	public void addNode(Node node) {
		if(ChildNodes == null)
			ChildNodes = new ArrayList<Node>();
		ChildNodes.add(node);
	}
	
	public void print(int Tabs) {
		if(ChildNodes!=null)
		{		
				System.out.print(InsertTabs(Tabs));
				System.out.println("CHILDREN OF  :---->"+this.getType());

				
				System.out.print(InsertTabs(Tabs));
				System.out.print("[ ");
				for(Node node: ChildNodes) {
						System.out.print(" "+node.getType()+" ");
					}
		System.out.print(" ]");
		System.out.println();
			Tabs+=1;
			for(Node node: ChildNodes) {
				node.print(Tabs);
				}

		
		}
	
	}
	private String InsertTabs(int tabs)
	{	String temp="";
		for(int i=0;i<tabs;i++)
		{
			temp+="\t";
		}
		return temp;
	}
}


