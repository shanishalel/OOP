package Ex1;

import Ex1.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonomTest {

	@Test
	void testDerivative() {
		String[] monoms = {"4x", "-x^2", "8x^3","-5x", "-9x^9"};
		String[] testMonoms = {"4", "-2x", "24x^2","-5", "-81x^8"};
		for(int i=0;i<monoms.length;i++) {
			Monom temp=new Monom(monoms[i]);
			Monom temptest=new Monom(testMonoms[i]);
			Monom tempDer = new Monom (temp.derivative());
			if(!tempDer.equals(temptest)) {
				fail();
			}
		}
	}

	@Test
	void testF() {
		String[] monoms = {"4 x", "- x ^2", "8 x ^3","-5x", "-9x^9"};
		double [] testMonoms = {20, -25, 1000,-25, -17578125};
		for(int i=0;i<monoms.length;i++) {
			Monom temp=new Monom(monoms[i]);
			double  ans = temp.f(5);
			if (ans !=  testMonoms[i]) {
				fail("this is not eqaul");
			}
		}
	}

	@Test
	void testIsZero() {
		Monom mul=new Monom("0");
		mul.isZero();
		Monom temp=new Monom("x^2");
		temp.multipy(mul);
		if(!temp.isZero()) {
			fail();
		}
	}

	@Test
	void testMonomString() {
		String[] monoms = {"4x", "-x", "8","-5x^7", "-9x^6"};
		String[] monoms2 = {"4.0x", "-1.0x", "8.0","-5.0x^7", "-9.0x^6"};
		for(int i=0;i<monoms.length;i++) {
			Monom temp=new Monom(monoms[i]);
			Monom temptest=new Monom(monoms2[i]);
			if(!temp.equals(temptest)) {
				fail();
			}
		}
	}

	@Test
	void testAdd() {
		String[] monoms = {"4x", "-x", "8x","-5x", "-9x"};
		String[] testMonoms = {"13x", "8x", "17x","4x", "0"};
		Monom mul=new Monom("9x");
		for(int i=0;i<monoms.length;i++) {
			Monom temp=new Monom(monoms[i]);
			Monom temptest=new Monom(testMonoms[i]);
			temp.add(mul);
			if(!temp.equals(temptest)) {
				fail();
			}
		}
	}

	@Test
	void testEqualsMonom() {
		String[] monoms = {"4x", "-x", "8x","-5x", "-9x"};
		String[] testMonoms = {"13x", "8x", "17x","4x", "0"};
		Monom mul=new Monom("9x");
		for(int i=0;i<monoms.length;i++) {
			Monom temp=new Monom(monoms[i]);
			Monom temptest=new Monom(testMonoms[i]);
			temp.add(mul);
			if(!temp.equals(temptest)) {
				fail();
			}
		}
	}

	@Test
	void testInitFromString() {
		String[] monoms = { "0", "4x", "-x", "8","12x^2", "9x^7"};
		for(int i=0;i<monoms.length;i++) {
			Monom monom= new Monom (monoms[i]);			
			if(!monom.equals(monom.initFromString(monoms[i].toString()))) {
				fail();
			}
		}
	}

	@Test
	void testCopy() {
		String[] monoms = { "0", "4x", "-x", "8","12x^2", "9x^7"};
		for(int i=0;i<monoms.length;i++) {
			Monom monom= new Monom (monoms[i]);			
			if(!monom.equals(monom.copy())) {
				fail();
			}
			
			}
		}
	

	@Test
	void testMultipy() {
		String[] monoms = { "0", "4x", "-x", "8","12x^2", "9x^7"};
		String[] testMonoms = { "0", "36.0x^2", "-9.0x^2", "72.0x","108.0x^3", "81.0x^8"};
		Monom mul=new Monom("9x");
		for(int i=0;i<monoms.length;i++) {
			Monom temp=new Monom(monoms[i]);
			Monom temptest=new Monom(testMonoms[i]);
			temp.multipy(mul);
			if(!temp.equals(temptest)) {
				fail();
			}
		}

	}
}