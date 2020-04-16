import java.util.ArrayList;

public class Node {
	String TYPE;
	ArrayList<Node> ChildNodes = null;
	
	public Node(String type) {
		TYPE = type;
	}
	
	String getType() {
		return TYPE;
	}

	public void addNode(String type) {
		if(ChildNodes == null)
			ChildNodes = new ArrayList<Node>();
		ChildNodes.add(new Node(type));
	}
	
	public void print() {
		System.out.print("[ ");
		for(Node node: ChildNodes) {
			System.out.print(node.getType());
		}
		System.out.print(" ]");
		for(Node node: ChildNodes) {
			node.print();
		}
	}
	
}
