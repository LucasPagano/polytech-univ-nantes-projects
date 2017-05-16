package mvcInteger;

import java.util.Locale;
import java.util.ResourceBundle;

import mvcInteger.controller.GeneralController;
import mvcInteger.controller.SynchronizedController;
import mvcInteger.i18n.Constants;
import mvcInteger.model.IntegerWithUndo;
import mvcInteger.model.MyInteger;

public class Main {

	public static void main(String[] args) {
		
		Constants.res = ResourceBundle.getBundle("mvcInteger.i18n.Locale", Locale.FRANCE);
		MyInteger integer = new IntegerWithUndo();
		MyInteger integer2 = new IntegerWithUndo();
		new GeneralController(integer);
		new GeneralController(integer2);
		new SynchronizedController(integer, integer2);
	}
}
