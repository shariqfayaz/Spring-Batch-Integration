package com.SpringBatchIntegration.SpringIntegrationConfig;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
@Component
public class FileMessageToJobRequest {
    private Job job;
    public static String file_path;

    private String fileParameterName = "input.file.name";

    public void setFileParameterName(String fileParameterName) {
        this.fileParameterName = fileParameterName;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Transformer
    public JobLaunchRequest toRequest(Message<File> message) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        jobParametersBuilder.addString(fileParameterName, message.getPayload().getAbsolutePath());
        // We add a dummy value to make job params unique, or else spring batch
        // will only run it the first time
        file_path =message.getPayload().getName();
        jobParametersBuilder.addDate("dummy", new Date());
        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
