public class Type {
	public EntryTable ty;
	public int dim;
	public Type(EntryTable t, int d) {
		ty = t;
		dim = d;
	}
	public String dscJava() {
		return EntryTable.strDim(dim) + ty.dscJava();
	}
}
