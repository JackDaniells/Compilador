public class Symtable {
  public EntryTable top;
  public int scptr;
  public EntryClass levelup;

  public Symtable () {
    top = null;
    scptr = 0;
    levelup = null;
  }

  public Symtable (EntryClass up) {
    top = null;
    scptr = 0;
    levelup = up;
  }

  public void add (EntryTable x) {
    x.next = top;
    top = x;
    x.scope = scptr;
    x.mytable = this;
  }

   public void beginScope () {
      scptr++;
   }

   public void endScope () {
      while (top != null && top.scope == scptr)
         top = top.next;
      scptr--;
   }
   
	public EntryVar varFind (String x, int n) {
		EntryTable p = top;
		EntryClass q;

		while (p != null) {
		  if (p instanceof EntryVar && p.name.equals(x))
			if (--n == 0)
			  return (EntryVar) p;
		  p = p.next;
		}
		q = levelup;
		if (q.parent == null) 
			return null;
		return q.parent.nested.varFind(x, n);
	}

	public EntryVar varFind (String x) {
		return varFind(x, 1);
	}

	public EntryMethod methodFind(String x, EntryRec r) {
		EntryTable p = top;
		EntryClass q;
		
		while (p != null) {
			if (p instanceof EntryMethod && p.name.equals(x)) {
				EntryMethod t = (EntryMethod) p;
				if (t.param == null) {
					if (r == null)
						return t;
				} else {
					if (t.param.equals(r))
						return t;
				}
			}
			p = p.next;
		}
		q = levelup;
		if (q.parent == null)
			return null;
		return q.parent.nested.methodFind(x, r);
	}
	
   public EntryMethod methodFindInClass(String x, EntryRec r) {
	   EntryTable p = top;
	   EntryClass q;
	   while (p != null) {
         if (p instanceof EntryMethod && p.name.equals(x)) {
            EntryMethod t = (EntryMethod) p;
            if (t.param == null) {
               if (r == null)
                  return t;
            } else {
               if (t.param.equals(r))
					return t;
            }
         }
         p = p.next;
	   }
      return null;
   }
  
  public EntryTable classFindUp(String x) {
    EntryTable p = top;
    while (p != null) {
      if ( ((p instanceof EntryClass) || (p instanceof EntrySimple)) && p.name.equals(x))
		   return p;
      p = p.next;
    }
    if (levelup == null)
		return null;
    return levelup.mytable.classFindUp(x); 
  }
  
  public void forEach() {
	EntryTable p = top;
	while (p != null) {
		System.out.println(p.name);
		p = p.next;
	}
  }
}
