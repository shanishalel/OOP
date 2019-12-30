package Ex1;

public class ComplexFunction implements complex_function {
	public static final double EPS = 0.00001;


	function left;
	function right;
	Operation OP;

	//build a complex function by the op, left and right 
	public ComplexFunction (String op, function left , function right) {

		if (op == null || left==null) {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}

		if((left!=null && right == null) && !op.toLowerCase().equals("none")) {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}

		if ( left != null ) {
			this.left = left.copy();
		}
		if ( right != null ) {
			this.right = right.copy();
		}

		switch(op.toLowerCase()) {
		case "plus" :OP = Operation.Plus;
		break;
		case "mul" :OP = Operation.Times;
		break;
		case "div" :OP = Operation.Divid;
		break;
		case "max" :OP = Operation.Max;
		break;
		case "min" :OP = Operation.Min;
		break;
		case "comp" :OP = Operation.Comp;
		break;
		case "none" :
			//a none case is only with one function
			if(left != null && right != null ) {
				OP = Operation.Error;
				throw new RuntimeException("ERR The Complexfunction isn't vaild ");
			}
			OP = Operation.None;
			break;
		default:
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}
	}
	
	
	public ComplexFunction (Operation op, function left , function right) {
		if((left != null && right != null) && op.equals(OP.None)) {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}
		if (op == null || left==null) {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}

		if((left!=null && right == null) && !op.equals(OP.None)) {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}

		if ( left != null ) {
			this.left = left.copy();
		}
		if ( right != null ) {
			this.right = right.copy();
		}

		if (op.equals(OP.Error)) {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}
		this.OP = op;
	}
	
	//build a complex function from String
	public ComplexFunction (String s) {
		s = s.replaceAll("\\s+", "");
		function new_fun = initFromString(s);
		if (new_fun instanceof ComplexFunction) {
			ComplexFunction cf_new = (ComplexFunction) new_fun;
			this.left = cf_new.left.copy();
			this.right = cf_new.right.copy();
			this.OP = cf_new.getOp();
		}
		else if ( new_fun instanceof Polynom) {
			Polynom poly = (Polynom) new_fun;
			this.left = poly.copy();
			this.OP = Operation.None;
		}
		else {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}
	}

	//gets only left function
	public ComplexFunction (function left) {
		if (left ==null) {
			throw new RuntimeException("ERR The Complexfunction isn't vaild ");
		}
		this.left = left.copy();
		this.OP = Operation.None;
	}


	@Override
	public double f(double x) {

		switch(OP.toString().toLowerCase()) {

		case "plus" : return this.left.f(x) + this.right.f(x);

		case "times" : return this.left.f(x) * this.right.f(x);

		case "divid" : 
			if (this.left.f(x) == 0 && this.right.f(x) == 0) {
				throw new RuntimeException("ERR You cant divid 0/0");
			}
			return this.left.f(x) / this.right.f(x);

		case "max" : 
			if (this.left.f(x) > this.right.f(x)) return this.left.f(x);
			else return this.right.f(x);

		case "min" : 
			if (this.left.f(x) < this.right.f(x)) return this.left.f(x);
			else return this.right.f(x);

		case "comp" : 
			if (this.right != null ) {
				return this.left.f(this.right.f(x));
			}
			else {
				// need to check
				return this.left.f(x);
			}
		case "none" :
			return this.left.f(x);
		}


		return 0;
	}


	@Override
	/**
	 * this function gets a string and init it to function 
	 */
	public function initFromString(String s) { 
		s = s.replaceAll("\\s+", "");
		int i =0;
		if ( s.indexOf("(") == -1 && s.indexOf(")") == -1  ) { //returns -1 if the char don't found in the string
			Polynom poly = new Polynom (s);
			return poly.initFromString(s);
		}
		else {
			while (s.charAt(i) != '(') {
				i++;
			}
			String sub3 = s.substring(0, i);
			int last = s.lastIndexOf(')');
			if (sub3.equals("none") || sub3.equals("None")) {
				String sub1=s.substring(i+1, last);
				function left = initFromString(sub1);
				function function_new= new ComplexFunction(left);
				return function_new;
			}
			else {
				int split=LocationSplit(s , i+1);
				String sub1=s.substring(i+1, split);
				String sub2=s.substring(split+1,last);
				function left = initFromString(sub1);
				function right = initFromString(sub2);
				function function_new= new ComplexFunction(sub3, left, right);
				return function_new;
			}
		}

	}

