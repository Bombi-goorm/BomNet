package com.bombi.core.common.exception;

public class EntityNotFoundByPropertyException extends RuntimeException {

	public EntityNotFoundByPropertyException(String entity, String propertyName, String propertyValue) {
		super("Entity " + entity + " not found by " + propertyName + " - " + propertyValue);
	}
}
