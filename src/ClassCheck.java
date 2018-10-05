public class ClassCheck {
	Symtable Maintable; 			
	protected Symtable Curtable;	
	int foundSemanticError;

	public ClassCheck() {
		foundSemanticError = 0;
		Maintable = new Symtable();
		Maintable.add(new EntrySimple("int"));
		Maintable.add(new EntrySimple("string"));
		Maintable.add(new EntrySimple("float"));
		Maintable.add(new EntrySimple("char"));
		Maintable.add(new EntrySimple("boolean"));
	}

	public void ClassCheckRoot(ListNode x) throws SemanticException {
		Curtable = Maintable;			
		ClassCheckClassDeclListNode(x);	
		if (foundSemanticError != 0)	
			throw new SemanticException(foundSemanticError + " Semantic Erros found (phase 1)");
	}

	public void ClassCheckClassDeclListNode(ListNode x) {
		if (x == null) return;
		try {
			ClassCheckClassDeclNode( (ClassDeclNode) x.node);
		}
		catch (SemanticException e) { 
			System.out.println(e.getMessage());
		  	foundSemanticError++;
		}
		ClassCheckClassDeclListNode(x.next);
	}

	public void ClassCheckClassDeclNode(ClassDeclNode x) throws SemanticException {
		Symtable temphold = Curtable;
        EntryClass nc;

        if (x == null)
            return;

        nc = (EntryClass) Curtable.classFindUp(x.name.image);

        if (nc != null)
            throw new SemanticException(x.name, "Class " + x.name.image + " already declared");
        Curtable.add(nc = new EntryClass(x.name.image, Curtable));
        Curtable = nc.nested;
        ClassCheckClassBodyNode(x.body);
        Curtable = temphold;
	}

	public void ClassCheckClassBodyNode(ClassBodyNode x) {
		if (x == null) return;
		ClassCheckClassDeclListNode(x.clist);
	}

}