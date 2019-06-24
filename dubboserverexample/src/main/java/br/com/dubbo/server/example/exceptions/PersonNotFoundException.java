package br.com.dubbo.server.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7747728608217773232L;

	public PersonNotFoundException() {
		super("Person not found");
	}

}