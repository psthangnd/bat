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

public class QuartzScheduleWriteToGSheet{
	private static final String GROUP_NAME = "WRITE_GSHEET";
	
	
	public QuartzScheduleWriteToGSheet() {
		try{
			SchedulerFactory schedFact = new StdSchedulerFactory();
			Scheduler scheduler = schedFact.getScheduler();
			scheduler.start();
	
			// define the job and tie it to our HelloJob class
			JobBuilder jobBuilder = JobBuilder.newJob(QuartzJobScheduler.class);
	
			JobDetail jobDetail = jobBuilder
					.usingJobData("example", "cowell.vn.service.QuartzJobScheduler")
					.withIdentity("myJob", "group1").build();
	
			System.out.println("Current time: " + new Date());
	
			// Tell quartz to schedule the job using our trigger
			// Fire at current time + 1 min every day
			scheduler.scheduleJob(jobDetail, fireAt());
		} catch(SchedulerException se){
			se.printStackTrace();
		}
	}
	
	// Fire at 12pm every day
	public static Trigger fireAt() {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("fireAt", GROUP_NAME)
				.withSchedule(cronSchedule("Fire at...", ApplicationConstant.CRON_EXPRESSION))
				.build();
		return trigger;
	}
	
	private static CronScheduleBuilder cronSchedule(String desc, String cronExpression) {
		System.out.println(desc + "->(" + cronExpression + ")");
		return CronScheduleBuilder.cronSchedule(cronExpression);
	}
	
}