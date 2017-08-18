package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.struts2.util.DateFormatter;

public class DateUtil {
	
	public static String getFormattedDate(Date date)
	{
		DateFormatter df = new DateFormatter();
		df.setDate(date);
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
		df.setFormat(format);
		return df.getFormattedDate();
	}
	public static Calendar getCalendarInstance(Date date){
		DateFormatter df = new DateFormatter();
		df.setDate(date);
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
		return format.getCalendar();
	}
	public static Date getDateObject(String date)
	{
		Date result = new Date();
		DateFormat format = DateFormat.getDateInstance();
		try {
			//date = "04-04-2016";
			result = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
