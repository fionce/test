package models;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class TestsErrosLogger {
	
	private Logger logger;

	public <T> TestsErrosLogger(Class<T> clazz) {
		DOMConfigurator.configure("log4jErrors.xml");
		logger = Logger.getLogger(clazz);
	}

	public void logInfo(String message) {
		logger.info(String.format("Thread %s - %s", Thread.currentThread().getId(), message));
	}

	public void logDebug(String message) {
		logger.debug(String.format("Thread %s - %s", Thread.currentThread().getId(), message));
	}

	public void logError(String message) {
		logger.error(String.format("Thread %s - %s", Thread.currentThread().getId(), message));
	}

	public void logWarning(String message) {
		logger.warn(String.format("Thread %s - %s", Thread.currentThread().getId(), message));
	}

}
