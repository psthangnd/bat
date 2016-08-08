package cowell.vn.service;

import java.io.IOException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cowell.vn.api.backlog4j.BackLogUtils;
import cowell.vn.api.google.Task2Utils;
import cowell.vn.constant.BackLogConstant;
import cowell.vn.constant.GoogleConstant;

public class Task2Scheduler implements Job{
	List<List<Object>> backlogData;
	
	public Task2Scheduler() {
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
				backlogData = BackLogUtils.getDataForTask2();
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
						Task2Utils.writeDataToSheet(backlogData);
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