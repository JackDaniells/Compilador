public class EntrySimple extends EntryTable {

  public EntrySimple (String n) {
    name = n;
  }
  
  public String dscJava() {
	return ((name.equals("int")) ? "I" : "Ljava/lang/String;");
  }
  
}
