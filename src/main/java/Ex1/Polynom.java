package Ex1;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;
import Ex1.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */


public class Polynom implements Polynom_able{
	ArrayList<Monom> Polynom_new;


	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		Polynom_new = new ArrayList<Monom>();
		Polynom_new.add(new Monom(Monom.ZERO));
	}

	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		s = s.replaceAll("\\s+", "");
		s=s.toLowerCase();
		Polynom_new = new ArrayList<Monom>();
		int i=0;
		String monom1 = "";
		while (i <= s.length() ) {
			//the polynom will get an value of +x^4
			if (i==0 && (s.charAt(i) == '-' || s.charAt(i)=='+')) {
				monom1+=s.charAt(i);
				i++;
			}

			if(i < s.length() && s.charAt(i)!='+' && s.charAt(i) !='-') {
				monom1+=s.charAt(i);
				i++;
			}
			else {
				Monom new_monom = new Monom(monom1);
				this.Polynom_new.add(new_monom);
				monom1= "";
				if (i < s.length() && s.charAt(i)!= '+' ) {
					monom1+=s.charAt(i);
				}
				i++;
			}
		}
		Comparator<Monom> sort_Poly = new Monom_Comperator();
		Polynom_new.sort(sort_Poly); //organize the polynom by the size of the power
		shrinkMyPoly ( Polynom_new);
	}

	/** 
	 * sum the polynoms with the same power 
	 * @param polynom_new2
	 */

	public void shrinkMyPoly(ArrayList<Monom> polynom_new2) {
		for (int i = 0; i < polynom_new2.size(); i++) {
			if(i < polynom_new2.size()-1 && polynom_new2.size() >= 2) {
				if(polynom_new2.get(i).get_power() == polynom_new2.get(i+1).get_power()) {
					polynom_new2.get(i+1).add(polynom_new2.get(i));
					polynom_new2.remove(i);
					i--;
				}
			}
		}
		remove_zero();
	}

	@Override
	public double f(double x) {
		double ans=0;
		for(int i=0; i<Polynom_new.size() ;i++) {
			Monom temp=	Polynom_new.get(i);
			ans+=temp.f(x);
		}
		return ans;
	}


	@Override
	public void add(Polynom_able p1) {
		Iterator <Monom> poly_it = p1.iteretor();
		boolean flag = false;
		while (poly_it.hasNext()) {
			Monom temp=	poly_it.next();
			flag=false;
			for (int i =0 ; i < Polynom_new.size() ; i++) {
				if ( Polynom_new.get(i).get_power() == temp.get_power() ) {
					Polynom_new.get(i).add(temp);
					flag=true;
				}
			}
			if (!flag) {
				Polynom_new.add(temp);
			}
		}
		remove_zero();
	}

	@Override
	/**
	 * add monom to the polynom , example:
	 *  polynom:'3x^3+2x^2' , monom:'x', after adding: '3x^3+2x^2+x'
	 * @param m1: represent monom
	 */
	public void add(Monom m1) {
		boolean add = false;
		for(int i=0; i<Polynom_new.size() ;i++) {
			Monom temp=	Polynom_new.get(i);
			if(temp.get_power()==m1.get_power()) { 
				temp.add(m1);
				add=true;
				if(temp.get_coefficient() == 0) Polynom_new.remove(i);
			}
		}
		if (!add) {
			Polynom_new.add(m1);
		}
		Comparator<Monom> sort_Poly = new Monom_Comperator();
		Polynom_new.sort(sort_Poly);
		remove_zero();
	}


	@Override
	public void substract(Polynom_able p1) {
		if(this.equals(p1)) {
			Polynom_new.clear();
		}
		else {
			Iterator <Monom> poly_it = p1.iteretor();
			while (poly_it.hasNext()) {
				Monom temp= poly_it.next();
				Monom temp_negative = new Monom (temp);
				temp_negative.multipy(Monom.MINUS1);
				this.add(temp_negative);	
			}
		}
		this.remove_zero();
	}


	@Override
	/**
	 * multiply monom to  polynom , example:
	 *  polynom1:'4x^2+4x' , polynom1:'2x+3x^2', after multiplying: '12x^4+20x^3+8x^2'
	 * @param p1: represent polynom
	 */
	public void multiply(Polynom_able p1) {
		ArrayList<Monom> Poly_temp = new ArrayList<Monom>();
		Iterator <Monom> poly_it = p1.iteretor();
		while (poly_it.hasNext()) {
			Monom temp = poly_it.next();
			for (int i =0 ; i< Polynom_new.size() ; i++) {
				Monom monom_temp = new Monom (Polynom_new.get(i));
				monom_temp.multipy(temp);
				Poly_temp.add(monom_temp);
			}
		}
		Polynom p8 = new Polynom();
		for (int i =0 ; i < Poly_temp.size() ; i++) {
			p8.add(Poly_temp.get(i));
		}
		this.Polynom_new = p8.Polynom_new;
		for ( int i = 1 ; i< Polynom_new.size() ; i++) {
			if ( Polynom_new.get(i).equals(Monom.ZERO)) {
				Polynom_new.remove(Polynom_new.get(i));
				i--;
			}
		}
	}	


	@Override
	public boolean equals(Object p1) {
		if (p1 instanceof Polynom ) {
			Polynom p2 = (Polynom) p1;
			Iterator <Monom> poly_it = p2.iteretor();
			boolean flag = false;
			int i =0;
			while (poly_it.hasNext()) {
				flag=false;
				i++;
				Monom temp=	poly_it.next();
				for (Monom monom : Polynom_new) {
					if (monom.equals(temp) && monom.get_power() == temp.get_power()) {
						flag=true;
					}
				}
				if (!flag) {
					return false;
				}
			}
			if ( i != Polynom_new.size()) return false;
			return true;
		}
		else if (p1 instanceof Monom ) {
			Monom moni_new = new Monom (this.toString());
			return p1.equals(moni_new);
		}
		else if (p1 instanceof ComplexFunction) {
			ComplexFunction func = new ComplexFunction(this.toString());
			return	func.equals(p1);
		}

		return false;
	}

	@Override
	/**
	 * check if the polynom is Zero
	 * @return true of false
	 * 
	 */
	public boolean isZero() {
		boolean isZero=false;
		if ( Polynom_new.size() == 0) {
			return true;
		}
		for(int i=0;i<Polynom_new.size();i++) {
			if(Polynom_new.get(i).equals('0')) {
				isZero=true;
			}
			else {
				return false; //is there is a monom that isn't equal to 0 
			}
		}
		return true; 
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if((f(x0)*f(x1))>0) {
			throw new RuntimeException("ERR:f(x0)*f(x1)>0" );
		}
		else {
			double mid=0;
			while(Math.abs(f(x1)-f(x0))>eps) {
				mid=(x1+x0)/2;
				if(f(x1)*f(mid)>0) {
					x1=mid;
				}
				else {
					x0=mid;
				}
			}
			return mid;
		}
	}


	@Override
	public Polynom_able copy() {
		Polynom ans= new Polynom();
		for(int i=0;i<Polynom_new.size();i++) {
			Monom temp=new Monom(Polynom_new.get(i));
			ans.add(temp);
		}
		return ans;
	}

	@Override
	/**
	 * derivative polynom, example:
	 *  polynom:'4x^2+4x' ,derivation: '8x+4'
	 * 
	 */

	public Polynom_able derivative() {
		Polynom ans1= new Polynom();
		for(int i=0;i<Polynom_new.size();i++) {
			Monom temp=Polynom_new.get(i);
			ans1.add(temp.derivative());
		}
		ans1.remove_zero();
		return ans1;

	}

	@Override
	public double area(double x0, double x1, double eps) {
		double ans = 0;
		if (x1 <= x0) {
			return 0;
		}
		while ( x0 <= x1) {
			if (f(x0) > 0) {
				ans+=(eps*f(x0));
			}
			x0+=eps;
		}
		return ans;
	}


	@Override
	public Iterator<Monom> iteretor() {
		return Polynom_new.iterator();
	}
	@Override
	/**
	 * multiply monom to polynom , example:
	 *  polynom:'3x^3+2x^2' , monom:'x', after multiplying: '3x^4+2x^3'
	 * @param m1: represent monom
	 */
	public void multiply(Monom m1) {
		Monom m2 = new Monom (m1);
		Polynom n_poly=new Polynom();
		for(int i=0;i<Polynom_new.size();i++) {
			Polynom_new.get(i).multipy(m2);
		}
		remove_zero();
	}

	/**
	 * this function remove all the zero's inside the array
	 */

	private void remove_zero() {
		for (int i =0 ; i< Polynom_new.size() ; i++) {
			if (Polynom_new.get(i).get_coefficient() == 0) {
				Polynom_new.remove(i);
			}
		}
		Comparator<Monom> sort_Poly = new Monom_Comperator();
		Polynom_new.sort(sort_Poly); //organize the polynom by the size of the power
	}

	/**
	 * this function orgnize the polynom that we will print it
	 */
	public String toString() {
		String ans = "";
		if ( Polynom_new.size() == 0) {
			ans="0";
		}
		for (int i = 0 ; i< Polynom_new.size() ; i++) {
			if ( i > 0 && Polynom_new.get(i).get_coefficient() > 0) {
				ans+= "+";
			}
			ans+=Polynom_new.get(i).toString();	
		}
		return ans;
	}

	@Override
	public function initFromString(String s) {
		function polynom= new Polynom(s);
		return polynom;
	}



}