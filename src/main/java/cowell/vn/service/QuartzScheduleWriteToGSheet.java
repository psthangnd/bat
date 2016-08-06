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
			scheduler.scheduleJob(jobDetail, fireAt12pmEveryDay());
		} catch(SchedulerException se){
			se.printStackTrace();
		}
	}
	
	// Fire between 10AM and 3PM every day
	public static Trigger fireAt12pmEveryDay() {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("fireAt12pmEveryDay", GROUP_NAME)
				.withSchedule(cronSchedule("Fire at 12pm every day", "0 50 16 ? * *"))
				.build();
		return trigger;
	}
	
	private static CronScheduleBuilder cronSchedule(String desc, String cronExpression) {
		System.out.println(desc + "->(" + cronExpression + ")");
		return CronScheduleBuilder.cronSchedule(cronExpression);
	}
	
}