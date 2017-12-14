package sar.jobs;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import sar.App;

import java.util.Random;

public class ErrorJob implements org.quartz.Job{
    private final static Logger log = Logger.getLogger(App.class);

    private int ERROR_PERCENTAGE = App.PERCENTAGE;
    private Random random = new Random();

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        double num = random.nextInt(100);

        if(num <= ERROR_PERCENTAGE){
            try{
                int i = 1/0;
            } catch (Exception e){
                log.error("Dividing by zero.", e);
            }
        } else{
            log.info("Avoided error.");
        }
    }
}
