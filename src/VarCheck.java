public class VarCheck extends ClassCheck {
	public VarCheck() {
		super();
	}
	
	public void VarCheckRoot(ListNode x) throws SemanticException {
		ClassCheckRoot(x);
		VarCheckClassDeclListNode(x);
		if (foundSemanticError != 0)
			throw new SemanticException(foundSemanticError + " Semantic Errors found (phase 2)");
	}
	
	public void VarCheckClassDeclListNode(ListNode x) {
		if (x == null)
			return;
		try {
			VarCheckClassDeclNode((ClassDeclNode) x.node);
		} catch(SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		VarCheckClassDeclListNode(x.next);
	}
	
	public void VarCheckClassDeclNode(ClassDeclNode x) throws SemanticException {
		EntryClass nc;
		EntryClass c = null;
		Symtable temphold = Curtable;
		if (x == null)
			return;
		if (x.supername != null) {
			c = (EntryClass) Curtable.classFindUp(x.supername.image);
			if (c == null)
				throw new SemanticException(x.position, " Superclass " + x.supername.image + " not found");
		}
		nc = (EntryClass) Curtable.classFindUp(x.name.image);
		nc.parent = c;
		Curtable = nc.nested;
		VarCheckClassBodyNode(x.body);
		Curtable = temphold;
	}
	
	public void VarCheckVarDeclListNode(ListNode x) {
		if (x == null)
			return;
		try {
			VarCheckVarDeclNode((VarDeclNode) x.node);
		} catch(SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		VarCheckVarDeclListNode(x.next);
	}
	
	public void VarCheckConstructDeclListNode(ListNode x) {
		if (x == null) 
			return;
		try {
			VarCheckConstructDeclNode((ConstructDeclNode) x.node);
		} catch(SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		VarCheckConstructDeclListNode(x.next);
	}
	
   public void VarCheckMethodDeclListNode(ListNode x) {
      if (x == null) 
			return;
		try {
			VarCheckMethodDeclNode((MethodDeclNode) x.node);
		} catch(SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		VarCheckMethodDeclListNode(x.next);
   }
   
   public void VarCheckMethodDeclNode(MethodDeclNode x) throws SemanticException {
		EntryMethod c;
		EntryRec r = null;
		EntryTable e;
		ListNode p;
		VarDeclNode q;
		VarNode u;
		int n;
		if (x == null)
			return;
		p = x.body.param;
		n = 0;
		while (p != null) {
			q = (VarDeclNode) p.node;
			u = (VarNode) q.vars.node;
			n++;
			e = Curtable.classFindUp(q.position.image);
			if (e == null) 
				throw new SemanticException(x.position, " Class " + q.position.image + " not found");
			r = new EntryRec(e, u.dim, n, r,(u.atrib == null) ? false : true);
			p = p.next;
		}
		if (r != null)
			r = r.inverte();
		e = Curtable.classFindUp(x.position.image);
		if (e == null) 
            throw new SemanticException(x.position, " Class (tipo de retorno não é válido) " + x.position.image + " not found");
		c = Curtable.methodFindInClass(x.name.image, r);
      if (c == null) {
         c = new EntryMethod(x.name.image, e, x.dim, r);
         Curtable.add(c);
		} else {
			throw new SemanticException(x.position + " Method " +
				Curtable.levelup.name + 
				"(" + (r == null ? "" : r.toString()) + ")" +
				" already declared");
		}
	}
   
	public void VarCheckConstructDeclNode(ConstructDeclNode x) throws SemanticException {
		EntryMethod c;
		EntryRec r = null;
		EntryTable e;
		ListNode p;
		VarDeclNode q;
		VarNode u;
		int n;
		if (x == null)
			return;
		p = x.body.param;
		n = 0;
		while (p != null) {
			q = (VarDeclNode) p.node;
			u = (VarNode) q.vars.node;
			n++;
			e = Curtable.classFindUp(q.position.image);
			if (e == null) 
				throw new SemanticException(x.position, " Class " + x.position.image + " not found");
			r = new EntryRec(e, u.dim, n, r,(u.atrib == null) ? false : true);
			p = p.next;
		}
		if (r != null)
			r = r.inverte();
		c = Curtable.methodFindInClass("constructor", r);
		if (c == null) {
			c = new EntryMethod("constructor", Curtable.levelup, 0, r);
			Curtable.add(c);
		} else {
			throw new SemanticException(x.position + " Constructor " +
				Curtable.levelup.name + 
				"(" + (r == null ? "" : r.toString()) + ")" +
				" already declared");
		}
	}
	
	public void VarCheckVarDeclNode(VarDeclNode x) throws SemanticException {
		ListNode p;
		EntryTable c;
		if (x == null)
			return;
		c = Curtable.classFindUp(x.position.image);
		if (c == null)
			throw new SemanticException(x.position, " Class " + x.position.image + " not found");
		for (p = x.vars ; p != null ; p = p.next) {
			VarNode q = (VarNode) p.node;
			Curtable.add(new EntryVar(q.position.image, c, q.dim));
		}
	}
	
	public void VarCheckClassBodyNode(ClassBodyNode x) {
		if (x == null)
			return;
		VarCheckClassDeclListNode(x.clist);
		VarCheckVarDeclListNode(x.vlist);
		VarCheckConstructDeclListNode(x.ctlist);
		
		if (Curtable.methodFindInClass("constructor", null) == null) 
			Curtable.add(new EntryMethod("constructor",Curtable.levelup, true));
		VarCheckMethodDeclListNode(x.mlist);
	}
}