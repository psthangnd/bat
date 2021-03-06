package cowell.vn.service;

import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cowell.vn.api.backlog4j.BackLogUtils;
import cowell.vn.api.google.Task1Utils;
import cowell.vn.constant.BackLogConstant;
import cowell.vn.constant.GoogleConstant;

public class Task1Scheduler implements Job{
	Integer[] backlogData;
	
	public Task1Scheduler() {
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.err.println("Auto write data from BackLog into Google Sheet!!!");
		
		executeWriteData();
	}
	
	private void executeWriteData(){
		//1. get data from BackLog API
		//try send 'N' time
		for(int i=0; i<BackLogConstant.N_TIME_TRY; i++){
			try{
				backlogData = BackLogUtils.getDataForTask1();
				i = BackLogConstant.N_TIME_TRY;
			} catch(Exception e){
				e.printStackTrace();
				System.err.println("Try again!!!");
			}
		}
		
		//2. create thread try write into Google Sheet
		new Runnable() {
			@Override
			public void run() {
				//try send 'N' time
				for(int i=0; i<GoogleConstant.N_TIME_TRY; i++){
					try {
						Task1Utils.writeDataToSheet(backlogData);
						i = GoogleConstant.N_TIME_TRY;
						System.out.println("Write data into GSheet completed!");
					} catch (IOException ie) {
						ie.printStackTrace();
						System.err.println("Try again!!!");
					}
				}
			}
		}.run();
	}
	
}