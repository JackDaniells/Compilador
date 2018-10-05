public class PrintTree {
	int kk;
	
	public PrintTree() {
		kk = 1;
	}
	
	public void printTree(ListNode x) {
		if (x == null )
			System.out.println("Empty syntatic tree. Nothing to be printed");
		else {
			numberClassDeclListNode(x);
			printClassDeclListNode(x);
		}
	}	
	
	public void numberClassBodyNode(ClassBodyNode x) {
		if (x == null) return;
		x.number = kk++;
		numberClassDeclListNode(x.clist);
		numberVarDeclListNode(x.vlist);
		numberConstructDeclListNode(x.ctlist);
		numberMethodDeclListNode(x.mlist);
	}
	
	public void numberClassDeclListNode(ListNode x) {
		if (x == null) return;
		x.number = kk++;
		numberClassDeclNode((ClassDeclNode) x.node);
		numberClassDeclListNode(x.next);
	}
	
	public void numberClassDeclNode(ClassDeclNode x) {
		if (x == null) return;
		x.number = kk++;
		numberClassBodyNode(x.body);
	}
	
	public void numberVarDeclListNode(ListNode x) {
		if (x == null) return;
		x.number = kk++;
		numberVarDeclNode((VarDeclNode) x.node);
		numberVarDeclListNode(x.next);
	}
	
	public void numberVarDeclNode(VarDeclNode x) {
		if (x == null) return;
		x.number = kk++;
		numberVarListNode(x.vars);
	}
	
	public void numberVarListNode(ListNode x) {
		if (x == null) return;
		x.number = kk++;
		numberVarNode((VarNode) x.node);
		numberVarListNode(x.next);
	}
	
	public void numberVarNode(VarNode x) {
		if (x == null) return;
		x.number = kk++;
		numberAtribVarNode(x.atrib);
	}
	
	public void numberAtribVarNode(AtribNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr2);
	}
	
	public void numberConstructDeclListNode(ListNode x) {
		if (x == null) return;
		x.number = kk++;
		numberConstructDeclNode((ConstructDeclNode) x.node);
		numberConstructDeclListNode(x.next);
	}
	
	public void numberConstructDeclNode(ConstructDeclNode x) {
		if (x == null) return;
		x.number = kk++;
		numberMethodBodyNode(x.body);
	}
	
	public void numberMethodDeclListNode(ListNode x) {
		if (x == null) return;
		x.number = kk++;
		numberMethodDeclNode((MethodDeclNode) x.node);
		numberMethodDeclListNode(x.next);
	}
	
	public void numberMethodDeclNode(MethodDeclNode x) {
		if (x == null) return;
		x.number = kk++;
		numberMethodBodyNode(x.body);
	}
	
	public void numberStatementListNode(ListNode x) {
		if (x == null) return;
		x.number = kk++;
		numberStatementNode((StatementNode) x.node);
		numberStatementListNode(x.next);
	}
	
	public void numberExpreListNode(ListNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode((ExpreNode) x.node);
		numberExpreListNode(x.next);
	}

	public void numberMethodBodyNode(MethodBodyNode x) {
		if (x == null) return;
		x.number = kk++;
		numberVarDeclListNode(x.param);
		numberStatementNode(x.stat);
	}
	
	public void numberBlockNode(BlockNode x) {
		if (x == null) return;
		x.number = kk++;
		numberStatementListNode(x.stats);
	}
	
	public void numberAtribNode(AtribNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}
	
	public void numberNopNode(NopNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberBreakNode(BreakNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberReadNode(ReadNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
	}
	
	public void numberReturnNode(ReturnNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
	}
	
	public void numberSuperNode(SuperNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreListNode(x.args);
	}
	
	public void numberForNode(ForNode x) {
		if (x == null) return;
		x.number = kk++;
		numberAtribNode(x.init);
		numberAtribNode(x.incr);
		numberExpreNode(x.expr);
		numberStatementNode(x.stat);
	}
	
	public void numberIfNode(IfNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
		numberStatementNode(x.stat1);
		numberStatementNode(x.stat2);
	}
	
	public void numberPrintNode(PrintNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
	}
	
	public void numberStatementNode (StatementNode x) {
		if (x instanceof BlockNode)
			numberBlockNode((BlockNode) x);
		else if (x instanceof VarDeclNode)
			numberVarDeclNode((VarDeclNode) x);
		else if (x instanceof AtribNode)
			numberAtribNode((AtribNode) x);
		else if (x instanceof IfNode)
			numberIfNode((IfNode) x);
		else if (x instanceof ForNode)
			numberForNode((ForNode) x);
		else if (x instanceof NopNode)
			numberNopNode((NopNode) x);
		else if (x instanceof BreakNode)
			numberBreakNode((BreakNode) x);
		else if (x instanceof ReadNode)
			numberReadNode((ReadNode) x);
		else if (x instanceof ReturnNode)
			numberReturnNode((ReturnNode) x);
		else if (x instanceof SuperNode)
			numberSuperNode((SuperNode) x);
		else if (x instanceof PrintNode)
			numberPrintNode((PrintNode) x);
	}
	
	public void numberNewObjectNode(NewObjectNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreListNode(x.args);
	}
	
	public void numberNewArrayNode(NewArrayNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreListNode(x.dims);
	}
	
	public void numberRelationalNode(RelationalNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}

	public void numberMultNode(MultNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}
	
	public void numberUnaryNode(UnaryNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
	}
	
	public void numberCallNode(CallNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
		numberExpreListNode(x.args);
	}
	
	public void numberIntConstNode(IntConstNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberStringConstNode(StringConstNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberNullConstNode(NullConstNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberIndexNode(IndexNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}
	
	public void numberDotNode(DotNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr);
	}
	
	public void numberBooleanConstNode(BooleanConstNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberFloatConstNode(FloatConstNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberCharConstNode(CharConstNode x) {
		if (x == null) return;
		x.number = kk++;
	}
	
	public void numberExpreNode (ExpreNode x) {
		if (x instanceof NewObjectNode)
			numberNewObjectNode((NewObjectNode) x);
		else if (x instanceof NewArrayNode)
			numberNewArrayNode((NewArrayNode) x);
		else if (x instanceof RelationalNode)
			numberRelationalNode((RelationalNode) x);
		else if (x instanceof AddNode)
			numberAddNode((AddNode) x);
		else if (x instanceof MultNode)
			numberMultNode((MultNode) x);
		else if (x instanceof UnaryNode)
			numberUnaryNode((UnaryNode) x);
		else if (x instanceof CallNode)
			numberCallNode((CallNode) x);
		else if (x instanceof IntConstNode)
			numberIntConstNode((IntConstNode) x);
		else if (x instanceof BooleanConstNode)
			numberBooleanConstNode((BooleanConstNode) x);
		else if (x instanceof FloatConstNode)
			numberFloatConstNode((FloatConstNode) x);
		else if (x instanceof CharConstNode)
			numberCharConstNode((CharConstNode) x);
		else if (x instanceof StringConstNode)
			numberStringConstNode((StringConstNode) x);
		else if (x instanceof NullConstNode)
			numberNullConstNode((NullConstNode) x);
		else if (x instanceof IndexNode)
			numberIndexNode((IndexNode) x);
		else if (x instanceof DotNode)
			numberDotNode((DotNode) x);
		else if (x instanceof VarNode)
			numberVarNode((VarNode) x);
	}
	
	public void numberAddNode(AddNode x) {
		if (x == null) return;
		x.number = kk++;
		numberExpreNode(x.expr1);
		numberExpreNode(x.expr2);
	}
	
	public void printClassBodyNode(ClassBodyNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ClassBodyNode ===> " +
			(x.clist == null ? "null" : String.valueOf(x.clist.number)) + " " +
			(x.vlist == null ? "null" : String.valueOf(x.vlist.number)) + " " +
			(x.ctlist == null ? "null" : String.valueOf(x.ctlist.number)) + " " +
			(x.mlist == null ? "null" : String.valueOf(x.mlist.number)));
			
		printClassDeclListNode(x.clist);
		printVarDeclListNode(x.vlist);
		printConstructDeclListNode(x.ctlist);
		printMethodDeclListNode(x.mlist);
	}
	
	public void printClassDeclNode(ClassDeclNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ClassDeclNode ===> " +
			x.name.image + " " +
			(x.supername == null ? "null" : x.supername.image) + " " +
			(x.body == null ? "null" : String.valueOf(x.body.number)));
		printClassBodyNode(x.body);
	}
	
	public void printClassDeclListNode(ListNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ListNode (ClassDeclNode) ===> " +
			(x.node == null ? "null" : String.valueOf(x.node.number)) + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printClassDeclNode((ClassDeclNode) x.node);
		printClassDeclListNode(x.next);
	}
	
	public void printAtribVarNode(AtribNode x) {
		if (x == null) return;
		System.out.println(x.number + ": AtribNode ===> " +
			(x.expr1 == null ? "null" : String.valueOf(x.expr1.position)) + " " +
			(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));
		printExpreNode(x.expr2);
	}
	
	public void printVarNode(VarNode x) {
		if (x == null) return;
		System.out.println(x.number + ": VarNode ===> " + x.position + " " +
			(x.atrib == null ? "null" : String.valueOf(x.atrib.number)));
		printAtribVarNode(x.atrib);		
	}
	
	public void printVarListNode(ListNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ListNode (VarNode) ===> " +
			(x.node == null ? "null" : String.valueOf(x.node.number)) + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printVarNode((VarNode) x.node);
		printVarListNode(x.next);
	}
	
	public void printVarDeclNode(VarDeclNode x) {
		if (x == null) return;
		System.out.println(x.number + ": VarDeclNode ===> " +
			x.position + " " +
			(x.vars == null ? "null" : String.valueOf(x.vars.number)));
		printVarListNode(x.vars);
	}
	
	public void printVarDeclListNode(ListNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ListNode (VarDeclNode) ===> " +
			(x.node == null ? "null" : String.valueOf(x.node.number)) + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printVarDeclNode((VarDeclNode) x.node);
		printVarDeclListNode(x.next);
	}
	
	public void printStatementListNode(ListNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ListNode (StatementNode) ===> " +
			(x.node == null ? "null" : String.valueOf(x.node.number)) + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printStatementNode((StatementNode) x.node);
		printStatementListNode(x.next);
	}
	
	public void printExpreListNode(ListNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ListNode (ExpreNode) ===> " +
			(x.node == null ? "null" : String.valueOf(x.node.number)) + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printExpreNode((ExpreNode) x.node);
		printExpreListNode(x.next);
	}
	
	public void printNewObjectNode(NewObjectNode x) {
		if (x == null) return;
		System.out.println(x.number + ": NewObjectNode ===> " +
			(x.name == null ? "null" : x.name) + " " +
			(x.args == null ? "null" : String.valueOf(x.args.number)));
		printExpreListNode(x.args);
	}
	public void printNewArrayNode(NewArrayNode x) {
		if (x == null) return;
		System.out.println(x.number + ": NewArrayNode ===> " +
			(x.name == null ? "null" : x.name) + " " +
			(x.dims == null ? "null" : String.valueOf(x.dims.number)));
		printExpreListNode(x.dims);
	}
	public void printRelationalNode(RelationalNode x) {
		if (x == null) return;
		System.out.println(x.number + ": RelationalNode ===> " +
			(x.position) + " " +
			(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) + " " +
			(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}
	public void printAddNode(AddNode x) {
		if (x == null) return;
		System.out.println(x.number + ": AddNode ===> " +
			(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) + " " +
			(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}
	public void printMultNode(MultNode x) {
		if (x == null) return;
		System.out.println(x.number + ": MultNode ===> " +
			(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) + " " +
			(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}
	public void printUnaryNode(UnaryNode x) {
		if (x == null) return;
		System.out.println(x.number + ": UnaryNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)));
		printExpreNode(x.expr);
	}
	
	public void printCallNode(CallNode x) {
		if (x == null) return;
		System.out.println(x.number + ": CallNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)) + " " +
			(x.meth == null ? "null" : x.meth) + " " +
			(x.args == null ? "null" : String.valueOf(x.args.number)));
		printExpreNode(x.expr);
		printExpreListNode(x.args);
	}
	
	public void printIntConstNode(IntConstNode x) {
		if (x == null) return;
		System.out.println(x.number + ": IntConstNode ===> " + x.position);
	}
	public void printStringConstNode(StringConstNode x) {
		if (x == null) return;
		System.out.println(x.number + ": StringConstNode ===> " + x.position);
	}
	public void printNullConstNode(NullConstNode x) {
		if (x == null) return;
		System.out.println(x.number + ": NullConstNode ===> " + x.position);
	}
	public void printIndexNode(IndexNode x) { 
		if (x == null) return;
		System.out.println(x.number + ": IndexNode ===> " +
			(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) + " " +
			(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}
	public void printDotNode(DotNode x) { 
		if (x == null) return;
		System.out.println(x.number + ": DotNode ===> " +
			(x.field == null ? "null" : x.field) + " " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)));
		printExpreNode(x.expr); 
	}
	
	public void printBooleanConstNode(BooleanConstNode x) {
		if (x == null) return;
		System.out.println(x.number + ": BooleanConstNode ===> " + x.position);
	}
	
	public void printFloatConstNode(FloatConstNode x) {
		if (x == null) return;
		System.out.println(x.number + ": FloatConstNode ===> " + x.position);
	}
	
	public void printCharConstNode(CharConstNode x) {
		if (x == null) return;
		System.out.println(x.number + ": CharConstNode ===> " + x.position);
	}
	
	public void printExpreNode(ExpreNode x) {
		if (x instanceof NewObjectNode)
			printNewObjectNode((NewObjectNode) x);
		else if (x instanceof NewArrayNode)
			printNewArrayNode((NewArrayNode) x);
		else if (x instanceof RelationalNode)
			printRelationalNode((RelationalNode) x);
		else if (x instanceof AddNode)
			printAddNode((AddNode) x);
		else if (x instanceof MultNode)
			printMultNode((MultNode) x);
		else if (x instanceof UnaryNode)
			printUnaryNode((UnaryNode) x);
		else if (x instanceof CallNode)
			printCallNode((CallNode) x);
		else if (x instanceof IntConstNode)
			printIntConstNode((IntConstNode) x);
		else if (x instanceof BooleanConstNode)
			printBooleanConstNode((BooleanConstNode) x);
		else if (x instanceof FloatConstNode)
			printFloatConstNode((FloatConstNode) x);
		else if (x instanceof CharConstNode)
			printCharConstNode((CharConstNode) x);
		else if (x instanceof StringConstNode)
			printStringConstNode((StringConstNode) x);
		else if (x instanceof NullConstNode)
			printNullConstNode((NullConstNode) x);
		else if (x instanceof IndexNode)
			printIndexNode((IndexNode) x);
		else if (x instanceof DotNode)
			printDotNode((DotNode) x);
		else if (x instanceof VarNode)
			printVarNode((VarNode) x);
	}
	
	public void printBlockNode(BlockNode x) {
		if (x == null) return;
		System.out.println(x.number + ": BlockNode ===> " + 
			(x.stats == null ? "null" : String.valueOf(x.stats.number)));
		printStatementListNode(x.stats);
	}
	
	public void printAtribNode(AtribNode x) {
		if (x == null) return;
		System.out.println(x.number + ": AtribNode ===> " +
			(x.expr1 == null ? "null" : String.valueOf(x.expr1.number)) + " " +
			(x.expr2 == null ? "null" : String.valueOf(x.expr2.number)));
		printExpreNode(x.expr1);
		printExpreNode(x.expr2);
	}
	public void printIfNode(IfNode x) {
		if (x == null) return;
		System.out.println(x.number + ": IfNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)) + " " +
			(x.stat1 == null ? "null" : String.valueOf(x.stat1.number)) + " " +
			(x.stat2 == null ? "null" : String.valueOf(x.stat2.number)));
		printExpreNode(x.expr);
		printStatementNode(x.stat1);
		printStatementNode(x.stat2);
	}
	public void printForNode(ForNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ForNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)) + " " +
			(x.init == null ? "null" : String.valueOf(x.init.number)) + " " +
			(x.incr == null ? "null" : String.valueOf(x.incr.number)) + " " +
			(x.stat == null ? "null" : String.valueOf(x.stat.number)));
		printExpreNode(x.expr);
		printAtribNode(x.init);
		printAtribNode(x.incr);
		printStatementNode(x.stat);
	}
	public void printNopNode(NopNode x) {
		if (x == null) return;
		System.out.println(x.number + ": NopNode ===>");
	}
	public void printBreakNode(BreakNode x) {
		if (x == null) return;
		System.out.println(x.number + ": BreakNode ===>");
	}
	public void printReadNode(ReadNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ReadNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)));
		printExpreNode(x.expr);
	}
	public void printReturnNode(ReturnNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ReturnNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)));
		printExpreNode(x.expr);
	}
	public void printSuperNode(SuperNode x) {
		if (x == null) return;
		System.out.println(x.number + ": SuperNode ===> " +
			(x.args == null ? "null" : String.valueOf(x.args.number)));
		printStatementListNode(x.args);
	}
	public void printPrintNode(PrintNode x) {
		if (x == null) return;
		System.out.println(x.number + ": PrintNode ===> " +
			(x.expr == null ? "null" : String.valueOf(x.expr.number)));
		printExpreNode(x.expr);
	}
	
	public void printStatementNode(StatementNode x) {
		if (x instanceof BlockNode)
			printBlockNode((BlockNode) x);
		else if (x instanceof VarDeclNode)
			printVarDeclNode((VarDeclNode) x);
		else if (x instanceof AtribNode)
			printAtribNode((AtribNode) x);
		else if (x instanceof IfNode)
			printIfNode((IfNode) x);
		else if (x instanceof ForNode)
			printForNode((ForNode) x);
		else if (x instanceof NopNode)
			printNopNode((NopNode) x);
		else if (x instanceof BreakNode)
			printBreakNode((BreakNode) x);
		else if (x instanceof ReadNode)
			printReadNode((ReadNode) x);
		else if (x instanceof ReturnNode)
			printReturnNode((ReturnNode) x);
		else if (x instanceof SuperNode)
			printSuperNode((SuperNode) x);
		else if (x instanceof PrintNode)
			printPrintNode((PrintNode) x);
	}
	
	public void printContructDeclNode(ConstructDeclNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ConstructDeclNode ===> " +
			(x.body == null ? "null" : String.valueOf(x.body.number)));
		printMethodBodyNode(x.body);
	}
	
	public void printMethodBodyNode(MethodBodyNode x) {
		if (x == null) return;
		System.out.println(x.number + ": MethodBodyNode ===> " +
			(x.param == null ? "null" : String.valueOf(x.param.number)) + " " +
			(x.stat == null ? "null" : String.valueOf(x.stat.number)));
		printVarDeclListNode(x.param);
		printStatementNode(x.stat);
	}
	
	public void printConstructDeclListNode(ListNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ListNode (ConstructDeclNode) ===> " +
			(x.node == null ? "null" : String.valueOf(x.node.number)) + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printContructDeclNode((ConstructDeclNode) x.node);
		printConstructDeclListNode(x.next);
	}
	
	public void printMethodDeclNode(MethodDeclNode x) {
		if (x == null) return;
		System.out.println(x.number + ": MethodDeclNode ===> " +
			(x.position == null ? "null" : String.valueOf(x.position)) + " " + 
			(x.name == null ? "null" : x.name) + " " + 
			(x.body == null ? "null" : String.valueOf(x.body.number)));
		printMethodBodyNode(x.body);
	}
	
	public void printMethodDeclListNode(ListNode x) {
		if (x == null) return;
		System.out.println(x.number + ": ListNode (MethodDeclNode) ===> " +
			(x.node == null ? "null" : String.valueOf(x.node.number)) + " " +
			(x.next == null ? "null" : String.valueOf(x.next.number)));
		printMethodDeclNode((MethodDeclNode) x.node);
		printMethodDeclListNode(x.next);
	}
}