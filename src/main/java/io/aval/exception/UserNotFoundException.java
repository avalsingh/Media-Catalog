package io.aval.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="User Not Found")
public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 5968000547444142953L;
}
