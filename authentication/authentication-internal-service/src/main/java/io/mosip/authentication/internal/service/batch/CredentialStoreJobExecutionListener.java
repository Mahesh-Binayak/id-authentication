package io.mosip.authentication.internal.service.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import io.mosip.authentication.core.exception.RetryingBeforeRetryIntervalException;
import io.mosip.authentication.core.logger.IdaLogger;
import io.mosip.kernel.core.logger.spi.Logger;

/**
 * The listener interface for receiving credentialStoreJobExecution events.
 * The class that is interested in processing a credentialStoreJobExecution
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCredentialStoreJobExecutionListener<code> method. When
 * the credentialStoreJobExecution event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CredentialStoreJobExecutionEvent
 */
@Component
public class CredentialStoreJobExecutionListener implements JobExecutionListener {
	
	/** The Constant logger. */
	private static final Logger logger = IdaLogger.getLogger(CredentialStoreJobExecutionListener.class);


	/**
	 * Before job.
	 *
	 * @param jobExecution the job execution
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
	}

	/**
	 * After job.
	 *
	 * @param jobExecution the job execution
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(!jobExecution.getStepExecutions().isEmpty() && 
				jobExecution.getStepExecutions().iterator().next().getReadCount() > 0
				//Skip logging for exception for retrying before retry interval
				&& !jobExecution.toString().contains(RetryingBeforeRetryIntervalException.class.getSimpleName())) {
			logger.error("CredentialStoreJobExecutionListener", "afterJob", "after job execution", String.valueOf(jobExecution));
		}
	}

}
