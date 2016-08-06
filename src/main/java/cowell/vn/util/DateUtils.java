package cowell.vn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static void main(String... arg){
		System.out.println(getDayInMonth("2016-06-06"));
		System.out.println(getDayInMonth("06-06-2016"));
	}
	
	
	public static int getDayInMonth(String date){
		if(date.length() != 10 || !date.matches("^\\d{4}-\\d{2}-\\d{2}$")){
			return -1;
		} else{
			String[] dataArr = date.split("-");
			if(dataArr.length >= 1)
				return Integer.parseInt(dataArr[dataArr.length - 1]);
			else
				return -1;
		}
	}
	
	public static String getNextDate(String curDate) {
		final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		
		try {
			Date date = format.parse(curDate);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			return format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getCurDate(){
		return new SimpleDateFormat(DATE_FORMAT).format(new Date());
	}
	
}