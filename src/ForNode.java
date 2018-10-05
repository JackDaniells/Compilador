public class ForNode extends StatementNode {
	public AtribNode init, incr;
	public StatementNode stat;
	public ExpreNode expr;
	
	public ForNode(Token t, ExpreNode e, AtribNode a1, AtribNode a2, StatementNode s) {
		super(t);
		expr = e;
		init = a1;
		incr = a2;
		stat = s;
	}
}