package Ex1;

import Ex1.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PolynomTest {

	@Test
	void testPolynomString() {
		String[] polynoms = { "-3x", "5x^5-9x^3+6x^5-0", "0", "8+5+6+x" };
		String[] polynomsTest = { "-3.0x", "5.0x^5-9.0x^3+6.0x^5-0", "0.0", "8.0+5.0+6.0+1.0x" };
		for (int i = 0; i < polynoms.length; i++) {
			Polynom temp = new Polynom(polynoms[i]);
			Polynom temp2 = new Polynom(polynomsTest[i]);
			if (!temp.equals(temp2)) {
				fail();
			}
		}
	}

	@Test
	void testF() {
		String[] polynoms = { "-3x", "5x^5-9x^3+6x^5-0", "0", "8+5+6+x" };
		double [] ans = { -6, 280 , 0 , 21};
		for ( int i = 0 ; i < polynoms.length ; i++) {
			Polynom temp = new Polynom(polynoms[i]);
			double x = temp.f(2);
			if (x != ans[i]) {
				fail();
			}
		}
	}

	@Test
	void testAddPolynom_able() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-8x^4+5", "x^7" };
		Polynom p1 = new Polynom ("2x+4");
		String [] ans = {"2x^5+3x^3+2x+4", "7x^2-5+2x", "-8x^4+9+2x", "x^7+2x+4"};
		for ( int i = 0 ; i < polynoms.length ; i++) {
			Polynom temp = new Polynom(polynoms[i]);
			Polynom temp2 = new Polynom(ans[i]);
			temp.add(p1);
			if (!temp.equals(temp2)) {
				fail();
			}
		}
	}

	@Test
	void testAddMonom() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-8x^4+5", "x^7" };
		Monom m1 = new Monom ("2x");
		String [] ans = {"2x^5+3x^3+2x", "7x^2-9+2x", "-8x^4+5+2x", "x^7+2x"};
		for ( int i = 0 ; i < polynoms.length ; i++) {
			Polynom temp = new Polynom(polynoms[i]);
			Polynom temp2 = new Polynom(ans[i]);
			temp.add(m1);
			if (!temp.equals(temp2)) {
				fail();
			}
		}
	}

	@Test
	void testSubstract() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-8x^4+5", "x^7" };
		Polynom p1 = new Polynom ("2x+4");
		String [] ans = {"2x^5+3x^3-2x-4", "7x^2-13-2x", "-8x^4+1-2x", "x^7-2x-4"};
		for ( int i = 0 ; i < polynoms.length ; i++) {
			Polynom temp = new Polynom(polynoms[i]);
			Polynom temp2 = new Polynom(ans[i]);
			temp.substract(p1);
			if (!temp.equals(temp2)) {
				fail();
			}
		}
	}

	@Test
	void testMultiplyPolynom_able() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-8x^4+5", "x^7" };
		String[] ans = { "-4.0x^7+9.0x^3", "-14.0x^4+39.0x^2-27.0", "16.0x^6-24.0x^4-10.0x^2+15.0", "-2.0x^9+3.0x^7" };
		Polynom poly1=new Polynom("-2x^2+3");
		for(int i=0;i<polynoms.length;i++) {
			Polynom temp_poly=new Polynom(polynoms[i]); 
			Polynom ans_poly=new Polynom(ans[i]); 
			temp_poly.multiply(poly1);
			if (!temp_poly.equals(ans_poly)) {
				fail();
			}
		}
	}

	@Test
	void testEqualsPolynom_able() {
		String[] polynoms = {"-4x^7+9", "9-4X^7" };
		Polynom p1 = new Polynom ("-4x^7+9");
		for (int i = 0; i < polynoms.length; i++) {
			Polynom p2 = new Polynom (polynoms[i]);
			if (!p2.equals(p1)) {
				fail();
			}
		}
	}

	@Test
	void testIsZero() {
		Polynom p1 = new Polynom ("5x-6x^2");
		Polynom p2 = new Polynom ("5x-6x^2");
		p1.substract(p2);
		if (!p1.isZero()) {
			fail();
		}
	}

	@Test
	void testRoot() {
		String[] polynoms = { "x^3+5" };
		double [] ans = { -1.7099759504199028};
		for (int i = 0; i < polynoms.length; i++) {
			Polynom p1 = new Polynom (polynoms[i]);
			double ans1 = p1.root(-3, 5, 0.0000001);
			if ( ans1 != ans[i]) {
				fail();
			}
		}
	}

	@Test
	void testCopy() {
		Polynom poly = new Polynom("2x^3-3x+4");
		Polynom_able x = poly.copy();
		if (!x.equals(poly)) {
			fail();
		}
	}

	@Test
	void testDerivative() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-8x^4+5", "x^7" };
		String[] ans = { "10x^4+9x^2", "14x", "-32x^3", "7x^6" };
		for(int i=0;i<polynoms.length;i++) {
			Polynom temp_poly=new Polynom(polynoms[i]); 
			Polynom p1 = new Polynom (ans[i]);
			if (!temp_poly.derivative().equals(p1)) {
				fail();
			}
		}
	}

	@Test
	void testArea() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-4x^7+9", "0", "4x^7+9", "-4x^7-9" , "9-4X^7" };
		double [] ans1 = {303.74971653909205, 85.60672102675231 , 3316.3466191138864 , 0.0 , 3316.337863280124 ,  3262.3466101351823 , 3316.3466191138864};
		for(int i=0;i<polynoms.length;i++) {
			Polynom temp_poly=new Polynom(polynoms[i]);
			double ans = temp_poly.area(-3, 3, 0.000001);
			if ( ans != ans1[i] ) {
				fail();
			}
		}
	}

	@Test
	void testMultiplyMonom() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-8x^4+5", "x^7" };
		String[] ans = { "4x^7+6x^5", "14x^4-18x^2", "-16x^6+10x^2", "2x^9" };
		Monom monom1=new Monom("2x^2");
		for(int i=0;i<polynoms.length;i++) {
			Polynom temp_poly=new Polynom(polynoms[i]); 
			Polynom ans_poly=new Polynom(ans[i]); 
			temp_poly.multiply(monom1);
			if (!temp_poly.equals(ans_poly)) {
				fail();
			}
		}
	}

	@Test
	void testInitFromString() {
		String[] polynoms = { "2x^5+3x^3", "7x^2-9", "-8x^4+5", "x^7" };
		for(int i=0;i<polynoms.length;i++) {
			Polynom polynom= new Polynom (polynoms[i]);			
			if(!polynom.equals(polynom.initFromString(polynoms[i].toString()))) {
				fail();
			}
		}
	}

}
