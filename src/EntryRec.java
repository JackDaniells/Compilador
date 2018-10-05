public class EntryRec extends EntryTable {
   public EntryTable type;
   public int dim;
   public EntryRec next;
   public int cont;
   public boolean opt;

   public EntryRec (EntryTable p, int d, int c, boolean o) {
      type = p;
      cont = c;
      dim = d;
      next = null;
	  opt = o;
   }

   //cria elemento a partir do inicio da lista
   public EntryRec (EntryTable p, int d, int c, EntryRec t, boolean o) {
	   type = p;
	   cont = c;
	   dim = d;
	   next = t;
	   opt = o;
   }
   
   public EntryRec inverte(EntryRec ant) {
	   EntryRec r = this;
	   if (next != null)
	  	 r = next.inverte(this);
	   cont = ant.cont + 1;
	   next = ant;
	   return r;
   }
   
   public EntryRec inverte() {
	   EntryRec r = this;
	   cont = 1;
	   if (next != null)
	  	   r = next.inverte(this);
	   next = null;
	   return r;
   }
   public String toStr() {
	   String s = type.name;
	   for (int i = 0; i < dim; i++)
		  s += "[]";
	   if (next != null)
	   	  s += (", " + next.toStr());
	   return s;
   }
   
   public String dscJava() {
       String s = strDim(dim);
       s += type.dscJava();
       if (next != null)
   	    s += next.dscJava();
       return s;
   }
   
   public boolean equals(EntryRec x) {
	    EntryRec p;
	    EntryRec q;
	    
	    p = this;
	    q = x;
	    do {
			if (p == null)
				return false;
			if (p.opt) {
				if (q == null) {
					p = p.next;
					continue;
				} else {
					if ((p.type != q.type) || (p.dim != q.dim))
						return false;
					p = p.next;
					q = q.next;
				}			
			} else {
				if ((q == null) || (p.type != q.type) || (p.dim != q.dim))
					return false;
				p = p.next;
				q = q.next;
			}
	    } while ((p != null) || (q != null));
	    return p == q;
   }
}
