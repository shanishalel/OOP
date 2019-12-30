package Ex1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Monom;
import Ex1.Polynom;
import Ex1.function;

class ComplexFunctionTest {

	@Test
	void testComplexFunctionString() {
		Polynom p1 = new Polynom ("3x^2+4");
		Polynom p2 = new Polynom ("10x+5");
		ComplexFunction cf = new ComplexFunction("plus" , p1 ,p2);
		String s ="plus(3x^2+4,10x+5)";
		ComplexFunction cf10 = new ComplexFunction(s);
		if (!cf10.equals(cf)) {
			fail();
		}
		
	}

	@Test
	void testF() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		String[] polynoms2 = { "x^2+5", "4+3x", "4x+3", "5x^2" };
		int ans [] = {3, 98, 11 , 30};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("plus", p1, p2));
		}
		for(int i =0 ; i<4 ; i++) {
			if (CF.get(i).f(2) != ans[i]) {
				fail();
			}
		}
	}

	@Test
	void testInitFromString() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			CF.add(new ComplexFunction(p1));
		}
		for(int i =0 ; i<4 ;i++) {
			String s= CF.get(0).toString();
			function fu = CF.get(0).initFromString(s);
			if (!fu.equals(CF.get(0))) {
				fail();
			}
		}
	}

	@Test
	void testCopy() {
		Polynom p1 = new Polynom ("3x^2+4");
		Polynom p2 = new Polynom ("10x+5");
		ComplexFunction cf = new ComplexFunction("plus" , p1 ,p2);
		function f = cf.copy();
		assertEquals(cf, f);
		cf.plus(new Polynom ("2x"));
		if(cf.equals(f)) {
			fail();
		}
		cf.plus(new Polynom ("-2x"));
		if(!cf.equals(f)) {
			fail();
		}
	}

	@Test
	void testPlus() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		String[] polynoms2 = { "x^2+5", "4+3x", "4x+3", "5x^2" };
		int ans [] = {3, 98, 11 , 30};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("plus", p1, p2));
		}
		for(int i =0 ; i<4 ; i++) {
			assertEquals(CF.get(i).f(2), ans[i] , Monom.EPSILON);
		}
	}

	@Test
	void testMul() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		String[] polynoms2 = { "x^2+5", "4+3x", "4x+3", "5x^2" };
		int ans [] = {-54, 880, 0 , 200};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("mul", p1, p2));
		}
		for(int i =0 ; i<4 ; i++) {
			assertEquals(CF.get(i).f(2), ans[i] , Monom.EPSILON);
		}
	}

	@Test
	void testDiv() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "x^2+5", "5x^5-9x^3", "4x+3", "8+x" };
		String[] polynoms2 = { "-3x", "4+3x", "0", "5x^2" };
		double ans [] = {-1.5, 8.8, Double.POSITIVE_INFINITY , 0.5};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("div", p1, p2));
		}
		for(int i =0 ; i<4 ; i++) {
			assertEquals(CF.get(i).f(2), ans[i] , Monom.EPSILON);
		}
	}

	@Test
	void testMax() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		String[] polynoms2 = { "x^2+5", "4+3x", "4x+3", "5x^2" };
		int ans [] = {9, 88 , 11 , 20};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("max", p1, p2));
		}
		for(int i =0 ; i<4 ; i++) {
			assertEquals(CF.get(i).f(2), ans[i] , Monom.EPSILON);
		}
	}

	@Test
	void testMin() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		String[] polynoms2 = { "x^2+5", "4+3x", "4x+3", "5x^2" };
		int ans [] = {-6, 10 , 0 , 10};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("min", p1, p2));
		}
		for(int i =0 ; i<4 ; i++) {
			assertEquals(CF.get(i).f(2), ans[i] , Monom.EPSILON);
		}
	}

	@Test
	void testComp() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		String[] polynoms2 = { "x^2+5", "4+3x", "4x+3", "5x^2" };
		int ans [] = {-27, 491000 , 0 , 28};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("comp", p1, p2));
		}
		for(int i =0 ; i<4 ; i++) {
			assertEquals(CF.get(i).f(2), ans[i] , Monom.EPSILON);
		}
	}

	@Test
	void Complex_functions() {
		ArrayList<ComplexFunction> CF = new ArrayList<ComplexFunction>();
		ArrayList<ComplexFunction> CF1 = new ArrayList<ComplexFunction>();
		ArrayList<ComplexFunction> CF2 = new ArrayList<ComplexFunction>();
		String[] polynoms = { "-3x", "5x^5-9x^3", "0", "8+x" };
		String[] polynoms2 = { "x^2+5", "4+3x", "4x+3", "5x^2" };
		Polynom p3 = new Polynom("3x+5x^2");
		Polynom p4 = new Polynom("-16+10x");
		double ans [] = {-7, 226.5, 6.5, 56.5};
		for(int i=0 ; i<polynoms.length ; i++ ) {
			Polynom p1 = new Polynom(polynoms[i]);
			Polynom p2 = new Polynom(polynoms2[i]);
			CF.add(new ComplexFunction("mul", p1, p2));
		}
		for(int i =0 ; i<4 ;i++) {
			CF1.add(new ComplexFunction("plus", CF.get(i) ,p3 ));
		}
		for(int i =0 ; i<4 ;i++) {
			CF2.add(new ComplexFunction("div", CF1.get(i) ,p4 ));
		}
		for(int i =0 ; i<4 ; i++) {
			assertEquals(CF2.get(i).f(2), ans[i] , Monom.EPSILON);
		}
	}

	@Test
	void testEqualsObject() {
		Monom m = new Monom ("x");
		Monom m1 = new Monom ("1");
		Polynom p = new Polynom ("x+1");
		Polynom p2 = new Polynom ("x^2+x");
		ComplexFunction cf = new ComplexFunction("div" ,m , m );
		assertEquals(cf, m1);
		cf.plus(m);
		assertEquals(cf, p);
		cf.mul(m);
		assertEquals(cf, p2);
	}
	
	@Test 
	void testSpace() {
		Monom m = new Monom ("x");
		ComplexFunction f = new ComplexFunction(m);
		function cf =f.initFromString("4x^  5");
		System.out.println(cf);
	}

}
