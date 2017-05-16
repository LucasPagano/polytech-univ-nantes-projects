package mvcInteger.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

@SuppressWarnings("serial")
public class RomanFormat extends Format {

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo,
			FieldPosition pos) {
		Integer val = (Integer) obj;
		StringBuffer s = new StringBuffer();
		if (val == 0) {
			s.append("nulla");
		} else {

			if (val == 10) {
				s.append("X");
				val -= 10;
			}
			if (val == 9) {
				s.append("IX");
				val -= 9;
			}
			if (val >= 5) {
				s.append("V");
				val -= 5;
			}
			if (val == 4) {
				s.append("IV");
				val -= 4;
			}
			while (val >= 1) {
				s.append("I");
				val -= 1;
			}
		}

		return s;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
