package Ex1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Ex1.Monom;
import Ex1.Range;
import Ex1.function;
import Ex1.functions;
import java.awt.Color;
import java.awt.Font;

public class Functions_GUI implements functions {

	ArrayList<function> functions_gui;
	
	public Functions_GUI() {
		functions_gui = new ArrayList <function>();
	}

	@Override
	public int size() {
		return functions_gui.size();
	}

	@Override
	public boolean isEmpty() {
		if	(functions_gui.size() == 0) return true;
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return functions_gui.contains(o);
	}

	@Override
	public Iterator<function> iterator() {
		return functions_gui.iterator();
	}

	@Override
	public Object[] toArray() {
		return functions_gui.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.functions_gui.toArray(a);
	}

	@Override
	public boolean add(function e) {
		return functions_gui.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return functions_gui.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return functions_gui.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		return functions_gui.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return functions_gui.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return functions_gui.retainAll(c);
	}

	@Override
	public void clear() {
		functions_gui.clear();
	}

	//should be in try and catch- ?
	@Override
	public void initFromFile(String file) throws IOException {
		functions_gui = new ArrayList<function>(); 
		FileReader file_Strings = new FileReader(file);
		BufferedReader reader = new BufferedReader(file_Strings);
		String line_String = reader.readLine();

		while (line_String != null ) {
			line_String = line_String.replaceAll("\\s+", ""); // cut the spaces
			int loc = line_String.indexOf("f(x)=");
			if (loc != -1) {
				line_String = line_String.substring(loc+"f(x)=".length() ,line_String.length()); // cut substring of "f(x)="
			}
			ComplexFunction cf_new = new ComplexFunction(line_String);
			cf_new.initFromString(line_String); 
			functions_gui.add(cf_new); // add the function to the array
			line_String = reader.readLine(); //skip to the next line
		}
		reader.close(); //close the reading
	}

	@Override
	public void saveToFile(String file) throws IOException {

			FileWriter writer= new FileWriter (file);
			Iterator<function> funWriter =  functions_gui.iterator();
			int numberFun =0;
			
			while (funWriter.hasNext()) {
				function f = funWriter.next();
				writer.write(numberFun + ") " + "f(x)=" + f.toString() + '\n');
				numberFun++;
			}
			writer.close();
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		int count=0;
		StdDraw.setCanvasSize(width, height);
		// rescale the coordinate system
				StdDraw.setXscale(rx.get_min(), rx.get_max());
				StdDraw.setYscale(ry.get_min(), ry.get_max());
				
				//////// vertical lines
				StdDraw.setPenColor(Color.LIGHT_GRAY);
				for (double i = ry.get_min(); i <= ry.get_max(); i++) {
					StdDraw.line(rx.get_min(), i, rx.get_max(), i);
				}
		//////// horizontal  lines
				for (double i = rx.get_min(); i <= rx.get_max(); i++) {
					StdDraw.line(i, ry.get_min(), i, ry.get_max());
				}
		//////// x axis		
				double midy = (ry.get_max()+ry.get_min())/2;
				double midx = (rx.get_max()+rx.get_min())/2;
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.setPenRadius(0.005);
				StdDraw.line(rx.get_min(),0, rx.get_max(), 0);
				StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
				for (double i = rx.get_min(); i <= rx.get_max(); i++) {
					StdDraw.text(i,-0.35, Double.toString(i));
				}
				//////// y axis	
				StdDraw.line(0, ry.get_min(), 0, ry.get_max());
				for (double i = ry.get_min(); i <= ry.get_max(); i++) {
					StdDraw.text(-0.3, i, Double.toString(i));
				}
				// plot the approximation to the function
				
				for (function function : functions_gui) {
					double step = ( ( Math.abs(rx.get_min()) ) + ( Math.abs(rx.get_max()) )) / resolution;
					int Red = (int) (Math.random()*256);
					int Green = (int) (Math.random()*256);
					int Blue = (int) (Math.random()*256);
					Color color_line = new Color (Red , Green , Blue);
					
					StdDraw.setPenColor(color_line);
					for (double i = rx.get_min(); i < rx.get_max(); i+=step) {
						StdDraw.line(i, function.f(i), i+step, function.f(i+step));
					}
					System.out.println(count + ") " + color_line.toString() + " f(x)= "+ function.toString());
					count++;
				}
				StdDraw.setPenColor(Color.BLACK);
		
	}

	@Override
	public void drawFunctions(String json_file) {
		JSONParser jsonParser = new JSONParser();
		int Width = 1000 , Height = 600 ,Resolution = 200;
		double Range_X [] = {-10 , 10};
		double Range_Y [] = {-5 , 15};
		JSONArray RangeX;
		JSONArray RangeY;

		try {
			FileReader reader = new FileReader(json_file);
			Object obj = jsonParser.parse(reader);
			JSONObject read = (JSONObject) obj;
			if (read.get("Width") != null ) {
				Width = Math.toIntExact((long) read.get("Width"));
			}
			if (read.get("Height") != null ) {
				Height = Math.toIntExact((long) read.get("Height"));
			}
			if (read.get("Resolution") != null ) {
				Resolution = Math.toIntExact((long) read.get("Resolution"));
			}

			if (read.get("Range_X") != null ) {
				RangeX = (JSONArray)read.get("Range_X");
				Iterator <Long> iter= RangeX.iterator();
				int i =0;
				while (iter.hasNext()) {
					Range_X[i] = iter.next().doubleValue();
					i++;
				}
			}
			if (read.get("Range_Y") != null ) {
				RangeY = (JSONArray)read.get("Range_Y");
				Iterator <Long> iter= RangeY.iterator();
				int i =0;
				while (iter.hasNext()) {
					Range_Y[i] = iter.next().doubleValue();
					i++;
				}
				Range Rx = new Range(Range_X[0] , Range_X[1]);
				Range Ry = new Range(Range_Y[0] , Range_Y[1]);
				drawFunctions(Width ,Height ,Rx, Ry , Resolution );
			}
		}
		catch(Exception e){
			System.out.println("the file is not found we will use defualt params");
			Range Rx = new Range(Range_X[0] , Range_X[1]);
			Range Ry = new Range(Range_Y[0] , Range_Y[1]);
			drawFunctions(Width ,Height ,Rx, Ry , Resolution );
		}
	}

}
