package cowell.vn.api.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import cowell.vn.api.google.auth.GoogleAuth;

public class UserUtils {
	//https://docs.google.com/spreadsheets/d/147mi90mVdV1WISYcF2Ipwwhu1Ygju3zkTsvos4Z3ips/edit#gid=90718708
	final static String spreadsheetId = "147mi90mVdV1WISYcF2Ipwwhu1Ygju3zkTsvos4Z3ips";	//"backlog_auto" file
	final static int sheetId = 90718708;
	final static String sheetName = "Member_Effort2";
	final static String range = sheetName + "!B4:B";
	
	final static int startRowIndex = 4;
	final static int endRowIndex = 35;
	
	
	public static void main(String[] args) {
		try {
			getListUserIdFromSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Object> getListUserIdFromSheet() throws IOException{
		List<Object> lstUser = new ArrayList<Object>();
		
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
					lstUser.add(obj);
					break;
				}
				System.out.println("");
			}
		}
		
		return lstUser;
	}
	
}