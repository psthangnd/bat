package cowell.vn.api.backlog4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cowell.vn.api.google.UserUtils;
import cowell.vn.constant.BackLogConstant;
import cowell.vn.util.DateUtils;


public class BackLogUtils {
	
	public static void main(String[] args) {
		/*//try send 'N' time
		for(int i=0; i<BackLogConstant.N_TIME_TRY; i++){
			try{
				Integer[] backlogData = BackLogUtils.getDataForTask1();
				System.out.println(backlogData.toString());
				i = BackLogConstant.N_TIME_TRY;
			} catch(Exception e){
				e.printStackTrace();
			}
		}*/
		
		//try send 'N' time
		for(int i=0; i<BackLogConstant.N_TIME_TRY; i++){
			try{
				List<List<Object>> backlogData = BackLogUtils.getDataForTask2();
				System.out.println(backlogData.toString());
				i = BackLogConstant.N_TIME_TRY;
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static List<List<Object>> getDataForTask2() throws IOException{
		return getDataForTask2(DateUtils.getCurDate());
	}
	public static List<List<Object>> getDataForTask2(String date) throws IOException{
		List<List<Object>> data = new ArrayList<List<Object>>();
		
		for(Object userId : UserUtils.getListUserFromSheet()){
			Map<Date, Object> mapData = IssueUtils.getDataForUser(userId.toString());
			
			//List<Object> data1 = new ArrayList<Object>();
			//data1.add(val);
			//data.add(data1);
		}
		
		return data;
	}
	
	/*
	 * 
	 * Status: when internet slow, throw IOException!!!
	 * Exception in thread "main" com.nulabinc.backlog4j.BacklogAPIException: java.io.IOException: Stream closed
	 *
	 * */
	public static Integer[] getDataForTask1(){
		return getDataForTask1(DateUtils.getCurDate());
	}
	public static Integer[] getDataForTask1(String date){
		//1. project: S_GWS
		System.out.println("==============Project: GWS+Pricing==============");
		int intHassei = IssueUtils.getHasseiList(BackLogConstant.GWS_PRICING_PROJECT, 
													BackLogConstant.GWS_PRICING_PROJECT_ISSUETYPE, date);
		int intShoriZumi = IssueUtils.getShoriZumiList(BackLogConstant.GWS_PRICING_PROJECT, 
													BackLogConstant.GWS_PRICING_PROJECT_ISSUETYPE, date);
		int intZan = IssueUtils.getZanList(BackLogConstant.GWS_PRICING_PROJECT, 
													BackLogConstant.GWS_PRICING_PROJECT_ISSUETYPE, date);
		System.out.println("発生: " + intHassei);
		System.out.println("処理済: " + intShoriZumi);
		System.out.println("残: " + intZan);
		
		
		//2. projects: S_ESA, S_WALLET, S_ADI, S_G_WIN, S_GSTATION_SERVER, S_LOCATION_JP
		System.out.println("==============Project: DOMESTIC==============");
		int intHassei2 = IssueUtils.getHasseiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[0]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[0]}, date);
		intHassei2 += IssueUtils.getHasseiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[1]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[1]}, date);
		intHassei2 += IssueUtils.getHasseiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[2]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[2]}, date);
		intHassei2 += IssueUtils.getHasseiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[3]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[3]}, date);
		intHassei2 += IssueUtils.getHasseiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[4]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[4]}, date);
		intHassei2 += IssueUtils.getHasseiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[5]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[5]}, date);
		
		int intShoriZumi2 = IssueUtils.getShoriZumiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[0]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[0]}, date);
		intShoriZumi2 += IssueUtils.getShoriZumiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[1]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[1]}, date);
		intShoriZumi2 += IssueUtils.getShoriZumiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[2]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[2]}, date);
		intShoriZumi2 += IssueUtils.getShoriZumiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[3]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[3]}, date);
		intShoriZumi2 += IssueUtils.getShoriZumiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[4]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[4]}, date);
		intShoriZumi2 += IssueUtils.getShoriZumiList(new String[]{BackLogConstant.DOMESTIC_PROJECT[5]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[5]}, date);
		
		int intZan2 = IssueUtils.getZanList(new String[]{BackLogConstant.DOMESTIC_PROJECT[0]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[0]}, date);
		intZan2 += IssueUtils.getZanList(new String[]{BackLogConstant.DOMESTIC_PROJECT[1]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[1]}, date);
		intZan2 += IssueUtils.getZanList(new String[]{BackLogConstant.DOMESTIC_PROJECT[2]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[2]}, date);
		intZan2 += IssueUtils.getZanList(new String[]{BackLogConstant.DOMESTIC_PROJECT[3]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[3]}, date);
		intZan2 += IssueUtils.getZanList(new String[]{BackLogConstant.DOMESTIC_PROJECT[4]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[4]}, date);
		intZan2 += IssueUtils.getZanList(new String[]{BackLogConstant.DOMESTIC_PROJECT[5]}, 
													new String[]{BackLogConstant.DOMESTIC_PROJECT_ISSUETYPE[5]}, date);
		
		System.out.println("発生: " + intHassei2);
		System.out.println("処理済: " + intShoriZumi2);
		System.out.println("残: " + intZan2);
		
		
		//3. projects: S_PSA, S_DN_NZ
		System.out.println("==============Project: INTERNATIONAL==============");
		int intHassei3 = IssueUtils.getHasseiList(new String[]{BackLogConstant.INTERNATIONAL_PROJECT[0]}, 
													new String[]{BackLogConstant.INTERNATIONAL_PROJECT_ISSUETYPE[0]}, date);
		intHassei3 += IssueUtils.getHasseiList(new String[]{BackLogConstant.INTERNATIONAL_PROJECT[1]}, 
													new String[]{BackLogConstant.INTERNATIONAL_PROJECT_ISSUETYPE[1]}, date);
		
		int intShoriZumi3 = IssueUtils.getShoriZumiList(new String[]{BackLogConstant.INTERNATIONAL_PROJECT[0]}, 
													new String[]{BackLogConstant.INTERNATIONAL_PROJECT_ISSUETYPE[0]}, date);
		intShoriZumi3 += IssueUtils.getShoriZumiList(new String[]{BackLogConstant.INTERNATIONAL_PROJECT[1]}, 
													new String[]{BackLogConstant.INTERNATIONAL_PROJECT_ISSUETYPE[1]}, date);
		
		int intZan3 = IssueUtils.getZanList(new String[]{BackLogConstant.INTERNATIONAL_PROJECT[0]}, 
													new String[]{BackLogConstant.INTERNATIONAL_PROJECT_ISSUETYPE[0]}, date);
		intZan3 += IssueUtils.getZanList(new String[]{BackLogConstant.INTERNATIONAL_PROJECT[1]}, 
													new String[]{BackLogConstant.INTERNATIONAL_PROJECT_ISSUETYPE[1]}, date);
		
		System.out.println("発生: " + intHassei3);
		System.out.println("処理済: " + intShoriZumi3);
		System.out.println("残: " + intZan3);
		
		return new Integer[]{intHassei, intShoriZumi, intZan, 
				intHassei2, intShoriZumi2, intZan2, 
				intHassei3, intShoriZumi3, intZan3};
	}
	
}