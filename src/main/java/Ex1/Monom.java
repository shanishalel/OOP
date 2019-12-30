
package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author 
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();

	public static Comparator<Monom> getComp() {
		return _Comp;
	}

	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}

	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}

	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {
			return getNewZeroMonom();
		}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}


	/** 
	 * this function represent a post in the monom
	 * @param x is the value that we will post in the monom 
	 * @return the value of a post in an equation
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 


	/**
	 * this function will return true if the monom value equals to 0 
	 * @return true or false
	 */
	public boolean isZero() {
		return this.get_coefficient() == 0;
	}
	// ***************** add your code below **********************
	/**
	 * init a monom from a String such as:
	 *  {"x", "3x", "4x^2", "6x^7"};
	 * @param s: is a string represents a monom
	 */

	public Monom(String s) {
		s = s.replaceAll("\\s+", "");
		s= s.toLowerCase();
		String a = "";
		String p = "";
		int i =0;
		if (IsMonom(s)) {
			while( i < s.length() && s.charAt(i) != 'x' ) {
				a+= s.charAt(i); 
				i++;
			}
			if (i< s.length() && s.charAt(i) == 'x') {
				if ( i+1 < s.length() && s.charAt(i+1) == '^') {
					i= i+2;
					while ( i < s.length()) {
						p+= s.charAt(i);
						i++;
					}	
				}
				else {
					p= "1";
				}
			}
			if (p == "") {
				p= "0";
			}
			if ( a.length() == 1 && a.charAt(0) == '-') {
				a= "-1";
			}
			//monom will get a value of +x^4
			if( a.length() == 1 && a.charAt(0) == '+') {
				a="1";
			}
			if (a.equals("")) {
				a= "1";
			}
			set_coefficient(Double.parseDouble(a));
			set_power(Integer.parseInt(p));
		}
		else {
			throw new RuntimeException("ERR The String isn't vaild ");
		}
	}	

	/**
	 * add monom to the monom , example:
	 *  monom:'4x^2' , monom:'3x^2', after adding: '7x^2'
	 * @param m: represent monom
	 */
	public void add(Monom m) {
		if(m._power!=this._power) {
			throw new RuntimeException("ERR The function isn't vaild ");
		}
		else {
			set_coefficient(this._coefficient+m._coefficient);
		}
	}

	/**
	 * multiply monom to the monom , example:
	 *  monom:'4x^2' , monom:'5x', after multiplying: '20x^3'
	 * @param d: represent monom
	 */
	public void multipy(Monom d) {
		set_power(this._power + d._power);
		set_coefficient(this._coefficient*d._coefficient);
	}

	/**
	 * this function orgnize the polynom that we will print it
	 */
	public String toString() {
		String ans = "";
		double a = get_coefficient();
		Integer p = get_power();
		if (a == 0 ) {
			ans = "0";
		}
		if ( p == 0) {
			ans = String.valueOf(a);
		}
		if ( p ==1 & a != 0) {
			ans = a + "x";
		}
		if ( p > 1  && a != 0) {
			ans = a + "x^" + p;
		}
		return ans;
	}
	// you may (always) add other methods.

	/**
	 * check if the string represent monom, example:
	 *  string:'p', ans:'false'; string:'3x', ans:'true'
	 * @param s: represent string
	 */
	public boolean IsMonom (String s) {	
		int i=0;
		int count = 0;
		if ((s.charAt(0) == '-' || s.charAt(0) == '+') && s.length() > 1) {
			i++;
		}
		while(( i < s.length()) &&( s.charAt(i) != 'x' )&& (s.charAt(i) >= '0' || s.charAt(i) <= '9' || s.charAt(i) == '.')) {
			if((s.charAt(i) < '0' || s.charAt(i) > '9') && (s.charAt(i) != '.')) {
				return false;
			}
			if ( s.charAt(0) == '.') {
				return false;
			}
			if (s.charAt(i) == '.') {
				count++; 
			}
			if ( count > 1) return false;
			i++;
		}

		if ( i >0 && s.charAt(i-1) == '.') {
			return false;
		}
		if (i < s.length() && s.charAt(i) != 'x') {
			return false;
		}
		i++;// get the i one up. 
		if ( i < s.length() && s.charAt(i) != '^') return false;
		i++; // get the i one up. 
		while(i < s.length() ) {
			if ( s.charAt(i) < '0' || s.charAt(i) > '9') {
				return false;
			}
			i++;
		}
		return true;
	}

	/**
	 * check if monoms ans equals, example:
	 *  monom1:'3x^2', monom2:'8x', ans:'false'
	 * @param m1: represent monom
	 * @return true or false
	 */

	public boolean equals (Object other) {
		if ( other instanceof Monom) {
			Monom m1 = (Monom) other;
		if ( this._coefficient ==0 && m1._coefficient==0) return true;
		double between = this._coefficient - m1._coefficient;
		if (Math.abs(between) <= EPSILON && this._power == m1._power) return true;
		}
		else if ( other instanceof Polynom) {
			Polynom poly_new = new Polynom (this.toString());
			return other.equals(poly_new);
		}
		else if (other instanceof ComplexFunction) {
			ComplexFunction func = new ComplexFunction(this.toString());
			return	func.equals(other);
		}
		return false;
	}

	//****************** Private Methods and Data *****************


	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;

	@Override
	public function initFromString(String s) {
	 function monom = new Monom (s);
	 return monom;
	}

	@Override
	public function copy() {
		function monom_copy=new Monom (this.toString());
		return monom_copy;
	}


}
