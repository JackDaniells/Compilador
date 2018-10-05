public class IndexNode extends ExpreNode {
	public ExpreNode expr1, expr2;
	
	public IndexNode(Token t, ExpreNode e1, ExpreNode e2) {
		super(t);
		expr1 = e1;
		expr2 = e2;
	}
}