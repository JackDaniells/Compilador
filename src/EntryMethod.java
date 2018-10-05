public class EntryMethod extends EntryTable {
  public EntryTable type;
  public int dim;
  public EntryRec param;
  public int totallocals;
  public int totalstack;
  public boolean fake;
  public boolean hassuper;
  
  public EntryMethod (String n, EntryTable p, int d, EntryRec r) {
    name = n;
    type = p;
    dim = d;
    param = r;
    totallocals = 0;
    totalstack = 0;
    fake = false;
    hassuper = false;
  }

  public EntryMethod (String n, EntryTable p, boolean b) {
    name = n;
    type = p;
    dim = 0;
    param = null;
    totallocals = 0;
    totalstack = 0;
    fake = b;
    hassuper = false;
  }
  
  public String dscJava() {
	String s = strDim(dim);
	s += type.dscJava();

	return s;
  }
}
