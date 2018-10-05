class T2 {
	
	constructor(int a, string b = "") {
		;
	}
	
	boolean teste(boolean t, int b = 5) {
		return true;
	}
}

class T1 extends T2 {
	class T3 {
		constructor() {
			T2 t;
			int i = 5;
			t = new T2(12);
			t = new T2(12, "12");
			boolean b = t.teste(true, i, "alo");
			boolean c = t.teste(true, i);
			boolean d = 4;
			if (true) {
				;
			}
		}
	}
}
