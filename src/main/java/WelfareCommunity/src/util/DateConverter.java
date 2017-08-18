package util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {
	public Object convertFromString(Map context, String[] values, Class toClass) {
		String calendar = values[0];
		Integer year = new Integer(calendar.substring(0, calendar.indexOf("-")));
		calendar = calendar.substring(calendar.indexOf("-")+1);
		Integer month = new Integer(calendar.substring(0, calendar.indexOf("-")));
		calendar = calendar.substring(calendar.indexOf("-")+1);
		Integer day = new Integer(calendar);
		return new GregorianCalendar (year, month-1, day);

	}

	public String convertToString(Map context, Object o) {
		/*Calendar eventDate = (java.util.GregorianCalendar)o;
		DecimalFormat mFormat= new DecimalFormat("00");
		String month = ""+(mFormat.format(Double.valueOf(Integer.parseInt(""+eventDate.get(Calendar.MONTH)))+1));
		String eDate = ""+(mFormat.format(Double.valueOf(Integer.parseInt(""+eventDate.get(Calendar.DATE)))));
		String editEventDate = eventDate.get(Calendar.YEAR)+"-"+month+"-"+eDate;*/

		return o.toString();
	}
}