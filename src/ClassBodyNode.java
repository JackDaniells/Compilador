public class ClassBodyNode extends GeneralNode {
	public ListNode vlist;
	public ListNode clist;
	public ListNode ctlist;
	public ListNode mlist;
	
	public ClassBodyNode (Token t1, ListNode c, ListNode v, ListNode ct, ListNode m) {
		super(t1);
		clist = c;
		vlist = v;
		ctlist = ct;
		mlist = m;		
	}
}