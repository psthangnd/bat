package cowell.vn;

import cowell.vn.api.backlog4j.IssueUtils;
import cowell.vn.constant.BackLogConstant;
import cowell.vn.service.QuartzScheduleWriteToGSheet;
import cowell.vn.util.DateUtils;

/*
 * 
 * Status: when internet slow, throw IOException!!!
 * Exception in thread "main" com.nulabinc.backlog4j.BacklogAPIException: java.io.IOException: Stream closed
 *  
 *  */
public class Main {
	public static void main(String... arg){
		QuartzScheduleWriteToGSheet c = new QuartzScheduleWriteToGSheet();
		
		
		//new ScheduleWriteToGSheet().run();
		
		
		/*String dateTest = "2016-08-04";
		
		//1. get data from BackLog API
		int[] backlogData = getDataFromBackLog(dateTest);
		//int[] backlogData = getDataFromBackLog();
		System.err.println("==============Writing into Google Sheet!==============");
		
		//2. create thread try write into Google Sheet
		new Runnable() {
			@Override
			public void run() {
				//try send 'N' time
				int nTime = 3;
				for(int i=0; i<nTime; i++){
					try {
						GSheetUtils.writeDataToSheet(backlogData, dateTest);
						i = nTime;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.run();*/
	}
	
	public static int[] getDataFromBackLog(){
		return getDataFromBackLog(DateUtils.getCurDate());
	}
	public static int[] getDataFromBackLog(String date){
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
		
		return new int[]{intHassei, intShoriZumi, intZan, 
				intHassei2, intShoriZumi2, intZan2, 
				intHassei3, intShoriZumi3, intZan3};
	}
	
}