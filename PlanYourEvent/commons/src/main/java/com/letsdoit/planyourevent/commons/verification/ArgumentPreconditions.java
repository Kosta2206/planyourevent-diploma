package com.letsdoit.planyourevent.commons.verification;

import org.apache.log4j.Logger;

import com.letsdoit.planyourevent.commons.exceptions.ServerException;

public class ArgumentPreconditions {

	public static <T> void checkNotNullArgument(T argument, String message, Logger logger) throws ServerException {
		if (argument == null) {
			throw new ServerException(message, logger);
		}
	}
	
}
