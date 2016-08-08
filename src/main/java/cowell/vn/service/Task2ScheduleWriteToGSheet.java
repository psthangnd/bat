package cowell.vn.service;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import cowell.vn.constant.ApplicationConstant;

public class Task2ScheduleWriteToGSheet{
	private static final String GROUP_NAME = "Member_Effort";
	
	
	public Task2ScheduleWriteToGSheet() {
		Scheduler scheduler = null;
		try{
			SchedulerFactory schedFact = new StdSchedulerFactory();
			scheduler = schedFact.getScheduler();
			scheduler.start();
	
			// define the job and tie it to our HelloJob class
			JobBuilder jobBuilder = JobBuilder.newJob(Task1Scheduler.class);
	
			JobDetail jobDetail = jobBuilder
					.usingJobData("example", "cowell.vn.service.Task2Scheduler")
					.withIdentity("myJob2", "group2").build();
	
			System.out.println("Current time: " + new Date());
	
			// Tell quartz to schedule the job using our trigger
			// Fire at current time + 1 min every day
			scheduler.scheduleJob(jobDetail, fireAt());
		} catch(SchedulerException se){
			se.printStackTrace();
		} catch(Exception ex){
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Fire at 12pm every day
	public static Trigger fireAt() {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("fireAt", GROUP_NAME)
				.withSchedule(cronSchedule("Fire at...", ApplicationConstant.CRON_EXPRESSION_TASK2))
				.build();
		return trigger;
	}
	
	private static CronScheduleBuilder cronSchedule(String desc, String cronExpression) {
		System.out.println(desc + "->(" + cronExpression + ")");
		return CronScheduleBuilder.cronSchedule(cronExpression);
	}
	
}