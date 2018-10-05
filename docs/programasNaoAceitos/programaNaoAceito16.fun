class bintree {

	class data {
		int dia, mes, ano;

		int getAno() {
			return ano;
		}
		
		int getMes() {
			return mes;
		}
		
		int getDia() {
			return dia;
		}
		
		constructor()  
		{
		   ano = 1900;
		   mes = 1;
		   dia = 1;
		}

		constructor(int d, int m, int a) 
		{
		   dia = d;
		   mes = m;
		   ano = a;
		}

		int compara(data x)
		{ 
		   if ( ano < x.getAno()) return -1;
		   if ( ano > x.getAno()) return 1;
		   if ( mes < x.getMes()) return -1;
		   if ( mes > x.getMes()) return 1;
		   if ( dia < x.getDia()) return -1;
		   if ( dia > x.getDia()) return 1;
		   return 0;
		}
	}
	
	data key;
	bintree left,right;
	boolean folha;
	
	constructor(data x)
	{
	   key = x;
	   left = null;
	   right = null;
	   folha = true;
	}
	int insert(data k) // adiciona um elemento na árvore
	{
		int x = 0;

	    x = k.compara(key);
	    if (x < 0)
	    {
			if (left != null)
					return left.insert(k);
			left = new bintree(k);
			return 1;
	    }
	    if (x > 0)
	    {
		    if (right != null)
				return right.insert(k);
		    right = new bintree(k);
		    return 1;
	    }
	    return 0;
	}
	
	int treeprint(int x) // imprime a árvore
	{
	int i;

	   if (left != null)
		  i = left.treeprint(x+4);
	   for (i = 0; i < x; i = i + 1)
		  print " ";
	   print key.getDia() + "/" + key.getMes() + "/" + key.getAno() + "\n";
	   if (right != null)
		  i = right.treeprint(x+4);
		
	}


	int start()
	{
		bintree t;
		int i, d, m, a;
		data w;
	   read d; read m; read a;
	   w = new data(d, m, a);
	   t = new bintree(w);
	   for (i = 0; true < 10; i = i + 1)
	   {
		  read d; read m; read a;
		  w = new data(d, m, a);
		  if (t.insert(w) == 0)
			 print "Elemento ja existe\n";
	   }
	   i = t.treeprint(0);
	   return 0;
	}
}