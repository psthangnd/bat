package cowell.vn.constant;

import java.util.Arrays;
import java.util.List;

import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.sheets.v4.SheetsScopes;

public class GoogleConstant {
	public static String CREDENTIALS_PATH = ".credentials/sheets.googleapis.com-bat.json";
	
	public static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, GmailScopes.GMAIL_SEND);
	
	public static final int N_TIME_TRY = 3;
	
	public static final String USER_ID_DEFAULT = "me";
	
}