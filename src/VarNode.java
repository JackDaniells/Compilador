public class VarNode extends ExpreNode {
	public int dim;
	public AtribNode atrib;
	
	public VarNode(Token t) {
		super(t);
		dim = 0;
		atrib = null;
	}

	
	public VarNode(Token t, int k) {
		super(t);
		dim = k;
		atrib = null;
	}
	
	public void setAtribNode(AtribNode a) {
		atrib = a;
	}
}