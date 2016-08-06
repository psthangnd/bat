package cowell.vn.api.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import cowell.vn.api.google.auth.GoogleAuth;
import cowell.vn.util.DateUtils;

public class GSheetUtils {
	// Mỗi file gsheet đều có 1 ID riêng
	static String spreadsheetId = "1eFZrerEm1ykdROQeaTa9tPbsdfPN7ooH-8LOLuKae2g";	// File "Auto_【IT】インシデント件数管理表"
	static String spreadsheetId_test = "1Xrxb2e2fbnK7Xlf07mCfWdcwU530oB9-2sUSB8zP5CQ";	//"BAT_test" file

	public static void main(String[] args) {
		try {
			//getDataFromSheet();
			writeDataToSheet(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});	//data for test
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public static void writeDataToSheet(int[] values) throws IOException{
		String date = DateUtils.getCurDate();
		writeDataToSheet(values, date);
	}

	public static void writeDataToSheet(int[] values, String date) throws IOException{
		final int sheetId = 295702833;	//sheet "2016/08"
		final int rowIndex = 39;	//row need write data (start index from 0)
		
		//TODO
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
						.setStart(new GridCoordinate().setSheetId(sheetId).setRowIndex(rowIndex+i).setColumnIndex(colIndex))
						.setRows(Arrays.asList(new RowData().setValues(lstCell)))
						.setFields("userEnteredValue")));
		}

		BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
	}
	
	public static void getDataFromSheet() throws IOException{
		String range = "2016/08!C40:AG48";
		
		// authorized
		Sheets service = GoogleAuth.getSheetsService();
		
		ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
		List<List<Object>> values = response.getValues();
		if (values == null || values.size() == 0) {
			System.out.println("No data found.");
		} else {
			System.out.println("1	2	3	4	5	6	7	8	9	10	11	12	13	14	15	"
					+ "16	17	18	19	20	21	22	23	24	25	26	27	28	29	30	31");
			for (List<Object> row : values) {
				for(Object obj : row){
					System.out.print(obj.toString() + "\t");
				}
				System.out.println("");
			}
		}
	}
	
}