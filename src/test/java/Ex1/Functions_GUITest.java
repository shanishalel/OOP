package Ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Functions_GUI;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.Range;
import Ex1.function;
import Ex1.functions;
/**
 * Note: minor changes (thanks to Amichai!!)
 * The use of "get" was replaced by iterator!
 * 
 * Partial JUnit + main test for the GUI_Functions class, expected output from the main:
 * 0) java.awt.Color[r=0,g=0,b=255]  f(x)= plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0)
1) java.awt.Color[r=0,g=255,b=255]  f(x)= plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)
2) java.awt.Color[r=255,g=0,b=255]  f(x)= div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)
3) java.awt.Color[r=255,g=200,b=0]  f(x)= -1.0x^4 +2.4x^2 +3.1
4) java.awt.Color[r=255,g=0,b=0]  f(x)= +0.1x^5 -1.2999999999999998x +5.0
5) java.awt.Color[r=0,g=255,b=0]  f(x)= max(max(max(max(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)
6) java.awt.Color[r=255,g=175,b=175]  f(x)= min(min(min(min(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)

 * @author boaz_benmoshe
 *
 */
class Functions_GUITest {
	private functions _data=null;

	@BeforeEach
	void setUp() throws Exception {
		_data = FunctionsFactory();
	}

	@Test
	void buildFromFunction() throws IOException{
		functions data = FunctionsFactory();
		String file = "function_file.txt";
		data.saveToFile(file);
		String JSON_param_file = "GUI_params.txt";
		data.drawFunctions(JSON_param_file);
		System.out.println();
	}
	
	@Test
	void testInitFromFile() throws IOException {
		Functions_GUI data = new Functions_GUI();
		data.initFromFile("a.txt");
	}

	@Test
	void testSaveToFile() throws IOException {
		Functions_GUI data = new Functions_GUI();
		String file = "function_file.txt";
		Polynom p1= new Polynom ("3x^3");
		Polynom p2=new Polynom("5x");
		ComplexFunction cf = new ComplexFunction("plus", p1,p2);
		data.add(cf);
		ComplexFunction cf1 = new ComplexFunction("mul", p1,p2);
		data.add(cf1);
		ComplexFunction cf2 = new ComplexFunction("div", p1,p2);
		data.add(cf2);
		data.saveToFile(file);
	}

	@Test
	void testDrawFromRead() throws IOException {
		Functions_GUI data = new Functions_GUI();
		data.initFromFile("function_file2.txt");
		String JSON_param_file = "GUI_params.txt";
		data.drawFunctions(JSON_param_file);
	}
	
	@Test
	void testDrawFunctions() throws IOException {
		Functions_GUI data = new Functions_GUI();
		String file = "function_file.txt";
		Polynom p1= new Polynom ("3x^3");
		Polynom p2=new Polynom("5x");
		ComplexFunction cf = new ComplexFunction("plus", p1,p2);
		data.add(cf);
		ComplexFunction cf1 = new ComplexFunction("mul", p1,p2);
		data.add(cf1);
		ComplexFunction cf2 = new ComplexFunction("div", p1,p2);
		data.add(cf2);
		data.saveToFile(file);
		String JSON_param_file = "GUI_params.txt";
		data.drawFunctions(JSON_param_file);
	}

	@Test 
	void testDrawFunctionWithOutParamsFile() throws IOException {
		Functions_GUI data = new Functions_GUI();
		data.initFromFile("function_file2.txt");
		String JSON_param_file = "GUI_param.txt";
		data.drawFunctions(JSON_param_file);
	}
	
	@Test
	void testDrawFunctionsIntIntRangeRangeInt() throws IOException {
		Functions_GUI data = new Functions_GUI();
		String file = "function_file.txt";
		Polynom p1= new Polynom ("3x^3");
		Polynom p2=new Polynom("5x");
		ComplexFunction cf = new ComplexFunction("plus", p1,p2);
		data.add(cf);
		ComplexFunction cf1 = new ComplexFunction("mul", p1,p2);
		data.add(cf1);
		ComplexFunction cf2 = new ComplexFunction("div", p1,p2);
		data.add(cf2);
		data.saveToFile(file);
		String JSON_param_file = "GUI_params.txt";
		double Range_X [] = {-10 , 10};
		double Range_Y [] = {-5 , 15};
		Range Rx = new Range(Range_X[0] , Range_X[1]);
		Range Ry = new Range(Range_Y[0] , Range_Y[1]);
		data.drawFunctions(1000 , 600 ,Rx ,Ry , 200);
	}
	
	public static functions FunctionsFactory() {
		functions ans = new Functions_GUI();
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x +3","x -2", "x -4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		for(int i=1;i<s3.length;i++) {
			cf3.mul(new Polynom(s3[i]));
		}
		
		ComplexFunction cf = new ComplexFunction(Operation.Plus, p1,p2);
		ComplexFunction cf4 = new ComplexFunction("div", new Polynom("x +1"),cf3);
		cf4.plus(new Monom("2"));
		ans.add(cf.copy());
		ans.add(cf4.copy());
		cf.div(p1);
		ans.add(cf.copy());
		String s = cf.toString();
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);
		ans.add(cf5.copy());
		ans.add(cf6.copy());
		Iterator<function> iter = ans.iterator();
		function f = iter.next();
		ComplexFunction max = new ComplexFunction(f);
		ComplexFunction min = new ComplexFunction(f);
		while(iter.hasNext()) {
			f = iter.next();
			max.max(f);
			min.min(f);
		}
		ans.add(max);
		ans.add(min);		
		return ans;
	}
}
