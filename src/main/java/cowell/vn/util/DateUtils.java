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
	
	public static Date getFirstDateInCurrentMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		
		return c.getTime();
	}
	
	public static Long getWorkingDaysFrom(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		
		try {
			Date startDate = getFirstDateInCurrentMonth();
			//FIXME: vì date định dạng yyyy-MM-dd, nên convert into Date: yyyy-MM-dd 00:00:00, nên +1 ngày
			Date endDate = format.parse(getNextDate(date));
			
			return getWorkingDaysBetweenTwoDates(startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Long getWorkingDaysBetweenTwoDates(String start, String end) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		
		try {
			Date startDate = format.parse(start);
			Date endDate = format.parse(end);
			
			return getWorkingDaysBetweenTwoDates(startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Long getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		int w1 = c1.get(Calendar.DAY_OF_WEEK);
		c1.add(Calendar.DAY_OF_WEEK, -w1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);
		int w2 = c2.get(Calendar.DAY_OF_WEEK);
		c2.add(Calendar.DAY_OF_WEEK, -w2);

		// end Saturday to start Saturday
		long days = (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		long daysWithoutWeekendDays = days - (days * 2 / 7);

		// Adjust days to add on (w2) and days to subtract (w1) so that Saturday
		// and Sunday are not included
		if (w1 == Calendar.SUNDAY && w2 != Calendar.SATURDAY) {
			w1 = Calendar.MONDAY;
		} else if (w1 == Calendar.SATURDAY && w2 != Calendar.SUNDAY) {
			w1 = Calendar.FRIDAY;
		}

		if (w2 == Calendar.SUNDAY) {
			w2 = Calendar.MONDAY;
		} else if (w2 == Calendar.SATURDAY) {
			w2 = Calendar.FRIDAY;
		}

		return daysWithoutWeekendDays - w1 + w2;
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
	
	public static String getPreviewDate(String curDate) {
		final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		
		try {
			Date date = format.parse(curDate);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			return format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
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