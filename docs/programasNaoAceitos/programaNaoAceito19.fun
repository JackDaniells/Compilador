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
			t = new T2(12);
			t = new T2(12, "12");
			boolean b = t.teste(true);
			boolean c = t.teste(true, 5);
			boolean d = 4;
			string uu = "ab" + true;
			if (true) {
				;
			}
		}
	}
}
