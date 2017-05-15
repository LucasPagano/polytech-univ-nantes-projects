package mvcInteger;

import mvcInteger.controller.GeneralController;
import mvcInteger.controller.SynchronizedController;
import mvcInteger.model.IntegerWithUndo;
import mvcInteger.model.MyInteger;

public class Main {

	public static void main(String[] args) {
		MyInteger integer = new IntegerWithUndo();
		MyInteger integer2 = new IntegerWithUndo();
		GeneralController generalController = new GeneralController(integer);
		GeneralController generalController2 = new GeneralController(integer2);
		SynchronizedController synchrController = new SynchronizedController(integer, integer2);
	}
}
