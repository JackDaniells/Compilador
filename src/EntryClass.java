public class EntryClass extends EntryTable {
  public Symtable nested;
  public EntryClass parent;
  
  public EntryClass (String n, Symtable t) {
    name = n;
	nested = new Symtable(this);
    parent = null;
  }
  
  public String dscJava() {
	return "L" + completeName() + ";";
  }
  
  private String completeName() {
	Symtable t = mytable;
	EntryClass up = (EntryClass) t.levelup;
	String p = ((up == null) ? "" : up.completeName() + "$");

	return p + name;
  }
}
