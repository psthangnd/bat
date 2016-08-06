package cowell.vn.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import cowell.vn.Main;
import cowell.vn.api.google.GSheetUtils;

public class ScheduleWriteToGSheet extends TimerTask{
	private static Timer timer;
	private static boolean isRun = true;
	public static final int WRITE_DATA_TIME = 1;	//day
	public static final long WRITE_DATA_PERIOD = WRITE_DATA_TIME * 24 * 60 * 60 * 1000;
	private static final int PERIOD = 10 * 1000;	//10s
	
	public static ScheduleWriteToGSheet INSTANCE;
	
	public static ScheduleWriteToGSheet getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ScheduleWriteToGSheet();
		}
		return INSTANCE;
	}
	
	
	public ScheduleWriteToGSheet() {
	}
	
	public void schedule(){
		Calendar today = Calendar.getInstance();
		today.add(Calendar.MILLISECOND, PERIOD);
		/*today.set(Calendar.HOUR_OF_DAY, 2);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);*/

		if(timer != null){
			isRun = false;
		}else{
			timer = new Timer(true);
			//Tu dong chay
			timer.schedule(new ScheduleWriteToGSheet(), today.getTime(), PERIOD);
		}
	}
	
	@Override
	public void run() {
		if(isRun){
			System.err.println("Auto write data from BackLog into Google Sheet!!!");
			
			executeWriteData();
		}else{
			timer.cancel();
			timer.purge();
			timer = null;
			isRun = true;
			schedule();
		}
	}
	
	private void executeWriteData(){
		//1. get data from BackLog API
		int[] backlogData = Main.getDataFromBackLog();
		
		//2. create thread try write into Google Sheet
		new Runnable() {
			@Override
			public void run() {
				//try send 'N' time
				int nTime = 3;
				for(int i=0; i<nTime; i++){
					try {
						GSheetUtils.writeDataToSheet(backlogData);
						i = nTime;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.run();
	}
	
}