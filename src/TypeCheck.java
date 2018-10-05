public class TypeCheck extends VarCheck {
  int nesting;
  protected int Nlocals;
  Type Returntype;
  protected final EntrySimple STRING_TYPE,
							  INT_TYPE,
							  NULL_TYPE,
							  FLOAT_TYPE,
							  CHAR_TYPE,
							  BOOLEAN_TYPE;
  protected EntryMethod CurMethod;
  boolean cansuper;

  public TypeCheck() {
    super();
    nesting = 0;
    Nlocals = 0;
    STRING_TYPE = (EntrySimple) Maintable.classFindUp("string");
    INT_TYPE = (EntrySimple) Maintable.classFindUp("int");
	FLOAT_TYPE = (EntrySimple) Maintable.classFindUp("float");
	CHAR_TYPE = (EntrySimple) Maintable.classFindUp("char");
	BOOLEAN_TYPE = (EntrySimple) Maintable.classFindUp("boolean");
    NULL_TYPE = new EntrySimple("$NULL$");
    Maintable.add(NULL_TYPE);
  }

  public void TypeCheckRoot(ListNode x) throws SemanticException {
    VarCheckRoot(x);
    TypeCheckClassDeclListNode(x);
    if (foundSemanticError != 0)
      throw new SemanticException(foundSemanticError + "Semantic Errors found (phase 3)");
  }

  public void TypeCheckClassDeclListNode(ListNode x) {
    if (x == null)
		return;
    try {
      TypeCheckClassDeclNode((ClassDeclNode) x.node);
    } catch (SemanticException e) {
      System.out.println(e.getMessage());
      foundSemanticError++;
    }
    TypeCheckClassDeclListNode(x.next);
  }

  public void TypeCheckClassDeclNode(ClassDeclNode x) throws SemanticException {
    Symtable temphold = Curtable;
    EntryClass nc;

    if (x == null) 
		return;
    nc = (EntryClass) Curtable.classFindUp(x.name.image);
    if (circularSuperclass(nc, nc.parent)) {
      nc.parent = null;
      throw new SemanticException(x.position, "Circular inheritance");
    }

    Curtable = nc.nested;
    TypeCheckClassBodyNode(x.body);
    Curtable = temphold;
  }

  private boolean circularSuperclass(EntryClass orig, EntryClass e) {
    if (e == null) return false;
    if (orig == e) return true;
    return circularSuperclass(orig, e.parent);
  }
  
  public void TypeCheckVarDeclListNode(ListNode x) {
	if (x == null) 
		return;
	try {
		TypeCheckVarDeclNode((VarDeclNode) x.node);
	} catch (SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	TypeCheckVarDeclListNode(x.next);
  }
  
  public void TypeCheckConstructDeclListNode(ListNode x) {
	if (x == null) 
		return;
	try {
		TypeCheckConstructDeclNode((ConstructDeclNode) x.node);
	} catch (SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	TypeCheckConstructDeclListNode(x.next);
  }
  
  public void TypeCheckMethodDeclListNode(ListNode x) {
	if (x == null) 
		return;
	try {
		TypeCheckMethodDeclNode((MethodDeclNode) x.node);
	} catch (SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	}
	TypeCheckMethodDeclListNode(x.next);
  }
  
  public void TypeCheckClassBodyNode(ClassBodyNode x) {
    if (x == null) 
		return;

    TypeCheckClassDeclListNode(x.clist);
    TypeCheckVarDeclListNode(x.vlist);
    TypeCheckConstructDeclListNode(x.ctlist);
    TypeCheckMethodDeclListNode(x.mlist);
  }

  public void TypeCheckVarDeclNode(VarDeclNode x) throws SemanticException {
    ListNode p;
    EntryVar l;

    if (x == null) 
		return;
    for (p = x.vars; p != null; p = p.next) {
      VarNode q = (VarNode) p.node;
      l = Curtable.varFind(q.position.image, 2);

      if (l != null)
        throw new SemanticException(q.position, "Variable " + q.position.image + "already declared");
    }
  }
  
  public void TypeCheckConstructDeclNode(ConstructDeclNode x) throws SemanticException {
	  EntryMethod t;
	  EntryRec r = null;
	  EntryTable e;
	  EntryClass thisclass;
	  EntryVar thisvar;
	  ListNode p;
	  VarDeclNode q;
	  VarNode u;
	  int n;
	  
	  if (x == null) 
		  return;
	  p = x.body.param;
	  n = 0;
	  
	  while(p != null) {
		q = (VarDeclNode) p.node;
		u = (VarNode) q.vars.node;
		n++;
		
		e = Curtable.classFindUp(q.position.image);
		
		r = new EntryRec(e, u.dim, n, r, (u.atrib == null) ? false : true);
		p = p.next;
	  }
	  if (r != null)
		  r = r.inverte();
	  
	  t = Curtable.methodFind("constructor", r);
	  CurMethod = t;
	  
	  Curtable.beginScope();
	  
	  thisclass = (EntryClass) Curtable.levelup;
	  
	  thisvar = new EntryVar("this", thisclass, 0, 0);
	  Curtable.add(thisvar);
	  Returntype = null;
	  nesting = 0;
	  Nlocals = 1;
	  TypeCheckMethodBodyNode(x.body);
	  t.totallocals = Nlocals;
	  Curtable.endScope();
  }
  
  public void TypeCheckMethodDeclNode(MethodDeclNode x) throws SemanticException {
	  EntryMethod t;
	  EntryRec r = null;
	  EntryTable e;
	  EntryClass thisclass;
	  EntryVar thisvar;
	  ListNode p;
	  VarDeclNode q;
	  VarNode u;
	  int n;
	  
	  if (x == null) 
		  return;
	  p = x.body.param;
	  n = 0;
	  
	  while(p != null) {
		q = (VarDeclNode) p.node;
		u = (VarNode) q.vars.node;
		n++;
		
		e = Curtable.classFindUp(q.position.image);
		
		r = new EntryRec(e, u.dim, n, r, (u.atrib == null) ? false : true);
		p = p.next;
	  }
	  if (r != null)
		  r = r.inverte();
	  
	  t = Curtable.methodFind(x.name.image, r);
	  CurMethod = t;
	  
	  Returntype = new Type(t.type, t.dim);
	  
	  Curtable.beginScope();
	  
	  thisclass = (EntryClass) Curtable.levelup;
	  
	  thisvar = new EntryVar("this", thisclass, 0, 0);
	  Curtable.add(thisvar);
	  
	  nesting = 0;
	  Nlocals = 1;
	  TypeCheckMethodBodyNode(x.body);
	  t.totallocals = Nlocals;
	  Curtable.endScope();
  }
  
  public void TypeCheckMethodBodyNode(MethodBodyNode x) {
	  if (x == null)
		  return;
	  TypeCheckLocalVarDeclListNode(x.param);
	  
	  cansuper = false;
	  if (Curtable.levelup.parent != null) {
		  StatementNode p = x.stat;
		  while (p instanceof BlockNode)
			  p = (StatementNode)((BlockNode)p).stats.node;
		  cansuper = p instanceof SuperNode;
	  }
	  try {
		  TypeCheckStatementNode(x.stat);
	  } catch(SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	  }
  }
  
  public void TypeCheckIfNode(IfNode x) {
	  Type t;
	  if (x == null)
	  	return;
      
	  try {
	  	  t = TypeCheckExpreNode(x.expr);
	  	  if (!(t.ty == BOOLEAN_TYPE || t.ty == INT_TYPE || t.ty == FLOAT_TYPE) || t.dim != 0)
			throw new SemanticException(x.expr.position,"Boolean or numbers expression expected");
	  } catch (SemanticException e) {
	  	  System.out.println(e.getMessage());
	  	  foundSemanticError++;
	  }
      
	  try {
	  	TypeCheckStatementNode(x.stat1);
	  } catch (SemanticException e) {
	  	System.out.println(e.getMessage());
	  	foundSemanticError++;
	  }
      
	  try {
	  	TypeCheckStatementNode(x.stat2);
	  } catch (SemanticException e) {
	  	System.out.println(e.getMessage());
	  	foundSemanticError++;
	  }
  }
  
  public void TypeCheckNopNode(NopNode x) {
  }
  
  public void TypeCheckStatementNode(StatementNode x) throws SemanticException {
  	  if (x instanceof BlockNode)
  	  	  TypeCheckBlockNode((BlockNode) x);
  	  else if (x instanceof VarDeclNode)
  	  	  TypeCheckLocalVarDeclNode((VarDeclNode) x);
  	  else if (x instanceof AtribNode)
  	  	  TypeCheckAtribNode((AtribNode) x);
  	  else if (x instanceof IfNode)
  	  	TypeCheckIfNode((IfNode) x);
  	  else if (x instanceof ForNode)
  	  	TypeCheckForNode((ForNode) x);
  	  else if (x instanceof PrintNode)
  	  	TypeCheckPrintNode((PrintNode) x);
  	  else if (x instanceof NopNode)
  	  	TypeCheckNopNode((NopNode) x);
  	  else if (x instanceof ReadNode)
  	  	TypeCheckReadNode((ReadNode) x);
  	  else if (x instanceof ReturnNode)
  	  	TypeCheckReturnNode((ReturnNode) x);
  	  else if (x instanceof SuperNode)
  	  	TypeCheckSuperNode((SuperNode) x);
  	  else if (x instanceof BreakNode)
  	  	TypeCheckBreakNode((BreakNode) x);
  }
  
	public Type TypeCheckExpreNode(ExpreNode x) throws SemanticException {
        if (x instanceof NewObjectNode)
            return TypeCheckNewObjectNode((NewObjectNode) x);
        else if (x instanceof NewArrayNode)
            return TypeCheckNewArrayNode((NewArrayNode) x);
        else if (x instanceof RelationalNode)
            return TypeCheckRelationalNode((RelationalNode) x);
        else if (x instanceof AddNode)
            return TypeCheckAddNode((AddNode) x);
        else if (x instanceof MultNode)
            return TypeCheckMultNode((MultNode) x);
        else if (x instanceof UnaryNode)
            return TypeCheckUnaryNode((UnaryNode) x);
        else if (x instanceof CallNode)
            return TypeCheckCallNode((CallNode) x);
        else if (x instanceof IntConstNode)
            return TypeCheckIntConstNode((IntConstNode) x);
		else if (x instanceof FloatConstNode)
            return TypeCheckFloatConstNode((FloatConstNode) x);
		else if (x instanceof BooleanConstNode)
            return TypeCheckBooleanConstNode((BooleanConstNode) x);
		else if (x instanceof CharConstNode)
            return TypeCheckCharConstNode((CharConstNode) x);
        else if (x instanceof StringConstNode)
            return TypeCheckStringConstNode((StringConstNode) x);
        else if (x instanceof NullConstNode)
            return TypeCheckNullConstNode((NullConstNode) x);
        else if (x instanceof IndexNode)
            return TypeCheckIndexNode((IndexNode) x);
        else if (x instanceof DotNode)
            return TypeCheckDotNode((DotNode) x);
        else if (x instanceof VarNode)
            return TypeCheckVarNode((VarNode) x);
        else
            return null;
    }
  
	public Type TypeCheckDotNode (DotNode x) throws SemanticException {
		EntryClass c;
        EntryVar v;
        Type t;

        if (x == null)
            return null;
        t = TypeCheckExpreNode(x.expr);

        if (t.dim > 0)
            throw new SemanticException(x.position, "Arrays do not have fields");
        if (!(t.ty instanceof EntryClass))
            throw new SemanticException(x.position,"Type " + t.ty.name + " does not have fields");
        c = (EntryClass) t.ty;
        v = c.nested.varFind(x.field.image);
        if (v == null)
            throw new SemanticException(x.position,"Variable " + x.field.image + " not found in class " + c.name);
        return new Type(v.type, v.dim);
	}
  
  public void TypeCheckLocalVarDeclListNode(ListNode x) {
	  if (x == null)
		  return;
	  try {
		TypeCheckLocalVarDeclNode((VarDeclNode) x.node);
	  } catch (SemanticException e) {
		System.out.println(e.getMessage());
		foundSemanticError++;
	  }
	  TypeCheckLocalVarDeclListNode(x.next);
  }
  
	public void TypeCheckLocalVarDeclNode(VarDeclNode x) throws SemanticException {
		ListNode p;
		VarNode q;
		EntryVar l, u;
		EntryTable c;
		
		if (x == null)
			return;
		c = Curtable.classFindUp(x.position.image);
		if (c == null)
			throw new SemanticException(x.position, " Class " + x.position.image + " not found");
		for (p = x.vars ; p != null ; p = p.next) {
			q = (VarNode) p.node;
			l = Curtable.varFind(q.position.image);
			if (l != null) {
				if (l.scope == Curtable.scptr)
					throw new SemanticException(p.position, " Variable " + q.position.image + " already declared");
				if (l.localcount < 0)
					System.out.println("Line " + q.position.beginLine + " Column " + q.position.beginColumn + " Warning: Variable " + q.position.image + " hides a class variable");
				else
					System.out.println("Line " + q.position.beginLine + " Column " + q.position.beginColumn + " Warning: Variable " + q.position.image + " hides a parameter or a local variable");
			}
			Curtable.add(new EntryVar(q.position.image, c, q.dim, Nlocals++));
			if (q.atrib != null)
				TypeCheckAtribNode(q.atrib);
		}
	}
	
	public void TypeCheckPrintNode(PrintNode x) throws SemanticException {
		Type t;
		if (x == null)
			return;
		t = TypeCheckExpreNode(x.expr);
		if (t.ty != STRING_TYPE || t.dim != 0)
			throw new SemanticException(x.position, " string expression required ");
	}
	
	public void TypeCheckReadNode(ReadNode x) throws SemanticException {
		Type t;
		if (x == null)
			return;
		if (! ( x.expr instanceof DotNode    ||
				x.expr instanceof IndexNode  ||
				x.expr instanceof VarNode )
			)
			throw new SemanticException(x.position, " invalid expression in read statement");
		if (x.expr instanceof VarNode) {
			EntryVar v = Curtable.varFind(x.expr.position.image);
			if (v != null && v.localcount == 0)
				throw new SemanticException(x.position, " reading into variable " + " \"this\" is not legal");
		}
		t = TypeCheckExpreNode(x.expr);
		if (t.ty != STRING_TYPE && t.ty != INT_TYPE)
			throw new SemanticException(x.position, " invalid type. Must be int or string");
		if (t.dim != 0)
			throw new SemanticException(x.position, " cannot read array");
	}
	
	public void TypeCheckReturnNode(ReturnNode x) throws SemanticException {
		Type t;
		
		if (x == null)
			return;
		t = TypeCheckExpreNode(x.expr);
		
		if (t == null) {
			if (Returntype == null)
				return;
			else
				throw new SemanticException(x.position, " return expression required");
		} else {
			if (Returntype == null)
				throw new SemanticException(x.position, " construtor cannot return a value");
		}
		if (t.ty != Returntype.ty || t.dim != Returntype.dim)
			throw new SemanticException(x.position, " invalid return type");
	}
	
	public void TypeCheckAtribNode(AtribNode x) throws SemanticException {
		Type t1, t2;
		EntryVar v;
		if (x == null)
			return;
		if ( ! (x.expr1 instanceof DotNode   ||
				x.expr1 instanceof IndexNode ||
				x.expr1 instanceof VarNode)
			)
			throw new SemanticException(x.position, " iinvalid left side of assignment");
		if (x.expr1 instanceof VarNode) {
			v = Curtable.varFind(x.expr1.position.image);
			if (v != null && v.localcount == 0)
				throw new SemanticException(x.position, " assigning to variable \"this\" is not legal");
		}
		
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);
		
		if (t1.dim != t2.dim)
			throw new SemanticException(x.position, " invalid dimension in assignment");
		if (t1.ty instanceof EntryClass && t2.ty == NULL_TYPE)
			return;
		if (t1.ty == BOOLEAN_TYPE && (t2.ty == INT_TYPE || t2.ty == FLOAT_TYPE))
			return;
		if (! (isSubClass(t2.ty, t1.ty) || isSubClass(t1.ty, t2.ty)))
			throw new SemanticException(x.position, " incompatible types for assignment");
	}
	
	public boolean isSubClass(EntryTable t1, EntryTable t2) {
		if (t1 == t2)
			return true;
		if (!(t1 instanceof EntryClass && t2 instanceof EntryClass))
			return false;
		for (EntryClass p = ((EntryClass)t1).parent ; p != null ; p = p.parent) {
			if (p == t2)
				return true;
		}
		return false;
	}
	
	public void TypeCheckSuperNode(SuperNode x) throws SemanticException {
		Type t;
		
		if (x == null)
			return;
		if (Returntype != null)
			throw new SemanticException(x.position, " super is only allowed in constructors");
		if (!cansuper)
			throw new SemanticException(x.position, " super must be first statement in the constructor");
		cansuper = false;
		EntryClass p = Curtable.levelup.parent;
		if (p == null)
			throw new SemanticException(x.position, " no superclass for this class");
		t = TypeCheckExpreListNode(x.args);
		EntryMethod m = p.nested.methodFindInClass("constructor", (EntryRec) t.ty);
		if (m == null)
			throw new SemanticException(x.position, " constructor " +p.name + "(" + (t.ty == null ? "" : ((EntryRec)t.ty).toStr()) + ") not found");
		CurMethod.hassuper = true;
	}
  
	public void TypeCheckForNode(ForNode x) {
		Type t;
		
		if (x == null)
			return;
		
		try {
			TypeCheckStatementNode(x.init);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		
		try {
			t = TypeCheckExpreNode(x.expr);
			if (!(t.ty == BOOLEAN_TYPE || t.ty == INT_TYPE || t.ty == FLOAT_TYPE) || t.dim != 0)
				throw new SemanticException(x.expr.position,"Boolean or numbers expression expected");
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		
		try {
			TypeCheckStatementNode(x.incr);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		
		try {
			nesting++;
			TypeCheckStatementNode(x.stat);
		} catch(SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		nesting--;
	}
	
	public void TypeCheckBreakNode(BreakNode x) throws SemanticException {
		if (x == null)
			return;
		if (nesting <= 0)
			throw new SemanticException(x.position, " break not in a for statement");
	}
	
	public Type TypeCheckFloatConstNode(FloatConstNode x) throws SemanticException {
		float k;
		if (x == null) 
			return null;
		try {
			k = Float.parseFloat(x.position.image);
		} catch (NumberFormatException e) {
			throw new SemanticException(x.position, " invalid float constant");
		}
		return new Type(FLOAT_TYPE, 0);
	}
	
	public Type TypeCheckCharConstNode(CharConstNode x) throws SemanticException {
		char k;
		if (x == null) 
			return null;
		try {
			k = x.position.image.charAt(0);
		} catch (IndexOutOfBoundsException e) {
			throw new SemanticException(x.position, " invalid char constant");
		}
		return new Type(CHAR_TYPE, 0);
	}
	
	public Type TypeCheckBooleanConstNode(BooleanConstNode x) throws SemanticException {
		boolean k;
		if (x == null) 
			return null;
		try {
  			k = Boolean.parseBoolean(x.position.image) || x.position.image.equalsIgnoreCase("false") || (Integer.parseInt(x.position.image) != 0);
		} catch (Exception e) {
			throw new SemanticException(x.position, " invalid boolean constant");
		}
		return new Type(BOOLEAN_TYPE, 0);
	}
	
	public Type TypeCheckIntConstNode(IntConstNode x) throws SemanticException{
		int k;
		if (x == null) 
			return null;
		try {
			k = Integer.parseInt(x.position.image);
		} catch (NumberFormatException e) {
			throw new SemanticException(x.position, " invalid int constant");
		}
		return new Type(INT_TYPE, 0);
	}
	
	public Type TypeCheckStringConstNode(StringConstNode x) {
		if (x == null)
			return null;
		return new Type(STRING_TYPE, 0);
	}
	
	public Type TypeCheckNullConstNode(NullConstNode x) {
		if (x == null)
			return null;
		return new Type(NULL_TYPE, 0);
	}
	
	public Type TypeCheckVarNode(VarNode x) throws SemanticException {
		EntryVar p;
		if (x == null) return null;
		p = Curtable.varFind(x.position.image);
		
		if (p == null)
			throw new SemanticException(x.position, " variable " + x.position.image + " not found");
		return new Type(p.type, p.dim);
	}
	
	public Type TypeCheckCallNode(CallNode x) throws SemanticException {
		EntryClass c;
		EntryMethod m;
		Type t1,t2;
		
		if (x == null)
			return null;
		t1 = TypeCheckExpreNode(x.expr);
		if (t1.dim > 0)
			throw new SemanticException(x.position, " arrays do not have methods");
		if (!(t1.ty instanceof EntryClass))
			throw new SemanticException(x.position, " type " + t1.ty.name + " does not have method");
		t2 = TypeCheckExpreListNode(x.args);
		
		c = (EntryClass) t1.ty;
		m = c.nested.methodFind(x.meth.image, (EntryRec) t2.ty);
		
		if (m == null)
			throw new SemanticException(x.position, " method " + x.meth.image + "(" + (t2.ty == null ? "" : ((EntryRec)t2.ty).toStr()) + ") not found in class" + c.name);
		return new Type(m.type, m.dim);
	}
	
	public Type TypeCheckExpreListNode(ListNode x) {
		Type t, t1;
		EntryRec r;
		int n;
		if (x == null) 
			return new Type(null, 0);
		try {
			t = TypeCheckExpreNode((ExpreNode) x.node);
		} catch (SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
			t = new Type(NULL_TYPE, 0);
		}
		
		t1 = TypeCheckExpreListNode(x.next);
		n = (t1.ty == null) ? 0 : ((EntryRec)t1.ty).cont;
		r = new EntryRec(t.ty, t.dim, n + 1, (EntryRec)t1.ty, false);
		
		t = new Type(r, 0);
		return t;
	}
	
	public Type TypeCheckIndexNode(IndexNode x) throws SemanticException {
		EntryClass c;
		Type t1, t2;
		
		if (x == null) 
			return null;
		t1 = TypeCheckExpreNode(x.expr1);
		
		if (t1.dim <= 0)
			throw new SemanticException(x.position, " can not index non array variables");
		t2 = TypeCheckExpreNode(x.expr2);
		
		if (t2.ty != INT_TYPE || t2.dim > 0)
			throw new SemanticException(x.position, " invalid type. Indexx must be int");
		
		return new Type(t1.ty, t1.dim - 1);
	}
	
	public Type TypeCheckNewObjectNode(NewObjectNode x) throws SemanticException {
		Type t;
		EntryMethod p;
		EntryTable c;
		
		if (x == null)
			return null;
		c = Curtable.classFindUp(x.name.image);
		if (c == null)
			throw new SemanticException(x.position, " class " + x.name.image + "not found");
		t = TypeCheckExpreListNode(x.args);
		Symtable s = ((EntryClass)c).nested;
		p = s.methodFindInClass("constructor", (EntryRec) t.ty);
		if (p == null)
			throw new SemanticException(x.position, " constructor " + x.name.image + "(" + (t.ty == null ? "" : ((EntryRec)t.ty).toStr()) + ") not found");
		t = new Type(c, 0);
		return t;
	}
	
	public Type TypeCheckNewArrayNode(NewArrayNode x) throws SemanticException {
		Type t;
		EntryTable c;
		ListNode p;
		ExpreNode q;
		int k;
		if (x == null)
			return null;
		c = Curtable.classFindUp(x.name.image);
		if (c == null)
			throw new SemanticException(x.position, " type " + x.name.image + " not found");
		for (k = 0, p = x.dims ; p != null ; p = p.next) {
			t = TypeCheckExpreNode((ExpreNode) p.node);
			if (t.ty != INT_TYPE || t.dim != 0)
				throw new SemanticException(x.position, " invalid expression for an array dimension");
			k ++;
		}
		return new Type(c, k);
	}
	
	public Type TypeCheckUnaryNode(UnaryNode x) throws SemanticException{
		Type t;
		if (x == null)
			return null;
		t = TypeCheckExpreNode(x.expr);
		
		if (t.dim > 0)
			throw new SemanticException(x.position, " can not use unary " + x.position.image + " for arrays");
		if (t.ty != INT_TYPE)
			throw new SemanticException(x.position, " imcompatible type for unary " + x.position.image);
		return new Type(INT_TYPE, 0);
	}
	
	 public Type TypeCheckMultNode(MultNode x) throws SemanticException {
        Type t1, t2;
        int op, i, j;

        if (x == null)
            return null;

        op = x.position.kind;
        t1 = TypeCheckExpreNode(x.expr1);
        t2 = TypeCheckExpreNode(x.expr2);

        if ((t1.dim > 0) || (t2.dim > 0))
            throw new SemanticException(x.position,"Can not use " + x.position.image + " for arrays");
        if ((t1.ty != INT_TYPE) || (t2.ty != INT_TYPE))
            throw new SemanticException(x.position,"Invalid types for " + x.position.image);
        return new Type(INT_TYPE, 0);
    }
	
	public Type TypeCheckAddNode(AddNode x) throws SemanticException {
		Type t1, t2;
		int op;
		int i, s, c, f;
		
		if (x == null)
			return null;
		op = x.position.kind;
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);
		
		if (t1.dim > 0 || t2.dim > 0)
			throw new SemanticException(x.position, " can not use " + x.position.image + " for arrays");
		i = s = c = f = 0;
		
		if (t1.ty == INT_TYPE)
			i++;
		else if (t1.ty == FLOAT_TYPE)
			f++;
		else if (t1.ty == CHAR_TYPE)
			c++;
		else if (t1.ty == STRING_TYPE)
			s++;
		
		if (t2.ty == INT_TYPE)
			i++;
		else if (t2.ty == FLOAT_TYPE)
			f++;
		else if (t2.ty == CHAR_TYPE)
			c++;
		else if (t2.ty == STRING_TYPE)
			s++;
		
		if (op == LangGEDConstants.PLUS) {
			if (i == 2 || i == 1 && f == 1)
				return new Type(INT_TYPE, 0);
			if (f == 2)
				return new Type(FLOAT_TYPE, 0);
			if (i + f + c + s == 2)
				return new Type(STRING_TYPE, 0);
		}
		throw new SemanticException(x.position, " invalid types for " + x.position.image);
	}
	
	public Type TypeCheckRelationalNode(RelationalNode x) throws SemanticException {
		Type t1, t2;
		int op;
		
		if (x == null)
			return null;
		op = x.position.kind;
		t1 = TypeCheckExpreNode(x.expr1);
		t2 = TypeCheckExpreNode(x.expr2);
		
		if (t1.ty == INT_TYPE && t2.ty == INT_TYPE)
			return new Type(INT_TYPE, 0);
		if (t1.ty == BOOLEAN_TYPE && t2.ty == BOOLEAN_TYPE)
			return new Type(BOOLEAN_TYPE, 0);
		if (t1.ty == FLOAT_TYPE && t2.ty == FLOAT_TYPE)
			return new Type(FLOAT_TYPE, 0);
		if (op == LangGEDConstants.OR || op == LangGEDConstants.XOR || op == LangGEDConstants.AND) {
			if ((t1.ty == INT_TYPE || t1.ty == FLOAT_TYPE) && t2.ty == BOOLEAN_TYPE) 
				return new Type(BOOLEAN_TYPE, 0);
			if ((t2.ty == INT_TYPE || t2.ty == FLOAT_TYPE) && t1.ty == BOOLEAN_TYPE)
				return new Type(BOOLEAN_TYPE, 0);
		}
		if (t1.dim != t2.dim)
			throw new SemanticException(x.position, " can not compare object with different dimensions");
		if (t1.dim > 0 && op != LangGEDConstants.EQ && op != LangGEDConstants.NEQ)
			throw new SemanticException(x.position, " can not use " + x.position.image + " for arrays");
		if ((isSubClass(t2.ty, t2.ty) || isSubClass(t1.ty, t2.ty)) && (op == LangGEDConstants.NEQ || op == LangGEDConstants.EQ))
			return new Type(INT_TYPE, 0);
		if (((t1.ty instanceof EntryClass && t2.ty == NULL_TYPE)   ||
			 (t2.ty instanceof EntryClass && t1.ty == NULL_TYPE )) &&
			 (op == LangGEDConstants.NEQ || op == LangGEDConstants.EQ))
				return new Type(INT_TYPE, 0); 
		
		throw new SemanticException(x.position, " invalid types for " + x.position.image);
	}

	public void TypeCheckBlockNode(BlockNode x) {
		Curtable.beginScope();
		TypeCheckStatementListNode(x.stats);
		Curtable.endScope();
	}
	
	public void TypeCheckStatementListNode(ListNode x) {
		if (x == null)
			return;
		try {
			TypeCheckStatementNode((StatementNode)x.node);
		} catch(SemanticException e) {
			System.out.println(e.getMessage());
			foundSemanticError++;
		}
		TypeCheckStatementListNode(x.next);
	}

}
