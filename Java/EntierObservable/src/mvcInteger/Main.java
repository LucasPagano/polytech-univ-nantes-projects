package mvcInteger;

import mvcInteger.controller.GeneralController;
import mvcInteger.model.MyInteger;
import mvcInteger.model.MyRangeException;

public class Main {

	public static void main(String[] args) {
		MyInteger integer = new MyInteger();
		GeneralController generalController = new GeneralController(integer);

	
	}
}
