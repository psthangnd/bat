package cowell.vn.api.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import cowell.vn.api.google.auth.GoogleAuth;
import cowell.vn.util.DateUtils;

public class Task1Utils {
	// Mỗi file gsheet đều có 1 ID riêng
	final static String spreadsheetId = "1eFZrerEm1ykdROQeaTa9tPbsdfPN7ooH-8LOLuKae2g";	//"Auto_【IT】インシデント件数管理表" file
	final static String spreadsheetId_test = "1Xrxb2e2fbnK7Xlf07mCfWdcwU530oB9-2sUSB8zP5CQ";	//"BAT_test" file
	final static String spreadsheetId_test2 = "1AXRQ7DWUXonT4wim5wlX0CrJwUa_dTFpqi_DEnlWL6E";	//"Test_20160804" file
	
	final static int sheetId = 295702833;
	final static String sheetName = "2016/08";
	//final static int sheetId = 0;
	//final static String sheetName = "Sheet1";
	
	final static int startRowIndex = 40;
	final static int endRowIndex = 48;
	
	
	public static void main(String[] args) {
		try {
			//getDataFromSheet("2016-08-01");
			writeDataToSheet(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});	//data for test
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		return String.format("%s!%s%d:%s%d", sheetName, colLetter, startRowIndex, colLetter, endRowIndex);
	}
	
	/*
	 * Date: 2016/08/08
	 * Author: ThangND
	 * Purpose: get column index from specific date
	 * Example: with sheet "2016/08", date="2016-08-04" -> day=04 -> column index:5
	 * 
	 */
	public static int getColumnIdxFromDay(int day){
		int colIndex = 2;
		
		for(int i=1; i<=31; i++){
			if(day != i){
				colIndex ++;
			} else{
				break;
			}
		}
		
		return colIndex;
	}
	
	public static void writeDataToSheet(Integer[] values) throws IOException{
		String date = DateUtils.getCurDate();
		writeDataToSheet(values, date);
	}
	
	public static void writeDataToSheet(Integer[] values, String date) throws IOException{
		String range = getRange(sheetName, date);
		
		// authorized
		Sheets service = GoogleAuth.getSheetsService();
		
		List<List<Object>> arrData = getData(values);

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
	
	@Deprecated
	public static void writeDataToSheet_Old(int[] values, String date) throws IOException{
		int day = DateUtils.getDayInMonth(date);
		int colIndex = getColumnIdxFromDay(day);	//col need write data
		
		// authorized
		Sheets service = GoogleAuth.getSheetsService();
		
		List<Request> requests = new ArrayList<>();
		List<CellData> lstCell = new ArrayList<>();
		for(int i = 0; i<values.length ;i++){
			double val = values[i];
			lstCell = new ArrayList<>();
			lstCell.add(new CellData().setUserEnteredValue(new ExtendedValue().setNumberValue(val)));
			
			requests.add(new Request().setUpdateCells(
					new UpdateCellsRequest()
						.setStart(new GridCoordinate().setSheetId(sheetId).setRowIndex(startRowIndex+i).setColumnIndex(colIndex))
						.setRows(Arrays.asList(new RowData().setValues(lstCell)))
						.setFields("userEnteredValue")));
		}

		BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
	}
	
	public static void getDataFromSheet(String date) throws IOException{
		String range = getRange(sheetName, date);
		
		// authorized
		Sheets service = GoogleAuth.getSheetsService();
		
		ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = response.getValues();
		if (values == null || values.size() == 0) {
			System.out.println("No data found.");
		} else {
			for (List<Object> row : values) {
				for(Object obj : row){
					System.out.print(obj.toString() + "\t");
				}
				System.out.println("");
			}
		}
	}
	
}