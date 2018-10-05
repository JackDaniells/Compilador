abstract public class EntryTable {
  public String name;       
  public EntryTable next;  
  public int scope;        
  public Symtable mytable; 

  abstract public String dscJava();
  
  static public String strDim(int n) {
	String p = "";
	for (int i = 0 ; i < n ; i++)
		p += "[";
	return p;
  }
}
