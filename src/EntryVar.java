public class EntryVar extends EntryTable {
  public EntryTable type;
  public int dim;
  public int localcount;
  
  public EntryVar(String n, EntryTable p, int d) {
    name = n;
    type = p;
    dim = d;
    localcount = -1;
  }
  
  public EntryVar(String n, EntryTable p, int d, int k) {
    name = n;
    type = p;
    dim = d;
    localcount = k;
  }
  
  public String dscJava() {
	String s = strDim(dim);
	s += type.dscJava();

	return s;
  }


}
