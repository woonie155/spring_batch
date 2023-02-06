package jw.study.spring_batch.ex_executionContext;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class JobRunner implements ApplicationRunner {

    @Autowired private JobLauncher jobLauncher;
    @Autowired private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("seq", 2L)
//                .addDate("date", new Date())
                .addDouble("age", 16.5)
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}