	/**
	 * 
	 * @param s gets an string that represents a complex function
	 * @param i location after the "("
	 * @return the location of the split (left and right)
	 */
	private int LocationSplit (String s , int i) {
		int comma=0;
		int opener=1;
		int locationSplit=0;
		while(i != s.length()) {
			if(s.charAt(i)=='(') {
				opener++;
			}
			if(s.charAt(i)==',') {
				comma++;
			}
			if(comma==opener && s.charAt(i) == ',') {
				locationSplit=i;
				return locationSplit;
			}
			i++;
		}
		return locationSplit;
	}

	@Override
	public function copy() {
		function copy= new ComplexFunction(switchBackOP (), this.left, this.right);
		return copy;
	}

	@Override
	public void plus(function f1) {
		f1 = f1.copy();
		if ( this.right != null ) {
			function f = new ComplexFunction(switchBackOP (), this.left,this.right);
			this.left = f.copy();
		}
		this.right = f1.copy();
		this.OP = Operation.Plus;
	}

	@Override
	public void mul(function f1) {
		f1 = f1.copy();
		if ( this.right != null ) {
			function f = new ComplexFunction(switchBackOP (), this.left.copy(),this.right.copy());
			this.left = f.copy();
		}
		this.right = f1.copy();
		this.OP = Operation.Times;
	}

	@Override
	public void div(function f1) {
		f1 = f1.copy();
		if ( this.right != null ) {
			function f = new ComplexFunction(switchBackOP (), this.left,this.right);
			this.left = f.copy();
		}
		this.right = f1.copy();
		this.OP = Operation.Divid;
	}

	@Override
	public void max(function f1) {
		f1 = f1.copy();
		if ( this.right != null ) {
			function f = new ComplexFunction(switchBackOP (), this.left,this.right);
			this.left = f.copy();
		}
		this.right = f1.copy();
		this.OP = Operation.Max;
	}

	@Override
	public void min(function f1) {
		f1 = f1.copy();
		if ( this.right != null ) {
			function f = new ComplexFunction(switchBackOP (), this.left,this.right);
			this.left = f.copy();
		}
		this.right = f1.copy();
		this.OP = Operation.Min;
	}

	@Override
	public void comp(function f1) {
		f1 = f1.copy();
		if ( this.right != null ) {
			function f = new ComplexFunction(switchBackOP (), this.left,this.right);
			this.left = f.copy();
		}
		this.right = f1.copy();
		this.OP = Operation.Comp;
	}

	@Override
	public function left() {
		return this.left;
	}

	@Override
	public function right() {
		return this.right;
	}

	@Override
	public Operation getOp() {
		return this.OP;
	}


	@Override
	public boolean equals (Object other) {
		boolean equals=false;
		if (other instanceof ComplexFunction) {
			ComplexFunction cf = (ComplexFunction) other;
			if(((ComplexFunction) other).left==this.left) {
				if(((ComplexFunction) other).OP==this.OP) {
					if(((ComplexFunction) other).right()==this.right) {
						equals=true;
					}
				}
			}
		}
		if(equals==false) { //answer the problem 
			double i=-10;
			function temp=this.initFromString(other.toString());
			int sim=0;
			while(i<11) {
				double ans1=(this.f(i));
				double ans2= temp.f(i);
				if((Math.abs(ans1-ans2))<EPS) {
					sim++;
					if(sim==21) { //need to be similar on all the values between -10 to 10
						equals=true;
					}	
				}
				i = i + 0.1;
			}			
		}
		return equals;

	}



	/**
	 * 
	 * @return string to print the function
	 */
	public String toString() {
		String ans="";
		String op ="";
		if(this.OP!=Operation.None) { //check if this.op=None
			if (this.OP == Operation.Plus) {
				ans+="plus";
			}
			if (this.OP == Operation.Times) {
				ans+="mul";
			}
			if (this.OP == Operation.Divid) {
				ans+="div";
			}
			if (this.OP == Operation.Max) {
				ans+="max";
			}
			if (this.OP == Operation.Min) {
				ans+="min";
			}
			if (this.OP == Operation.Comp) {
				ans+="comp";
			}
			ans+="(";
		}
		if(this.left!=null) {
			ans+=this.left;

		}

		if(this.right!=null) {
			ans+=" , ";
			ans+=this.right;
			ans+=")";
		}


		return ans;
	}



	/*the function switch the OP name in from upper case to lower case */
	private String switchBackOP () {
		switch (this.OP.toString())
		{

		case "Plus": return "plus";

		case "Divid" : return "div";

		case "Times" : return "mul";

		case "Max" : return "max";

		case "Min" : return "min";

		case "Comp" :return "comp";

		case "None" : return "none";

		default: return "ERROR";
		}

	}

}
