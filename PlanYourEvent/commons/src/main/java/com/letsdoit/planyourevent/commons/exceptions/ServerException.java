package com.letsdoit.planyourevent.commons.exceptions;

import org.apache.log4j.Logger;

public class ServerException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServerException(Throwable e, String message, Logger logger) {
		super(message, e);
		logger.error(message, e);
	}

	public ServerException(String message, Logger logger) {
		super(message);
		logger.error(message);
	}
}
