package pl.nes.detector.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExitListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        ExitStatus exitStatus = jobExecution.getExitStatus();

        if (exitStatus.equals(ExitStatus.COMPLETED)) {
            System.exit(0);
        }

        System.exit(-1);
    }
}