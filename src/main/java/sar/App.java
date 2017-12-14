package sar;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import sar.jobs.ErrorJob;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Hello world!
 */
public class App 
{
    public static int PERCENTAGE = 50;
    private final static Logger log = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
        if (!System.getProperty("logfile.name").isEmpty())
        {
            log.info("SET");
            //Do something
        }
        if(args.length > 0){
            try{
                PERCENTAGE = Integer.parseInt(args[0]);
            } catch (Exception e){
                log.error("Wrong argument percentage, must be Integer.");
            }
        }
        if(PERCENTAGE < 0 || PERCENTAGE > 100){
            log.fatal("Dumb ass cant set percentage, haHAA.");
            PERCENTAGE = 50;
        }
        log.info("Setting fail percentage to " + PERCENTAGE + "%");
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            JobDetail job = newJob(ErrorJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }

    }
}
