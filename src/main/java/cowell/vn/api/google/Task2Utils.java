package cowell.vn.api.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import cowell.vn.api.google.auth.GoogleAuth;
import cowell.vn.util.DateUtils;

public class Task2Utils {
	final static String spreadsheetId = "147mi90mVdV1WISYcF2Ipwwhu1Ygju3zkTsvos4Z3ips";
	final static int sheetId = 90718708;
	final static String sheetName = "Member_Effort2";
	
	final static int startRowIndex = 4;
	final static int endRowIndex = 35;
	

	public static void main(String[] args) {
		try {
			List<List<Object>> values = new ArrayList<List<Object>>();
			
			for(int i=4; i<=35; i++){
				List<Object> lstRow = new ArrayList<Object>();
				for(int j=1; j<=5; j++)
					lstRow.add(j);
				values.add(lstRow);
			}
			
			writeDataToSheet(values);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeDataToSheet(List<List<Object>> values) throws IOException{
		String date = DateUtils.getCurDate();
		
		writeDataBaseToSheet(date);
		writeDataEffortToSheet(values, date);
	}
	
	public static void writeDataBaseToSheet(String date) throws IOException{
		String range = sheetName + "!D1:D2";
		
		// authorized
		Sheets service = GoogleAuth.getSheetsService();
		
		List<List<Object>> arrData = getDataBase(date);

		ValueRange oRange = new ValueRange();
		oRange.setRange(range);
		oRange.setValues(arrData);

		List<ValueRange> oList = new ArrayList<>();
		oList.add(oRange);

		BatchUpdateValuesRequest batchUpdateRequest = new BatchUpdateValuesRequest();
		batchUpdateRequest.setValueInputOption("RAW");
		batchUpdateRequest.setData(oList);

		service.spreadsheets().values().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
	}
	
	public static void writeDataEffortToSheet(List<List<Object>> values, String date) throws IOException{
		String range = getRange(sheetName, date);
		
		// authorized
		Sheets service = GoogleAuth.getSheetsService();
		
		ValueRange oRange = new ValueRange();
		oRange.setRange(range);
		oRange.setValues(values);

		List<ValueRange> oList = new ArrayList<>();
		oList.add(oRange);

		BatchUpdateValuesRequest batchUpdateRequest = new BatchUpdateValuesRequest();
		batchUpdateRequest.setValueInputOption("RAW");
		batchUpdateRequest.setData(oList);

		service.spreadsheets().values().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
	}
	
	/*
	 * Date: 2016/08/08
	 * Author: ThangND
	 * Purpose: format data get from BackLog API into List<List<Object>>
	 * 
	 */
	public static List<List<Object>> getDataBase(String date) {
		List<List<Object>> data = new ArrayList<List<Object>>();
		
		long day = DateUtils.getWorkingDaysFrom(date);
		int baseEffort = (int) (8 * day);
		
		List<Object> data1 = new ArrayList<Object>();
		data1.add(baseEffort);
		data.add(data1);
		
		return data;
	}
	
	/*
	 * Date: 2016/08/08
	 * Author: ThangND
	 * Purpose: format data get from BackLog API into List<List<Object>>
	 * 
	 */
	public static List<List<Object>> getData(Integer[] values) {
		List<List<Object>> data = new ArrayList<List<Object>>();
		
		for(int val : values){
			List<Object> data1 = new ArrayList<Object>();
			data1.add(val);
			data.add(data1);
		}

		return data;
	}
	
	/*
	 * Date: 2016/08/08
	 * Author: ThangND
	 * Purpose: get range for value cover. Example "2016/08!C39:C47"
	 * 
	 */
	public static String getRange(String sheetName, String date){
		int day = DateUtils.getDayInMonth(date);
		int colIndex = getColumnIdxFromDay(day);	//col need write data
		String colLetter = GSheetUtils.convertColumnIndex2Letter(colIndex);
		
		//return String.format("%s!%s%d:%s%d", sheetName, colLetter, startRowIndex, colLetter, endRowIndex);
		return String.format("%s!%s%d:%s", sheetName, colLetter, startRowIndex, colLetter);
	}
	
	/*
	 * Date: 2016/08/08
	 * Author: ThangND
	 * Purpose: get column index from specific date
	 * Example: with sheet "2016/08", date="2016-08-04" -> day=04 -> column index:5
	 * 
	 */
	public static int getColumnIdxFromDay(int day){
		int colIndex = 6;
		
		for(int i=1; i<=31; i++){
			if(day != i){
				colIndex ++;
			} else{
				break;
			}
		}
		
		return colIndex;
	}
	
}