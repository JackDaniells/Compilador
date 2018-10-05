public class ListNode extends GeneralNode {
	public ListNode next;
	public GeneralNode node;
	
	public ListNode (GeneralNode t2) {
		super(t2.position);
		node = t2;
		next = null;
	}
	
	public ListNode (GeneralNode t2, ListNode l) {
		super(t2.position);
		next = l;
		node = t2;
	}
	
	public void add(GeneralNode t2) {
		if (next == null) 
			next = new ListNode(t2);
		else
			next.add(t2);
	}
}