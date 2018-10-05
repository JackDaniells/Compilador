import java.util.*;

public class First {
	static public final RecoverySet methoddecl = new RecoverySet();
	static public final RecoverySet vardecl = new RecoverySet();
	static public final RecoverySet classlist = new RecoverySet();
	static public final RecoverySet constructdecl = new RecoverySet();
	static public final RecoverySet statlist = new RecoverySet();
	static public final RecoverySet program = classlist;
	
	static {
		methoddecl.add(new Integer(LangGEDConstants.INT));
		methoddecl.add(new Integer(LangGEDConstants.STRING));
		methoddecl.add(new Integer(LangGEDConstants.IDENT));
		
		vardecl.add(new Integer(LangGEDConstants.INT));
		vardecl.add(new Integer(LangGEDConstants.STRING));
		vardecl.add(new Integer(LangGEDConstants.IDENT));
		
		classlist.add(new Integer(LangGEDConstants.CLASS));
		
		constructdecl.add(new Integer(LangGEDConstants.CONSTRUCTOR));
		
		statlist.addAll(vardecl);
		statlist.add(new Integer(LangGEDConstants.IDENT));
		statlist.add(new Integer(LangGEDConstants.PRINT));
		statlist.add(new Integer(LangGEDConstants.READ));
		statlist.add(new Integer(LangGEDConstants.RETURN));
		statlist.add(new Integer(LangGEDConstants.SUPER));
		statlist.add(new Integer(LangGEDConstants.IF));
		statlist.add(new Integer(LangGEDConstants.FOR));
		statlist.add(new Integer(LangGEDConstants.LBRACE));
		statlist.add(new Integer(LangGEDConstants.BREAK));
		statlist.add(new Integer(LangGEDConstants.SEMICOLON));
	}
}