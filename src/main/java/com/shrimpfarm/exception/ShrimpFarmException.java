package com.shrimpfarm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShrimpFarmException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ShrimpFarmException(String error) {
		super(error);
	}
}
