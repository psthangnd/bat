package cowell.vn.service;

import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cowell.vn.Main;
import cowell.vn.api.google.GSheetUtils;

public class QuartzJobScheduler implements Job{
	public QuartzJobScheduler() {
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.err.println("Auto write data from BackLog into Google Sheet!!!");
		
		executeWriteData();
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