package cowell.vn.api.google;

import cowell.vn.util.StringUtils;

public class GSheetUtils {

	/*
	 * Date: 2016/08/08
	 * Author: ThangND
	 * Purpose: Convert column index into corresponding column letter
	 * 
	 */
	public static String convertColumnIndex2Letter(int colIndex) {
		//TODO: Google Sheet calculation column index from 0
		colIndex++;
		
		if (colIndex < 27) { 
			return StringUtils.fromCharCode(64 + colIndex);
		} else {
			int first = Math.round(colIndex / 26);
			int second = colIndex % 26;
			return StringUtils.fromCharCode(64 + first) + StringUtils.fromCharCode(64 + second);
		}
	}
}
