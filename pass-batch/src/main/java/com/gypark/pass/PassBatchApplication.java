package com.gypark.pass;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class PassBatchApplication {
	private final int CHUNK_SIZE = 5; // 임시

	@Bean
	public Job passJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
		return new JobBuilder("passJob", jobRepository)
				.start(passStep(jobRepository, transactionManager))
				.build();
	}

	@Bean
	public Step passStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
		return new StepBuilder("passStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("executed");
					return RepeatStatus.FINISHED;
				}, transactionManager).build();
	}


	public static void main(String[] args) {
		SpringApplication.run(PassBatchApplication.class, args);
	}

}
