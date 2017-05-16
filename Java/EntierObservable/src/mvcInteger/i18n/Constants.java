package mvcInteger.i18n;

import java.util.ResourceBundle;

public enum Constants {
	 MINUS_BUTTON_LABEL,
	 PLUS_BUTTON_LABEL,
	 UNDO_BUTTON_LABEL,
	 REDO_BUTTON_LABEL;
	 
	 public static ResourceBundle res;
	 
	 public String translate(){
		 return res.getString(this.name());
	 }
}
