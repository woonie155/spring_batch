package jw.study.spring_batch.ex_jobLauncher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

//@RestController
public class JobLauncherController {

    @Autowired private Job job;
    @Autowired private JobLauncher jobLauncher;
    @Autowired private BasicBatchConfigurer basicBatchConfigurer;


    @PostMapping("/batch")
    public String launch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

//        jobLauncher.run(job, jobParameters); //동기식
        SimpleJobLauncher jobLauncher_asyn = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher_asyn.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher_asyn.run(job, jobParameters); //비동기식
        return "launch completed";
    }



}
